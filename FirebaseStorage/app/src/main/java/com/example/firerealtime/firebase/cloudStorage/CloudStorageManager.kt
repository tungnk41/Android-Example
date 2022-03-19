package com.example.firerealtime.firebase.cloudStorage

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.io.File


private const val FOLDER_REFERENCE = "Files"

class CloudStorageManager {
    private val cloudStorage = FirebaseStorage.getInstance()

    fun uploadFile(fileUri : String, onSuccessAction: (String) -> Unit){
        val folderRef = cloudStorage.getReference(FOLDER_REFERENCE)

        var file = Uri.fromFile(File(fileUri))
        val fileRef = folderRef.child("${file.lastPathSegment}")

        fileRef.putFile(file)
            .addOnSuccessListener { onSuccessAction }
            .addOnFailureListener {
                Log.d("TAG", "uploadFile: Failure")
            }
    }
}