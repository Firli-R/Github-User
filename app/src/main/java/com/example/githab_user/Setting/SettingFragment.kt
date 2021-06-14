package com.example.githab_user.Setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githab_user.R

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences
.OnSharedPreferenceChangeListener {
    private lateinit var CHECK:String
    private lateinit var TRANSLATE :String
    private lateinit var switchReminder: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver
    companion object{
        const val TAG = "SettingFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        alarmReceiver = AlarmReceiver()
        init()
        setSummaries()
        val changeLanguage = findPreference<Preference>(TRANSLATE) as Preference
        changeLanguage.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            true
        }
        
    }
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
    
    private fun init(){
        CHECK = resources.getString(R.string.repeating_alarm)
        TRANSLATE = resources.getString(R.string.Translate)
        
        switchReminder = findPreference<SwitchPreference>(CHECK) as SwitchPreference
      
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == CHECK){
            switchReminder.isChecked = sharedPreferences.getBoolean(CHECK,false)
            Log.d(TAG, "onSharedPreferenceChanged: ${switchReminder.isChecked}")
         
            if (switchReminder.isChecked){
                Log.d(TAG, "onSharedPreferenceChanged: berhasil")
                context?.let { alarmReceiver.setRepeatingAlarm(it) }
            }else{
                Log.d(TAG, "onSharedPreferenceChanged: gagal")
                context?.let { alarmReceiver.cancelAlarm(it) }
            }
            
        }
    }
    
    private fun setSummaries(){
        val sh = preferenceManager.sharedPreferences
        switchReminder.isChecked = sh.getBoolean(CHECK,false)
        
    }


}