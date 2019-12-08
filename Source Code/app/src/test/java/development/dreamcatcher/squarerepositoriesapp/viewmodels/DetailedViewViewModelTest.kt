package development.dreamcatcher.squarerepositoriesapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
import development.dreamcatcher.squarerepositoriesapp.features.detailedView.DetailedViewViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailedViewViewModelTest {

    private var viewModel: DetailedViewViewModel? = null
    private var fakeRepositoryDatabaseEntity: RepositoryDatabaseEntity? = null

    @Mock
    private val repositoriesRepository: RepositoriesRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        viewModel = DetailedViewViewModel(repositoriesRepository!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Article Entity (DB object)
        fakeRepositoryDatabaseEntity = RepositoryDatabaseEntity(contentId, title, summary, contentUrl, thumbnailUrl)
    }

    @Test
    fun fetchArticleByFeedViewModel() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<RepositoryDatabaseEntity>()
        articleEntityLiveData.setValue(fakeRepositoryDatabaseEntity);

        // Prepare fake article id
        val fakeArticleId = "fake/article/id"

        // Set testing conditions
        Mockito.`when`(repositoriesRepository?.getSingleSavedArticleById(fakeArticleId)).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticles = viewModel?.getSingleSavedArticleById(fakeArticleId)

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }
}