package development.dreamcatcher.squarerepositoriesapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDao
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabase
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.ApiResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RepositoriesDatabaseInteractorTest {

    private var repositoriesDatabaseInteractor: RepositoriesDatabaseInteractor? = null
    private var fakeArticle: ApiResponse.Article? = null
    private var fakeRepositoryDatabaseEntity: RepositoryDatabaseEntity? = null

    @Mock
    private val repositoriesDatabase: RepositoriesDatabase? = null

    @Mock
    private val repositoriesDao: RepositoriesDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        repositoriesDatabaseInteractor = RepositoriesDatabaseInteractor(repositoriesDatabase!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake sub-objects
        val mainImageThumbnailSubObject = ApiResponse.MainImageThumbnail(thumbnailUrl)
        val imagesObjectSubObject = ApiResponse.Images(mainImageThumbnailSubObject)

        // Prepare fake Article (API object)
        fakeArticle = ApiResponse.Article(contentId, title, summary, contentUrl, imagesObjectSubObject)

        // Prepare fake Article Entity (DB object)
        fakeRepositoryDatabaseEntity = RepositoryDatabaseEntity(contentId, title, summary, contentUrl, thumbnailUrl)
    }

    @Test
    fun saveArticleByDatabaseInteractor() {

        // Perform the action
        val resultStatus = repositoriesDatabaseInteractor!!.addNewRepository(fakeArticle).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchArticleByDatabaseInteractor() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<RepositoryDatabaseEntity>()
        articleEntityLiveData.setValue(fakeRepositoryDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(repositoriesDatabase?.getRepositoriesDao()).thenReturn(repositoriesDao)
        Mockito.`when`(repositoriesDao?.getSingleSavedRepositoryById(anyString())).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticle = repositoriesDatabaseInteractor?.getSingleSavedArticleById("fake-article-id")

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticle);
    }
}