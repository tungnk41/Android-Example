package com.example.simplenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simplenotes.databinding.ActivityMainBinding
import com.example.simplenotes.model.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val repository : NoteRepository by lazy {
       ExternalFileRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRead.setOnClickListener {
            binding.tvNote.setText("")
            val note : Note = repository.getNote(binding.tvFileName.text.toString())
            if(note.noteText.isEmpty()){
                showMessage("Empty data")
            }else{
                binding.tvNote.setText(note.noteText)
            }
        }

        binding.btnWrite.setOnClickListener {
            val result = repository.addNote(Note(binding.tvFileName.text.toString(),binding.tvNote.text.toString()))
            if (result) showMessage("Write data success") else  showMessage("Write data fail")
        }

        binding.btnDelete.setOnClickListener {
            binding.tvNote.setText("")
            val result = repository.deleteNote(binding.tvFileName.text.toString())
            if (result) showMessage("Delete data success") else  showMessage("Delete data fail")
        }
    }


    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}