package com.example.githab_user.Activity

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githab_user.Database.UserContract
import com.example.githab_user.Database.UserHelper
import com.example.githab_user.Follow.SectionsPagerAdapter
import com.example.githab_user.R
import com.example.githab_user.data.userItems
import com.example.githab_user.databinding.ActivityDetailBinding
import com.example.githab_user.provider.MappingHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userHelper: UserHelper
    
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab1,
                R.string.tab2
        )
    }
    
    val TAG = "Detail"
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        val user = intent.getParcelableExtra<userItems>(EXTRA_USERNAME) as userItems
        val id = user.id.toString()
        
        userHelper = UserHelper.getInstance(applicationContext)
        
        val checkFavorite = checkFav(id)
        var statusFavorite: Boolean
        
        if (checkFavorite) {
            statusFavorite = true
            binding.btnFav.setImageResource(R.drawable.ic_favorite)
        } else {
            statusFavorite = false
            binding.btnFav.setImageResource(R.drawable.ic_favorite_border)
        }
        
        binding.apply {
            btnFav.setOnClickListener {
                statusFavorite = !statusFavorite
                if (statusFavorite) {
                    binding.btnFav.setImageResource(R.drawable.ic_favorite)
                    getFav(user)
                } else {
                    binding.btnFav.setImageResource(R.drawable.ic_favorite_border)
                    deleteFav(id)
                }
                
            }
        }
        
        Log.d(TAG, "onCreate: $user")
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        user.username?.let { detailViewModel.setDetail(it) }
        
        detailViewModel.getDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                            .load(it.avatar_url)
                            .placeholder(R.drawable.icon)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .fitCenter()
                            .override(400, 400)
                            .into(imageDetail)
                    name.text = it.name
                    username.text = it.username
                    txtbio.text = it.bio
                    txtfollowers.text = it.followers.toString()
                    txtfollowing.text = it.following.toString()
                    txtrepository.text = it.public_repos.toString()
                }
            }
    
        })
    }

    fun getFav(user: userItems) {
        val txtSaved = getString(R.string.fav_saved)
        val values = ContentValues()
        userHelper.open()
        values.put(UserContract.UserColums.COLUMN_NAME_USERNAME, user.username)
        values.put(UserContract.UserColums._ID, user.id)
        values.put(UserContract.UserColums.COLUMN_AVATAR_URL, user.ava_url)
        val result = userHelper.insert(values)
        userHelper.close()
        
        if (result>0) {
            Toast.makeText(this@DetailActivity, txtSaved, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@DetailActivity, R.string.error, Toast.LENGTH_SHORT).show()
        }
        
    }
    
    fun deleteFav(id: String) {
        val txtDeleted = getString(R.string.fav_deleted)
        userHelper.open()
        userHelper.deleteById(id)
        Toast.makeText(this@DetailActivity, txtDeleted, Toast.LENGTH_SHORT).show()
        userHelper.close()
    }
    
    fun checkFav(id: String): Boolean {
        userHelper.open()
        val cursor = userHelper.queryById(id)
        Log.d(TAG, "checkFav: $cursor")
        val result = MappingHelper.mapCursorToObject(cursor)
        Log.d(TAG, "checkFavUser: ${result.id}")
        userHelper.close()
        if (result.id > 0){
           return true
        }
        else{
            return false
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }
}





