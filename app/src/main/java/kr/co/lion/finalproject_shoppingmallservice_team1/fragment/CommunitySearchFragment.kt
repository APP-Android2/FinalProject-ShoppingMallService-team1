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
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunitySearchBinding

class CommunitySearchFragment : Fragment() {
    lateinit var fragmentCommunitySearchBinding: FragmentCommunitySearchBinding
    lateinit var navigationActivity:NavigationActivity
    lateinit var communityFragment: CommunityFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCommunitySearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_search, container, false)
        communityFragment = parentFragment as CommunityFragment
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()

        return fragmentCommunitySearchBinding.root
    }

    fun settingToolbar() {
        fragmentCommunitySearchBinding.apply {
            toolbarCommunitySearch.apply {
                // 검색창 클릭 후 화면이므로 키보드가 올라가고 검색창의 커서 활성화
                Tools.showSoftInput(navigationActivity, searchViewCommunitySearch)

                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    // 뒤로 가면서 키보드 숨김
                    Tools.hideSoftInput(navigationActivity)
                    communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_SEARCH_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_SEARCH_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}