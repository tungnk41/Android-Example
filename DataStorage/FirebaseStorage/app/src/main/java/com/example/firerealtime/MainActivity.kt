package com.example.firerealtime

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.firerealtime.databinding.ActivityMainBinding
import com.example.firerealtime.firebase.cloudStorage.CloudStorageManager
import com.example.firerealtime.firebase.firestoreDatabase.FirestoreDatabaseManager
import com.example.firerealtime.firebase.realtimeDatabase.RealtimeDatabaseManager
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile


private const val FILE_REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val realtimeDatabaseManager by lazy { RealtimeDatabaseManager() }
    private val fireStoreDatabaseManager by lazy {FirestoreDatabaseManager()}
    private val cloudStorageManager by lazy{ CloudStorageManager() }
    private var lastestPost : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener {
            onRealtimeCreateClicked()
            //onFireStoreCreateClicked()
            //requestFilePicker()
        }

        binding.btnDelete.setOnClickListener {
              onRealtimeDeleteClicked()
            //onFireStoreDeleteClicked()
        }

        binding.btnUpdate.setOnClickListener {
              onRealtimeUpdateClicked()
            //onFireStoreUpdateClicked()
        }

    }

    override fun onStart() {
        super.onStart()
        realtimeDatabaseManager.onPostValueChanged().observe(this, Observer {
            if(!it.isEmpty()){
                val post = it.get(0)
                Log.d("TAG", "onPostValueChanged: " + it.size + " " + post.content)
                binding.tvPost.setText(post.content)
            }
        })

        fireStoreDatabaseManager.onPostValueChanged().observe(this, Observer {
            if(!it.isEmpty()){
                val post = it.get(0)
                Log.d("TAG", "onPostValueChanged: " + it.size + " " + post.content)
                binding.tvPost.setText(post.content)
            }
        })
    }

    fun onRealtimeCreateClicked(){
        val content = binding.tvPost.text.toString()
        val id = realtimeDatabaseManager.createPost(content,
                { Log.d("TAG", "onSuccessAction ")},
                {Log.d("TAG", "onFailureAction ")})

        lastestPost = id
    }

    fun onRealtimeDeleteClicked(){
        if(!lastestPost.isNullOrEmpty()){
            realtimeDatabaseManager.deletePost(lastestPost)
        }
    }

    fun onRealtimeUpdateClicked(){
        if(!lastestPost.isNullOrEmpty()){
            realtimeDatabaseManager.updatePostContent(lastestPost,"lastest update")
        }
    }

    /**********************************************************************************************/
    fun onFireStoreCreateClicked(){
        val content = binding.tvPost.text.toString()
        lastestPost = fireStoreDatabaseManager.createPost(content,
                    {Log.d("TAG", "onSuccessAction ")},
                    {Log.d("TAG", "onFailureAction ")})
    }

    fun onFireStoreDeleteClicked(){
        if(!lastestPost.isNullOrEmpty()){
            fireStoreDatabaseManager.deletePost(lastestPost,
                { Log.d("TAG", "onSuccessAction ")},
                {Log.d("TAG", "onFailureAction ")})
        }
    }

    fun onFireStoreUpdateClicked(){
        if(!lastestPost.isNullOrEmpty()){
            fireStoreDatabaseManager.updatePost(lastestPost,"lastest update",
                { Log.d("TAG", "onSuccessAction ")},
                {Log.d("TAG", "onFailureAction ")})
        }
    }

    /***********************************************************************************************/
    fun requestFilePicker(){
        val intent = Intent(this, FilePickerActivity::class.java)
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowFiles(true)
                .setShowVideos(true)
//                .setShowAudios(true)
                .enableImageCapture(true)
                .setMaxSelection(10)
                .setSkipZeroSizeFiles(true)
                .build()
        )
        startActivityForResult(intent, FILE_REQUEST_CODE)
    }

    /***********************************************************************************************/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            var mediaFile : ArrayList<MediaFile> = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)!!
            if(mediaFile.size > 0){
                Log.d("TAG", "onActivityResult: " + mediaFile.size)
                cloudStorageManager.uploadFile(mediaFile.get(0).path,{})
            }
        }
    }
}