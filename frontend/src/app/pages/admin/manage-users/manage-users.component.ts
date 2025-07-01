import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-manage-users',
  imports: [CommonModule],
  templateUrl: './manage-users.component.html',
  styleUrl: './manage-users.component.css'
})
export class ManageUsersComponent {
  users = [
    { id: 'U101', name: 'Alice Brown', email: 'alice@example.com', role: 'Customer', status: 'Active' },
    { id: 'U102', name: 'Bob Green', email: 'bob@example.com', role: 'Customer', status: 'Suspended' },
  ];
}
