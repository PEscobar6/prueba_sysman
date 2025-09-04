import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'front-navbar',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './front-navbar.component.html',
  styleUrl: './front-navbar.component.css',
})
export class FrontNavbarComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  currentUser$ = this.authService.currentUser$;
  isLoggedIn$ = this.authService.isLoggedIn$;

  constructor() {
    // Suscribirse a cambios de estado de autenticación para actualizar la UI
    this.authService.isLoggedIn$.subscribe(() => {
      this.cdr.detectChanges();
    });
    
    this.authService.currentUser$.subscribe(() => {
      this.cdr.detectChanges();
    });
  }

  onLogout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.cdr.detectChanges();
        this.router.navigate(['/auth/login']);
      },
      error: () => {
        // En caso de error, igual limpiar el token localmente
        this.authService.clearTokenAndUser();
        this.cdr.detectChanges();
        this.router.navigate(['/auth/login']);
      }
    });
  }

  onLogin(): void {
    this.router.navigate(['/auth/login']);
  }
}
