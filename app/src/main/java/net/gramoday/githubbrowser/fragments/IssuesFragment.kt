package net.gramoday.githubbrowser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import net.gramoday.githubbrowser.R
import net.gramoday.githubbrowser.adapters.BranchesAdapter
import net.gramoday.githubbrowser.adapters.IssuesAdapter
import net.gramoday.githubbrowser.databinding.FragmentBranchesBinding
import net.gramoday.githubbrowser.databinding.FragmentIssuesBinding
import net.gramoday.githubbrowser.entities.Issues

class IssuesFragment(_orgName:String, _repoName:String) : Fragment() {

    private var _binding: FragmentIssuesBinding? = null
    private val binding get() = _binding!!

    private val orgName=_orgName
    private val repoName=_repoName

    private val issues= mutableListOf<Issues>()

    private lateinit var adapter: IssuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIssuesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView=binding.issuesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        adapter= IssuesAdapter(requireContext())
        recyclerView.adapter=adapter

        adapter.onItemClick={_,pos->

        }
        getIssuesData()
    }

    private fun getIssuesData()
    {
        val url="https://api.github.com/repos/$orgName/$repoName/issues?state=open"
        val queue= Volley.newRequestQueue(requireContext())

        val jsonArrayRequest=object: JsonArrayRequest(
            Method.GET,url,null,
            {
                issues.clear()
                for(i in 0 until it.length())
                {
                    val jsonObject=it.getJSONObject(i)
                    val temp=Issues(
                        jsonObject.getString("title"),
                        jsonObject.getJSONObject("user").getString("avatar_url"),
                        jsonObject.getJSONObject("user").getString("login")
                    )
                    issues.add(temp)
//                    Toast.makeText(requireContext(),jsonObject.getString("name"),Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility=View.GONE
                adapter.updateIssuesItems(issues)
            },
            {
                Toast.makeText(requireContext(),"Some Error Occurred $it", Toast.LENGTH_SHORT).show()
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