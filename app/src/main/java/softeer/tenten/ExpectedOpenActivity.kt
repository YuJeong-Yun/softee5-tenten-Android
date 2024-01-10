package softeer.tenten

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.databinding.ActivityExpectedOpenBinding
import softeer.tenten.dialog.OnDialogResultListener
import softeer.tenten.fragments.VoteDialogFragment
import softeer.tenten.network.api.PopUpStoreService
import softeer.tenten.network.api.VoteApiService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.PopUpDetail
import softeer.tenten.network.response.VoteInformationResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App

class ExpectedOpenActivity : AppCompatActivity(), OnDialogResultListener {
    private lateinit var binding: ActivityExpectedOpenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpectedOpenBinding.inflate(layoutInflater)

        val popUpId = intent.getLongExtra("id", 0)
        setContentView(binding.root)

        getPopUpDetail(popUpId)
        getVoteInformation(popUpId)

        binding.apply {
            expectedOpenVote.setOnClickListener {
                VoteDialogFragment(popUpId).show(supportFragmentManager, "open vote dialog")
            }
        }
    }

    private fun getVoteInformation(popUpId: Long){
        val retrofit = RetrofitApi.getInstance().create(VoteApiService::class.java)
        val userId = App.prefs.getString("id", "")

        retrofit.getVoteInformation(popUpId, userId).enqueue(object: Callback<BaseResponse<VoteInformationResponse>>{
            @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            override fun onResponse(
                call: Call<BaseResponse<VoteInformationResponse>>,
                response: Response<BaseResponse<VoteInformationResponse>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()!!.data

                    if(data.myVoteNumber != -1L){
                        binding.apply {
                            expectedOpenVote.text = "투표 결과 보기"
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<VoteInformationResponse>>,
                t: Throwable
            ) {

            }

        })
    }

    override fun onEventParticipateDialogResult(result: Boolean?) {
        if(result!!){
            binding.apply {
                expectedOpenVote.text = "투표 결과 보기"
            }
        }
    }


    private fun getPopUpDetail(popUpId: Long) {
        val retrofit = RetrofitApi.getInstance().create(PopUpStoreService::class.java)
        Log.e("test", "test")

        retrofit.getPopUpDetail(popUpId).enqueue(object : Callback<BaseResponse<PopUpDetail>> {
            override fun onResponse(
                call: Call<BaseResponse<PopUpDetail>>, response: Response<BaseResponse<PopUpDetail>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    binding.apply {
                        Glide.with(this@ExpectedOpenActivity)
                            .load(data.images[0])
                            .into(binding.expectedOpenImage)
//                        expectedOpenImage.setImageResource(data.images[0])
                        expectedOpenBrand.text = data.brand
                        expectedOpenTitle.text = data.title
                        expectedOpenPopUpStoreDescription.text = data.introduction
                        expectedOpenLocation.text = "미정"
                        expectedOpenTime.text = "10:00 - 17:00"
                        expectedOpenDuration.text = data.duration
                        expectedOpenCapacity.text = "수용인원 미정 · 차종 미정"
                    }

                } else {

                }
            }

            override fun onFailure(call: Call<BaseResponse<PopUpDetail>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}