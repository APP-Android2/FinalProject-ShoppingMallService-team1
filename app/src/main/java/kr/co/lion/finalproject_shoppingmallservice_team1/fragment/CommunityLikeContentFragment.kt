package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.COMMUNITY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_SHOP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunityLikeContentBinding

class CommunityLikeContentFragment : Fragment() {
    lateinit var fragmentCommunityLikeContentBinding: FragmentCommunityLikeContentBinding
    lateinit var communityFragment: CommunityFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCommunityLikeContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_like_content, container, false)
        communityFragment = parentFragment as CommunityFragment

        settingToolbar()
        handleBackPress()

        return fragmentCommunityLikeContentBinding.root
    }
    fun settingToolbar() {
        fragmentCommunityLikeContentBinding.apply {
            toolbarCommunityLike.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_LIKECONTENT_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_LIKECONTENT_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}