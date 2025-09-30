package com.rentalmanager.app.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object TenantList : Screen("tenant_list")
    object AddTenant : Screen("add_tenant")
    object TenantDetail : Screen("tenant_detail/{tenantId}") {
        fun createRoute(tenantId: String) = "tenant_detail/$tenantId"
    }
    object PaymentList : Screen("payment_list")
    object AddPayment : Screen("add_payment")
    object MaintenanceList : Screen("maintenance_list")
    object AddMaintenance : Screen("add_maintenance")
    object DocumentList : Screen("document_list")
    object Settings : Screen("settings")
    object Analytics : Screen("analytics")
}