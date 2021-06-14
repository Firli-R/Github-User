package com.example.githab_user.Database


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '${UserContract.UserColums.TABLE_NAME}'")
        onCreate(db)
    }
    companion object{
        private const val DATABASE_NAME = "dbFavoriteUser"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVORITE ="CREATE TABLE ${UserContract.UserColums.TABLE_NAME}" +
                "(${UserContract.UserColums._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UserContract.UserColums.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                "${UserContract.UserColums.COLUMN_AVATAR_URL} TEXT NOT NULL)"
    }
}