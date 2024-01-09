package softeer.tenten.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.EventDetailResponse
import softeer.tenten.network.response.EventsResponse

interface EventApiService {
    @GET("/api/pop-up/{id}/events")
    fun getEvents(@Path(value = "id") id: Long) : Call<BaseResponse<List<EventsResponse>>>

    @GET("/api/pop-up/{pop-up-id}/events/{event-id}")
    fun getEventDetail(@Path(value = "pop-up-id") popUpId: Long, @Path(value = "event-id") eventId: Long) : Call<BaseResponse<EventDetailResponse>>
}