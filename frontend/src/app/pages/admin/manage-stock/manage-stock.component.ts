import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import { DEFAULT_LIMIT, DEFAULT_PAGE } from '../../../constants';

@Component({
  selector: 'app-manage-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-stock.component.html',
  styleUrls: ['./manage-stock.component.css']
})
export class ManageStockComponent {
  products = signal<Product[]>([]);
  page = signal(DEFAULT_PAGE);
  limit = DEFAULT_LIMIT;
  showModal = signal(false);
  editing = signal(false);
  editingId = signal<number | null>(null);

  form = signal<Omit<Product, 'id'>>({
    name: '',
    description: '',
    price: 0,
    quantity: 0
  });

  constructor(private productService: ProductService) {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAll({ page: this.page(), limit: this.limit }).subscribe(p => {
      this.products.set(p);
    });
  }

  nextPage() {
    this.page.update(p => p + 1);
    this.loadProducts();
  }

  prevPage() {
    if (this.page() > 0) {
      this.page.update(p => p - 1);
      this.loadProducts();
    }
  }

  openCreateModal() {
    this.form.set({
      name: '',
      description: '',
      price: 0,
      quantity: 0
    });
    this.editing.set(false);
    this.showModal.set(true);
  }

  openEditModal(product: Product) {
    this.form.set({
      name: product.name,
      description: product.description,
      price: product.price,
      quantity: product.quantity
    });
    this.editing.set(true);
    this.editingId.set(product.id);
    this.showModal.set(true);
  }

  updateFormField(field: keyof Omit<Product, 'id'>, value: any) {
    this.form.set({
      ...this.form(),
      [field]: value
    });
  }

  saveProduct() {
    const data = { ...this.form() };
    if (this.editing()) {
      const id = this.editingId();
      if (id != null) {
        this.productService.update(id, { ...data, id }).subscribe(() => {
          this.loadProducts();
          this.showModal.set(false);
        });
      }
    } else {
      this.productService.create(data).subscribe(() => {
        this.loadProducts();
        this.showModal.set(false);
      });
    }
  }

  deleteProduct(id: number) {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.delete(id).subscribe(() => this.loadProducts());
    }
  }
}
