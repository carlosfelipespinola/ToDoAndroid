package com.example.letsdo.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_dos_table")
data class ToDo (
    @ColumnInfo(name = "name")
    var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}