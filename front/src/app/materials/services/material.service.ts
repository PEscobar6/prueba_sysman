import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, MaterialDTO, CiudadDTO, DepartamentoDTO } from '../interfaces/material.interface';

@Injectable({
  providedIn: 'root'
})
export class MaterialService {
  private readonly API_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAllMateriales(): Observable<ApiResponse<MaterialDTO[]>> {
    return this.http.get<ApiResponse<MaterialDTO[]>>(`${this.API_URL}/materiales`);
  }

  getMaterialById(id: number): Observable<ApiResponse<MaterialDTO>> {
    return this.http.get<ApiResponse<MaterialDTO>>(`${this.API_URL}/materiales/${id}`);
  }

  getMaterialesByTipo(tipo: string): Observable<ApiResponse<MaterialDTO[]>> {
    return this.http.get<ApiResponse<MaterialDTO[]>>(`${this.API_URL}/materiales/tipo/${tipo}`);
  }

  getMaterialesByFechaCompra(fechaCompra: string): Observable<ApiResponse<MaterialDTO[]>> {
    return this.http.get<ApiResponse<MaterialDTO[]>>(`${this.API_URL}/materiales/fecha-compra/${fechaCompra}`);
  }

  getMaterialesByCiudad(ciudad: string): Observable<ApiResponse<MaterialDTO[]>> {
    return this.http.get<ApiResponse<MaterialDTO[]>>(`${this.API_URL}/materiales/ciudad/${ciudad}`);
  }

  searchMateriales(tipo?: string, fechaCompra?: string): Observable<ApiResponse<MaterialDTO[]>> {
    let params = new HttpParams();
    if (tipo) {
      params = params.set('tipo', tipo);
    }
    if (fechaCompra) {
      params = params.set('fechaCompra', fechaCompra);
    }
    return this.http.get<ApiResponse<MaterialDTO[]>>(`${this.API_URL}/materiales/buscar`, { params });
  }

  createMaterial(material: MaterialDTO): Observable<ApiResponse<MaterialDTO>> {
    return this.http.post<ApiResponse<MaterialDTO>>(`${this.API_URL}/materiales`, material);
  }

  updateMaterial(id: number, material: MaterialDTO): Observable<ApiResponse<MaterialDTO>> {
    return this.http.put<ApiResponse<MaterialDTO>>(`${this.API_URL}/materiales/${id}`, material);
  }

  deleteMaterial(id: number): Observable<ApiResponse<any>> {
    return this.http.delete<ApiResponse<any>>(`${this.API_URL}/materiales/${id}`);
  }

  getAllCiudades(): Observable<ApiResponse<CiudadDTO[]>> {
    return this.http.get<ApiResponse<CiudadDTO[]>>(`${this.API_URL}/ciudades`);
  }

  getAllDepartamentos(): Observable<ApiResponse<DepartamentoDTO[]>> {
    return this.http.get<ApiResponse<DepartamentoDTO[]>>(`${this.API_URL}/departamentos`);
  }
}
