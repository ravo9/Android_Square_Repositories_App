package development.dreamcatcher.squarerepositoriesapp.features.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import development.dreamcatcher.squarerepositoriesapp.R
import development.dreamcatcher.squarerepositoriesapp.data.database.RepositoryDatabaseEntity
import kotlinx.android.synthetic.main.main_feed_list_row.view.*

// Main adapter used for managing repositories list within the main Recycler (List) View
class RepositoriesListAdapter (val clickListener: (String) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var repositoriesList: List<RepositoryDatabaseEntity> = ArrayList()

    fun setRepositories(repositoriesList: List<RepositoryDatabaseEntity>) {
        this.repositoriesList = repositoriesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.main_feed_list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Prepare fetched data
        val name = repositoriesList[position].name
        val description = repositoriesList[position].description
        val avatarUrl = repositoriesList[position].avatarUrl

        // Set data within the holder
        holder.name.text = name
        holder.description.text = description

        // Load thumbnail
        if (avatarUrl != null) {
            Picasso.get().load(avatarUrl).into(holder.avatar)
        }

        // Set onClickListener
        holder.rowContainer.setOnClickListener{
            val repositoryId = repositoriesList[position].id
            clickListener(repositoryId)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.textView_name
    val description = view.textView_description
    val avatar = view.imageView_avatar
    val rowContainer = view.row_container
}