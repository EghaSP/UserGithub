package com.example.usergithub.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.usergithub.CustomOnItemClickListener
import com.example.usergithub.R
import com.example.usergithub.models.Favourite
import com.example.usergithub.view.DetailActivity
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*

class FavouriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavouriteAdapter.NoteViewHolder>() {
    var listNotes = ArrayList<Favourite>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = this.listNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: Favourite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(fav.avatar)
                    .apply(RequestOptions().override(250, 250))
                    .into(itemView.avatar)
                username.text = fav.username
                user_name.text = fav.name
                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                                intent.putExtra(DetailActivity.EXTRA_NOTE, fav)
                                activity.startActivity(intent)
                            }
                        }
                    )
                )
            }
        }
    }
}