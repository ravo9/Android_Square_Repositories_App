package development.dreamcatcher.squarerepositoriesapp.data.network

import com.google.gson.annotations.SerializedName

// ApiResponse object and its sub-objects used for deserializing data coming from API endpoint
data class RepositoryGsonObject(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("owner")
    val owner: Owner?,

    @SerializedName("html_url")
    val htmlUrl: String?
) {

    data class Owner(
        @SerializedName("login")
        val login: String?,

        @SerializedName("avatar_url")
        val avatarUrl: String?
    )
}