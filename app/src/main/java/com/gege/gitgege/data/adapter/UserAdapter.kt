package com.gege.gitgege.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gege.gitgege.R
import com.gege.gitgege.data.ModelUsers
import com.squareup.picasso.Picasso

class UserAdapter(
    private var userList: List<ModelUsers>,
    private val itemClickListener: (ModelUsers) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUserAvatar: ImageView = itemView.findViewById(R.id.img_user_avatar)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)

        fun bind(user: ModelUsers) {
            tvName.text = user.login
            Picasso.get().load(user.avatarURL).into(imgUserAvatar)
            itemView.setOnClickListener { itemClickListener(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateData(newUserList: List<ModelUsers>) {
        userList = newUserList
        notifyDataSetChanged()
    }
}
