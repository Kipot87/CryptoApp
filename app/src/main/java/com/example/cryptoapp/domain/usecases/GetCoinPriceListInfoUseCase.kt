package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository

class GetCoinPriceListInfoUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke() = repository.getCoinPriceListInfo()
}