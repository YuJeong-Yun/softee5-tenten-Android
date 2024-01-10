package softeer.tenten.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import softeer.tenten.ExpectedOpenActivity
import softeer.tenten.MoreInfoActivity
import softeer.tenten.category.CategoryExpectedOpenRVAdapter
import softeer.tenten.category.CategoryItemModel
import softeer.tenten.category.CategoryNearCarRVAdapter
import softeer.tenten.category.CategoryRecommendCarRVAdapter
import softeer.tenten.R
import softeer.tenten.network.api.PopUpStoreService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.PopUpList
import softeer.tenten.network.retrofit.RetrofitApi

class HomeFragment : Fragment() {

    private lateinit var rvNearCar: RecyclerView
    private lateinit var rvRecommendCar: RecyclerView
    private lateinit var rvExpectedOpenCar: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //////////////////// RVAdapter에 전달할 데이터 담기 ////////////////////
        val recommendCarItems = mutableListOf<CategoryItemModel>()
        val expectedOpen = mutableListOf<CategoryItemModel>()


        // 내 근처 팝업카
        rvNearCar = view.findViewById<RecyclerView>(R.id.categoryNearCar)
        getNearCarList(1)

        // 추천하는 팝업카
        rvRecommendCar = view.findViewById<RecyclerView>(R.id.categoryRecommendCar)
        getRecommendCarList(1)

        // 오픈 예정
        rvExpectedOpenCar = view.findViewById<RecyclerView>(R.id.categoryExpectedOpen)
        getExpectedOpenCarList(1)


        // 아이템 클릭시 상세 정보로 이동
//        rvNearCarAdapter.itemClick = object : CategoryNearCarRVAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                // 선택한 팝업스토어의 정보 담아서 액티비티 이동
//                val intent = Intent(requireContext(), MoreInfoActivity::class.java)
//                intent.putExtra("id", nearCarItems[position].id)
//                startActivity(intent)
//            }
//        }

//        rvExpectedOpenAdapter.itemClick = object : CategoryExpectedOpenRVAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                // 선택한 팝업스토어의 정보 담아서 액티비티 이동
//                val intent = Intent(requireContext(), ExpectedOpenActivity::class.java)
//                intent.putExtra("id", expectedOpen[position].id)
//                startActivity(intent)
//            }
//        }


//        rvExpectedOpen.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return view
    }


    // 팝업카 리스트로 가져오기
    fun getNearCarList(cnt: Int) {
        val retrofit = RetrofitApi.getInstance().create(PopUpStoreService::class.java)


        retrofit.getPopUpList()
            .enqueue(object : retrofit2.Callback<BaseResponse<List<PopUpList>>> {
                @RequiresApi(34)
                override fun onResponse(
                    call: Call<BaseResponse<List<PopUpList>>>,
                    response: Response<BaseResponse<List<PopUpList>>>
                ) {
                    // 정상적으로 응답이 도착했을 경우 (OK, 200)
                    if (response.isSuccessful) {
                        val data = response.body()!!.data
                        var popUpList = data.stream().filter { i -> i.status == 1 }.map {
                            CategoryItemModel(
                                it.id,
                                it.image,
                                it.brand,
                                it.title,
                                it.destinations[0],
                                it.duration
                            )
                        }.toList()

                        val rvNearCarAdapter =
                            CategoryNearCarRVAdapter(requireContext(), popUpList)
                        rvNearCar.adapter = rvNearCarAdapter

                        rvNearCarAdapter.itemClick = object : CategoryNearCarRVAdapter.ItemClick {
                            override fun onClick(view: View, position: Int) {
                                // 선택한 팝업스토어의 정보 담아서 액티비티 이동
                                val intent = Intent(requireContext(), MoreInfoActivity::class.java)
                                intent.putExtra("id", popUpList[position].id)
                                startActivity(intent)
                            }
                        }

                    }
                    // 요청은 서버로 갔지만, 올바르지 않은 요청인 경우(400, 403, 404)
                    // 없는 데이터 요청, 중복되는 데이터 등록 등
                    else {

                    }

                }

                override fun onFailure(
                    call: Call<BaseResponse<List<PopUpList>>>,
                    t: Throwable
                ) {
                }
            })
    }

    fun getRecommendCarList(cnt: Int) {
        val retrofit = RetrofitApi.getInstance().create(PopUpStoreService::class.java)


        retrofit.getPopUpList()
            .enqueue(object : retrofit2.Callback<BaseResponse<List<PopUpList>>> {
                @RequiresApi(34)
                override fun onResponse(
                    call: Call<BaseResponse<List<PopUpList>>>,
                    response: Response<BaseResponse<List<PopUpList>>>
                ) {
                    // 정상적으로 응답이 도착했을 경우 (OK, 200)
                    if (response.isSuccessful) {
                        val data = response.body()!!.data
                        var popUpList: List<CategoryItemModel> = emptyList()

                        popUpList = data.filter { i -> i.status == 1 }.stream().map {
                            CategoryItemModel(
                                it.id,
                                it.image,
                                it.brand,
                                it.title,
                                "",
                                it.duration
                            )
                        }.toList()

                        val rvRecommendAdapter =
                            CategoryRecommendCarRVAdapter(requireContext(), popUpList)
                        rvRecommendCar.adapter = rvRecommendAdapter
                    } else {

                    }

                }

                override fun onFailure(
                    call: Call<BaseResponse<List<PopUpList>>>,
                    t: Throwable
                ) {
                }
            })
    }

    fun getExpectedOpenCarList(cnt: Int) {
        val retrofit = RetrofitApi.getInstance().create(PopUpStoreService::class.java)


        retrofit.getPopUpList()
            .enqueue(object : retrofit2.Callback<BaseResponse<List<PopUpList>>> {
                @RequiresApi(34)
                override fun onResponse(
                    call: Call<BaseResponse<List<PopUpList>>>,
                    response: Response<BaseResponse<List<PopUpList>>>
                ) {
                    // 정상적으로 응답이 도착했을 경우 (OK, 200)
                    if (response.isSuccessful) {
                        val data = response.body()!!.data
                        var popUpList: List<CategoryItemModel> = emptyList()

                        popUpList = data.stream().filter { i -> i.status == 0 }.map {
                            CategoryItemModel(
                                it.id,
                                it.image,
                                it.brand,
                                it.title,
                                "",
                                "",
                            )
                        }.toList()

                        val rvExpectedOpenCarAdapter =
                            CategoryExpectedOpenRVAdapter(requireContext(), popUpList)
                        rvExpectedOpenCar.adapter = rvExpectedOpenCarAdapter
                        rvExpectedOpenCarAdapter.itemClick =
                            object : CategoryExpectedOpenRVAdapter.ItemClick {
                                override fun onClick(view: View, position: Int) {
                                    // 선택한 팝업스토어의 정보 담아서 액티비티 이동
                                    val intent =
                                        Intent(requireContext(), ExpectedOpenActivity::class.java)
                                    intent.putExtra("id", popUpList[position].id)
                                    startActivity(intent)
                                }
                            }

                    } else {

                    }

                }

                override fun onFailure(
                    call: Call<BaseResponse<List<PopUpList>>>,
                    t: Throwable
                ) {
                }
            })
    }
}