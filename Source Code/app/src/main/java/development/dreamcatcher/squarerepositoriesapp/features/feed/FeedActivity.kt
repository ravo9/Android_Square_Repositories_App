package development.dreamcatcher.squarerepositoriesapp.features.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import development.dreamcatcher.squarerepositoriesapp.R
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.features.detailedView.DetailedViewFragment
import development.dreamcatcher.squarerepositoriesapp.injection.SquareRepositoriesApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import javax.inject.Inject

// Main ('feed') view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var repositoriesListAdapter: RepositoriesListAdapter

    init {
        SquareRepositoriesApp.mainComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (Repositories List)
        setupRecyclerView()

        // Fetch repositories from the back-end and load them into the view
        subscribeForRepositories()

        // Catch and handle potential network issues
        subscribeForNetworkError()

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshRepositoriesSubscription()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView_repositories.layoutManager = layoutManager
        repositoriesListAdapter = RepositoriesListAdapter{
                repositoryId: String -> displayDetailedView(repositoryId)
        }
        recyclerView_repositories.adapter = repositoriesListAdapter
    }

    private fun subscribeForRepositories() {
        viewModel.getAllRepositories()?.observe(this, Observer<List<RepositoryDatabaseEntity>> {

            if (!it.isNullOrEmpty()) {

                // Hide the loading view
                showLoadingView(false)

                // Display fetched repositories
                repositoriesListAdapter.setRepositories(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // In case of Network Error...
            // If no repositories have been cached
            if (repositoriesListAdapter.itemCount == 0) {

                // Stop the loading progress bar (circle)
                progressBar.visibility = View.INVISIBLE

                // Display "Try Again" button
                tryagain_button.visibility = View.VISIBLE

                // Setup onClick listener that resets repositories data subscription
                tryagain_button.setOnClickListener {
                    refreshRepositoriesSubscription()

                    // Re-display the loading progress bar (circle)
                    progressBar.visibility = View.VISIBLE
                }
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection,
                Toast.LENGTH_LONG) .show()
        })
    }

    private fun refreshRepositoriesSubscription() {
        viewModel.getAllRepositories()?.removeObservers(this)
        subscribeForRepositories()
    }

    private fun displayDetailedView(repositoryId: String) {

        val fragment = DetailedViewFragment()
        val bundle = Bundle()
        bundle.putString("repositoryId", repositoryId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            loading_container.visibility = View.VISIBLE
            appbar_container.visibility = View.GONE
        } else {
            loading_container.visibility = View.GONE
            appbar_container.visibility = View.VISIBLE
        }
    }
}
