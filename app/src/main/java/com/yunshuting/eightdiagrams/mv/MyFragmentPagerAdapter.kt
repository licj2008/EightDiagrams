package com.yunshuting.eightdiagrams.mv

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yunshuting.eightdiagrams.ui.YaoFragment

class MyFragmentPagerAdapter(fm: FragmentManager, private val fragments: List<YaoFragment>, private val titles: List<String>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): YaoFragment {
        return fragments[position]
    }
    override fun getCount(): Int {
        return fragments.size
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}