package com.rentalmanager.app.presentation.screens.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.rentalmanager.app.data.local.entities.PaymentStatus
import com.rentalmanager.app.data.local.entities.PaymentType
import com.rentalmanager.app.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentListScreen(
    onNavigateBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    
    // Sample payment data
    val samplePayments = remember {
        listOf(
            PaymentDisplayData(
                id = "1",
                tenantName = "John Smith",
                unitNumber = "A101",
                amount = 1200.0,
                paymentType = PaymentType.RENT,
                status = PaymentStatus.PAID,
                dueDate = System.currentTimeMillis() - 5L * 24 * 60 * 60 * 1000,
                paidDate = System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000
            ),
            PaymentDisplayData(
                id = "2",
                tenantName = "Sarah Johnson",
                unitNumber = "B205",
                amount = 150.0,
                paymentType = PaymentType.ELECTRICITY,
                status = PaymentStatus.PENDING,
                dueDate = System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000,
                paidDate = null
            ),
            PaymentDisplayData(
                id = "3",
                tenantName = "Mike Wilson",
                unitNumber = "C301",
                amount = 1800.0,
                paymentType = PaymentType.RENT,
                status = PaymentStatus.OVERDUE,
                dueDate = System.currentTimeMillis() - 10L * 24 * 60 * 60 * 1000,
                paidDate = null
            ),
            PaymentDisplayData(
                id = "4",
                tenantName = "John Smith",
                unitNumber = "A101",
                amount = 75.0,
                paymentType = PaymentType.WATER,
                status = PaymentStatus.PAID,
                dueDate = System.currentTimeMillis() - 15L * 24 * 60 * 60 * 1000,
                paidDate = System.currentTimeMillis() - 12L * 24 * 60 * 60 * 1000
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Payments") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Export payments */ }) {
                    Icon(Icons.Default.FileDownload, contentDescription = "Export")
                }
                IconButton(onClick = { /* Payment analytics */ }) {
                    Icon(Icons.Default.Analytics, contentDescription = "Analytics")
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
                label = { Text("Search payments...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Summary Cards
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(getPaymentSummary(samplePayments)) { summary ->
                    PaymentSummaryCard(
                        title = summary.title,
                        amount = summary.amount,
                        count = summary.count,
                        color = summary.color
                    )
                }
            }

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(listOf("All", "Paid", "Pending", "Overdue", "Rent", "Utilities")) { filter ->
                    FilterChip(
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        selected = selectedFilter == filter
                    )
                }
            }

            // Payments List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(samplePayments.filter { payment ->
                    val matchesSearch = payment.tenantName.contains(searchQuery, ignoreCase = true) ||
                            payment.unitNumber.contains(searchQuery, ignoreCase = true)
                    
                    val matchesFilter = when (selectedFilter) {
                        "All" -> true
                        "Paid" -> payment.status == PaymentStatus.PAID
                        "Pending" -> payment.status == PaymentStatus.PENDING
                        "Overdue" -> payment.status == PaymentStatus.OVERDUE
                        "Rent" -> payment.paymentType == PaymentType.RENT
                        "Utilities" -> payment.paymentType in listOf(PaymentType.ELECTRICITY, PaymentType.WATER, PaymentType.GAS)
                        else -> true
                    }
                    
                    matchesSearch && matchesFilter
                }) { payment ->
                    PaymentCard(payment = payment)
                }
            }
        }
    }
}

@Composable
fun PaymentSummaryCard(
    title: String,
    amount: Double,
    count: Int,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Column {
                Text(
                    text = "$${String.format("%.0f", amount)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                Text(
                    text = "$count payments",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCard(payment: PaymentDisplayData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navigate to payment details */ }
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
                        text = payment.tenantName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Unit ${payment.unitNumber} • ${payment.paymentType.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                PaymentStatusChip(status = payment.status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "Amount",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${String.format("%.2f", payment.amount)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (payment.status) {
                            PaymentStatus.PAID -> AccentGreen
                            PaymentStatus.OVERDUE -> ErrorRed
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (payment.paidDate != null) "Paid Date" else "Due Date",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                            .format(Date(payment.paidDate ?: payment.dueDate)),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentStatusChip(status: PaymentStatus) {
    val (color, text) = when (status) {
        PaymentStatus.PAID -> AccentGreen to "Paid"
        PaymentStatus.PENDING -> WarningOrange to "Pending"
        PaymentStatus.OVERDUE -> ErrorRed to "Overdue"
        PaymentStatus.PARTIAL -> PrimaryBlue to "Partial"
        PaymentStatus.CANCELLED -> MaterialTheme.colorScheme.onSurfaceVariant to "Cancelled"
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

data class PaymentDisplayData(
    val id: String,
    val tenantName: String,
    val unitNumber: String,
    val amount: Double,
    val paymentType: PaymentType,
    val status: PaymentStatus,
    val dueDate: Long,
    val paidDate: Long?
)

data class PaymentSummary(
    val title: String,
    val amount: Double,
    val count: Int,
    val color: androidx.compose.ui.graphics.Color
)

fun getPaymentSummary(payments: List<PaymentDisplayData>): List<PaymentSummary> {
    val paidPayments = payments.filter { it.status == PaymentStatus.PAID }
    val pendingPayments = payments.filter { it.status == PaymentStatus.PENDING }
    val overduePayments = payments.filter { it.status == PaymentStatus.OVERDUE }
    
    return listOf(
        PaymentSummary(
            title = "Collected",
            amount = paidPayments.sumOf { it.amount },
            count = paidPayments.size,
            color = AccentGreen
        ),
        PaymentSummary(
            title = "Pending",
            amount = pendingPayments.sumOf { it.amount },
            count = pendingPayments.size,
            color = WarningOrange
        ),
        PaymentSummary(
            title = "Overdue",
            amount = overduePayments.sumOf { it.amount },
            count = overduePayments.size,
            color = ErrorRed
        )
    )
}