export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface JwtResponse {
  token: string;
  id: number;
  username: string;
  email: string;
  nombreCompleto: string;
  roles: string[];
  fechaExpiracion: string;
}

export interface UserInfo {
  id: number;
  username: string;
  email: string;
  nombreCompleto: string;
  telefono: string;
  estado: string;
  roles: string[];
  ultimoAcceso: string;
  fechaCreacion: string;
}

export interface ApiResponse<T> {
  status: number;
  message: string;
  data: T;
}
