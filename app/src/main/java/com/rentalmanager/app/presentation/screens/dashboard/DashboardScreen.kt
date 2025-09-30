package com.rentalmanager.app.presentation.screens.dashboard

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentalmanager.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToTenants: () -> Unit,
    onNavigateToPayments: () -> Unit,
    onNavigateToMaintenance: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good Morning!",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "John Landlord",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            IconButton(onClick = { /* Navigate to profile */ }) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Quick Stats Cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            items(getQuickStats()) { stat ->
                QuickStatCard(
                    title = stat.title,
                    value = stat.value,
                    icon = stat.icon,
                    color = stat.color
                )
            }
        }

        // Action Cards
        Text(
            text = "Quick Actions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getActionCards()) { action ->
                ActionCard(
                    title = action.title,
                    description = action.description,
                    icon = action.icon,
                    onClick = when (action.title) {
                        "Manage Tenants" -> onNavigateToTenants
                        "Payment Tracking" -> onNavigateToPayments
                        "Maintenance Requests" -> onNavigateToMaintenance
                        else -> { {} }
                    }
                )
            }
        }
    }
}

@Composable
fun QuickStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            
            Column {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

data class QuickStat(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: androidx.compose.ui.graphics.Color
)

data class ActionItem(
    val title: String,
    val description: String,
    val icon: ImageVector
)

fun getQuickStats(): List<QuickStat> = listOf(
    QuickStat("Active Tenants", "12", Icons.Default.People, AccentGreen),
    QuickStat("Properties", "5", Icons.Default.Home, PrimaryBlue),
    QuickStat("Pending Payments", "3", Icons.Default.Payment, WarningOrange),
    QuickStat("Maintenance", "2", Icons.Default.Build, ErrorRed)
)

fun getActionCards(): List<ActionItem> = listOf(
    ActionItem(
        "Manage Tenants",
        "Add, edit, or view tenant information",
        Icons.Default.People
    ),
    ActionItem(
        "Payment Tracking",
        "Monitor rent and utility payments",
        Icons.Default.Payment
    ),
    ActionItem(
        "Maintenance Requests",
        "Handle property maintenance issues",
        Icons.Default.Build
    ),
    ActionItem(
        "Document Management",
        "Store and manage lease agreements",
        Icons.Default.Description
    ),
    ActionItem(
        "Analytics & Reports",
        "View financial reports and insights",
        Icons.Default.Analytics
    ),
    ActionItem(
        "Settings",
        "Configure app preferences",
        Icons.Default.Settings
    )
)