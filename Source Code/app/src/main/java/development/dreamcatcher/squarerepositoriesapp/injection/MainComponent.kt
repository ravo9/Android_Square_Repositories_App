package development.dreamcatcher.squarerepositoriesapp.injection

import dagger.Component
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import development.dreamcatcher.squarerepositoriesapp.features.detailedView.DetailedViewFragment
import development.dreamcatcher.squarerepositoriesapp.features.detailedView.DetailedViewViewModel
import development.dreamcatcher.squarerepositoriesapp.features.feed.FeedActivity
import development.dreamcatcher.squarerepositoriesapp.features.feed.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface MainComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedViewFragment: DetailedViewFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedViewViewModel: DetailedViewViewModel)
    fun inject(repositoriesNetworkInteractor: RepositoriesNetworkInteractor)
    fun inject(repositoriesDatabaseInteractor: RepositoriesDatabaseInteractor)
}