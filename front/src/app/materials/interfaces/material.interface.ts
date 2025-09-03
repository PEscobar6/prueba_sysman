export interface DepartamentoDTO {
  codigo: string;
  nombre: string;
}

export interface CiudadDTO {
  codigo: string;
  nombre: string;
  departamento: DepartamentoDTO;
}

export interface MaterialDTO {
  id?: number;
  nombre: string;
  descripcion?: string;
  tipo: string;
  precio: number;
  fechaCompra: string; // Format: YYYY-MM-DD
  fechaVenta?: string; // Format: YYYY-MM-DD
  estado: EstadoMaterial;
  ciudad: CiudadDTO;
}

export enum EstadoMaterial {
  ACTIVO = 'ACTIVO',
  DISPONIBLE = 'DISPONIBLE',
  ASIGNADO = 'ASIGNADO'
}

export interface ApiResponse<T> {
  status: number;
  message: string;
  data: T;
  timestamp: string;
}
