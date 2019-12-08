package development.dreamcatcher.squarerepositoriesapp.features.detailedView

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
import javax.inject.Inject

class DetailedViewViewModel @Inject constructor(private val repositoriesRepository: RepositoriesRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedRepositoryById(repositoryId: String): LiveData<RepositoryDatabaseEntity>? {
        return repositoriesRepository.getSingleSavedRepositoryById(repositoryId)
    }
}