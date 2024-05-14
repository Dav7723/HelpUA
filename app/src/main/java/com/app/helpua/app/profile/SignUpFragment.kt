package com.app.helpua.app.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.helpua.databinding.FragmentSignUpBinding
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel by activityViewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        subscribeToFlow()
        bindView()
        return binding.root
    }

    private fun subscribeToFlow() {
        lifecycleScope.launch {
            viewModel.signUpFlow.collect {
                when (it) {
                    is Results.Success -> {
                        Toast.makeText(requireContext(), "User Authorized", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
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
        btnSignUp.setOnClickListener {
            viewModel.signUp(
                password = etPassword.text.toString(),
                name = etName.text.toString(),
                email = etEmail.text.toString()
            )
        }

        btnOnBackPressed.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
