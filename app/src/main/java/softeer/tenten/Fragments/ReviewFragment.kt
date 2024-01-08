package softeer.tenten.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.R
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

        var reviews = mutableListOf<ReviewItemModel>()

        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))
        reviews.add(ReviewItemModel("test1.png", "리뷰 이름 입니다.", "방문 장소", "24.1.6 토 방문", "리뷰 내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용"))

        reviewRv.adapter = ReviewRVAdapter(requireContext(), reviews)
    }
}