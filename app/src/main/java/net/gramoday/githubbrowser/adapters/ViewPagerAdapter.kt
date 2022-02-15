package net.gramoday.githubbrowser.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.gramoday.githubbrowser.fragments.BranchesFragment
import net.gramoday.githubbrowser.fragments.IssuesFragment

class ViewPagerAdapter(fragmentManger: FragmentManager, lifecycle: Lifecycle, _orgName:String, _repoName:String) :
    FragmentStateAdapter(fragmentManger, lifecycle) {

    private val orgName=_orgName
    private val repoName=_repoName

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position)
                {
                    0->BranchesFragment(orgName,repoName)
                    1->IssuesFragment(orgName,repoName)
                    else->Fragment()
                }
    }

}