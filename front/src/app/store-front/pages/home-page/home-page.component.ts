import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
})
export class HomePageComponent {
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);
  
  currentUser$ = this.authService.currentUser$;
  isLoggedIn$ = this.authService.isLoggedIn$;

  constructor() {
    // Suscribirse a cambios de estado de autenticación
    this.authService.isLoggedIn$.subscribe(() => {
      this.cdr.detectChanges();
    });
    
    this.authService.currentUser$.subscribe(() => {
      this.cdr.detectChanges();
    });
  }
}