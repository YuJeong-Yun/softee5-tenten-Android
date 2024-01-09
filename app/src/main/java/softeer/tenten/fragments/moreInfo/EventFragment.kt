package softeer.tenten.fragments.moreInfo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.EventDetailActivity
import softeer.tenten.R
import softeer.tenten.event.EventItemModel
import softeer.tenten.event.EventRVAdapter
import softeer.tenten.network.api.EventApiService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.EventsResponse
import softeer.tenten.network.retrofit.RetrofitApi

class EventFragment : Fragment() {
    private lateinit var eventRV: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        eventRV = view.findViewById(R.id.event_rv)

        getEvents(2)
    }

    private fun getEvents(popUpId: Long){
        val retrofit = RetrofitApi.getInstance().create(EventApiService::class.java)

        retrofit.getEvents(popUpId).enqueue(object: Callback<BaseResponse<List<EventsResponse>>>{
            @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            override fun onResponse(
                call: Call<BaseResponse<List<EventsResponse>>>,
                response: Response<BaseResponse<List<EventsResponse>>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()!!.data
                    val events = data.stream().map{
                        EventItemModel(it.image, it.name, it.title)
                    }.toList()

                    val adapter = EventRVAdapter(requireContext(), events)

                    adapter.setOnItemClickListener(object: EventRVAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(context, EventDetailActivity::class.java)
                            startActivity(intent)
                        }
                    })

                    eventRV.adapter = adapter
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<EventsResponse>>>, t: Throwable) {

            }

        })
    }
}