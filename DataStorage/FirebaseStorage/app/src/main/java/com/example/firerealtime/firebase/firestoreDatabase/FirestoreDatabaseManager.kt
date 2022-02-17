package com.example.firerealtime.firebase.firestoreDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firerealtime.model.Post
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

private const val POSTS_COLLECTION = "posts"

class FirestoreDatabaseManager {
    private val database = FirebaseFirestore.getInstance()
    private lateinit var eventListener: EventListener<QuerySnapshot>

    fun createPost(content: String, onSuccessAction : () -> Unit, onFailureAction : () -> Unit) : String{
        val document = database.collection(POSTS_COLLECTION).document()

        document
            .set(Post(document.id,content,"user_id_1",getCurrentTime()))
            .addOnSuccessListener { onSuccessAction }
            .addOnFailureListener { onFailureAction }

        return document.id
    }

    fun deletePost(id: String,onSuccessAction: () -> Unit, onFailureAction: () -> Unit){
        database.collection(POSTS_COLLECTION).document(id)
            .delete()
            .addOnFailureListener { onFailureAction }
            .addOnSuccessListener { onSuccessAction }
    }

    fun updatePost(id: String,content : String, onSuccessAction: () -> Unit, onFailureAction: () -> Unit){
        val post = Post(id,content,"user_id_updated",getCurrentTime())
        database.collection(POSTS_COLLECTION).document(id)
            .set(post)
            .addOnSuccessListener { onSuccessAction }
            .addOnFailureListener { onFailureAction }
    }

    fun onPostValueChanged() : LiveData<List<Post>>{
        val postsValues = MutableLiveData<List<Post>>()

        eventListener = object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null || value == null){

                }else{
                    if(value.isEmpty){
                        postsValues.postValue(emptyList())
                    }else{
                        val posts = ArrayList<Post>()
                        for(doc in value){
                            val post = doc.toObject(Post::class.java)
                            posts.add(post)
                        }
                        postsValues.postValue(posts)
                    }
                }
            }

        }

        database.collection(POSTS_COLLECTION)
            .addSnapshotListener(eventListener)

        return postsValues
    }



    private fun getCurrentTime() = System.currentTimeMillis()
}