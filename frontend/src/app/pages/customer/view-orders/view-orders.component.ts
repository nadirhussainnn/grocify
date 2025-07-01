import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-orders',
  imports: [CommonModule],
  templateUrl: './view-orders.component.html',
  styleUrl: './view-orders.component.css'
})
export class ViewOrdersComponent {
  orders = [
    {
      id: 'ORD123456',
      product: 'Wireless Mouse',
      quantity: 2,
      price: 59.98,
      status: 'Shipped',
      date: '2025-06-29'
    },
    {
      id: 'ORD123457',
      product: 'Keyboard',
      quantity: 1,
      price: 59.99,
      status: 'Delivered',
      date: '2025-06-27'
    },
    {
      id: 'ORD123458',
      product: 'USB-C Cable',
      quantity: 3,
      price: 29.97,
      status: 'Processing',
      date: '2025-07-01'
    }
  ];
}
