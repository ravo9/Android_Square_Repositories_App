package development.dreamcatcher.squarerepositoriesapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val repositoriesRepository: RepositoriesRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllRepositories(): LiveData<List<RepositoryDatabaseEntity>>? {
        return repositoriesRepository.getAllRepositories(true)
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return repositoriesRepository.getNetworkError()
    }
}