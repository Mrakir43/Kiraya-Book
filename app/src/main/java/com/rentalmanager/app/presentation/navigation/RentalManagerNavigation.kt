package com.rentalmanager.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rentalmanager.app.presentation.screens.auth.LoginScreen
import com.rentalmanager.app.presentation.screens.dashboard.DashboardScreen
import com.rentalmanager.app.presentation.screens.tenant.TenantListScreen
import com.rentalmanager.app.presentation.screens.tenant.AddTenantScreen
import com.rentalmanager.app.presentation.screens.payment.PaymentListScreen
import com.rentalmanager.app.presentation.screens.maintenance.MaintenanceListScreen

@Composable
fun RentalManagerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToTenants = {
                    navController.navigate(Screen.TenantList.route)
                },
                onNavigateToPayments = {
                    navController.navigate(Screen.PaymentList.route)
                },
                onNavigateToMaintenance = {
                    navController.navigate(Screen.MaintenanceList.route)
                }
            )
        }
        
        composable(Screen.TenantList.route) {
            TenantListScreen(
                onNavigateBack = { navController.popBackStack() },
                onAddTenant = { navController.navigate(Screen.AddTenant.route) }
            )
        }
        
        composable(Screen.AddTenant.route) {
            AddTenantScreen(
                onNavigateBack = { navController.popBackStack() },
                onTenantAdded = { navController.popBackStack() }
            )
        }
        
        composable(Screen.PaymentList.route) {
            PaymentListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.MaintenanceList.route) {
            MaintenanceListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}