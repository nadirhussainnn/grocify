import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import { FormsModule } from '@angular/forms';
import { DEFAULT_LIMIT, DEFAULT_PAGE } from '../../../constants';

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

  page = signal(DEFAULT_PAGE);
  limit = DEFAULT_LIMIT + 2;
  search = signal<string>('');

  constructor(private productService: ProductService) {
    this.load();
  }

  load() {
    const filters: any = {
      page: this.page(),
      limit: this.limit,
    };

    if (this.search()) filters.search = this.search();
    this.productService.getAll(filters).subscribe({
      next: p => this.products.set(p),
      error: () => alert('Failed to load products')
    });
  }

  nextPage() {
    this.page.update((p) => p + 1);
    this.load();
  }

  prevPage() {
    if (this.page() > 0) {
      this.page.update((p) => p - 1);
      this.load();
    }
  }

  applyFilters() {
    this.page.set(0);
    this.load();
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
