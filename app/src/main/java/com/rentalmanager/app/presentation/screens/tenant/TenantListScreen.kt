package com.rentalmanager.app.presentation.screens.tenant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentalmanager.app.data.local.entities.LeaseStatus
import com.rentalmanager.app.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantListScreen(
    onNavigateBack: () -> Unit,
    onAddTenant: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    
    // Sample tenant data
    val sampleTenants = remember {
        listOf(
            TenantDisplayData(
                id = "1",
                name = "John Smith",
                email = "john.smith@email.com",
                phone = "+1 234-567-8900",
                unitNumber = "A101",
                monthlyRent = 1200.0,
                leaseStatus = LeaseStatus.ACTIVE,
                leaseEndDate = System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000
            ),
            TenantDisplayData(
                id = "2",
                name = "Sarah Johnson",
                email = "sarah.j@email.com",
                phone = "+1 234-567-8901",
                unitNumber = "B205",
                monthlyRent = 1500.0,
                leaseStatus = LeaseStatus.ACTIVE,
                leaseEndDate = System.currentTimeMillis() + 200L * 24 * 60 * 60 * 1000
            ),
            TenantDisplayData(
                id = "3",
                name = "Mike Wilson",
                email = "mike.wilson@email.com",
                phone = "+1 234-567-8902",
                unitNumber = "C301",
                monthlyRent = 1800.0,
                leaseStatus = LeaseStatus.EXPIRED,
                leaseEndDate = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Tenants") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Filter options */ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search tenants...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(listOf("All", "Active", "Expired", "Pending")) { filter ->
                    FilterChip(
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        selected = selectedFilter == filter
                    )
                }
            }

            // Tenants List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(sampleTenants.filter { tenant ->
                    val matchesSearch = tenant.name.contains(searchQuery, ignoreCase = true) ||
                            tenant.email.contains(searchQuery, ignoreCase = true) ||
                            tenant.unitNumber.contains(searchQuery, ignoreCase = true)
                    
                    val matchesFilter = when (selectedFilter) {
                        "All" -> true
                        "Active" -> tenant.leaseStatus == LeaseStatus.ACTIVE
                        "Expired" -> tenant.leaseStatus == LeaseStatus.EXPIRED
                        "Pending" -> tenant.leaseStatus == LeaseStatus.PENDING
                        else -> true
                    }
                    
                    matchesSearch && matchesFilter
                }) { tenant ->
                    TenantCard(tenant = tenant)
                }
            }
        }

        // Floating Action Button
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onAddTenant,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Tenant")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantCard(tenant: TenantDisplayData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navigate to tenant details */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tenant.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Unit ${tenant.unitNumber}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = tenant.email,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                StatusChip(status = tenant.leaseStatus)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Monthly Rent",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${String.format("%.0f", tenant.monthlyRent)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = AccentGreen
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Lease Ends",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                            .format(Date(tenant.leaseEndDate)),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: LeaseStatus) {
    val (color, text) = when (status) {
        LeaseStatus.ACTIVE -> AccentGreen to "Active"
        LeaseStatus.EXPIRED -> ErrorRed to "Expired"
        LeaseStatus.PENDING -> WarningOrange to "Pending"
        LeaseStatus.TERMINATED -> MaterialTheme.colorScheme.onSurfaceVariant to "Terminated"
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

data class TenantDisplayData(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val unitNumber: String,
    val monthlyRent: Double,
    val leaseStatus: LeaseStatus,
    val leaseEndDate: Long
)