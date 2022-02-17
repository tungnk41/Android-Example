package com.example.pagingsource.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingsource.Model.Model
import com.example.pagingsource.Network.RetrofitClient
import com.example.pagingsource.Network.ServiceApi
import com.example.pagingsource.Paging.PagingDataSource
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    val data : Flow<PagingData<Model>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = {
            PagingDataSource()
        }).flow.cachedIn(viewModelScope)

    suspend fun getData(){
        RetrofitClient.instance().create(ServiceApi::class.java).getAll(1)
    }
}