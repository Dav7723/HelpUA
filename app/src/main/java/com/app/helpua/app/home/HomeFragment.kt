package com.app.helpua.app.home

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.MenuRes
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.helpua.R
import com.app.helpua.app.GUMMANITAR_TYPE
import com.app.helpua.app.MEDICAL_TYPE
import com.app.helpua.app.PSYHIC_TYPE
import com.app.helpua.app.home.adapters.HomeAdapter
import com.app.helpua.app.home.adapters.TitleAdapter
import com.app.helpua.app.home.adapters.TitleModel
import com.app.helpua.databinding.FragmentHomeBinding
import com.app.helpua.domain.models.Post
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<HomeViewModel>()
    private val listAdapter by lazy {
        HomeAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    author = it.author.toString(),
                    title = it.title.toString(),
                    text = it.text.toString(),
                    type = it.type ?: 1,
                    image = it.image.toString()
                )
            )
        }
    }
    private val titleAdapter by lazy {
        TitleAdapter(navigate = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStartFragment())
        }, showMenu = {
            showMenu(it, R.menu.sort)
        }, infoAction = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSecurityPolicyFragment())
        }, signOut = {
            showAlertDialog()
    })
    }
    private val mergeAdapter by lazy { ConcatAdapter(titleAdapter, listAdapter) }

    private var list = listOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        bindView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToFlow()
        viewModel.getIsAuth()
        viewModel.subcribeToPosts()
    }

    private fun bindView() = with(binding) {

        rvRoot.layoutManager = LinearLayoutManager(context)
        rvRoot.adapter = mergeAdapter

        btCreatePost.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCreatePostFragment(
                    viewModel.getUserName().toString()
                )
            )
        }

    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item?.itemId) {
                R.id.gumanitar -> {
                    sortPosts(type = GUMMANITAR_TYPE, list)
                }

                R.id.psyhical -> {
                    sortPosts(type = PSYHIC_TYPE, list)
                }

                R.id.medical -> {
                    sortPosts(type = MEDICAL_TYPE, list)
                }

                R.id.all -> {
                    listAdapter.submitList(list)
                }
            }
            true
        }
        // Show the popup menu.
        popup.show()
    }

    private fun sortPosts(type: Int, list: List<Post>) {
        listAdapter.submitList(list.filter { it.type?.equals(type) ?: false })
    }


    private fun subscribeToFlow() = with(viewModel) {

        lifecycleScope.launch {
            posts.collect {
                list = it
                listAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            user.collect {
                when (it) {
                    is Results.Success -> {
                        val data = it.data?.apply {
                            displayName = viewModel.getUserName()
                        }
                        titleAdapter.submitList(mutableListOf(TitleModel(data)))
                        binding.btCreatePost.visibility = View.VISIBLE
                    }

                    is Results.Error -> {
                        titleAdapter.submitList(mutableListOf(TitleModel(null)))
                    }
                }
            }
        }

    }

    private fun showAlertDialog() {
         AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.baseline_manage_accounts_24)
            .setTitle("Вийти")
            .setMessage("Ви дійсно бажаєте вийти з Профілю?")
            .setPositiveButton("Так") { _, _ -> viewModel.signOut() }
            .setNegativeButton("Ні") { view, _ ->  view.dismiss() }
            .setCancelable(true)
            .create()
            .show()
    }
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}