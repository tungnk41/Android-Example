package com.example.sharedatatoapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.senddatatoapp.MainViewModel
import com.facebook.share.model.ShareMediaContent
import com.facebook.share.model.SharePhoto
import com.facebook.share.widget.ShareDialog


const val PACKAGE_FACEBOOK = "com.facebook.katana"
const val PACKAGE_TELEGRAM = "org.telegram.messenger"
const val PACKAGE_TWITTER = "com.twitter.android"
const val PACKAGE_INSTAGRAM = "com.instagram.android"

class MainActivity : AppCompatActivity() {
    private lateinit var btnShareFacebook: Button
    private lateinit var btnShareInstagram: Button
    private lateinit var btnShareTwitter: Button
    private lateinit var btnShareTelegram: Button
    private lateinit var btnDownloadImage: Button
    private lateinit var shareDialog: ShareDialog
    private lateinit var imgScreenshot: ImageView


    private lateinit var uriBitmapPath: Uri

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDownloadImage = findViewById(R.id.btnDownloadImage)
        btnShareFacebook = findViewById(R.id.btnShareFacebook)
        btnShareInstagram = findViewById(R.id.btnShareInstagram)
        btnShareTwitter = findViewById(R.id.btnShareTwitter)
        btnShareTelegram = findViewById(R.id.btnShareTelegram)
        imgScreenshot = findViewById(R.id.imgScreenshot)
        shareDialog = ShareDialog(this)

        btnDownloadImage.setOnClickListener {
            downloadImage()
        }
        btnShareFacebook.setOnClickListener {
            if(!isPackageInstalled(PACKAGE_FACEBOOK,packageManager)){
                Toast.makeText(this,"App is not installed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            shareFacebook()
        }
        btnShareInstagram.setOnClickListener {
//            if(!isPackageInstalled(PACKAGE_INSTAGRAM,packageManager)){
//                Toast.makeText(this,"App is not installed", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            shareInstagram()
        }
        btnShareTwitter.setOnClickListener {
//            if(!isPackageInstalled(PACKAGE_TWITTER,packageManager)){
//                Toast.makeText(this,"App is not installed", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            shareTwitter()
        }
        btnShareTelegram.setOnClickListener {
//            if(!isPackageInstalled(PACKAGE_TELEGRAM,packageManager)){
//                Toast.makeText(this,"App is not installed", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            shareTelegram()
        }
    }


    private fun downloadImage() {
        val bitmap = takeScreenshotForScreen(this)
        bitmap?.let {
            viewModel.saveImage(it)
        }
    }

    private fun shareFacebook() {
//            ShareLink
//         val shareLinkContent: ShareLinkContent = ShareLinkContent.Builder()
//            .setContentUrl(Uri.parse("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/art.jpg"))
//            .build()
//            ShareDialog.show(this, shareLinkContent)

        //Share image using native app
            val sharePhoto = SharePhoto.Builder()
                .setBitmap(takeScreenshotForScreen(this))
                .build()
            val shareContent = ShareMediaContent.Builder()
                .addMedium(sharePhoto)
                .build()
            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC)

        imgScreenshot.setImageBitmap(takeScreenshotForScreen(this))

    }

    private fun shareInstagram() {
        val bitmapUri = viewModel.imageUri
        bitmapUri?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_STREAM, it)
            intent.putExtra(Intent.EXTRA_TEXT, "Test ABC")
            startActivity(Intent.createChooser(intent,"Share Instagram"))
        }
    }

    private fun shareTwitter() {
        val bitmapUri = viewModel.imageUri
        bitmapUri?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_STREAM, it)
            intent.putExtra(Intent.EXTRA_TEXT, "Test ABC")
            val packageManager = packageManager
            val activities = packageManager.queryIntentActivities(intent, 0)
            val isIntentSafe = activities.size > 0

            if (isIntentSafe) {
                startActivity(Intent.createChooser(intent,"Share Twitter"))
            }
        }
    }

    private fun shareTelegram() {
        val bitmapUri = viewModel.imageUri
        bitmapUri?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_STREAM, it)
            intent.putExtra(Intent.EXTRA_TEXT, "Test ABC")
            startActivity(Intent.createChooser(intent,"Share Telegram"))
        }
    }

    private fun takeScreenshotForView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun takeScreenshotForScreen(activity: Activity): Bitmap? {
        return takeScreenshotForView(activity.window.decorView.rootView)
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        try {
            packageManager.getPackageInfo(packageName, 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }


}