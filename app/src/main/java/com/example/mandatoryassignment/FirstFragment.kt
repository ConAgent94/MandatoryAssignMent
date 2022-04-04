package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandatoryassignment.databinding.FragmentFirstBinding
import com.example.mandatoryassignment.models.ItemsViewModel
import com.example.mandatoryassignment.models.MyAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val itemsViewModel: ItemsViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            Log.d("APPLE", items.toString())
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = MyAdapter(items) { position ->
                    val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }


        itemsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        itemsViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            itemsViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        
        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}