import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { MaterialService } from '../../../materials/services/material.service';
import { MaterialDTO } from '../../../materials/interfaces/material.interface';

@Component({
  selector: 'app-material-page',
  imports: [CommonModule, RouterModule],
  templateUrl: './material-page.component.html',
  styleUrl: './material-page.component.css',
})
export class MaterialPageComponent implements OnInit {
  material: MaterialDTO | null = null;
  loading = false;
  error = '';
  materialId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private materialService: MaterialService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id && !isNaN(+id)) {
        this.materialId = +id;
        this.loadMaterial();
      } else {
        this.error = 'ID de material inválido';
      }
    });
  }

  loadMaterial(): void {
    if (!this.materialId) return;

    this.loading = true;
    this.error = '';

    this.materialService.getMaterialById(this.materialId).subscribe({
      next: (response) => {
        if (response.status === 200) {
          this.material = response.data;
          this.error = ''; // Limpiar cualquier error anterior
        } else {
          this.error = response.message;
        }
        this.loading = false;
        this.cdr.detectChanges(); // Forzar detección de cambios para Angular Zoneless
      },
      error: (error) => {
        console.error('Error loading material:', error);
        this.handleLoadError(error);
        this.loading = false;
        this.cdr.detectChanges(); // Forzar detección de cambios para Angular Zoneless
      }
    });
  }

  private handleLoadError(error: any): void {
    if (error.status === 404) {
      this.error = 'Material no encontrado';
    } else if (error.status === 500) {
      this.error = 'Error del servidor. Por favor intente más tarde.';
    } else if (error.status === 0) {
      this.error = 'No se puede conectar al servidor. Verifique su conexión.';
    } else {
      this.error = 'Error al cargar el material';
    }
  }

  goBack(): void {
    this.router.navigate(['/materials']);
  }

  onEdit(): void {
    // Esta función será llamada para abrir el modal de edición
    this.router.navigate(['/materials'], {
      queryParams: { edit: this.materialId }
    });
  }
}
