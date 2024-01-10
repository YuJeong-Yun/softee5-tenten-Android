package softeer.tenten.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.joinAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.R
import softeer.tenten.network.api.VoteApiService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.VoteInformationResponse
import softeer.tenten.network.response.VoteResult
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App
import softeer.tenten.vote.VoteItemModel
import softeer.tenten.vote.VoteRVAdapter
import softeer.tenten.vote.VoteResultItemModel
import softeer.tenten.vote.VoteResultRVAdapter
import java.util.Collections
import java.util.stream.Collectors

class VoteResultDialogFragment(private val popUpId: Long) : DialogFragment() {
    private lateinit var voteResultRv: RecyclerView
    private lateinit var xButton: ImageButton
    private lateinit var closeButton: AppCompatButton
    private lateinit var higherLocation: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vote_result_dialog, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        voteResultRv = view.findViewById(R.id.voteResultDialogRv)
        xButton = view.findViewById(R.id.voteResultDialogXButton)
        closeButton = view.findViewById(R.id.voteResultDialogCloseButton)
        higherLocation = view.findViewById(R.id.voteResultDialogHigherLocation)

        getVoteResult()

        xButton.setOnClickListener {
            dismiss()
        }

        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun getVoteResult(){
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
                    val voteResults = data.voteResults
                    val myVoteNumber = data.myVoteNumber

                    val sum = voteResults.sumOf {
                        it.result
                    }

                    val max = voteResults.maxOf {
                        it.result
                    }

                    val higherLocations = voteResults.stream()
                        .filter { it.result == max }
                        .map { it.name }
                        .collect(Collectors.joining(", "))
                        .toString()

                    higherLocation.text = higherLocations

                    Log.d("vote result", myVoteNumber.toString())
                    val items = voteResults.stream().map {
                        VoteResultItemModel(it.name, it.id == myVoteNumber, (it.result / sum) * 100)
                    }.toList()

                    val adapter = VoteResultRVAdapter(requireContext(), items)

                    voteResultRv.adapter = adapter
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<VoteInformationResponse>>,
                t: Throwable
            ) {

            }

        })
    }

    fun resizeDialog() {
        val windowManager = this.context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onResume() {
        super.onResume()

        resizeDialog()
    }
}