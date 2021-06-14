package com.example.githab_user.Follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username:String? = null
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val mBundle = Bundle()
        mBundle.putString(FollowersFragment.EXTRA_FOLLOWERS,username)
        mBundle.putString(FollowingFragment.EXTRA_FOLLOWING,username)

        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = mBundle
        return fragment as Fragment

    }
}