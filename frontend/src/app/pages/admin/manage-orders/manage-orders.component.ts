import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-manage-orders',
  imports: [CommonModule],
  templateUrl: './manage-orders.component.html',
  styleUrl: './manage-orders.component.css'
})
export class ManageOrdersComponent {
  orders = [
    { id: 'ORD1001', customer: 'John Doe', product: 'Mouse', status: 'Delivered', date: '2025-06-28' },
    { id: 'ORD1002', customer: 'Jane Smith', product: 'Keyboard', status: 'Processing', date: '2025-07-01' },
  ];
}
