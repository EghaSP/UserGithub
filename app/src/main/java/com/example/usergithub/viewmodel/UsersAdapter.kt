package com.example.usergithub.viewmodel

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.usergithub.R
import com.example.usergithub.models.Users
import com.example.usergithub.view.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

var userFilterList = ArrayList<Users>()

class UsersAdapter(private var listUser: ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.ListDataHolder>(), Filterable {

    init {
        userFilterList = listUser
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
        fun onItemClicked(users: Users)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListDataHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)
        val sch = ListDataHolder(view)
        mcontext = viewGroup.context
        return sch
    }

    override fun getItemCount(): Int {
        return userFilterList.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val data = userFilterList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgphoto)
        holder.name.text = data.name
        holder.username.text = data.username

        holder.itemView.setOnClickListener {
            val dataUser = Users(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following,
                data.isFav
            )
            val intentDetail = Intent(mcontext, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, dataUser)
            intentDetail.putExtra(DetailActivity.EXTRA_FAV, dataUser)
            mcontext.startActivity(intentDetail)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                userFilterList = if (charSearch.isEmpty()) {
                    listUser
                } else {
                    val resultList = ArrayList<Users>()
                    for (row in userFilterList) {
                        if ((row.username.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)))
                        ) {
                            resultList.add(
                                Users(
                                    row.username,
                                    row.name,
                                    row.avatar,
                                    row.company,
                                    row.location,
                                    row.repository,
                                    row.followers,
                                    row.following
                                )
                            )
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = userFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                userFilterList = results.values as ArrayList<Users>
                notifyDataSetChanged()
            }
        }
    }
}