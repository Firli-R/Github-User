package com.example.githab_user.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githab_user.R
import com.example.githab_user.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding :ActivitySettingBinding
    companion object{
        const val TITLE ="Settings"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.setting_holder, SettingFragment())
                    .commit()
        }
        supportActionBar?.title = TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        
        
    }
    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }

}