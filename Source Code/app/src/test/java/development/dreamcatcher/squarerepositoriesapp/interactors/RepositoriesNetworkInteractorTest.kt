package development.dreamcatcher.squarerepositoriesapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import development.dreamcatcher.squarerepositoriesapp.data.network.ApiClient
import development.dreamcatcher.squarerepositoriesapp.data.network.ApiResponse
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RepositoriesNetworkInteractorTest {

    private var repositoriesNetworkInteractor: RepositoriesNetworkInteractor? = null
    private val fakeApiResponse: ApiResponse = ApiResponse()

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        repositoriesNetworkInteractor = RepositoriesNetworkInteractor(apiClient!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake sub-object
        val mainImageThumbnailSubObject = ApiResponse.MainImageThumbnail(thumbnailUrl)
        val imagesObjectSubObject = ApiResponse.Images(mainImageThumbnailSubObject)
        val fakeArticle = ApiResponse.Article(contentId, title, summary, contentUrl, imagesObjectSubObject)
        val fakeArticlesList = ArrayList<ApiResponse.Article>()
        fakeArticlesList.add(fakeArticle)

        // Prepare fake ApiResponse
        fakeApiResponse.content = fakeArticlesList
    }
}
