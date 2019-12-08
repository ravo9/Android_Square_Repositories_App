package development.dreamcatcher.squarerepositoriesapp.features.detailedView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.squarerepositoriesapp.R
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import development.dreamcatcher.squarerepositoriesapp.injection.SquareRepositoriesApp
import kotlinx.android.synthetic.main.detailed_view.*
import javax.inject.Inject

// Detailed view for displaying chosen item
class DetailedViewFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedViewViewModel

    init {
        SquareRepositoriesApp.mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailedViewViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detailed_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fetch detailed data from Data Repository
        subscribeForRepository()

        // Setup Cross Button
        btn_cross.setOnClickListener{
           activity?.onBackPressed()
        }
    }

    private fun subscribeForRepository() {
        val repositoryId = this.arguments?.getString("repositoryId")
        if (!repositoryId.isNullOrEmpty()) {
            viewModel.getSingleSavedRepositoryById(repositoryId)?.observe(this, Observer<RepositoryDatabaseEntity> {
                setupWebView(it.htmlUrl)
            })
        }
    }

    // Setup website view
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        website_view.settings.javaScriptEnabled = true
        website_view.webViewClient = WebViewClient()
        website_view.loadUrl(url)
        showLoadingView(false)
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}