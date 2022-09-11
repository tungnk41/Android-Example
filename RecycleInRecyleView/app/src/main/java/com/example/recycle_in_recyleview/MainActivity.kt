package com.example.recycle_in_recyleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.recycle_in_recyleview.databinding.ActivityMainBinding
import com.example.recycle_in_recyleview.model.ChildData
import com.example.recycle_in_recyleview.model.ParentData

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var dummyParentData : List<ParentData>

    private lateinit var mAdapter: ParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        mAdapter = ParentAdapter(context = this, onItemClickListener = { first, second ->
            Log.d("TAG", "onItemClickListener: " + first + " " + second)
        })
        binding.rvParent.adapter = mAdapter
        mAdapter.submitList(dummyParentData)

    }


    fun initData() {
        dummyParentData = listOf(
            ParentData(
                title = "1",
                listOf(
                ChildData("q"),
                ChildData("w"),
                ChildData("e"),
                )
            ),
            ParentData(
                title = "2",
                listOf(
                    ChildData("d"),
                    ChildData("e"),
                    ChildData("f"),
                )
            ),
            ParentData(
                title = "3",
                listOf(
                    ChildData("g"),
                    ChildData("h"),
                    ChildData("j"),
                )
            ),
            ParentData(
                title = "4",
                listOf(
                    ChildData("z"),
                    ChildData("x"),
                    ChildData("c"),
                )
            )
        )
    }
}