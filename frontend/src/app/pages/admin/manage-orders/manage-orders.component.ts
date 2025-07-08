import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../../services/order.service';
import { Order } from '../../../models/order.model';
import { formatDate } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DEFAULT_LIMIT, DEFAULT_PAGE } from '../../../constants';

@Component({
  selector: 'app-manage-orders',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-orders.component.html',
  styleUrls: ['./manage-orders.component.css'],
})
export class ManageOrdersComponent {
  orders = signal<Order[]>([]);
  page = signal(DEFAULT_PAGE);
  limit = DEFAULT_LIMIT;

  // Filters
  status = signal<string>('');
  date = signal<string>('');

  constructor(private orderService: OrderService) {
    this.loadOrders();
  }

  loadOrders() {
    const filters: any = {
      page: this.page(),
      limit: this.limit,
    };

    if (this.status()) filters.status = this.status();
    if (this.date()) filters.date = this.date();

    this.orderService.getAll(false, filters).subscribe({
      next: (res) => this.orders.set(res),
      error: () => alert('Failed to load orders'),
    });
  }

  nextPage() {
    this.page.update((p) => p + 1);
    this.loadOrders();
  }

  prevPage() {
    if (this.page() > 0) {
      this.page.update((p) => p - 1);
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
        alert('Order marked as delivered');
        this.loadOrders();
      },
      error: () => alert('Failed to update order'),
    });
  }

  formatDate(ms: number): string {
    return formatDate(ms, 'yyyy-MM-dd', 'en');
  }
}
