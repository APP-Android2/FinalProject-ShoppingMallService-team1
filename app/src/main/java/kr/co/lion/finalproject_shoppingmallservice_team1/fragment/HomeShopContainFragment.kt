package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_SHOP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeShopContainBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowShoppingcartBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeShopContainViewModel
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeShopViewModel

class HomeShopContainFragment : Fragment() {
    lateinit var fragmentHomeShopContainBinding: FragmentHomeShopContainBinding
    lateinit var shoppingCartActivity: ShoppingCartActivity
    lateinit var homeShopContainViewModel: HomeShopContainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeShopContainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_shop_contain, container, false)
        homeShopContainViewModel = HomeShopContainViewModel()
        fragmentHomeShopContainBinding.homeShopContainViewModel = homeShopContainViewModel
        fragmentHomeShopContainBinding.lifecycleOwner = this

        shoppingCartActivity = activity as ShoppingCartActivity

        settingToolbar()
        settingReclyerViewHomeShop()
        settingEvent()

        return fragmentHomeShopContainBinding.root
    }

    fun settingToolbar() {
        fragmentHomeShopContainBinding.apply {
            toolbarHomeShopContain.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    shoppingCartActivity.removeFragment(HOME_SHOP_FRAGMENT_NAME.SHOP_CONTAIN_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingEvent(){
        fragmentHomeShopContainBinding.apply {
            // 결제하기 버튼 클릭
            buttonHomeShopPayment.apply {
                setOnClickListener {
                    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                    materialAlertDialogBuilder.setTitle("결제")
                    materialAlertDialogBuilder.setMessage("결제 완료했습니다!")
                    // 확인 버튼 누르면  다시 NavigationActivity로 돌아감
                    materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        val intent = Intent(shoppingCartActivity,  NavigationActivity::class.java)
                        startActivity(intent)
                        shoppingCartActivity.finish()
                    }
                    materialAlertDialogBuilder.show()
                }
            }
        }
    }


    // 장바구니 목록
    fun settingReclyerViewHomeShop(){
        fragmentHomeShopContainBinding.apply {
            recyclerViewHomeShop.apply {
                adapter = HomeShopRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(shoppingCartActivity)
            }
        }
    }

    inner class HomeShopRecyclerViewAdapter:RecyclerView.Adapter<HomeShopRecyclerViewAdapter.HomeShopViewHolder>(){
        inner class HomeShopViewHolder(rowShoppingcartBinding: RowShoppingcartBinding):RecyclerView.ViewHolder(rowShoppingcartBinding.root){
            val rowShoppingcartBinding:RowShoppingcartBinding

            init {
                this.rowShoppingcartBinding = rowShoppingcartBinding
                rowShoppingcartBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeShopViewHolder {
            val rowShoppingcartBinding = RowShoppingcartBinding.inflate(layoutInflater)
            val homeShopViewHolder = HomeShopViewHolder(rowShoppingcartBinding)
            return homeShopViewHolder
        }

        override fun getItemCount(): Int {
            return 4
        }

        override fun onBindViewHolder(holder: HomeShopViewHolder, position: Int) {
            holder.rowShoppingcartBinding.textViewHomeShopCenterName.text = "피트모아 헬스장"
            holder.rowShoppingcartBinding.textViewHomeShopMembershipName.text = "1개월 회원권"
            holder.rowShoppingcartBinding.textViewShopPrice.text = "190000원"
        }
    }
}