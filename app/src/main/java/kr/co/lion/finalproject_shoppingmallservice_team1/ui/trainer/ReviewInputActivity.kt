package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityReviewInputBinding

class ReviewInputActivity : AppCompatActivity() {

    lateinit var activityReviewInputBinding: ActivityReviewInputBinding
    val photoList = listOf(
        R.drawable.photo,
        R.drawable.photo_permission,
        R.drawable.photo
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityReviewInputBinding = ActivityReviewInputBinding.inflate(layoutInflater)
        setContentView(activityReviewInputBinding.root)


        settingToolbar()
        settingButton()
        settingTextInputLayout()
        settingImage()
    }

    fun settingToolbar() {
        activityReviewInputBinding.apply {
            toolbarReviewInput.apply {
                setNavigationIcon(R.drawable.close)
                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }

    fun settingButton(){
        activityReviewInputBinding.apply {
            reviewAddButton.apply {
                setOnClickListener {
                    finish()
                }
            }
        }
    }

    fun settingTextInputLayout(){
        activityReviewInputBinding.apply {
            editTextText2.apply {

            }
        }

    }

    fun settingImage(){
        activityReviewInputBinding.apply {
            //reviewImageView1.visibility = View.INVISIBLE
            reviewImageView2.visibility = View.INVISIBLE
            reviewImageView3.visibility = View.INVISIBLE
            reviewImageView4.visibility = View.INVISIBLE

            deleteImageButton2.visibility = View.GONE
            deleteImageButton3.visibility = View.GONE
            deleteImageButton4.visibility = View.GONE
        }
    }

}