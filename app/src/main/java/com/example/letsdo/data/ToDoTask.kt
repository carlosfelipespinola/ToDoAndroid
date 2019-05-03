package com.example.letsdo.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "to_do_task_table",
    foreignKeys = [
        ForeignKey(
            entity = ToDo::class,
            onDelete = ForeignKey.CASCADE,
            childColumns = ["to_do_uid"],
            parentColumns = ["uid"]
        )
    ]
)
data class ToDoTask(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "to_do_uid")
    @NonNull
    var toDoUid: Long
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0

    @ColumnInfo(name = "is_finished")
    var isFinished: Boolean = false
}