package com.app.helpua.app.home.details

import android.os.Bundle
import android.support.annotation.ColorRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.helpua.R
import com.app.helpua.app.GUMMANITAR_TYPE
import com.app.helpua.app.MEDICAL_TYPE
import com.app.helpua.app.PSYHIC_TYPE
import com.app.helpua.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        bindView()
        return binding.root
    }

    private fun setBackgroundWithTypeTextColor(@ColorRes color: Int) = with(binding) {
        tvTitle.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                color
            )
        )
        tvText.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                color
            )
        )
    }

    private fun bindView() = with(binding) {
        Picasso.get().load(args.image.ifEmpty { "error" })
            .placeholder(R.color.gray)
            .error(R.drawable.default_holder)
            .into(ivImage)

        tvText.text = args.text
        tvTitle.text = args.title

        ivClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        when (args.type) {
            MEDICAL_TYPE -> {
                setBackgroundWithTypeTextColor(R.color.medical_type_color)
                tvType.text = "Медична допомога"
            }

            GUMMANITAR_TYPE -> {
                setBackgroundWithTypeTextColor(R.color.gumanitar_type_color)
                tvType.text = "Гуманітарна допомога"
            }

            PSYHIC_TYPE -> {
                setBackgroundWithTypeTextColor(R.color.psyhical_type_color)
                tvType.text = "Психологічна допомога"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}