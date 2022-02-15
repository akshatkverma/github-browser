package net.akshat.githubbrowser.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import net.akshat.githubbrowser.databinding.FragmentAddRepoBinding
import net.akshat.githubbrowser.entities.Repo
import net.akshat.githubbrowser.viewModels.RepoViewModel


class AddRepoFragment : Fragment() {

    private var _binding: FragmentAddRepoBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: RepoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                Application()
            )
        )[RepoViewModel::class.java]

        setToolBar()

        binding.addRepoButton.setOnClickListener {
            binding.addRepoButton.visibility=View.GONE
            binding.addProgress.visibility=View.VISIBLE
            val org = binding.ownerOrgName.text.toString()
            val repo = binding.repoName.text.toString()
            getRepo(org,repo)
        }
    }

    private fun getRepo(org:String, repo:String)
    {
        val queue= Volley.newRequestQueue(requireContext())
        val url = "https://api.github.com/repos/$org/$repo"

        val jsonObjectRequest=object:JsonObjectRequest(
            Method.GET,url,null,
                {response->
                    Toast.makeText(requireContext(),response["description"].toString(),Toast.LENGTH_LONG).show()
                    viewModel.insertRepo(Repo(org,repo,response["description"].toString()))
                    val action=AddRepoFragmentDirections.actionAddRepoFragmentToRepoFragment()
                    findNavController().navigate(action)
                },
                {
                    binding.addRepoButton.visibility=View.VISIBLE
                    binding.addProgress.visibility=View.GONE
//                    Toast.makeText(requireContext(),"Couldn't find the repository...",Toast.LENGTH_LONG).show()
                    binding.ownerOrgName.setText("")
                    binding.repoName.setText("")
                }
            )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/vnd.github.v3+json"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }

    private fun setToolBar() {
        val toolbar = binding.toolbars
        toolbar.toolbarTitle.text = "Add Repository"
        toolbar.toolbarSubtitle.visibility = View.GONE
        toolbar.addButton.visibility = View.INVISIBLE
        toolbar.deleteButton.visibility=View.INVISIBLE
        toolbar.openBrowser.visibility=View.INVISIBLE

        toolbar.backButton.setOnClickListener {
            val action = AddRepoFragmentDirections.actionAddRepoFragmentToRepoFragment()
            findNavController().navigate(action)
        }
    }

}