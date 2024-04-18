package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.tabs.TabLayout
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCenterListBinding

class CenterFragment : Fragment() {

    lateinit var fragmentCenterBinding: FragmentCenterBinding
    lateinit var recyclerView: RecyclerView


    val centers = listOf(
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCenterBinding = FragmentCenterBinding.inflate(inflater, container, false)
        val view = fragmentCenterBinding.root


        // Chip 색상 변경 로직 유지
        val purpleColor = Color.parseColor("#800080")
        val defaultColor = ColorStateList.valueOf(Color.TRANSPARENT)
        val chipClickListener = View.OnClickListener { v ->
            val chip = v as Chip
            if (chip.chipBackgroundColor?.defaultColor != purpleColor) {
                chip.chipBackgroundColor = ColorStateList.valueOf(purpleColor)
            } else {
                chip.chipBackgroundColor = defaultColor
            }
        }

        fragmentCenterBinding.chipDistance.setOnClickListener(chipClickListener)
        fragmentCenterBinding.chipDailyPass.setOnClickListener(chipClickListener)
        fragmentCenterBinding.chipDiscount.setOnClickListener(chipClickListener)

        setupRecyclerView()

        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCenterBinding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> showHealth()
                    1 -> replaceFragmentWith(SelectPilatesFragment())
                    2 -> replaceFragmentWith(SelectSwimmingFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Not needed for this implementation
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Not needed for this implementation
            }
        })
    }

    private fun showHealth() {
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        fragmentCenterBinding.gymListRecylcler.apply {
            adapter = CenterAdapter(centers, object : CenterAdapter.OnItemClickListener {
                override fun onItemClick(center:CenterGym) {
                    val fragment = InfoCenterFragment().apply {
                        arguments = Bundle().apply {
                            putString("centerName", center.name)
                        }
                    }
                    replaceFragment(fragment)
                }
            })
            layoutManager = LinearLayoutManager(context)
            val deco = MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }
    private fun replaceFragmentWith(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_home, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_center, fragment)
            .addToBackStack(null)
            .commit()
    }


    class CenterAdapter(private val centerList: List<CenterGym>, private val listener: OnItemClickListener) : RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {
        interface OnItemClickListener {
            fun onItemClick(center: CenterGym)
        }

        class CenterViewHolder(val binding: RowCenterListBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
            val binding = RowCenterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CenterViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
            val center = centerList[position]
            holder.binding.imageViewCenterPicture.setImageResource(center.imageResourceId)
            holder.binding.textViewNotice.text = center.notice
            holder.binding.textViewCategory.text = center.category
            holder.binding.textViewCenterName.text = center.name
            holder.binding.textViewDistance.text = center.address
        }



        override fun getItemCount() = centerList.size
    }
}

data class CenterGym(
    val name: String,
    val notice: String,
    val category: String,
    val address: String,
    val imageResourceId: Int
)