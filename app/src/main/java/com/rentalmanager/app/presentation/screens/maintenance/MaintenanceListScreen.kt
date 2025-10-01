package com.rentalmanager.app.presentation.screens.maintenance

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
import com.rentalmanager.app.data.local.entities.MaintenanceCategory
import com.rentalmanager.app.data.local.entities.MaintenanceStatus
import com.rentalmanager.app.data.local.entities.Priority
import com.rentalmanager.app.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceListScreen(
    onNavigateBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    
    // Sample maintenance data
    val sampleRequests = remember {
        listOf(
            MaintenanceDisplayData(
                id = "1",
                title = "Leaking Kitchen Faucet",
                description = "The kitchen faucet has been dripping constantly for the past week.",
                tenantName = "John Smith",
                unitNumber = "A101",
                category = MaintenanceCategory.PLUMBING,
                priority = Priority.MEDIUM,
                status = MaintenanceStatus.IN_PROGRESS,
                createdAt = System.currentTimeMillis() - 2L * 24 * 60 * 60 * 1000,
                scheduledDate = System.currentTimeMillis() + 1L * 24 * 60 * 60 * 1000
            ),
            MaintenanceDisplayData(
                id = "2",
                title = "Broken Air Conditioning",
                description = "AC unit not cooling properly, making loud noises.",
                tenantName = "Sarah Johnson",
                unitNumber = "B205",
                category = MaintenanceCategory.HVAC,
                priority = Priority.HIGH,
                status = MaintenanceStatus.SUBMITTED,
                createdAt = System.currentTimeMillis() - 1L * 24 * 60 * 60 * 1000,
                scheduledDate = null
            ),
            MaintenanceDisplayData(
                id = "3",
                title = "Electrical Outlet Not Working",
                description = "Bedroom outlet stopped working suddenly.",
                tenantName = "Mike Wilson",
                unitNumber = "C301",
                category = MaintenanceCategory.ELECTRICAL,
                priority = Priority.LOW,
                status = MaintenanceStatus.COMPLETED,
                createdAt = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000,
                scheduledDate = System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000
            ),
            MaintenanceDisplayData(
                id = "4",
                title = "Emergency Water Leak",
                description = "Major water leak in bathroom ceiling, urgent repair needed.",
                tenantName = "Lisa Davis",
                unitNumber = "D402",
                category = MaintenanceCategory.PLUMBING,
                priority = Priority.EMERGENCY,
                status = MaintenanceStatus.ACKNOWLEDGED,
                createdAt = System.currentTimeMillis() - 3L * 60 * 60 * 1000,
                scheduledDate = System.currentTimeMillis() + 2L * 60 * 60 * 1000
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Maintenance Requests") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Filter options */ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }
                IconButton(onClick = { /* Add new request */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Request")
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
                label = { Text("Search maintenance requests...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Priority Summary
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(getPrioritySummary(sampleRequests)) { summary ->
                    PrioritySummaryCard(
                        priority = summary.priority,
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
                items(listOf("All", "Emergency", "High", "In Progress", "Completed", "Plumbing", "Electrical", "HVAC")) { filter ->
                    FilterChip(
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        selected = selectedFilter == filter
                    )
                }
            }

            // Maintenance Requests List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleRequests.filter { request ->
                    val matchesSearch = request.title.contains(searchQuery, ignoreCase = true) ||
                            request.tenantName.contains(searchQuery, ignoreCase = true) ||
                            request.unitNumber.contains(searchQuery, ignoreCase = true)
                    
                    val matchesFilter = when (selectedFilter) {
                        "All" -> true
                        "Emergency" -> request.priority == Priority.EMERGENCY
                        "High" -> request.priority == Priority.HIGH
                        "In Progress" -> request.status == MaintenanceStatus.IN_PROGRESS
                        "Completed" -> request.status == MaintenanceStatus.COMPLETED
                        "Plumbing" -> request.category == MaintenanceCategory.PLUMBING
                        "Electrical" -> request.category == MaintenanceCategory.ELECTRICAL
                        "HVAC" -> request.category == MaintenanceCategory.HVAC
                        else -> true
                    }
                    
                    matchesSearch && matchesFilter
                }.sortedWith(
                    compareBy<MaintenanceDisplayData> { 
                        when (it.priority) {
                            Priority.EMERGENCY -> 0
                            Priority.HIGH -> 1
                            Priority.MEDIUM -> 2
                            Priority.LOW -> 3
                        }
                    }.thenBy { it.createdAt }
                )) { request ->
                    MaintenanceRequestCard(request = request)
                }
            }
        }
    }
}

@Composable
fun PrioritySummaryCard(
    priority: Priority,
    count: Int,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = priority.name.lowercase().replaceFirstChar { it.uppercase() },
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceRequestCard(request: MaintenanceDisplayData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navigate to request details */ }
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PriorityIndicator(priority = request.priority)
                        Text(
                            text = request.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Text(
                        text = "${request.tenantName} • Unit ${request.unitNumber}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Text(
                        text = request.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 2
                    )
                }
                
                MaintenanceStatusChip(status = request.status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        getCategoryIcon(request.category),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = request.category.name.lowercase().replaceFirstChar { it.uppercase() },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (request.scheduledDate != null) "Scheduled" else "Created",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                            .format(Date(request.scheduledDate ?: request.createdAt)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityIndicator(priority: Priority) {
    val color = when (priority) {
        Priority.EMERGENCY -> ErrorRed
        Priority.HIGH -> WarningOrange
        Priority.MEDIUM -> PrimaryBlue
        Priority.LOW -> AccentGreen
    }
    
    Box(
        modifier = Modifier
            .size(8.dp)
            .padding(2.dp)
    ) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxSize()
        ) {}
    }
}

@Composable
fun MaintenanceStatusChip(status: MaintenanceStatus) {
    val (color, text) = when (status) {
        MaintenanceStatus.SUBMITTED -> WarningOrange to "Submitted"
        MaintenanceStatus.ACKNOWLEDGED -> PrimaryBlue to "Acknowledged"
        MaintenanceStatus.IN_PROGRESS -> SecondaryBlue to "In Progress"
        MaintenanceStatus.COMPLETED -> AccentGreen to "Completed"
        MaintenanceStatus.CANCELLED -> MaterialTheme.colorScheme.onSurfaceVariant to "Cancelled"
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

fun getCategoryIcon(category: MaintenanceCategory) = when (category) {
    MaintenanceCategory.PLUMBING -> Icons.Default.Plumbing
    MaintenanceCategory.ELECTRICAL -> Icons.Default.ElectricalServices
    MaintenanceCategory.HVAC -> Icons.Default.Thermostat
    MaintenanceCategory.APPLIANCES -> Icons.Default.Kitchen
    MaintenanceCategory.FLOORING -> Icons.Default.Layers
    MaintenanceCategory.PAINTING -> Icons.Default.Brush
    MaintenanceCategory.PEST_CONTROL -> Icons.Default.BugReport
    MaintenanceCategory.SECURITY -> Icons.Default.Security
    MaintenanceCategory.LANDSCAPING -> Icons.Default.Grass
    MaintenanceCategory.OTHER -> Icons.Default.Build
}

data class MaintenanceDisplayData(
    val id: String,
    val title: String,
    val description: String,
    val tenantName: String,
    val unitNumber: String,
    val category: MaintenanceCategory,
    val priority: Priority,
    val status: MaintenanceStatus,
    val createdAt: Long,
    val scheduledDate: Long?
)

data class PrioritySummary(
    val priority: Priority,
    val count: Int,
    val color: androidx.compose.ui.graphics.Color
)

fun getPrioritySummary(requests: List<MaintenanceDisplayData>): List<PrioritySummary> {
    return Priority.values().map { priority ->
        PrioritySummary(
            priority = priority,
            count = requests.count { it.priority == priority },
            color = when (priority) {
                Priority.EMERGENCY -> ErrorRed
                Priority.HIGH -> WarningOrange
                Priority.MEDIUM -> PrimaryBlue
                Priority.LOW -> AccentGreen
            }
        )
    }
}