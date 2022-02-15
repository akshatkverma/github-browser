package net.akshat.githubbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import net.akshat.githubbrowser.adapters.CommitsAdapter
import net.akshat.githubbrowser.databinding.FragmentBranchCommitBinding
import net.akshat.githubbrowser.entities.Commits

class BranchCommitFragment : Fragment() {

    private var _binding: FragmentBranchCommitBinding? = null
    private val binding get() = _binding!!

    private lateinit var orgName: String
    private lateinit var repoName: String
    private lateinit var branch: String
    private lateinit var description: String

    private lateinit var adapter: CommitsAdapter

    private var commits = mutableListOf<Commits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orgName = it.getString("orgName").toString()
            repoName = it.getString("repoName").toString()
            branch = it.getString("branch").toString()
            description = it.getString("description").toString()
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action =
                BranchCommitFragmentDirections.actionBranchCommitFragmentToRepoDetailFragment(
                    orgName = orgName,
                    repoName = repoName,
                    description = description
                )
            findNavController().navigate(action)
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBranchCommitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.commitsRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CommitsAdapter(requireContext())
        recyclerView.adapter = adapter

        getCommitData()
        setToolBar()

//        Toast.makeText(requireContext(),description,Toast.LENGTH_SHORT).show()
    }


    private fun getCommitData() {
        val url = "https://api.github.com/repos/${orgName}/${repoName}/commits?sha=${branch}"
        val queue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = object : JsonArrayRequest(
            Method.GET, url, null,
            {
                commits.clear()
                for (i in 0 until it.length()) {
                    val jsonObject = it.getJSONObject(i)
                    val temp = Commits(
                        jsonObject.getString("sha"),
                        jsonObject.getJSONObject("commit").getJSONObject("author").getString("date"),
                        jsonObject.getJSONObject("commit").getString("message"),
                        jsonObject.getJSONObject("author").getString("login"),
                        jsonObject.getJSONObject("commit").getJSONObject("author").getString("name"),
                        jsonObject.getJSONObject("author").getString("avatar_url"),
                    )
                    commits.add(temp)
                }
                binding.progressBar.visibility = View.GONE
                adapter.updateCommits(commits)
            },
            {
                Toast.makeText(requireContext(), "Some Error Occurred $it", Toast.LENGTH_SHORT)
                    .show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/vnd.github.v3+json"
                return headers
            }
        }
        queue.add(jsonArrayRequest)
    }

    private fun setToolBar() {
        binding.toolbars.backButton.setOnClickListener {
            val action =
                BranchCommitFragmentDirections.actionBranchCommitFragmentToRepoDetailFragment(
                    orgName = orgName,
                    repoName = repoName,
                    description = description
                )
            findNavController().navigate(action)
        }

        binding.toolbars.deleteButton.visibility = View.INVISIBLE
        binding.toolbars.openBrowser.visibility = View.INVISIBLE
        binding.toolbars.addButton.visibility = View.INVISIBLE
        binding.toolbars.toolbarTitle.text = "Commits"
        binding.toolbars.toolbarSubtitle.text = branch
    }




}