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
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_SIGNUP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunityCommentContentBinding

class CommunityCommentContentFragment : Fragment() {
    lateinit var fragmentCommunityCommentContentBinding: FragmentCommunityCommentContentBinding
    lateinit var communityFragment: CommunityFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCommunityCommentContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_comment_content, container, false)
        communityFragment = parentFragment as CommunityFragment

        settingToolbar()
        handleBackPress()

        return fragmentCommunityCommentContentBinding.root
    }

    fun settingToolbar() {
        fragmentCommunityCommentContentBinding.apply {
            toolbarCommunityCommentContent.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_COMMENTCONTENT_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }
    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_COMMENTCONTENT_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}