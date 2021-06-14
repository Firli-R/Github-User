package com.example.githab_user.Database

import android.net.Uri
import android.provider.BaseColumns

internal class UserContract {
    internal class UserColums : BaseColumns{
        companion object{

            const val AUTHORITY = "com.example.githab_user"
            const val SCHEME = "content"

            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val COLUMN_NAME_USERNAME = "username"
            const val COLUMN_AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}