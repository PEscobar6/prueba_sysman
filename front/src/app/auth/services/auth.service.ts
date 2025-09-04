import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest, JwtResponse, UserInfo, ApiResponse } from '../interfaces/auth.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/auth';

  private currentUserSubject = new BehaviorSubject<UserInfo | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());
  public isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor() {
    // Si hay token al inicializar, cargar información del usuario
    if (this.hasToken()) {
      this.loadCurrentUser().subscribe();
    }
  }

  login(credentials: LoginRequest): Observable<ApiResponse<JwtResponse>> {
    return this.http.post<ApiResponse<JwtResponse>>(`${this.baseUrl}/login`, credentials)
      .pipe(
        tap(response => {
          if (response.status === 200 && response.data) {
            this.setToken(response.data.token);
            this.loadCurrentUser().subscribe();
          }
        })
      );
  }

  logout(): Observable<ApiResponse<string>> {
    return this.http.post<ApiResponse<string>>(`${this.baseUrl}/logout`, {})
      .pipe(
        tap(() => {
          this.clearTokenAndUser();
        })
      );
  }

  loadCurrentUser(): Observable<ApiResponse<UserInfo>> {
    return this.http.get<ApiResponse<UserInfo>>(`${this.baseUrl}/user`)
      .pipe(
        tap(response => {
          if (response.status === 200 && response.data) {
            this.currentUserSubject.next(response.data);
            this.isLoggedInSubject.next(true);
          }
        })
      );
  }

  private setToken(token: string): void {
    localStorage.setItem('jwt_token', token);
    this.isLoggedInSubject.next(true);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  private hasToken(): boolean {
    return !!this.getToken();
  }

  clearTokenAndUser(): void {
    localStorage.removeItem('jwt_token');
    this.currentUserSubject.next(null);
    this.isLoggedInSubject.next(false);
  }

  getCurrentUser(): UserInfo | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.hasToken();
  }
}
