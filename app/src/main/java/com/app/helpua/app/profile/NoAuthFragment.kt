package com.app.helpua.app.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.helpua.databinding.FragmentStartBinding


class NoAuthFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater)

        bindView()
        return binding.root
    }

    private fun bindView() = with(binding) {
        btnSignIn.setOnClickListener {
            findNavController().navigate(NoAuthFragmentDirections.actionStartFragmentToSignInFragment())
        }

        btnSignUp.setOnClickListener {
            findNavController().navigate(NoAuthFragmentDirections.actionStartFragmentToSignUpFragment())
        }

        btnOnBackPressed.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

}