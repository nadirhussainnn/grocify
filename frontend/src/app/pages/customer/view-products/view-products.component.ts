import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-view-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './view-products.component.html',
  styleUrls: ['./view-products.component.css']
})
export class ViewProductsComponent {
  products = signal<Product[]>([]);
  selectedProduct = signal<Product | null>(null);
  quantity = signal(1);
  showModal = signal(false);

  constructor(private productService: ProductService) {
    this.load();
  }

  load() {
    this.productService.getAll().subscribe({
      next: p => this.products.set(p),
      error: () => alert('Failed to load products')
    });
  }

  openBuyModal(product: Product) {
    this.selectedProduct.set(product);
    this.quantity.set(1);
    this.showModal.set(true);
  }

  total(): number {
    const q = this.quantity();
    const p = this.selectedProduct();
    return p ? p.price * q : 0;
  }

  buy() {
    const p = this.selectedProduct();
    if (!p) return;

    this.productService.buy(p.id, this.quantity()).subscribe({
      next: () => {
        alert('Purchase successful!');
        this.showModal.set(false);
        this.load(); 
      },
      error: () => alert('Failed to complete purchase')
    });
  }
}
