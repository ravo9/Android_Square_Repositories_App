package development.dreamcatcher.squarerepositoriesapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class RepositoriesRepository @Inject constructor(private val repositoriesNetworkInteractor:  RepositoriesNetworkInteractor,
                                                 private val databaseInteractor: RepositoriesDatabaseInteractor) {

    fun getSingleSavedRepositoryById(id: String): LiveData<RepositoryDatabaseEntity>? {
        return databaseInteractor.getSingleSavedRepositoryById(id)
    }

    fun getAllRepositories(backendUpdateRequired: Boolean): LiveData<List<RepositoryDatabaseEntity>>? {
        if (backendUpdateRequired) {
            updateDataFromBackEnd()
        }
        return databaseInteractor.getAllRepositories()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return repositoriesNetworkInteractor.networkError
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {
        repositoriesNetworkInteractor.getAllRepositories().subscribe {
            if (it.isSuccess && it.getOrDefault(null)?.size!! > 0) {

                // Clear database not to store outdated repositories
                databaseInteractor.clearDatabase()

                // Save freshly fetched repositories
                it.getOrNull()?.forEach {
                    databaseInteractor.addNewRepository(it)
                }
            }
        }
    }
}