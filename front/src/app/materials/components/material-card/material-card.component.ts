import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MaterialDTO } from '../../interfaces/material.interface';

@Component({
  selector: 'app-material-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="card bg-base-100 shadow-xl">
      <div class="card-body">
        <!-- Header con nombre y estado -->
        <div class="flex justify-between items-start mb-4">
          <h2 class="card-title">{{ material.nombre }}</h2>
          <div class="badge" [ngClass]="getEstadoBadgeClass()">
            {{ material.estado }}
          </div>
        </div>

        <!-- Descripción -->
        <p class="text-base-content/70 mb-4">
          {{ material.descripcion || 'Sin descripción' }}
        </p>

        <!-- Información del material -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-3 mb-4">
          <div class="flex flex-col">
            <span class="text-sm font-semibold text-base-content/60">Tipo</span>
            <span class="badge badge-outline">{{ material.tipo }}</span>
          </div>

          <div class="flex flex-col">
            <span class="text-sm font-semibold text-base-content/60">Precio</span>
            <span class="text-lg font-bold text-success">
              {{ material.precio | currency:'COP':'symbol':'1.0-0' }}
            </span>
          </div>

          <div class="flex flex-col">
            <span class="text-sm font-semibold text-base-content/60">Fecha de compra</span>
            <span>{{ material.fechaCompra | date:'dd/MM/yyyy' }}</span>
          </div>

          <div class="flex flex-col" *ngIf="material.fechaVenta">
            <span class="text-sm font-semibold text-base-content/60">Fecha de venta</span>
            <span>{{ material.fechaVenta | date:'dd/MM/yyyy' }}</span>
          </div>

          <div class="flex flex-col md:col-span-2">
            <span class="text-sm font-semibold text-base-content/60">Ubicación</span>
            <span class="badge badge-ghost">
              {{ material.ciudad.nombre }}, {{ material.ciudad.departamento.nombre }}
            </span>
          </div>
        </div>

        <!-- Acciones -->
        <div class="card-actions justify-end space-x-2">
          <button
            class="btn btn-primary btn-sm"
            [routerLink]="['/material', material.id]">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
            </svg>
            Ver
          </button>

          <button
            class="btn btn-secondary btn-sm"
            (click)="onEdit()">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
            </svg>
            Editar
          </button>

          <button
            class="btn btn-error btn-sm"
            (click)="onDelete()">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
            </svg>
            Eliminar
          </button>
        </div>
      </div>
    </div>
  `,
  styleUrl: './material-card.component.css'
})
export class MaterialCardComponent {
  @Input() material!: MaterialDTO;
  @Output() edit = new EventEmitter<MaterialDTO>();
  @Output() delete = new EventEmitter<number>();

  getEstadoBadgeClass(): string {
    switch (this.material.estado) {
      case 'ACTIVO': return 'badge-success';
      case 'DISPONIBLE': return 'badge-info';
      case 'ASIGNADO': return 'badge-warning';
      default: return 'badge-neutral';
    }
  }

  getEstadoClass(): string {
    switch (this.material.estado) {
      case 'ACTIVO': return 'estado-activo';
      case 'DISPONIBLE': return 'estado-disponible';
      case 'ASIGNADO': return 'estado-asignado';
      default: return '';
    }
  }

  onEdit(): void {
    this.edit.emit(this.material);
  }

  onDelete(): void {
    if (this.material.id && confirm(`¿Estás seguro de eliminar el material "${this.material.nombre}"?`)) {
      this.delete.emit(this.material.id);
    }
  }
}
