package com.filipaivankovic.lucijanpavic.pokemonpokedex.data

import android.content.Context
import androidx.room.*
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.dao.FavoriteDao
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "favorites")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String> {
        return string.split(",")
    }
}