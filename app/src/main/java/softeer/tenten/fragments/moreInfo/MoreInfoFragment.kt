package softeer.tenten.fragments.moreInfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import softeer.tenten.R


class MoreInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_info, container, false)
    }

}

// 네이버 MAP 객체 얻어오기
//class MapFragmentActivity : FragmentActivity(), OnMapReadyCallback {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.fragment_more_info)
//
//        val fm = supportFragmentManager
//        val mapFragment =
//            fm.findFragmentById(R.id.map_fragment) as MapFragment? ?: MapFragment.newInstance()
//                .also {
//                    fm.beginTransaction().add(R.id.map_fragment, it).commit()
//                }
//
//        mapFragment.getMapAsync(this)
//    }
//
//    // naverMap 객체 준비되면 위에서 OnMapReadyCallback 호출하고 비동기로 naverMap 가져옴.
//    @UiThread
//    override fun onMapReady(naverMap: NaverMap) {
//        val lat = 37.55251509007124
//        val lon = 126.97246600086454
//        // 초기 지도 설정
//        val options = NaverMapOptions()
//            .camera(CameraPosition(LatLng(lat, lon), 8.0))
//            .mapType(NaverMap.MapType.Terrain)
//        //  건물 레이어 그룹을 비활성화하고 대중교통 레이어 그룹을 활성화
//        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false)
//        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
//
//        // 마커 추가
//        val marker = Marker()
//        marker.position = LatLng(37.55251509007124, 126.97246600086454)
//        marker.map = naverMap
//
//    }
//}