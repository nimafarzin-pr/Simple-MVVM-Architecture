package com.example.mvvm

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.Adapter.RecyclerAdapter
import com.example.mvvm.models.NicePlace
import com.example.mvvm.viewmodels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    private lateinit var mFab: FloatingActionButton
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerAdapter
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mMainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFab = findViewById(R.id.fab)
        mRecyclerView = findViewById(R.id.recycler_view)
        mProgressBar = findViewById(R.id.progress_bar)
        mMainActivityViewModel = ViewModelProviders.of(this).get(
            MainActivityViewModel::class.java
        )
        mMainActivityViewModel.init()

        //this part is advantage of view model this part  Responsible for maintaining changes and data status
        //when user out and again back to app situation is save
        mMainActivityViewModel.nicePlaces?.observe(this, Observer<List<NicePlace>> { mAdapter.notifyDataSetChanged() })

        mMainActivityViewModel.isUpdating.observe(this, Observer<Boolean> { aBoolean ->
                if (aBoolean) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                    mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.nicePlaces!!.value!!.size - 1)
                }
            })
        mFab.setOnClickListener {
            mMainActivityViewModel.addNewValue(
                NicePlace("https://res.cloudinary.com/https-www-isango-com/image/upload/f_auto/t_m_Prod/v7682/north%20america/u.s.a./new%20york%20city/2644.jpg", "Washington")
            )
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = RecyclerAdapter(this, mMainActivityViewModel.nicePlaces!!.value!!)
        val linearLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun showProgressBar() {
        mProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mProgressBar.visibility = View.GONE
    }
}