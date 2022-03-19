package com.example.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.model.DataModelUI
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed

class MainViewModel : ViewModel() {


//    val _flow = flow {
//        repeat(10) {
//            emit(it)
//            delay(10)
//        }
//    }

    val _flow = listOf(1,1,1,1,1,1,1,1,1).asFlow()

   val _stateflow = _flow.stateIn(viewModelScope, started = WhileSubscribed(),0)

   val _shareflow = _flow.shareIn(viewModelScope, started = WhileSubscribed(),4).buffer(capacity = 64, onBufferOverflow = BufferOverflow.SUSPEND)

   val _mutableStateflow = MutableStateFlow(DataModelUI(0,0))

   val _mutableShareflow = MutableSharedFlow<DataModelUI>(replay = 4,0,onBufferOverflow = BufferOverflow.SUSPEND)

    suspend fun emitData() {
        _mutableShareflow.tryEmit(DataModelUI(1,1))
    }

}