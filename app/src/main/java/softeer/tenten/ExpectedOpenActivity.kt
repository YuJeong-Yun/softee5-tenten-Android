package softeer.tenten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.databinding.ActivityExpectedOpenBinding
import softeer.tenten.fragments.VoteDialogFragment
import softeer.tenten.network.api.PopUpStoreService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.PopUpDetail
import softeer.tenten.network.retrofit.RetrofitApi

class ExpectedOpenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpectedOpenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val popUpId = intent.getLongExtra("id", 0)

        super.onCreate(savedInstanceState)
        binding = ActivityExpectedOpenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            expectedOpenVote.setOnClickListener {
                VoteDialogFragment().show(supportFragmentManager, "open vote dialog")
            }
        }

        getPopUpDetail(popUpId)
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