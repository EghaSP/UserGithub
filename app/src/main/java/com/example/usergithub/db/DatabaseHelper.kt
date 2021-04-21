package com.example.usergithub.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.usergithub.db.DatabaseUsers.FavColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbUsergithub"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseUsers.FavColumns.USERNAME} TEXT PRIMARY KEY  NOT NULL," +
                " ${DatabaseUsers.FavColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseUsers.FavColumns.AVATAR} TEXT NOT NULL," +
                " ${DatabaseUsers.FavColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseUsers.FavColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseUsers.FavColumns.REPOSITORY} INTEGER NOT NULL," +
                " ${DatabaseUsers.FavColumns.FOLLOWERS} INTEGER NOT NULL," +
                " ${DatabaseUsers.FavColumns.FOLLOWING} INTEGER NOT NULL," +
                " ${DatabaseUsers.FavColumns.FAVOURITE} TEXT NOT NULL)"
    }

    // create the SQLite database table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    // if the table of database is exist, this function will delete it
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}