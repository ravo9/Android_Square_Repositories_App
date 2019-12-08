package development.dreamcatcher.squarerepositoriesapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.ApiResponse
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class RepositoriesRepositoryTest {

    private var repositoriesRepository: RepositoriesRepository? = null
    private var fakeRepositoryDatabaseEntity: RepositoryDatabaseEntity? = null
    private var fakeArticleEntitiesList = ArrayList<RepositoryDatabaseEntity>()

    @Mock
    private val repositoriesDatabaseInteractor: RepositoriesDatabaseInteractor? = null

    @Mock
    private val repositoriesNetworkInteractor: RepositoriesNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        repositoriesRepository = RepositoriesRepository(repositoriesNetworkInteractor!!, repositoriesDatabaseInteractor!!)

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
    fun fetchAllArticlesByArticlesRepository() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<List<RepositoryDatabaseEntity>>()
        articleEntityLiveData.setValue(fakeArticleEntitiesList);

        // Prepare fake empty list for backend call
        val fakeList = ArrayList<ApiResponse.Article>()
        val fakeNetworkCallResult = Result.success(fakeList)

        // Set testing conditions
        Mockito.`when`(repositoriesDatabaseInteractor?.getAllArticles()).thenReturn(articleEntityLiveData)
        Mockito.`when`(repositoriesNetworkInteractor?.getAllArticles()).thenReturn(Observable.just(fakeNetworkCallResult))

        // Perform the action
        val storedArticles = repositoriesRepository?.getAllArticles()

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }

    @Test
    fun fetchArticleByArticlesRepository() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<RepositoryDatabaseEntity>()
        articleEntityLiveData.setValue(fakeRepositoryDatabaseEntity);

        // Prepare fake article id
        val fakeArticleId = "fake/article/id"

        // Set testing conditions
        Mockito.`when`(repositoriesDatabaseInteractor?.getSingleSavedArticleById(fakeArticleId))
            .thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticle = repositoriesRepository?.getSingleSavedArticleById(fakeArticleId)

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticle);
    }
}