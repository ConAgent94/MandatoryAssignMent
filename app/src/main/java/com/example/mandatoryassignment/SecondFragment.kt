package com.example.mandatoryassignment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatoryassignment.databinding.FragmentSecondBinding
import com.example.mandatoryassignment.models.ItemsViewModel
import com.example.mandatoryassignment.models.Item

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val itemsViewModel: ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val item = itemsViewModel[position]
        if (item == null) {
            binding.textviewMessage.text = "No item"
            return
        }
         //Bring data with you
        binding.editTextTitle.setText(item.title)
        binding.editTextPrice.setText(item.price.toString())


        // Go back button
        binding.buttonBack.setOnClickListener {
           /*findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)*/
            findNavController().popBackStack() //Tilbage hvor man kom fra
        }


        //Delete button
        binding.buttonDelete.setOnClickListener {
            itemsViewModel.delete(item.id)
            findNavController().navigate(R.id.FirstFragment)
           // findNavController().popBackStack()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}