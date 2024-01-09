package softeer.tenten.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.ReviewListResponse

interface ReviewApiService {
    @GET("/api/pop-up/{id}/reviews")
    fun getReviews(@Path(value = "id") id: Long) : Call<BaseResponse<List<ReviewListResponse>>>
}