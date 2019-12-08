package development.dreamcatcher.squarerepositoriesapp.data.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Interactor used for communication between data repository and external API
class RepositoriesNetworkInteractor @Inject constructor(var apiClient: ApiClient) {

    val networkError: MutableLiveData<Boolean> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getAllRepositories(): Observable<Result<List<RepositoryGsonObject>>> {
        val allRepositoriesSubject = SingleSubject.create<Result<List<RepositoryGsonObject>>>()

        apiClient.getRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    allRepositoriesSubject.onSuccess(Result.success(it))
                },
                {
                    networkError.postValue(true)
                    Log.e("getRepositories error: ", it.message)
                })

        return allRepositoriesSubject.toObservable()
    }
}