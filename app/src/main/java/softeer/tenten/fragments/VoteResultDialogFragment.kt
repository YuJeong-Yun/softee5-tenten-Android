package softeer.tenten.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.R
import softeer.tenten.vote.VoteItemModel
import softeer.tenten.vote.VoteRVAdapter
import softeer.tenten.vote.VoteResultItemModel
import softeer.tenten.vote.VoteResultRVAdapter

class VoteResultDialogFragment : DialogFragment() {
    private lateinit var voteResultRv: RecyclerView
    private lateinit var xButton: ImageButton
    private lateinit var closeButton: AppCompatButton
    private lateinit var higherLocation: TextView
    private lateinit var higherMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vote_result_dialog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        voteResultRv = view.findViewById(R.id.voteResultDialogRv)
        xButton = view.findViewById(R.id.voteResultDialogXButton)
        closeButton = view.findViewById(R.id.voteResultDialogCloseButton)

        var voteList = mutableListOf<VoteResultItemModel>()

        voteList.add(VoteResultItemModel("지역지역지역지역", true, 40))
        voteList.add(VoteResultItemModel("지역지역지역지역", false, 30))
        voteList.add(VoteResultItemModel("지역지역지역지역", false, 20))
        voteList.add(VoteResultItemModel("지역지역지역지역", false, 10))

        val adapter = VoteResultRVAdapter(requireContext(), voteList)

        voteResultRv.adapter = adapter

        xButton.setOnClickListener {
            dismiss()
        }

        closeButton.setOnClickListener {
            dismiss()
        }
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