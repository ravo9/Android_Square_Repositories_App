package development.dreamcatcher.squarerepositoriesapp.data.network

import io.reactivex.Observable
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/orgs/square/repos")
    fun getRepositories(): Observable<List<RepositoryGsonObject>>
}