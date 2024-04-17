package kr.co.lion.finalproject_shoppingmallservice_team1.ui.login

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_SIGNUP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginSignUpBinding

class LoginSignUpFragment : Fragment() {

    lateinit var fragmentLoginSignUpBinding: FragmentLoginSignUpBinding
    lateinit var loginActivity: LoginActivity

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginSignUpBinding = FragmentLoginSignUpBinding.inflate(inflater)
        loginActivity = activity as LoginActivity

        replaceFragment(LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP1_FRAGMENT, false, false, null)
        settingProgressBar(0, 50)

        return fragmentLoginSignUpBinding.root
    }

    fun settingToolbar(loginSignUpFragment : LOGIN_SIGNUP_FRAGMENT_NAME){

        fragmentLoginSignUpBinding.loginsignupToolbar.setNavigationOnClickListener {

            when(loginSignUpFragment.num){

                LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP1_FRAGMENT.num -> {
                    loginActivity.removeFragment(LOGIN_FRAGMENT_NAME.LOGIN_SIGNUP_FRAGMENT)
                }

                LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP2_FRAGMENT.num -> {
                    removeFragment(LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP2_FRAGMENT)
                    settingProgressBar(100, 50)
                }

            }
        }
    }

    fun settingProgressBar(start:Int, end:Int){

        ObjectAnimator.ofInt(fragmentLoginSignUpBinding.loginsignupProgressBar, "progress", start, end).apply {
            duration = 500 // 애니메이션 시간
            interpolator = LinearInterpolator() // 애니메이션 속도를 일정하게 설정
            start() // 애니메이션 시작
        }
    }

    fun replaceFragment(name: LOGIN_SIGNUP_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction()

        if(newFragment != null){
            oldFragment = newFragment
        }

        newFragment = when(name){

            LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP1_FRAGMENT -> {
                LoginSignUp1Fragment()
            }

            LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP2_FRAGMENT -> {
                LoginSignUp2Fragment()
            }

        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){
                if(oldFragment != null){
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈때 old의 애니메이션
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈때 new의 애니메이션
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            // Fragment를 교체한다.(이전 Fragment가 없으면 새롭게 추가하는 역할을 수행한다)
            // 첫 번째 매개 변수 : Fragment를 배치할 FragmentContainerView의 ID
            // 두 번째 매개 변수 : 보여주고하는 Fragment 객체를
            fragmentTransaction.replace(R.id.loginsignup_fragmentContainerView, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if(addToBackStack == true){
                // BackStack 포함 시킬때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: LOGIN_SIGNUP_FRAGMENT_NAME){
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        childFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}