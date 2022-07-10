package com.example.pagination.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagination.api.model.Character
import com.example.pagination.api.model.RickMortyResponse
import com.example.pagination.repository.RickMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

const val isTestingLoadMoreLocalData: Boolean = true

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RickMortyRepository) : ViewModel() {

    private val _characters: MutableLiveData<List<Character>> = MutableLiveData()
    val characters: LiveData<List<Character>> = _characters

    private var _currentPage = 1
    private var _totalPage = 0
    private var _totalItem = 0
    private var _isRequestToNextPage: Boolean = false
    private var _isRequestToPrevPage: Boolean = false
    private var _isLoadingMoreData: Boolean = false

    init {
        findAllCharacters(_currentPage)
    }

    private fun findAllCharacters(page: Int) = viewModelScope.launch {
        repository.findAllCharacters(page)
            .onStart {

            }.onCompletion {
                if (_isRequestToNextPage) _isRequestToNextPage = false
                if (_isRequestToPrevPage) _isRequestToPrevPage = false
                if (_isLoadingMoreData) _isLoadingMoreData = false
            }.catch { e ->
                e.printStackTrace()
            }.collect {
                if (_isLoadingMoreData) {
                    handleLoadMoreRickMorty(it)
                } else {
                    handleRickMortyResponse(it)
                }
            }
    }

    private fun handleRickMortyResponse(response: RickMortyResponse) {
        response.info?.let {
            _totalPage = it.totalPages ?: 0
            _totalItem = it.totalItems ?: 0
        }
        response.results?.let {
            if (_isRequestToNextPage) _currentPage++
            if (_isRequestToPrevPage) _currentPage--
            it.forEach() {
                it.totalItems = _totalPage
                it.totalPages = _totalPage
            }
            _characters.value = it
        }
    }

    private fun handleLoadMoreRickMorty(response: RickMortyResponse) {
        response.info?.let {
            _totalPage = it.totalPages ?: 0
            _totalItem = it.totalItems ?: 0
        }
        response.results?.let {
            if (_isLoadingMoreData) _currentPage++
            val currentListCharacter = _characters.value?.toMutableList() ?: mutableListOf()
            currentListCharacter.addAll(it)
            currentListCharacter.forEach() {
                it.totalItems = _totalPage
                it.totalPages = _totalPage
            }
            _characters.value = currentListCharacter
        }
    }


    fun requestToLoadNextPage() {
        _isRequestToNextPage = true
        findAllCharacters(_currentPage + 1)
    }

    fun requestToLoadPrevPage() {
        _isRequestToPrevPage = true
        findAllCharacters(_currentPage - 1)
    }

    fun isCanLoadMoreData(): Boolean {
        return if(isTestingLoadMoreLocalData) (_currentLocalPage < _totalLocalPage) else (_currentPage < _totalPage)
    }

    fun requestToLoadMoreData() {
        if(isTestingLoadMoreLocalData){
            val _refListData = _characters.value
            if (_refListData != null) {
                val take = (++_currentLocalPage) * _localPageSize
                _chunkData.value = _refListData!!.take(  take)
            }
        }
        else {
            _isLoadingMoreData = true
            findAllCharacters(_currentPage + 1)
        }
    }




    /////////////////////////////////////////////

    private val _localPageSize = 5
    private var _currentLocalPage = 1
    private var _totalLocalPage = 0
    private val _chunkData: MutableLiveData<List<Character>> = MutableLiveData()
    val chunkData: LiveData<List<Character>> = _chunkData

    fun initConditionToLocalLoadMore() {
        val _refListData = _characters.value
        if (_refListData != null) {
            _totalLocalPage = if (_refListData!!.size % _localPageSize == 0) _refListData!!.size / _localPageSize else ((_refListData!!.size / _localPageSize) + 1)
            _chunkData.value = _refListData!!.take(_currentLocalPage*_localPageSize)
        }
    }
}