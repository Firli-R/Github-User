package com.example.consumerapp

import android.database.Cursor


object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<userItems> {
        val userList = ArrayList<userItems>()
        
        userCursor?.apply {
            try {
                while (moveToNext()) {
                    val username = getString(getColumnIndexOrThrow(UserContract.UserColums.COLUMN_NAME_USERNAME))
                    val id = getInt(getColumnIndexOrThrow(UserContract.UserColums._ID))
                    val avatarUrl = getString(getColumnIndexOrThrow(UserContract.UserColums.COLUMN_AVATAR_URL))
                    userList.add(userItems(username,id,avatarUrl))
                }
            }catch (e:Exception){}
            
        }
        return userList
    }
}