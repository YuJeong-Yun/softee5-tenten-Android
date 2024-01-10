package softeer.tenten.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import softeer.tenten.network.request.VoteRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.VoteInformationResponse

interface VoteApiService {
    @POST("/api/pop-up/{id}/vote")
    fun takeVote(@Path(value = "id") popUpId: Long, @Body voteRequest: VoteRequest): Call<BaseResponse<Long>>

    @GET("/api/pop-up/{id}/vote")
    fun getVoteInformation(@Path(value = "id") popUpId: Long, @Query(value = "userId") userId: String): Call<BaseResponse<VoteInformationResponse>>
}