package development.dreamcatcher.squarerepositoriesapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoryGsonObject
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
        val id = "31231"
        val name = "Fake Repository Name"
        val description = "Fake description..."
        val htmlUrl = "http://google.com"

        // Prepare fake sub-objects
        val login = "John Owner"
        val avatarUrl = "http://google.com/picture.jpg"
        val owner = RepositoryGsonObject.Owner(login, avatarUrl)
        
        // Prepare fake Repository Entity (DB object)
        fakeRepositoryDatabaseEntity = RepositoryDatabaseEntity(id, name, description, owner.login!!, owner.avatarUrl, htmlUrl)
    }

    @Test
    fun fetchRepositoryByFeedViewModel() {

        // Prepare LiveData structure
        val repositoryEntityLiveData = MutableLiveData<RepositoryDatabaseEntity>()
        repositoryEntityLiveData.setValue(fakeRepositoryDatabaseEntity);

        // Prepare fake repository id
        val fakeRepositoryId = "fake/repository/id"

        // Set testing conditions
        Mockito.`when`(repositoriesRepository?.getSingleSavedRepositoryById(fakeRepositoryId)).thenReturn(repositoryEntityLiveData)

        // Perform the action
        val storedRepositories = viewModel?.getSingleSavedRepositoryById(fakeRepositoryId)

        // Check results
        Assert.assertSame(repositoryEntityLiveData, storedRepositories);
    }
}