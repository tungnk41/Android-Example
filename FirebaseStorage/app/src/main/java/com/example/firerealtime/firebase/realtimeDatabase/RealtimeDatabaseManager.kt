package com.example.firerealtime.firebase.realtimeDatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firerealtime.model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val POSTS_REFERENCE = "posts"
private const val POSTS_CONTENT_REFERENCE = "content"

class RealtimeDatabaseManager {
    private val database = FirebaseDatabase.getInstance()

    private lateinit var valueEventListener: ValueEventListener

    fun createPost(content : String, onSuccessAction: () -> Unit, onFailureAction: () -> Unit) : String{
        val id = database.getReference(POSTS_REFERENCE).push().key ?: ""
        val post = Post(id,content, "user_id_1", getCurrentTime())

        database.getReference(POSTS_REFERENCE)
            .child(id) //Create Post with name is key, this post is child from root
            .setValue(post)
            .addOnSuccessListener { onSuccessAction }
            .addOnFailureListener { onFailureAction }

        return id
    }

    fun deletePost(id: String){
        database.getReference(POSTS_REFERENCE)
            .child(id)
            .removeValue()
            .addOnFailureListener {  }
            .addOnSuccessListener {  }

    }

    fun updatePostContent(id : String, content : String){
        database.getReference(POSTS_REFERENCE)
            .child(id)
            .child(POSTS_CONTENT_REFERENCE)
            .setValue(content)
    }

    fun onPostValueChanged() : LiveData<List<Post>> {
        val postsValues = MutableLiveData<List<Post>>()

         valueEventListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val posts = mutableListOf<Post>()
                    for(child in snapshot.children){ //Get list child from root, convert child to Post entity
                        child.getValue(Post::class.java)?.let { posts.add(it) }
                    }
                    postsValues.postValue(posts)
                }else{
                    postsValues.postValue(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        database.getReference(POSTS_REFERENCE)
            .addValueEventListener(valueEventListener)

        return postsValues
    }

    fun removePostsValueChangeListener(){
        database.getReference(POSTS_REFERENCE).removeEventListener(valueEventListener)
    }

    private fun getCurrentTime() = System.currentTimeMillis()

}