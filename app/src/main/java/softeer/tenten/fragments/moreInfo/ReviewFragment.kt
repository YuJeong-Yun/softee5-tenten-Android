package softeer.tenten.fragments.moreInfo

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.R
import softeer.tenten.network.api.ReviewApiService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.ReviewListResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.reivew.ReviewItemModel
import softeer.tenten.reivew.ReviewRVAdapter

class ReviewFragment : Fragment() {
    private lateinit var reviewRv : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reviewRv = view.findViewById(R.id.reviewRv)

        getReviews(1)
    }

    private fun getReviews(popUpId: Long){
        val retrofit = RetrofitApi.getInstance().create(ReviewApiService::class.java)

        retrofit.getReviews(popUpId).enqueue(object: Callback<BaseResponse<List<ReviewListResponse>>>{
            @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            override fun onResponse(
                call: Call<BaseResponse<List<ReviewListResponse>>>,
                response: Response<BaseResponse<List<ReviewListResponse>>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()!!.data
                    val reviews = data.stream().map {
                        ReviewItemModel(it.image,
                            it.nickname,
                            it.destination,
                            it.date,
                            it.content)
                    }.toList()

                    val adapter = ReviewRVAdapter(requireContext(), reviews)
                    reviewRv.adapter = adapter
                } else{

                }
            }

            override fun onFailure(
                call: Call<BaseResponse<List<ReviewListResponse>>>,
                t: Throwable
            ) {

            }

        })
    }
}