package kr.co.lion.finalproject_shoppingmallservice_team1.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.alarm.AlarmActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.chat.ChatActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership.TransferMembershipActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.shoppingcart.ShoppingCartActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.viewmodel.HomeViewModel
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.viewmodel.RecyclerPopulatTrainerModel
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.viewmodel.RecyclerRecentCenterModel

class HomeFragment : Fragment() {
    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeViewModel: HomeViewModel
    lateinit var shoppingCartActivityLauncher:ActivityResultLauncher<Intent>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = HomeViewModel()
        fragmentHomeBinding.homeViewModel = homeViewModel
        fragmentHomeBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingAddress()
        settingSearch()
        settingCategory()
        startAroundCenter()
        startTransferMembershipActivity()

        initRecyclerPopularTrainer()
        initRecyclerRecentCenter()


        return fragmentHomeBinding.root
    }


    // 다른 액티비티 다녀온 후 변화를 반영하려면 onViewCreated()에서 작성해야 함
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 장바구니에서 다양한 운동 시설 보러가기 버튼 클릭 후 NavigationActivty(에서 HomeFragment)로 돌아왔을 때 실행
        val contract = ActivityResultContracts.StartActivityForResult()
        shoppingCartActivityLauncher = registerForActivityResult(contract){
            if(it != null){
                when(it.resultCode){
                    Activity.RESULT_OK -> {
                        if (it.data!= null){
                            // 데이터 얻음
                            val value = it?.data!!.getIntExtra("buttonHomeShopSwap", 0)

                            // 운동 센터로 화면 전환
                            navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.CENTER_FRAGMENT, false, true, null)
                        }
                    }
                }
            }
        }
    }

    fun settingToolbar(){
        fragmentHomeBinding.apply {
            toolbarHome.apply {
                setNavigationIcon(R.drawable.notifications)

                setNavigationOnClickListener {
                    val intent = Intent(navigationActivity, AlarmActivity::class.java)
                    startActivity(intent)
                    // finish()하지 않아도 됨 -> navigationActivity는 모든 화면의 상위 화면이므로 절대 종료시키면 안됨!
                }

                inflateMenu(R.menu.home_menu)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemHomeChat -> {
                            val intent = Intent(navigationActivity, ChatActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.menuItemHomeShopping -> {
                            val intent = Intent(navigationActivity, ShoppingCartActivity::class.java)
                            //intent.putExtra("home", 1)
                            shoppingCartActivityLauncher.launch(intent)
                        }
                    }
                    true
                }
            }
        }
    }

    // 현재 위치로 설정 클릭 시
    fun settingAddress(){
        fragmentHomeBinding.apply {
            btnHomeNowLocation.apply {
                setOnClickListener {
                    showHomeAddressBottomSheet()
                }
            }
        }

    }

    // BottomSheet 설정
    fun showHomeAddressBottomSheet(){
        val homeAddressBottomFragment = HomeAddressBottomFragment()
        homeAddressBottomFragment.show(navigationActivity.supportFragmentManager, "HomeAddressBottomSheet")
    }

    // 검색창 클릭 시 SearchActivity로 전환
    fun settingSearch(){
        fragmentHomeBinding.apply {
            searchBarHome.apply {
                setOnClickListener {
                    val intent = Intent(navigationActivity, SearchActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    fun settingCategory(){
        fragmentHomeBinding.cardViewHomeCategoryHealth.setOnClickListener {
            navigateToCenterFragment("health")
        }
        fragmentHomeBinding.cardViewHomeCategoryPilates.setOnClickListener {
            navigateToCenterFragment("pilates")
        }
        fragmentHomeBinding.cardViewHomeCategorySwim.setOnClickListener {
            navigateToCenterFragment("swim")
        }
        fragmentHomeBinding.cardViewHomeCategoryDailyTicket.setOnClickListener {
            navigateToCenterFragment("dailyTicket")
        }
        fragmentHomeBinding.cardViewHomeCategorySale.setOnClickListener {
            navigateToCenterFragment("sale")
        }
    }
    // CenterFragment로 이동하며 카테고리 데이터를 전달하는 함수
    private fun navigateToCenterFragment(category: String) {
        val bundle = Bundle().apply {
            putString("category", category)
        }
        // CenterFragment로 이동
        navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.CENTER_FRAGMENT, true, true, bundle)
    }

    fun startAroundCenter(){
        fragmentHomeBinding.cardViewHomeAroundCenter.setOnClickListener {
            navigateToCenterFragment("aroundCenter")
        }
    }

    fun startTransferMembershipActivity(){
        fragmentHomeBinding.cardViewHomeAssign.setOnClickListener {
            val intent = Intent(navigationActivity, TransferMembershipActivity::class.java)
            startActivity(intent)
        }
    }

    //인기있는 트레이너
    fun initRecyclerPopularTrainer() {
        val itemList = mutableListOf<RecyclerPopulatTrainerModel>()
        itemList.add(RecyclerPopulatTrainerModel(R.drawable.populartrainer1, "원 트레이너", "서울 중랑구 신내동"))
        itemList.add(RecyclerPopulatTrainerModel(R.drawable.populartrainer2, "투 트레이너", "서울 강남구 서초대로"))
        itemList.add(RecyclerPopulatTrainerModel(R.drawable.populartrainer3, "쓰리 트레이너", "서울 용산구 한남동"))
        itemList.add(RecyclerPopulatTrainerModel(R.drawable.populartrainer4, "포 트레이너", "서울 노원구 노원동"))

        val adapter = RecyclerPopularTrainerAdapter(itemList)
        fragmentHomeBinding.recyclerViewPopularTrainer.adapter = adapter
        fragmentHomeBinding.recyclerViewPopularTrainer.layoutManager = LinearLayoutManager(navigationActivity, LinearLayoutManager.HORIZONTAL, false)

        adapter.setItemClickListener(object: RecyclerPopularTrainerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(navigationActivity, "RecyclerView: ${itemList[position].trainerName}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    class RecyclerPopularTrainerAdapter(val items: MutableList<RecyclerPopulatTrainerModel>) :
        RecyclerView.Adapter<RecyclerPopularTrainerAdapter.ViewHolder>() {
        interface onItemClickListener {
            fun onItemClick(position: Int)
        }

        private lateinit var itemClickListener: onItemClickListener

        fun setItemClickListener(itemClickListener: onItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
        ): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_popular_trainer, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
            holder.bindItems(items[position])
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            fun bindItems(items: RecyclerPopulatTrainerModel) {
                val imageViewPopularTrainer = itemView.findViewById<ImageView>(R.id.imageViewPopularTrainer)
                val textViewPopularTrainerName = itemView.findViewById<TextView>(R.id.textViewPopularTrainerName)
                val textViewPopularTrainerAddress = itemView.findViewById<TextView>(R.id.textViewPopularTrainerAddress)

                imageViewPopularTrainer.setImageResource(items.image)
                textViewPopularTrainerName.text = items.trainerName
                textViewPopularTrainerAddress.text = items.address
            }
        }
    }


    // 최근 본 시설
    fun initRecyclerRecentCenter() {
        val itemList = mutableListOf<RecyclerRecentCenterModel>()
        itemList.add(RecyclerRecentCenterModel(R.drawable.recentcenter1, "원 운동센터", "서울 중랑구 신내동"))
        itemList.add(RecyclerRecentCenterModel(R.drawable.recentcenter2, "투 운동센터", "서울 강남구 서초대로"))
        itemList.add(RecyclerRecentCenterModel(R.drawable.recentcenter3, "쓰리 운동센터", "서울 용산구 한남동"))
        itemList.add(RecyclerRecentCenterModel(R.drawable.recentcenter4, "포 운동센터", "서울 노원구 노원동"))

        val adapter = RecyclerRecentCenterAdapter(itemList)
        fragmentHomeBinding.recyclerViewRecentCenter.adapter = adapter
        fragmentHomeBinding.recyclerViewRecentCenter.layoutManager = LinearLayoutManager(navigationActivity, LinearLayoutManager.HORIZONTAL, false)

        adapter.setItemClickListener(object: RecyclerRecentCenterAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(navigationActivity, "RecyclerView: ${itemList[position].centerName}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    class RecyclerRecentCenterAdapter(val items: MutableList<RecyclerRecentCenterModel>) :
        RecyclerView.Adapter<RecyclerRecentCenterAdapter.ViewHolder>() {
        interface onItemClickListener {
            fun onItemClick(position: Int)
        }

        private lateinit var itemClickListener: onItemClickListener

        fun setItemClickListener(itemClickListener: onItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
        ): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_recent_center, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
            holder.bindItems(items[position])
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            fun bindItems(items: RecyclerRecentCenterModel) {
                val imageViewRecentCenter = itemView.findViewById<ImageView>(R.id.imageViewRecentCenter)
                val textViewRecentCenterName = itemView.findViewById<TextView>(R.id.textViewRecentCenterName)
                val textViewRecentCenterAddress = itemView.findViewById<TextView>(R.id.textViewRecentCenterAddress)

                imageViewRecentCenter.setImageResource(items.image)
                textViewRecentCenterName.text = items.centerName
                textViewRecentCenterAddress.text = items.address
            }
        }
    }
}