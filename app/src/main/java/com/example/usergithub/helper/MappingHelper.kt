package com.example.usergithub.helper

import android.database.Cursor
import com.example.usergithub.db.DatabaseUsers
import com.example.usergithub.models.Favourite
import java.util.*

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favourite> {
        val favList = ArrayList<Favourite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseUsers.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseUsers.FavColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseUsers.FavColumns.AVATAR))
                val company = getString(getColumnIndexOrThrow(DatabaseUsers.FavColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseUsers.FavColumns.LOCATION))
                val repository = getInt(getColumnIndexOrThrow(DatabaseUsers.FavColumns.REPOSITORY))
                val followers = getInt(getColumnIndexOrThrow(DatabaseUsers.FavColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(DatabaseUsers.FavColumns.FOLLOWING))
                val favourite =
                    getString(getColumnIndexOrThrow(DatabaseUsers.FavColumns.FAVOURITE))
                favList.add(
                    Favourite(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        favourite
                    )
                )
            }
        }
        return favList
    }
}