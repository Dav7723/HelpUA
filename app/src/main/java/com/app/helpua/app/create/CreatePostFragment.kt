package com.app.helpua.app.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.helpua.app.GUMMANITAR_TYPE
import com.app.helpua.app.MEDICAL_TYPE
import com.app.helpua.app.PSYHIC_TYPE
import com.app.helpua.databinding.FragmentCreatePostBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CreatePostFragment : Fragment() {

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<CreatePostFragmentArgs>()

    private val viewModel by activityViewModel<CreatePostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(layoutInflater)


        bindView()

        return binding.root
    }


    private fun getType(): Int = with(binding) {
        return when {
            rbGumanitar.isChecked -> GUMMANITAR_TYPE
            rbMedical.isChecked -> MEDICAL_TYPE
            rbPsyhical.isChecked -> PSYHIC_TYPE
            else -> 0
        }
    }

    private fun bindView() = with(binding) {

        ivClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        btCreate.setOnClickListener {
            viewModel.createPost(
                userName = args.userName,
                title = etTitle.text.toString(),
                text = etText.text.toString(),
                image = etImageUrl.text.toString(),
                type = getType()

            )
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

}