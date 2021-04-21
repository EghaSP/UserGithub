package com.example.usergithub.view

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.example.usergithub.R
import com.example.usergithub.db.DatabaseUsers.FavColumns.Companion.CONTENT_URI
import com.example.usergithub.helper.MappingHelper
import com.example.usergithub.models.Favourite
import com.example.usergithub.viewmodel.FavouriteAdapter
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavouriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setActionBarTitle()

        recycleViewFav.layoutManager = LinearLayoutManager(this)
        recycleViewFav.setHasFixedSize(true)
        adapter = FavouriteAdapter(this)
        recycleViewFav.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favourite>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title = "Favourite Users"
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFav.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = deferredNotes.await()
            progressBarFav.visibility = View.INVISIBLE
            if (favData.size > 0) {
                adapter.listNotes = favData
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    private fun showSnackbarMessage() {
        Snackbar.make(recycleViewFav, "There is No Data", Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadNotesAsync()
    }
}
