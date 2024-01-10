package softeer.tenten

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.databinding.ActivityExpectedOpenBinding
import softeer.tenten.dialog.OnDialogResultListener
import softeer.tenten.fragments.VoteDialogFragment
import softeer.tenten.fragments.VoteResultDialogFragment
import softeer.tenten.network.api.VoteApiService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.VoteInformationResponse
import softeer.tenten.network.response.VoteResult
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App
import softeer.tenten.vote.VoteItemModel
import softeer.tenten.vote.VoteRVAdapter

class ExpectedOpenActivity : AppCompatActivity(), OnDialogResultListener {
    private lateinit var binding: ActivityExpectedOpenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpectedOpenBinding.inflate(layoutInflater)

        val popUpId = intent.getLongExtra("id", 0)
        setContentView(binding.root)

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
}