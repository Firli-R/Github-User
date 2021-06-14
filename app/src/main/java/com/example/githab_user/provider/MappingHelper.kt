package com.example.githab_user.provider

import android.database.Cursor
import com.example.githab_user.Database.UserContract
import com.example.githab_user.data.userItems

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
    fun mapCursorToObject(userCursor: Cursor?): userItems {
        var user = userItems()
        userCursor?.apply {
            try {
                moveToNext()
                val username = getString(getColumnIndexOrThrow(UserContract.UserColums.COLUMN_NAME_USERNAME))
                val id = getInt(getColumnIndexOrThrow(UserContract.UserColums._ID))
                val avatarUrl = getString(getColumnIndexOrThrow(UserContract.UserColums.COLUMN_AVATAR_URL))
                user = userItems(username, id,avatarUrl)
            } catch (e: Exception) {}
        }
        return user
    }
}