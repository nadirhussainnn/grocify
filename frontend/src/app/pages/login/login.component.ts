import { Component, signal, computed } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email = signal('');
  password = signal('');
  error = signal('');

  constructor(private auth: AuthService, private router: Router) {}

  handleLogin() {
    this.auth.login(this.email(), this.password()).subscribe({
      next: (res) => {
        this.auth.setToken(res.token);
        const role = this.auth.getRole();
        if (role === 'ADMIN') this.router.navigate(['/admin']);
        else if (role === 'CUSTOMER') this.router.navigate(['/customer']);
        else this.error.set('Unknown role');
      },
      error: () => {
        this.error.set('Invalid credentials');
      }
    });
  }
}
