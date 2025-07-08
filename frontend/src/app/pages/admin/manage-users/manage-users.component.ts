import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Customer } from '../../../models/customer.model';
import { CustomerService } from '../../../services/customer.service';
import { FormsModule } from '@angular/forms';
import { formatDate } from '@angular/common';
import { DEFAULT_LIMIT, DEFAULT_PAGE } from '../../../constants';

@Component({
  selector: 'app-manage-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent {
  customers = signal<Customer[]>([]);
  page = signal(DEFAULT_PAGE);
  limit = DEFAULT_LIMIT;
  search = signal('');

  constructor(private customerService: CustomerService) {
    this.loadCustomers();
  }

  loadCustomers() {
    this.customerService.getAll({
      page: this.page(),
      limit: this.limit,
      search: this.search()
    }).subscribe({
      next: res => this.customers.set(res),
      error: () => alert('Failed to load customers')
    });
  }

  applySearch() {
    this.page.set(0);
    this.loadCustomers();
  }

  nextPage() {
    this.page.update(p => p + 1);
    this.loadCustomers();
  }

  prevPage() {
    if (this.page() > 0) {
      this.page.update(p => p - 1);
      this.loadCustomers();
    }
  }

  deleteCustomer(id: number) {
    if (confirm('Are you sure you want to delete this customer?')) {
      this.customerService.delete(id).subscribe({
        next: () => {
          alert('Customer deleted');
          this.loadCustomers();
        },
        error: () => alert('Failed to delete customer')
      });
    }
  }

  formatDate(ms: number): string {
    return formatDate(ms, 'yyyy-MM-dd', 'en');
  }
}
