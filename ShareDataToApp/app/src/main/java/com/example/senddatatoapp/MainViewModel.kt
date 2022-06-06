package com.example.senddatatoapp

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext = getApplication<Application>().applicationContext

    var imageUri : Uri? = null

    fun saveImage(bitmap: Bitmap, onCompleted: ((uri: Uri?) -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) saveImageInQ(bitmap)
            else saveTheImageLegacyStyle(bitmap)
            onCompleted?.invoke(imageUri)
        }
    }

    @WorkerThread
    private fun saveImageInQ(bitmap: Bitmap): Uri? {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }
        //use application context to get contentResolver
        val contentResolver = applicationContext.contentResolver
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { contentResolver.openOutputStream(it) }
        fos?.let { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }
        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        imageUri?.let {
            contentResolver.update(it, contentValues, null, null)
        }
        return imageUri
    }

    @WorkerThread
    private fun saveTheImageLegacyStyle(bitmap: Bitmap): Uri {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, filename)
        fos = FileOutputStream(imageFile)
        fos.let {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
            it.close()
        }
        MediaScannerConnection.scanFile(applicationContext, arrayOf(imageFile.absolutePath), null, null)
        return FileProvider.getUriForFile(applicationContext, "${applicationContext.packageName}.provider", imageFile)
    }
}