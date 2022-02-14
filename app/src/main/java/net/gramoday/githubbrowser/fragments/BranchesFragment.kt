package net.gramoday.githubbrowser.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import net.gramoday.githubbrowser.adapters.BranchesAdapter
import net.gramoday.githubbrowser.databinding.FragmentBranchesBinding
import net.gramoday.githubbrowser.viewModels.RepoViewModel


class BranchesFragment (_orgName:String, _repoName:String): Fragment() {

    private var _binding: FragmentBranchesBinding? = null
    private val binding get() = _binding!!

    private  var orgName=_orgName
    private  var repoName=_repoName

    private var branches= mutableListOf<String>()

    private lateinit var adapter:BranchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBranchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = binding.branchesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BranchesAdapter(requireContext())
        recyclerView.adapter = adapter

        adapter.onItemClick={_,pos->

        }

        getBranchData()
    }

    private fun getBranchData()
    {
        val url="https://api.github.com/repos/${orgName}/${repoName}/branches"
        val queue= Volley.newRequestQueue(requireContext())
//        Toast.makeText(requireContext(),"Debug: Making API call",Toast.LENGTH_SHORT).show()
        val jsonArrayRequest=object:JsonArrayRequest(
            Method.GET,url,null,
            {
                branches.clear()
                for(i in 0 until it.length())
                {
                    val jsonObject=it.getJSONObject(i)
                    branches.add(jsonObject.getString("name"))
//                    Toast.makeText(requireContext(),jsonObject.getString("name"),Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility=View.GONE
                adapter.updateBranchItems(branches)
            },
            {
                Toast.makeText(requireContext(),"Some Error Occurred $it",Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/vnd.github.v3+json"
                return headers
            }
        }
        queue.add(jsonArrayRequest)
    }

}