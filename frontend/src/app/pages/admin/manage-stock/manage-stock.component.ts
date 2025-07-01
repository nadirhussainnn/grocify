import { Component, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-stock',
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-stock.component.html',
  styleUrl: './manage-stock.component.css'
})
export class ManageStockComponent {
  stockItems = [
    { name: 'Mouse', quantity: 100, category: 'Accessories', status: 'In Stock' },
    { name: 'Keyboard', quantity: 0, category: 'Accessories', status: 'Out of Stock' },
    { name: 'Laptop', quantity: 20, category: 'Computers', status: 'In Stock' },
  ];

  showModal = false;

  newProduct = {
    name: '',
    description: '',
    price: 0,
    quantity: 0,
    category: '',
    thumbnail: ''
  };

  toggleModal() {
    this.showModal = !this.showModal;
  }

  addProduct() {
    const status = this.newProduct.quantity > 0 ? 'In Stock' : 'Out of Stock';
    const newItem = {
      ...this.newProduct,
      status
    };
    this.stockItems.push(newItem);
    this.newProduct = {
      name: '',
      description: '',
      price: 0,
      quantity: 0,
      category: '',
      thumbnail: ''
    };
    this.toggleModal();
  }

  @HostListener('document:keydown.escape', ['$event'])
  onEscapeKey(event: KeyboardEvent) {
    if (this.showModal) this.toggleModal();
  }

}
