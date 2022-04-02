package com.example.custombottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var btnShowHidePersistent: Button
    private lateinit var btnShowHideModal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomSheet()

        btnShowHidePersistent = findViewById(R.id.btnShowHidePersistent)
        btnShowHideModal = findViewById(R.id.btnShowHideModal)
        btnShowHidePersistent.setOnClickListener {
            val state = if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                BottomSheetBehavior.STATE_EXPANDED
            }
            else {
                BottomSheetBehavior.STATE_COLLAPSED
            }
            bottomSheetBehavior.state = state
        }
        btnShowHideModal.setOnClickListener {
            BottomSheetModalFragment().apply {
                show(supportFragmentManager,tag)
            }
        }



    }

    private fun initBottomSheet(){
        val bottomSheetPersistent = findViewById<ConstraintLayout>(R.id.bottomSheet)
        val btnCloseBS = bottomSheetPersistent.findViewById<Button>(R.id.btnCloseBS)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetPersistent)
        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
        btnCloseBS.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }
}