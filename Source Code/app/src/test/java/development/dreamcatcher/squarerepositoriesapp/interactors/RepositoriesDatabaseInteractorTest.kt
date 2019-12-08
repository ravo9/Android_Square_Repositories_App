package development.dreamcatcher.squarerepositoriesapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDao
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabase
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoriesDatabaseInteractor
import development.dreamcatcher.squarerepositoriesapp.data.network.RepositoryGsonObject
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
    private var fakeRepositoryGsonObject: RepositoryGsonObject? = null
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

        // Prepare fake Repository Entity (DB object)
        fakeRepositoryDatabaseEntity = RepositoryDatabaseEntity(id, name, description, owner.login!!, owner.avatarUrl, htmlUrl)
    }

    @Test
    fun saveRepositoryByDatabaseInteractor() {

        // Perform the action
        val resultStatus = repositoriesDatabaseInteractor!!.addNewRepository(fakeRepositoryGsonObject).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchRepositoryByDatabaseInteractor() {

        // Prepare LiveData structure
        val repositoryEntityLiveData = MutableLiveData<RepositoryDatabaseEntity>()
        repositoryEntityLiveData.setValue(fakeRepositoryDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(repositoriesDatabase?.getRepositoriesDao()).thenReturn(repositoriesDao)
        Mockito.`when`(repositoriesDao?.getSingleSavedRepositoryById(anyString())).thenReturn(repositoryEntityLiveData)

        // Perform the action
        val storedRepository = repositoriesDatabaseInteractor?.getSingleSavedRepositoryById("fake-repository-id")

        // Check results
        Assert.assertSame(repositoryEntityLiveData, storedRepository);
    }
}