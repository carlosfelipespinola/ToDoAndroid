package com.example.letsdo.data

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "to_dos_table")
data class ToDo (
    @ColumnInfo(name = "name")
    var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0

    var percentage: Int = 0
}