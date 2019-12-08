package development.dreamcatcher.squarerepositoriesapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewRepository(repositoryDatabaseEntity: RepositoryDatabaseEntity)

    @Query("SELECT * FROM repositories WHERE id = :id LIMIT 1")
    fun getSingleSavedRepositoryById(id: String): LiveData<RepositoryDatabaseEntity>?

    @Query("SELECT * FROM repositories")
    fun getAllSavedRepositories(): LiveData<List<RepositoryDatabaseEntity>>?

    @Query("DELETE FROM repositories")
    fun clearDatabase()
}