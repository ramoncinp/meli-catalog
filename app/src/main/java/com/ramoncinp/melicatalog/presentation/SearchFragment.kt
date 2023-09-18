package com.ramoncinp.melicatalog.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.ramoncinp.melicatalog.R
import com.ramoncinp.melicatalog.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                menu.initSearchMenuItem()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun Menu.initSearchMenuItem() {
        val item = findItem(R.id.search_view)
        val searchView = item?.actionView as SearchView
        searchView.apply {
            isIconified = false
            queryHint = resources.getString(R.string.buscar)
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    onSubmitQuery(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Timber.d("New query text: $newText")
                    return false
                }
            })
        }
    }

    private fun onSubmitQuery(query: String?) {
        if (query == null || query.isEmpty()) {
            return
        }

        viewModel.searchItems(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
