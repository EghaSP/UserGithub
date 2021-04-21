package com.example.consumerapp.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import com.example.consumerapp.models.Users
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_list.view.*

var followingFilterList = ArrayList<Users>()

class FollowingAdapter(listUser: ArrayList<Users>) : RecyclerView.Adapter<FollowingAdapter.ListDataHolder>() {

    init {
        followingFilterList = listUser
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgphoto: CircleImageView = itemView.avatar
        var name: TextView = itemView.user_name
        var username: TextView = itemView.username
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(Users: Users)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListDataHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)
        val search = ListDataHolder(view)
        mcontext = viewGroup.context
        return search
    }

    override fun getItemCount(): Int {
        return followingFilterList.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val data = followingFilterList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgphoto)
        holder.name.text = data.name
        holder.itemView.setOnClickListener {

        }
    }
}