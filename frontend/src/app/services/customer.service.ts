import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/customer.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CustomerService {

  private apiUrl = `${environment.apiBaseUrl}/customers`

  constructor(private http: HttpClient) {}

  getAll(params: { page: number; limit: number; search?: string }): Observable<Customer[]> {
    const query: any = {
      page: params.page.toString(),
      limit: params.limit.toString()
    };
    if (params.search) query.search = params.search;
    return this.http.get<Customer[]>(this.apiUrl, { params: query });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
