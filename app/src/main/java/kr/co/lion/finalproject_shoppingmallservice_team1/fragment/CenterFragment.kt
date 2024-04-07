package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kr.co.lion.finalproject_shoppingmallservice_team1.R

class CenterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_center, container, false)

        val purpleColor = Color.parseColor("#800080") // 보라색 코드
        val defaultColor = ColorStateList.valueOf(Color.TRANSPARENT) // 기본 색상을 설정

        // 각 Chip을 찾고 클릭 리스너 설정
        val chipDistance = view.findViewById<Chip>(R.id.chip_distance)
        val chipDailyPass = view.findViewById<Chip>(R.id.chip_daily_pass)
        val chipDiscount = view.findViewById<Chip>(R.id.chip_discount)

        val chipClickListener = View.OnClickListener { v ->
            val chip = v as Chip
            // 현재 칩 색상 확인 후 색상 변경
            if (chip.chipBackgroundColor?.defaultColor != purpleColor) {
                chip.chipBackgroundColor = ColorStateList.valueOf(purpleColor)
            } else {
                chip.chipBackgroundColor = defaultColor
            }
        }

        chipDistance.setOnClickListener(chipClickListener)
        chipDailyPass.setOnClickListener(chipClickListener)
        chipDiscount.setOnClickListener(chipClickListener)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        val recyclerView: RecyclerView = view.findViewById(R.id.gym_list_recylcler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // 비트맵 이미지 목록을 생성하거나 불러옵니다.
        val images = mutableListOf<Bitmap>()
        val resources = context?.resources
        resources?.let {
            val imageIds = arrayOf(R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background) // drawable 폴더에 있는 이미지들의 ID
            for (id in imageIds) {
                val bitmap = BitmapFactory.decodeResource(resources, id)
                images.add(bitmap)
            }
        }

        recyclerView.adapter = ImageAdapter(images)
    }

    class ImageAdapter(private val images: List<Bitmap>) :
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        // 뷰 홀더 정의
        class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.imageViewRow1)
        }

        // 새로운 뷰 홀더 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_health_list, parent, false)
            return ImageViewHolder(view)
        }

        // 뷰 홀더에 데이터 바인딩
        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.imageView.setImageBitmap(images[position])
        }

        // 데이터셋 크기 반환
        override fun getItemCount() = images.size
    }
}
