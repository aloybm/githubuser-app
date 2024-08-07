import com.aloysius.aplikasigithub.data.response.DetailUserResponse
import com.aloysius.aplikasigithub.data.response.FollowUserResponseItem
import com.aloysius.aplikasigithub.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getAllUsers(
        @Query("q") query: String,
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUsers(@Path("username") username: String): Call<List<FollowUserResponseItem>>
    
    @GET("users/{username}/following")
    fun getFollowingUsers(@Path("username") username: String): Call<List<FollowUserResponseItem>>

}

