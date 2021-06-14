package com.example.consumerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter :UserAdapter
    private lateinit var favModel:FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel::class.java)
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        showLoading(true)
        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = userAdapter
        }
//        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
//            override fun onItemClicked(data: userItems) {
////                Toast.makeText(this@MainActivity,"coba",Toast.LENGTH_SHORT).show()
//                val moveIntent = Intent(this@FavoriteActivity,DetailActivity::class.java)
//                moveIntent.putExtra(DetailActivity.EXTRA_USERNAME,data)
//                startActivity(moveIntent)
//            }
//        })

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
    }
