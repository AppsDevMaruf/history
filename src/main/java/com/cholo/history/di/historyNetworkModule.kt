package com.cholo.history.di

import com.cholo.history.data.HistoryRepositoryImpl
import com.cholo.history.data.api.HistoryApiService
import com.cholo.history.domain.GetRideHistoryUseCase
import com.cholo.history.domain.HistoryRepository
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.cholo.history.ui.HistoryViewModel
import retrofit2.Retrofit

val historyNetworkModule = module {
    single<HistoryApiService> {
        get<Retrofit>().create(HistoryApiService::class.java)
    }
}
val historyRepositoryModule = module {
    single<HistoryRepository> {
        HistoryRepositoryImpl(get())
    }
}

val historyUseCaseModule = module {
    factory { GetRideHistoryUseCase(get()) }
    // Add more use cases if needed
}

val historyViewModelModule = module {
    viewModelOf(::HistoryViewModel)
}

val historyModule = listOf(
    historyNetworkModule,
    historyRepositoryModule,
    historyUseCaseModule,
    historyViewModelModule
)
