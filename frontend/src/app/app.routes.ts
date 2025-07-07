import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { CustomerDashboardComponent } from './pages/customer/customer-dashboard/customer-dashboard.component';

import { ManageOrdersComponent } from './pages/admin/manage-orders/manage-orders.component';
import { ManageStockComponent } from './pages/admin/manage-stock/manage-stock.component';
import { ManageUsersComponent } from './pages/admin/manage-users/manage-users.component';
import { ViewProductsComponent } from './pages/customer/view-products/view-products.component';
import { ViewOrdersComponent } from './pages/customer/view-orders/view-orders.component';
import { CustomerAuthGuard } from './guards/customer-auth.guard';
import { AdminAuthGuard } from './guards/admin-auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { 
    path: 'admin', 
    component: AdminDashboardComponent,
    canActivate: [AdminAuthGuard],
    children:[
        { path: 'manage-stock', component: ManageStockComponent },
        { path: 'manage-orders', component: ManageOrdersComponent },
        { path: 'manage-users', component: ManageUsersComponent },
    ] 
  
  },
  { 
    path: 'customer', 
    component: CustomerDashboardComponent,
    canActivate: [CustomerAuthGuard],
    children:[
      { path: 'view-orders', component: ViewOrdersComponent },
      { path: 'view-products', component: ViewProductsComponent },
    ]
  },
];
