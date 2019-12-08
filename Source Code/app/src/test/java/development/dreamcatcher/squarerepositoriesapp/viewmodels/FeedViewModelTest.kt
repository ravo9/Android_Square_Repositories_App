package development.dreamcatcher.squarerepositoriesapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
import development.dreamcatcher.squarerepositoriesapp.features.feed.FeedViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeRepositoryDatabaseEntity: RepositoryDatabaseEntity? = null
    private var fakeArticleEntitiesList = ArrayList<RepositoryDatabaseEntity>()

    @Mock
    private val repositoriesRepository: RepositoriesRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        viewModel = FeedViewModel(repositoriesRepository!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Article Entity (DB object)
        fakeRepositoryDatabaseEntity = RepositoryDatabaseEntity(contentId, title, summary, contentUrl, thumbnailUrl)

        // Prepare fake Articles Entities List
        fakeArticleEntitiesList.add(fakeRepositoryDatabaseEntity!!)
    }

    @Test
    fun fetchAllArticlesByFeedViewModel() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<List<RepositoryDatabaseEntity>>()
        articleEntityLiveData.setValue(fakeArticleEntitiesList);

        // Set testing conditions
        Mockito.`when`(repositoriesRepository?.getAllArticles()).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticles = viewModel?.getAllArticles()

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }
}