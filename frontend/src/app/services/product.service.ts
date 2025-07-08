import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = `${environment.apiBaseUrl}/products`;
  private buyUrl = `${environment.apiBaseUrl}/orders`;

  constructor(private http: HttpClient) {}

  getAll(params: { page: number; limit: number; search?: string }) {
    const query: any = {
      page: params.page.toString(),
      limit: params.limit.toString(),
    };
    if (params.search) query.search = params.search;
    return this.http.get<Product[]>(this.apiUrl, { params: query });
  }

  create(product: Omit<Product, 'id'>): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  update(id: number, product: Product): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}`, product);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buy(productId: number, quantity: number): Observable<void> {
    return this.http.post<void>(this.buyUrl, {
      items: [{ productId, quantity }],
    });
  }
}
