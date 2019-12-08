package development.dreamcatcher.squarerepositoriesapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import development.dreamcatcher.squarerepositoriesapp.data.network.ApiClient
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoryGsonObject
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RepositoriesNetworkInteractorTest {

    private var repositoriesNetworkInteractor: RepositoriesNetworkInteractor? = null
    private var fakeRepositoryGsonObject: RepositoryGsonObject? = null

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
        val id = "fake/repository/id"
        val name = "Fake Repository Name"
        val description = "Fake description..."
        val htmlUrl = "http://google.com"

        // Prepare fake sub-objects
        val login = "John Owner"
        val avatarUrl = "http://google.com/picture.jpg"
        val owner = RepositoryGsonObject.Owner(login, avatarUrl)

        // Prepare fake Gson (API) object
        fakeRepositoryGsonObject = RepositoryGsonObject(id, name, description, owner, htmlUrl)
    }
}
