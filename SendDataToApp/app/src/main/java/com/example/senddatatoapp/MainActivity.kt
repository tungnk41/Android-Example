package com.example.senddatatoapp

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessTokenManager
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog


class MainActivity : AppCompatActivity() {
    private lateinit var btnShare: Button
    private lateinit var shareDialog: ShareDialog
    private lateinit var imgScreenshot: ImageView
    val shareLinkContent: ShareLinkContent = ShareLinkContent.Builder()
        .setContentUrl(Uri.parse("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/art.jpg"))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShare = findViewById(R.id.btnShare)
        imgScreenshot = findViewById(R.id.imgScreenshot)
        shareDialog = ShareDialog(this)
        btnShare.setOnClickListener {

            Log.d("TAG", "setOnClickListener: " + AccessTokenManager.getInstance().currentAccessToken)
//            ShareLink
//            ShareDialog.show(this, shareLinkContent)

            //Share image using native app
//            val sharePhoto = SharePhoto.Builder()
//                .setBitmap(defaultBitmap())
//                .build()
//            val shareContent = ShareMediaContent.Builder()
//                .addMedium(sharePhoto)
//                .build()
//            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC)


//            val sharePhoto = SharePhoto.Builder()
//                .setBitmap(takeScreenShot())
//                .build()
//            val sharePhotoContent = SharePhotoContent.Builder()
//                .setPhotos(listOf(sharePhoto))
//                .build()
//            ShareApi.share(sharePhotoContent, null)

            imgScreenshot.setImageBitmap(takeScreenshotForScreen(this))


        }

    }

    private fun defaultBitmap(): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.android_mirror)
        return bitmap
    }

    private fun takeScreenShot(): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.android_mirror)
        return bitmap
    }

    fun takeScreenshotForView(view: View): Bitmap? {

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun takeScreenshotForScreen(activity: Activity): Bitmap? {
        return takeScreenshotForView(activity.window.decorView.rootView)
    }
}