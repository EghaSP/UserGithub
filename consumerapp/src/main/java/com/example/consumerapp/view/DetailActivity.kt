package com.example.consumerapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.consumerapp.R
import com.example.consumerapp.models.Favourite
import com.example.consumerapp.viewmodel.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_list.username

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var imgphoto: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setDataObject()
        viewPagerConfig()
    }

    private fun viewPagerConfig() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataObject() {
        val favUser = intent.getParcelableExtra(EXTRA_NOTE) as Favourite
        setActionBarTitle("Detail of " + favUser.name.toString())
        name.text = favUser.name.toString()
        username.text = ("(" + favUser.username.toString() +")")
        company.text = favUser.company.toString()
        location.text = favUser.location.toString()
        repo.text = favUser.repository.toString()
        followerss.text = favUser.followers.toString()
        followings.text = favUser.following.toString()
        Glide.with(this)
            .load(favUser.avatar.toString())
            .into(avatars)
        imgphoto = favUser.avatar.toString()
    }

}
