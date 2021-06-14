package com.example.githab_user.Follow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githab_user.Activity.DetailActivity
import com.example.githab_user.Activity.UserAdapter
import com.example.githab_user.R
import com.example.githab_user.data.userItems
import com.example.githab_user.databinding.FragmentFollowersBinding

class FollowersFragment() : Fragment() {
    companion object{
        const val EXTRA_FOLLOWERS ="extra_following"
    }
    // TODO: Rename and change types of parameters

    private var Username: String? = null
    private lateinit var followersViewModel: FollowersModel
    private lateinit var binding :FragmentFollowersBinding
    private lateinit var userAdapter: UserAdapter
    val TAG = "followersfragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater,container,false)
        userAdapter = UserAdapter()
        showLoading(true)
        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = userAdapter
        }
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: userItems) {
                val moveIntent = Intent(this@FollowersFragment.context,DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USERNAME,data)
                startActivity(moveIntent)
            }
        })

        arguments?.let {
            Username = it.getString(EXTRA_FOLLOWERS) }
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersModel::class.java)
        Username?.let { followersViewModel.setFollowers(it) }
        Log.d(TAG, "onCreateView: $Username")


        followersViewModel.getFollowers().observe(viewLifecycleOwner,{
            if (it.isNotEmpty()){
                userAdapter.clearData()
                userAdapter.setData(it)
                showLoading(false)
            }
            else{
                userAdapter.clearData()
                showLoading(false)
                addErrorTxt(getString(R.string.Bio_zero))
            }
        })

        return binding.root
    }
    private fun showLoading(state:Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
        
    }
    private fun addErrorTxt(message:String){
        binding.txtError.visibility = View.VISIBLE
        binding.txtError.text = message
    }

}