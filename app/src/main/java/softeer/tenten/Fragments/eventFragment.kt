package softeer.tenten.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.R
import softeer.tenten.event.EventItemModel
import softeer.tenten.event.EventRVAdapter

class eventFragment : Fragment() {
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

        var events = mutableListOf<EventItemModel>()

        events.add(EventItemModel("test1.png", "이벤트 이름 입니다.", "이벤트 설명 입니다."))
        events.add(EventItemModel("test1.png", "이벤트 이름 입니다.", "이벤트 설명 입니다."))
        events.add(EventItemModel("test1.png", "이벤트 이름 입니다.", "이벤트 설명 입니다."))
        events.add(EventItemModel("test1.png", "이벤트 이름 입니다.", "이벤트 설명 입니다."))

        val adapter = EventRVAdapter(requireContext(), events)
        adapter.setOnItemClickListener(object: EventRVAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

            }
        })

        eventRV.adapter = adapter
    }
}