package net.akshat.githubbrowser.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import net.akshat.githubbrowser.fragments.BranchesFragment
import net.akshat.githubbrowser.fragments.IssuesFragment

class ViewPagerAdapter(
    fragmentManger: FragmentManager,
    lifecycle: Lifecycle,
    _orgName: String,
    _repoName: String,
    _desc: String,
    _tab: TabLayout
) :
    FragmentStateAdapter(fragmentManger, lifecycle) {

    private val orgName = _orgName
    private val repoName = _repoName
    private val desc = _desc
    private val tabLayout = _tab

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BranchesFragment(orgName, repoName, desc)
            1 -> IssuesFragment(orgName, repoName, tabLayout)
            else -> Fragment()
        }
    }

}