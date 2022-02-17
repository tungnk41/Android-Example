package com.example.sqldelight.view.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.sqldelight.App
import com.example.sqldelight.dao.INoteDAO
import com.example.sqldelight.dao.impl.NoteDAO
import database.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    private val noteDao : INoteDAO = NoteDAO(App.database)
    var result: MutableLiveData<List<Note>?> = MutableLiveData()

    fun insertNote(title: String, content: String, user_id: Long){
        viewModelScope.launch(Dispatchers.IO){
            noteDao.insert(title, content, user_id)
        }
    }

    fun deleteAll(user_id: Long){
        viewModelScope.launch(Dispatchers.IO){
            noteDao.delete(user_id)
        }
    }

    fun findAllNote(user_id: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Log.d("TAG", "findAllNote: " + noteDao.findAll(user_id).toString())
                result.postValue(noteDao.findAll(user_id))
            }
        }
    }
}