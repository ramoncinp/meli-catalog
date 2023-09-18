package com.ramoncinp.melicatalog.presentation.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ramoncinp.melicatalog.R
import com.ramoncinp.melicatalog.data.models.ItemDetail
import com.ramoncinp.melicatalog.databinding.FragmentDetailBinding
import com.ramoncinp.melicatalog.domain.utils.formatAsCurrency
import com.ramoncinp.melicatalog.presentation.adapter.ItemPictureAdapter
import com.ramoncinp.melicatalog.presentation.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var picturesAdapter: ItemPictureAdapter

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPicturesAdapter()
        initObservers()
        viewModel.getItem(args.itemId)
    }

    private fun initPicturesAdapter() {
        picturesAdapter = ItemPictureAdapter()
        binding.picturesList.adapter = picturesAdapter
    }

    private fun initObservers() {
        viewModel.itemData.observe(viewLifecycleOwner) {
            setItemData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            updateLoadingState(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            updateErrorState()
            onErrorMessage(it)
        }
    }

    private fun setItemData(item: ItemDetail) {
        with(binding) {
            title.text = item.title
            picturesAdapter.submitList(item.pictures)
            price.text = item.price.formatAsCurrency(item.currencyId)

            item.originalPrice?.let {
                originalPrice.isVisible = true
                originalPrice.text = item.originalPrice.formatAsCurrency(item.currencyId)
                originalPrice.paintFlags = originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                price.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_green_dark
                    )
                )
            }

            acceptsMercadoPagTv.isVisible = item.acceptsMercadoPago
            availableProducts.text = getString(
                R.string.available_products_message,
                item.availableQuantity
            )
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        with(binding) {
            contentGroup.isVisible = !isLoading
            progressBar.isVisible = isLoading
        }
    }

    private fun updateErrorState() {
        with(binding) {
            contentGroup.isVisible = false
            progressBar.isVisible = false
            errorMessage.root.isVisible = true
        }
    }

    private fun onErrorMessage(message: String?) {
        message?.let {
            view?.showSnackBar(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
