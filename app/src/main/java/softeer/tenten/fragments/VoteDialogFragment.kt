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
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.util.rangeTo
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.R
import softeer.tenten.reivew.ReviewItemModel
import softeer.tenten.reivew.ReviewRVAdapter
import softeer.tenten.vote.VoteItemModel
import softeer.tenten.vote.VoteRVAdapter

class VoteDialogFragment : DialogFragment() {
    private lateinit var voteRv: RecyclerView
    private lateinit var xButton: ImageButton
    private lateinit var voteButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vote_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        voteRv = view.findViewById(R.id.voteDialogRv)
        xButton = view.findViewById(R.id.voteDialogXButton)
        voteButton = view.findViewById(R.id.voteDialogButton)

        var voteList = mutableListOf<VoteItemModel>()

        voteList.add(VoteItemModel("지역지역지역지역"))
        voteList.add(VoteItemModel("지역지역지역지역"))
        voteList.add(VoteItemModel("지역지역지역지역"))
        voteList.add(VoteItemModel("지역지역지역지역"))

        val adapter = VoteRVAdapter(requireContext(), voteList)

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

                adapter.notifyItemChanged(position)
            }
        })

        voteRv.adapter = adapter

        xButton.setOnClickListener {
            dismiss()
        }

        voteButton.setOnClickListener {
            VoteResultDialogFragment().show(parentFragmentManager, "vote result dialog open")
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