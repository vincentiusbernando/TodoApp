package com.ubayadev.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubayadev.todoapp.model.TodoDatabase

val DB_NAME = "newtododb"
//fun buildDb(context: Context): TodoDatabase {
//    val db = TodoDatabase.buildDatabase(context)
//    return db
//}
fun buildDb(context: Context):TodoDatabase {
    val db = Room.databaseBuilder(context,
        TodoDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_3)
        .build()

    return db
}


fun buildDatabase(context: Context) =
    Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DB_NAME
    ).build()

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null"
        )
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            //because Boolean in Database is TINYINT, not true and false
            "ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 not null")
    }
}

