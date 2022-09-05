package com.example.desafiosupremo.ui.statement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiosupremo.data.models.Balance
import com.example.desafiosupremo.data.models.StatementResponse
import com.example.desafiosupremo.repository.StatementRepository
import com.example.desafiosupremo.ui.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StatementViewModel @Inject constructor(
    private val repository: StatementRepository
) : ViewModel() {
    private val _list = MutableStateFlow<ResourceState<StatementResponse>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<StatementResponse>> = _list

    private val _balance = MutableStateFlow<ResourceState<Balance>>(ResourceState.Loading())
    val balance: StateFlow<ResourceState<Balance>> = _balance

    init {
        fetchStatement()
        fetchBalance()
    }

    private fun fetchBalance() = viewModelScope.launch {
        safeFetchBalance()
    }

    private suspend fun safeFetchBalance() {
        try {
            val response = repository.getBalance()
            _balance.value = handleResponseBalance(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _balance.value = ResourceState.Error("${t.message}")
            }
        }
    }

    private fun handleResponseBalance(response: Response<Balance>): ResourceState<Balance> {
        if (response.isSuccessful) {
            response.body()?.let { value ->
                return ResourceState.Success(value)
            }
        }
        return ResourceState.Error(response.message())
    }

    private fun fetchStatement() = viewModelScope.launch {
        safeFetchStatement()
    }

    private suspend fun safeFetchStatement() {
        try {
            val response = repository.getListStatement()
            _list.value = handleResponseStatement(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _list.value = ResourceState.Error("${t.message}")
            }
        }
    }

    private fun handleResponseStatement(
        response: Response<StatementResponse>
    ): ResourceState<StatementResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}