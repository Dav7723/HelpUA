package com.app.helpua.app.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.helpua.databinding.FragmentSignInBinding
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    private val viewModel by activityViewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        bindView()
        subscribeToFlow()
        return binding.root
    }


    private fun subscribeToFlow() {
        lifecycleScope.launch {
            viewModel.signInFlow.collect {
                when (it) {
                    is Results.Success -> {
                        Toast.makeText(requireContext(), "User Authorized", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
                    }

                    is Results.Error -> {

                        Toast.makeText(
                            requireContext(),
                            it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun bindView() = with(binding) {
        btnSignIn.setOnClickListener {
            viewModel.signIn(password = etPassword.text.toString(), email = etEmail.text.toString())
        }

        btnOnBackPressed.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

}