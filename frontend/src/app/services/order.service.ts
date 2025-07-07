import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Order } from '../models/order.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private apiUrl = 'http://localhost:8080/api/orders';

  constructor(private http: HttpClient) {}

  getAll(params: { page: number; limit: number; status?: string; date?: string }) {
    const query: any = {
      page: params.page.toString(),
      limit: params.limit.toString()
    };
    if (params.status) query.status = params.status;
    if (params.date) query.date = params.date;
  
    return this.http.get<Order[]>(this.apiUrl, { params: query });
  }
  

  markDelivered(orderId: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${orderId}/deliver`, {});
  }
}