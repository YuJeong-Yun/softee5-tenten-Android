package softeer.tenten.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.R
import softeer.tenten.dialog.OnDialogResultListener
import softeer.tenten.network.api.VoteApiService
import softeer.tenten.network.request.VoteRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.VoteInformationResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App
import softeer.tenten.vote.VoteItemModel
import softeer.tenten.vote.VoteRVAdapter

class VoteDialogFragment(private val popUpId: Long) : DialogFragment() {
    private lateinit var voteRv: RecyclerView
    private lateinit var xButton: ImageButton
    private lateinit var voteButton: AppCompatButton

    private var myVoteNumber = -1L

    private var mListener: OnDialogResultListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vote_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.visibility = View.GONE

        voteRv = view.findViewById(R.id.voteDialogRv)
        xButton = view.findViewById(R.id.voteDialogXButton)
        voteButton = view.findViewById(R.id.voteDialogButton)

        getVoteInformation(view, popUpId)

        xButton.setOnClickListener {
            dismiss()
        }

        voteButton.setOnClickListener {
            if(myVoteNumber != -1L) {
                takeVote(popUpId, myVoteNumber)

                VoteResultDialogFragment(popUpId).show(parentFragmentManager.beginTransaction(), "vote result dialog open")
                dismiss()
            }
        }
    }

    private fun getVoteInformation(view: View, popUpId: Long){
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
                        VoteResultDialogFragment(popUpId).show(parentFragmentManager.beginTransaction(), "vote result dialog open")
                        dismiss()
                    }

                    val voteItems = data.voteResults.stream().map {
                        VoteItemModel(it.id, it.name)
                    }.toList()

                    val adapter = VoteRVAdapter(requireContext(), voteItems)

                    adapter.setOnItemClickListener(object: VoteRVAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val items = adapter.items

                            for(i in 0..< items.size){
                                if(items[i].checked){
                                    items[i].checked = false
                                    adapter.notifyItemChanged(i)
                                }
                            }

                            items[position].checked = true
                            myVoteNumber = items[position].id

                            adapter.notifyItemChanged(position)
                        }
                    })

                    voteRv.adapter = adapter
                }

                view.visibility = View.VISIBLE
            }

            override fun onFailure(
                call: Call<BaseResponse<VoteInformationResponse>>,
                t: Throwable
            ) {

            }

        })
    }

    private fun takeVote(popUpId: Long, myVoteNumber: Long){
        val retrofit = RetrofitApi.getInstance().create(VoteApiService::class.java)
        val userId = App.prefs.getString("id", "")

        retrofit.takeVote(popUpId, VoteRequest(userId, myVoteNumber)).enqueue(object: Callback<BaseResponse<Long>>{
            override fun onResponse(
                call: Call<BaseResponse<Long>>,
                response: Response<BaseResponse<Long>>
            ) {
                if(response.isSuccessful){
                    sendResultToActivity(true)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Long>>, t: Throwable) {

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as OnDialogResultListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnDialogResultListener")
        }
    }

    private fun sendResultToActivity(result: Boolean) {
        mListener?.onEventParticipateDialogResult(result)
    }
}