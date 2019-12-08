package development.dreamcatcher.squarerepositoriesapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoriesNetworkInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoryGsonObject
import development.dreamcatcher.squarerepositoriesapp.data.repositories.RepositoriesRepository
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
    private var fakeRepositoryEntitiesList = ArrayList<RepositoryDatabaseEntity>()

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
        val id = "fake/repository/id"
        val name = "Fake Repository Name"
        val description = "Fake description..."
        val htmlUrl = "http://google.com"

        // Prepare fake sub-objects
        val login = "John Owner"
        val avatarUrl = "http://google.com/picture.jpg"
        val owner = RepositoryGsonObject.Owner(login, avatarUrl)

        // Prepare fake Repository Entity (DB object)
        fakeRepositoryDatabaseEntity = RepositoryDatabaseEntity(id, name, description, owner.login!!, owner.avatarUrl, htmlUrl)

        // Prepare fake Repositories Entities List
        fakeRepositoryEntitiesList.add(fakeRepositoryDatabaseEntity!!)
    }

    @Test
    fun fetchAllRepositoriesByRepositoriesRepository() {

        // Prepare LiveData structure
        val repositoryEntityLiveData = MutableLiveData<List<RepositoryDatabaseEntity>>()
        repositoryEntityLiveData.setValue(fakeRepositoryEntitiesList);

        // Set testing conditions
        Mockito.`when`(repositoriesDatabaseInteractor?.getAllRepositories()).thenReturn(repositoryEntityLiveData)

        // Perform the action
        val storedRepositories = repositoriesRepository?.getAllRepositories(false)

        // Check results
        Assert.assertSame(repositoryEntityLiveData, storedRepositories);
    }

    @Test
    fun fetchRepositoryByRepositoriesRepository() {

        // Prepare LiveData structure
        val repositoryEntityLiveData = MutableLiveData<RepositoryDatabaseEntity>()
        repositoryEntityLiveData.setValue(fakeRepositoryDatabaseEntity);

        // Prepare fake repository id
        val fakeRepositoryId = "1231231"

        // Set testing conditions
        Mockito.`when`(repositoriesDatabaseInteractor?.getSingleSavedRepositoryById(fakeRepositoryId))
            .thenReturn(repositoryEntityLiveData)

        // Perform the action
        val storedRepository = repositoriesRepository?.getSingleSavedRepositoryById(fakeRepositoryId)

        // Check results
        Assert.assertSame(repositoryEntityLiveData, storedRepository);
    }
}