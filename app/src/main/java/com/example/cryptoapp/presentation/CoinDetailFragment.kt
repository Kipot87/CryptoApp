package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    private val component by lazy {
        (requireActivity().application as CoinApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromSymbol = getSymbol()
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            with(binding) {
                Picasso.get().load(it.imageUrl).into(ivCoinLogo)
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                tvPrice.text = it.price.toString()
                tvMinPrice.text = it.lowDay.toString()
                tvMaxPrice.text = it.highDay.toString()
                tvLastBuy.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL, "")
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}