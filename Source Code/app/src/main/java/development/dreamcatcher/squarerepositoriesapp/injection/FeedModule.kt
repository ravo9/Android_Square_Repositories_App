package development.dreamcatcher.squarerepositoriesapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabase
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.ApiClient
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.NetworkAdapter
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Context): RepositoriesDatabase {
        return Room.databaseBuilder(application, RepositoriesDatabase::class.java, "main_database").build()
    }

    @Provides
    @Singleton
    fun providesRepositoriesDatabaseInteractor(repositoriesDatabase: RepositoriesDatabase): RepositoriesDatabaseInteractor {
        return RepositoriesDatabaseInteractor(repositoriesDatabase)
    }

    @Provides
    @Singleton
    fun providesRepositoriesNetworkInteractor(apiClient: ApiClient): RepositoriesNetworkInteractor {
        return RepositoriesNetworkInteractor(apiClient)
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesArticlesRepository(repositoriesNetworkInteractor: RepositoriesNetworkInteractor,
                                   repositoriesDatabaseInteractor: RepositoriesDatabaseInteractor): RepositoriesRepository {
        return RepositoriesRepository(repositoriesNetworkInteractor, repositoriesDatabaseInteractor)
    }
}