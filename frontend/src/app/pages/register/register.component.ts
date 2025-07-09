import { Component, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

    email = signal('');
    password = signal('');
    error = signal('');
  
    constructor(private auth: AuthService, private router: Router) {}
  
    handleRegister() {
      this.auth.registerCustomer(this.email(), this.password()).subscribe({
        next: (res) => {
          this.router.navigate(['/login']);
        },
        error: () => {
          this.error.set('Failed to register - try again with other email!');
        }
      });
    }
  }
  
