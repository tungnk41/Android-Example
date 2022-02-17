package com.example.component.ViewModel

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import com.example.component.Network.RetrofitClient
import com.example.component.Network.ServiceApi
import com.example.component.model.ObjectDataModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import android.provider.MediaStore
import java.io.*


class MainViewModel: ViewModel(){

    fun upload(imageUri: Uri, data : ObjectDataModel){
        val file: File = File(imageUri.path)
        Log.d("TAG", "upload: " + file.absolutePath)

        RetrofitClient.INSTANCE().create(ServiceApi::class.java)
            .upload(
                data.name.toRequestBody("text/plain".toMediaType()),
                data.id.toString().toRequestBody("text/plain".toMediaType()),
                MultipartBody.Part.createFormData("image",file.name,file.asRequestBody("image/*".toMediaType()))
            )
    }

    fun getImageFromUri(context: Context, fileUri: String) : Bitmap{

        val file = File(context.cacheDir, "image.png")
        val inStream: InputStream? = context.contentResolver.openInputStream(Uri.parse(fileUri))
        val outStream: OutputStream = FileOutputStream(File(file.path))
        val buf = ByteArray(1024)
        var len: Int
        inStream?.let {
            while (inStream.read(buf).also { len = it } > 0) {
                outStream.write(buf, 0, len)
            }
        }
        outStream.close()
        inStream?.close()
        val bitmap = BitmapFactory.decodeFile(file.path)
        return bitmap
    }

    fun getImageFileFromBitmap(context: Context, bitmap: Bitmap?) : MultipartBody.Part?{
        val file = File(context.cacheDir, "image.png")
        var bitmapOutStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG,100,bitmapOutStream)

        val fileOutStream = FileOutputStream(file)
        fileOutStream.write(bitmapOutStream.toByteArray())
        fileOutStream.flush()
        fileOutStream.close()

        return MultipartBody.Part.createFormData("avatar",file.name,file.asRequestBody("image/*".toMediaType()))
    }




}