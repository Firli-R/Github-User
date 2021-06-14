package com.example.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.consumerapp.databinding.SearchUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
     var list = ArrayList<userItems>()

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(users:ArrayList<userItems>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    fun clearData(){
        list.clear()
        notifyDataSetChanged()
    }
    inner class UserViewHolder(private val binding: SearchUserBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(user: userItems) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.ava_url)
                    .placeholder(R.drawable.icon)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .fitCenter()
                    .into(ivUser)
                tvName.text = user.username

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = SearchUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  UserViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }
    
    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: userItems)
    }
}