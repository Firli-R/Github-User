package com.example.githab_user.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githab_user.R
import com.example.githab_user.data.userItems
import com.example.githab_user.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var favModel :FavoriteViewModel
    private val TAG = "favorite"

    companion object {
        private const val TITLE = "Favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        supportActionBar?.title = TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        favModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel::class.java)
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        
        showLoading(true)
        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = userAdapter
        }
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: userItems) {
                val moveIntent = Intent(this@FavoriteActivity,DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USERNAME,data)
                startActivity(moveIntent)
            }
        })
        
        favModel.setFav(applicationContext)
        favModel.getFav().observe(this,{
            if (it.isNotEmpty()){
                userAdapter.clearData()
                userAdapter.setData(it)
                showLoading(false)
            }
            else{
                userAdapter.clearData()
                showLoading(false)
                addErrorTxt(getString(R.string.error))
            }
        })
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
    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }
}