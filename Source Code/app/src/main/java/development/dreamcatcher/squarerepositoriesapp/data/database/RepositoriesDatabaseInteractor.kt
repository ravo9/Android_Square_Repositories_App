package development.dreamcatcher.squarerepositoriesapp.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoryGsonObject
import kotlinx.coroutines.launch

// Interactor used for communication between data repository and internal database
class RepositoriesDatabaseInteractor(private val repositoriesDatabase: RepositoriesDatabase) {

    fun addNewRepository(repository: RepositoryGsonObject?): LiveData<Boolean> {

        val saveRepositoryLiveData = MutableLiveData<Boolean>()

        repository?.let {
            if (it.id != null && it.name != null && it.owner?.login != null && it.htmlUrl != null) {
            val repositoryEntity = RepositoryDatabaseEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                owner = it.owner.login,
                avatarUrl = it.owner.avatarUrl,
                htmlUrl = it.htmlUrl)
            launch {
                repositoriesDatabase.getRepositoriesDao().insertNewRepository(repositoryEntity)
            }
            }
        }
        saveRepositoryLiveData.postValue(true)
        return saveRepositoryLiveData
    }

    fun getSingleSavedRepositoryById(id: String): LiveData<RepositoryDatabaseEntity>? {
        return repositoriesDatabase.getRepositoriesDao().getSingleSavedRepositoryById(id)
    }

    fun getAllRepositories(): LiveData<List<RepositoryDatabaseEntity>>? {
        return repositoriesDatabase.getRepositoriesDao().getAllSavedRepositories()
    }

    fun clearDatabase() {
        launch {
            repositoriesDatabase.getRepositoriesDao().clearDatabase()
        }
    }
}



