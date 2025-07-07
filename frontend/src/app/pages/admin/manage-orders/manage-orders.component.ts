import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../../services/order.service';
import { Order } from '../../../models/order.model';
import { ToastrService } from 'ngx-toastr';
import { formatDate } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-orders',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-orders.component.html',
  styleUrls: ['./manage-orders.component.css']
})
export class ManageOrdersComponent {
  orders = signal<Order[]>([]);
  page = signal(0);
  limit = 8;

  // Filters
  status = signal<string>('');
  date = signal<string>(''); 
  
  constructor(private orderService: OrderService, private toast: ToastrService) {
    this.loadOrders();
  }

  loadOrders() {
    const filters: any = {
      page: this.page(),
      limit: this.limit
    };

    if (this.status()) filters.status = this.status();
    if (this.date()) filters.date = this.date();

    this.orderService.getAll(filters).subscribe({
      next: res => this.orders.set(res),
      error: () => this.toast.error('Failed to load orders')
    });
  }

  nextPage() {
    this.page.update(p => p + 1);
    this.loadOrders();
  }

  prevPage() {
    if (this.page() > 0) {
      this.page.update(p => p - 1);
      this.loadOrders();
    }
  }

  applyFilters() {
    this.page.set(0);
    this.loadOrders();
  }

  deliver(orderId: number) {
    this.orderService.markDelivered(orderId).subscribe({
      next: () => {
        this.toast.success('Order marked as delivered');
        this.loadOrders();
      },
      error: () => this.toast.error('Failed to update order')
    });
  }

  formatDate(ms: number): string {
    return formatDate(ms, 'yyyy-MM-dd', 'en');
  }
}
