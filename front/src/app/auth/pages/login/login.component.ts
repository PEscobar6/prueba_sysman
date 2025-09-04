import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <div class="max-w-md w-full space-y-8">
        <div>
          <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
            Iniciar Sesión
          </h2>
          <p class="mt-2 text-center text-sm text-gray-600">
            Ingresa a tu cuenta del sistema de gestión
          </p>
        </div>

        <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="mt-8 space-y-6">
          <div class="space-y-4">
            <!-- Usuario o Email -->
            <div>
              <label for="usernameOrEmail" class="sr-only">Usuario o Email</label>
              <input
                id="usernameOrEmail"
                name="usernameOrEmail"
                type="text"
                formControlName="usernameOrEmail"
                class="input input-bordered w-full"
                [class.input-error]="loginForm.get('usernameOrEmail')?.invalid && loginForm.get('usernameOrEmail')?.touched"
                placeholder="Usuario o Email"
              />
              <div *ngIf="loginForm.get('usernameOrEmail')?.invalid && loginForm.get('usernameOrEmail')?.touched"
                   class="label">
                <span class="label-text-alt text-error">Usuario o email es requerido</span>
              </div>
            </div>

            <!-- Contraseña -->
            <div>
              <label for="password" class="sr-only">Contraseña</label>
              <input
                id="password"
                name="password"
                type="password"
                formControlName="password"
                class="input input-bordered w-full"
                [class.input-error]="loginForm.get('password')?.invalid && loginForm.get('password')?.touched"
                placeholder="Contraseña"
              />
              <div *ngIf="loginForm.get('password')?.invalid && loginForm.get('password')?.touched"
                   class="label">
                <span class="label-text-alt text-error">Contraseña es requerida</span>
              </div>
            </div>
          </div>

          <!-- Error general -->
          <div *ngIf="errorMessage" class="alert alert-error">
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span>{{ errorMessage }}</span>
          </div>

          <!-- Mensaje de éxito -->
          <div *ngIf="successMessage" class="alert alert-success">
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span>{{ successMessage }}</span>
          </div>

          <div>
            <button
              type="submit"
              [disabled]="loginForm.invalid || isLoading"
              class="btn btn-primary w-full"
            >
              <span *ngIf="isLoading" class="loading loading-spinner loading-sm"></span>
              {{ isLoading ? 'Iniciando sesión...' : 'Iniciar Sesión' }}
            </button>
          </div>

          <!-- Credenciales de prueba -->
          <div class="mt-6">
            <div class="relative">
              <div class="absolute inset-0 flex items-center">
                <div class="w-full border-t border-gray-300"></div>
              </div>
              <div class="relative flex justify-center text-sm">
                <span class="px-2 bg-gray-50 text-gray-500">Credenciales de prueba</span>
              </div>
            </div>
            <div class="mt-4 text-center text-sm text-gray-600">
              <p><strong>Usuario:</strong> admin</p>
              <p><strong>Contraseña:</strong> admin123</p>
            </div>
          </div>
        </form>
      </div>
    </div>
  `
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor() {
    this.loginForm = this.fb.group({
      usernameOrEmail: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';
      this.cdr.detectChanges();

      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.successMessage = 'Inicio de sesión exitoso';
            this.isLoading = false;
            this.cdr.detectChanges();
            setTimeout(() => {
              this.router.navigate(['/']);
            }, 1000);
          } else {
            this.errorMessage = response.message || 'Error en el inicio de sesión';
            this.isLoading = false;
            this.cdr.detectChanges();
          }
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Error de conexión. Verifique sus credenciales.';
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
    }
  }
}
