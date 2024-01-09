package softeer.tenten.fragments.moreInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage


class MoreInfoFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private var lat: Double = 37.55251509007124
    private var lon: Double = 126.97246600086454

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            softeer.tenten.R.layout.fragment_more_info,
            container, false
        ) as ViewGroup


        mapView = rootView.findViewById<View>(softeer.tenten.R.id.map_view) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return rootView
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        // 배경 지도 선택
//        naverMap.mapType = NaverMap.MapType.Satellite

        //  건물 레이어 그룹을 비활성화하고 대중교통 레이어 그룹을 활성화
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false)
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)


        // 위치 및 각도 조정
        val cameraPosition = CameraPosition(
            LatLng(lat, lon),  // 위치 지정
            17.0,  // 줌 레벨
//            45.0,  // 기울임 각도
//            45.0 // 방향
        )
        naverMap.cameraPosition = cameraPosition

        // 마커 추가
        val marker = Marker()
        marker.icon = OverlayImage.fromResource(softeer.tenten.R.drawable.icon_marker)
        marker.position = LatLng(lat, lon)
        marker.map = naverMap
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
