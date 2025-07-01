import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-products',
  imports: [CommonModule],
  templateUrl: './view-products.component.html',
  styleUrl: './view-products.component.css'
})
export class ViewProductsComponent {
  products = [
    {
      id: 1,
      name: 'Wireless Mouse',
      description: 'Ergonomic and smooth wireless mouse',
      thumbnail: 'https://dummyjson.com/icon/abc123/150',
      quantity: 50,
      price: 29.99
    },
    {
      id: 2,
      name: 'Keyboard',
      description: 'Mechanical keyboard with RGB lights',
      thumbnail: 'https://dummyjson.com/icon/abc123/150',
      quantity: 30,
      price: 59.99
    },
    {
      id: 3,
      name: 'USB-C Cable',
      description: 'Fast charging 1m USB-C cable',
      thumbnail: 'https://dummyjson.com/icon/abc123/150',
      quantity: 100,
      price: 9.99
    }
  ];
}
