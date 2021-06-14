package com.example.githab_user.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.example.githab_user.Database.UserContract.UserColums.Companion.COLUMN_AVATAR_URL
import com.example.githab_user.Database.UserContract.UserColums.Companion.COLUMN_NAME_USERNAME
import com.example.githab_user.Database.UserContract.UserColums.Companion.TABLE_NAME
import com.example.githab_user.data.userItems

class UserHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }


    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "username=?",
            null
        )
    }
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }
    
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${UserContract.UserColums._ID} = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${UserContract.UserColums._ID} = '$id'", null)
    }
    fun insertUser(user: userItems): Long {
        val args = ContentValues()
        args.put(COLUMN_NAME_USERNAME, user.username)
        args.put(COLUMN_AVATAR_URL, user.username)
        return database.insert(DATABASE_TABLE, null, args)
    }

}


