package com.example.pagingsource.Paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingsource.Model.Model
import com.example.pagingsource.Network.RetrofitClient
import com.example.pagingsource.Network.ServiceApi

class PagingDataSource : PagingSource<Int, Model>() {
    private val serviceApi = RetrofitClient.instance().create(ServiceApi::class.java)
    override fun getRefreshKey(state: PagingState<Int, Model>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Model> {
        val pageNumber = params.key ?: 1
        val response = serviceApi.getAll(pageNumber)
        val pagedResponse = response.body()
        val data = pagedResponse?.results


        //Get next page number
        var nextPageNumber : Int? = null
        if(pagedResponse?.pageInfo?.next != null){
            val uri = Uri.parse(pagedResponse.pageInfo.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()
        }

        return try {
            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}