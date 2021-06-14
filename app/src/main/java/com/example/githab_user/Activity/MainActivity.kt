package com.example.githab_user.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githab_user.R
import com.example.githab_user.Setting.SettingActivity
import com.example.githab_user.data.userItems
import com.example.githab_user.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        userAdapter =  UserAdapter()
        userAdapter.notifyDataSetChanged()
        
        showLoading(true)
        mainViewModel.setSearch("firli")

        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = userAdapter
        }
        binding.imgSearch.setOnClickListener {
            val Query = binding.edtTxt.text.toString()
            if (Query.isEmpty())return@setOnClickListener
            showLoading(true)
            userAdapter.clearData()
            binding.txtError.visibility = View.INVISIBLE
            mainViewModel.setSearch(Query)
        }
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: userItems) {
                val moveIntent = Intent(this@MainActivity,DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USERNAME,data)
                startActivity(moveIntent)
            }
        })

        mainViewModel.getSearch().observe(this,{
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       
        when(item.itemId){
            R.id.fav_menu ->{
                val intent = Intent(this,FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings ->{
                val intent = Intent(this,SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    }


