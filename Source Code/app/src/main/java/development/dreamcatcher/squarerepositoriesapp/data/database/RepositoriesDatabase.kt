package development.dreamcatcher.squarerepositoriesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(RepositoryDatabaseEntity::class)], version = 1)
abstract class RepositoriesDatabase : RoomDatabase() {
    abstract fun getRepositoriesDao(): RepositoriesDao
}