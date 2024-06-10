package com.gege.gitgege.data.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gege.gitgege.FollowersFragment
import com.gege.gitgege.FollowingFragment
import com.gege.gitgege.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PagerAdapter(private val context: Context, private val bundle: Bundle) :
    FragmentStateAdapter(context as FragmentActivity), TabLayoutMediator.TabConfigurationStrategy {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment().apply { arguments = bundle }
            1 -> FollowingFragment().apply { arguments = bundle }
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = context.resources.getString(TAB_TITLES[position])
    }
}
