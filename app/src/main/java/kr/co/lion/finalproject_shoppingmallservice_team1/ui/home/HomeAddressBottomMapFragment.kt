package kr.co.lion.finalproject_shoppingmallservice_team1.ui.home

import android.Manifest
import android.app.Dialog
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.SystemClock
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeAddressBottomMapBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.viewmodel.HomeAddressBottomMapViewModel

class HomeAddressBottomMapFragment : BottomSheetDialogFragment() {
    lateinit var fragmentHomeAddressBottomMapBinding: FragmentHomeAddressBottomMapBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeAddressBottomMapViewModel: HomeAddressBottomMapViewModel

    // 위치 정보를 관리하는 객체
    lateinit var locationManager: LocationManager
    // 위치 측정이 성공하면 동작할 리스너
    var gpsLocationListener: MyLocationListener? = null
    var networkLocationListener: MyLocationListener? = null

    // 구글 지도 객체를 담을 프로퍼티
    lateinit var homeAddressBottomGoogleMap: GoogleMap

    // 현재 사용자 위치를 표시하기 위한 마커
    var myMarker: Marker? = null

    // 현재 사용자의 위치를 가지고 있는 객체
    var userLocation: Location? = null

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeAddressBottomMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_address_bottom_map, container, false)
        homeAddressBottomMapViewModel = HomeAddressBottomMapViewModel()
        fragmentHomeAddressBottomMapBinding.homeAddressBottomMapViewModel = homeAddressBottomMapViewModel
        fragmentHomeAddressBottomMapBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        // 권한 확인
        navigationActivity.requestPermissions(permissionList, 0)

        settingToolbar()

        return fragmentHomeAddressBottomMapBinding.root
    }

    fun settingToolbar(){

        Log.d("test1234", "t1")

        fragmentHomeAddressBottomMapBinding.apply {
            toolbarAddressMap.apply {

                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    SystemClock.sleep(200)
                    // 프래그먼트와 프래그먼트 사이 전환이므로 parentFragmentManager 이용
                    parentFragmentManager.popBackStack()
                }
                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    // Bottom Sheet 설정
    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        Log.d("test1234", "t2")

        // 다이얼로그를 받는다.
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            // 높이를 설정한다.
            setBottomSheetHeight(bottomSheetDialog)

            Log.d("test1234", "test1234")
            settingGoogleMaps()

        }

        return dialog
    }

    // BottomSheet의 높이를 설정해준다.
    fun setBottomSheetHeight(bottomSheetDialog: BottomSheetDialog){
        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)
        // 높이를 설정한다.
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // BottomSheet의 높이를 구한다(화면 액정의 85% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.9).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환하는 메서드
    fun getWindowHeight() : Int {
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세로 길이 정보를 담아준다.
        navigationActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }


    // 구글 지도 세팅
    fun settingGoogleMaps(){

        Log.d("test1234", "구글맵 호출")

        // MapFragment 객체를 가져온다.
        val supportMapFragment = navigationActivity.supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment

        // 리스너를 설정한다.
        // 구글 지도 사용이 완료되면 동작한다.
        supportMapFragment.getMapAsync {

            Log.d("test1234", "구글맵 사용 완료 시")

            // 구글 지도 객체를 담아준다.
            homeAddressBottomGoogleMap = it

            // 확대 / 축소 버튼
            // homeAddressBottomGoogleMap.uiSettings.isZoomControlsEnabled = true

            // 지도의 타입
            // 데이터 사용량 : NONE < NORMAL < TERRAIN < SATELLITE < HYBRID
            // 지도 뜨는 속도 : HYBRID > SATELLITE > TERRAIN > NORMAL > NONE
            homeAddressBottomGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            // 위치 정보 관리 객체
            locationManager = navigationActivity.getSystemService(LOCATION_SERVICE) as LocationManager

            // 위치정보 사용 권한 허용 여부 확인
            val a1 = ActivityCompat.checkSelfPermission(navigationActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            val a2 = ActivityCompat.checkSelfPermission(navigationActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

            // 모든 권한이 허용되어 있다면
            if (a1 && a2){
                // 저장되어 있는 위치 값을 가져온다.
                val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                // 현재 위치를 지도에 표시한다.
                if (location1 != null){
                    settingMyLocation(location1)
                } else if (location2 != null){
                    settingMyLocation(location2)
                }

                // 현재 위치를 측정한다.
                gettingMyLocation()
            }
        }
    }

    // 현재 위치를 가져오는 메소드
    fun gettingMyLocation(){
        // 위치정보 사용 권한 허용 여부 확인
        val a1 = ActivityCompat.checkSelfPermission(navigationActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
        val a2 = ActivityCompat.checkSelfPermission(navigationActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED

        if (a1 || a2){
            // 하나라도 참인 경우
            return
        }

        // GPS 프로바이더가 사용 가능하다면
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true){

            if (gpsLocationListener == null){
                gpsLocationListener = MyLocationListener()
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, gpsLocationListener!!)
            }
        }
        // 네트워크 프로바이더가 사용 가능하다면
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true){

            if (networkLocationListener == null){
                networkLocationListener = MyLocationListener()
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, networkLocationListener!!)
            }
        }
    }

    // 위치 측정이 성공하면 동작하는 리스너
    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            // 사용자 위치 정보 프로바이더로 분기한다.
            when(location.provider){
                // GPS 프로바이더라면
                LocationManager.GPS_PROVIDER -> {
                    // GPS 리스너 연결을 해제해 준다.
                    locationManager.removeUpdates(gpsLocationListener!!)
                    gpsLocationListener = null
                }
                // 네트워크 프로바이더라면
                LocationManager.NETWORK_PROVIDER -> {
                    // 네트워크 리스너 연결을 해제해 준다.
                    locationManager.removeUpdates(networkLocationListener!!)
                    networkLocationListener = null
                }
            }

            // 측정된 위치로 지도를 움직인다.
            settingMyLocation(location)
        }
    }

    // 지도의 위치를 설정하는 메소드
    fun settingMyLocation(location: Location){

        // 현재 사용자의 위치를 프로퍼티에 담아준다.
        userLocation = location

        // 위도와 경도를 관리하는 객체를 생성한다.
        val userLocation = LatLng(location.latitude, location.longitude)

        // 지도를 이동하기 위한 객체
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f)

        // 카메라를 이동시킨다.
        homeAddressBottomGoogleMap.animateCamera(cameraUpdate)

        // 현재 위치를 표시할 마커
        val markerOptions = MarkerOptions()
        markerOptions.position(userLocation)

        // 이미 마커가 있다면 제거한다.
        if (myMarker != null){
            myMarker?.remove()
            myMarker = null
        }

        // 마커를 지도에 표시해준다.
        myMarker = homeAddressBottomGoogleMap.addMarker(markerOptions)
    }

}