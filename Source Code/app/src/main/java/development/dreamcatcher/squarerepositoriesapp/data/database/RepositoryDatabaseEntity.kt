package development.dreamcatcher.squarerepositoriesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryDatabaseEntity(
        @PrimaryKey val id: String,
        val name: String,
        val description: String?,
        val owner: String,
        val avatarUrl: String?,
        val htmlUrl: String)

