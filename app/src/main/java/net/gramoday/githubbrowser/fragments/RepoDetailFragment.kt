package net.gramoday.githubbrowser.fragments

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import net.gramoday.githubbrowser.R
import net.gramoday.githubbrowser.adapters.ViewPagerAdapter
import net.gramoday.githubbrowser.databinding.FragmentRepoBinding
import net.gramoday.githubbrowser.databinding.FragmentRepoDetailBinding
import net.gramoday.githubbrowser.entities.Repo
import net.gramoday.githubbrowser.viewModels.RepoViewModel


class RepoDetailFragment : Fragment() {

    private var _binding: FragmentRepoDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var orgName: String
    private lateinit var repoName: String
    private lateinit var desc: String
    private lateinit var pos: String

    private lateinit var viewModel:RepoViewModel

    companion object {
        const val ORGNAME = "orgName"
        const val REPONAME = "repoName"
        const val DESC = "description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orgName = it.getString(ORGNAME).toString()
            repoName = it.getString(REPONAME).toString()
            desc = it.getString(DESC).toString()
            pos=it.getString("pos").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle,pos.toInt(), orgName, repoName)
        binding.repoName.text = orgName+"/"+repoName
        binding.descriptionRepo.text = desc

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = "Branches"
                1 -> tab.text = "Issues"
            }
        }.attach()

        setToolBar()

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                Application()
            )
        )[RepoViewModel::class.java]
    }

    private fun setToolBar()
    {
        val toolbar=binding.toolbars
        toolbar.toolbarSubtitle.visibility=View.GONE
        toolbar.toolbarTitle.text="Details"
        toolbar.addButton.visibility=View.GONE
        toolbar.openBrowser.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            val uri="https://github.com/"+orgName+"/"+repoName
            openURL.data = Uri.parse(uri)
            startActivity(openURL)
        }
        toolbar.deleteButton.setOnClickListener {
            viewModel.deleteRepo(Repo(orgName,repoName,desc))
            val action=RepoDetailFragmentDirections.actionRepoDetailFragmentToRepoFragment()
            findNavController().navigate(action)
        }
    }

}