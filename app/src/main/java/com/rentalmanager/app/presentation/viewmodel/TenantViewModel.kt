package com.rentalmanager.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentalmanager.app.data.local.entities.Tenant
import com.rentalmanager.app.data.local.entities.LeaseStatus
import com.rentalmanager.app.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenantViewModel @Inject constructor(
    private val getTenantsUseCase: GetTenantsUseCase,
    private val addTenantUseCase: AddTenantUseCase,
    private val updateTenantUseCase: UpdateTenantUseCase,
    private val getExpiredLeasesUseCase: GetExpiredLeasesUseCase,
    private val getTenantByIdUseCase: GetTenantByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TenantUiState())
    val uiState: StateFlow<TenantUiState> = _uiState.asStateFlow()

    private val _tenants = MutableStateFlow<List<Tenant>>(emptyList())
    val tenants: StateFlow<List<Tenant>> = _tenants.asStateFlow()

    fun loadTenants(propertyId: String) {
        viewModelScope.launch {
            getTenantsUseCase(propertyId).collect { tenantList ->
                _tenants.value = tenantList
            }
        }
    }

    fun addTenant(tenant: Tenant) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                addTenantUseCase(tenant)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Tenant added successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to add tenant"
                )
            }
        }
    }

    fun updateTenant(tenant: Tenant) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                updateTenantUseCase(tenant)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Tenant updated successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update tenant"
                )
            }
        }
    }

    fun loadExpiredLeases() {
        viewModelScope.launch {
            getExpiredLeasesUseCase().collect { expiredLeases ->
                _uiState.value = _uiState.value.copy(expiredLeases = expiredLeases)
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null, error = null)
    }
}

data class TenantUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null,
    val expiredLeases: List<Tenant> = emptyList()
)