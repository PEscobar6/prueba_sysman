import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { MaterialService } from '../../../materials/services/material.service';
import { MaterialCardComponent } from '../../../materials/components/material-card/material-card.component';
import { MaterialDTO, EstadoMaterial, CiudadDTO } from '../../../materials/interfaces/material.interface';

@Component({
  selector: 'app-materials-page',
  imports: [CommonModule, FormsModule, RouterModule, MaterialCardComponent],
  templateUrl: './materials-page.component.html',
  styleUrl: './materials-page.component.css',
})
export class MaterialsPageComponent implements OnInit {
  materiales: MaterialDTO[] = [];
  filteredMateriales: MaterialDTO[] = [];
  loading = false;
  error = '';

  // Filtros
  searchTerm = '';
  selectedTipo = '';
  selectedEstado = '';
  selectedFechaCompra = '';

  tipos: string[] = [];
  estados = Object.values(EstadoMaterial);

  // Modal properties
  newMaterial: Partial<MaterialDTO> = {};
  editMaterial: Partial<MaterialDTO> = {};
  ciudades: CiudadDTO[] = [];
  selectedCiudadCodigo = '';
  selectedEditCiudadCodigo = '';
  creatingMaterial = false;
  updatingMaterial = false;
  createError = '';
  updateError = '';
  editingMaterialId: number | null = null;

  constructor(
    private materialService: MaterialService,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute
  ) { 
    this.initializeNewMaterial();
  }

  ngOnInit(): void {
    this.loadMateriales();
    this.loadCiudades();

    // Verificar si se debe abrir el modal de edición
    this.route.queryParams.subscribe(params => {
      if (params['edit']) {
        const editId = +params['edit'];
        if (!isNaN(editId)) {
          this.openEditModalById(editId);
        }
      }
    });
  }

  loadMateriales(): void {
    this.loading = true;
    this.error = '';

    this.materialService.getAllMateriales().subscribe({
      next: (response) => {
        if (response.status === 200) {
          this.materiales = response.data;
          this.filteredMateriales = [...this.materiales];
          this.extractTipos();
          this.error = ''; // Limpiar cualquier error anterior
        } else {
          this.error = response.message;
        }
        this.loading = false;
        this.cdr.detectChanges(); // Forzar detección de cambios para Angular Zoneless
      },
      error: (error) => {
        console.error('Error loading materials:', error);
        this.handleLoadError(error);
        this.loading = false;
        this.cdr.detectChanges(); // Forzar detección de cambios para Angular Zoneless
      }
    });
  }

  private handleLoadError(error: any): void {
    if (error.status === 404) {
      this.error = 'No se encontraron materiales';
      this.materiales = [];
      this.filteredMateriales = [];
    } else if (error.status === 500) {
      this.error = 'Error del servidor. Por favor intente más tarde.';
    } else if (error.status === 0) {
      this.error = 'No se puede conectar al servidor. Verifique su conexión.';
    } else {
      this.error = 'Error al cargar los materiales';
    }
  }

  private extractTipos(): void {
    const tiposSet = new Set(this.materiales.map(m => m.tipo));
    this.tipos = Array.from(tiposSet).sort();
  }

  onSearch(): void {
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  private applyFilters(): void {
    this.filteredMateriales = this.materiales.filter(material => {
      const matchesSearch = !this.searchTerm ||
        material.nombre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        material.descripcion?.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesTipo = !this.selectedTipo || material.tipo === this.selectedTipo;
      const matchesEstado = !this.selectedEstado || material.estado === this.selectedEstado;
      const matchesFecha = !this.selectedFechaCompra || material.fechaCompra === this.selectedFechaCompra;

      return matchesSearch && matchesTipo && matchesEstado && matchesFecha;
    });
  }

  onEditMaterial(material: MaterialDTO): void {
    this.openEditModal(material);
  }

  onDeleteMaterial(materialId: number): void {
    this.materialService.deleteMaterial(materialId).subscribe({
      next: (response) => {
        if (response.status === 200) {
          this.loadMateriales(); // Recargar la lista
        } else {
          alert('Error al eliminar el material: ' + response.message);
        }
      },
      error: (error) => {
        console.error('Error deleting material:', error);
        alert('Error al eliminar el material');
      }
    });
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.selectedTipo = '';
    this.selectedEstado = '';
    this.selectedFechaCompra = '';
    this.filteredMateriales = [...this.materiales];
  }

  trackByMaterialId(index: number, material: MaterialDTO): number {
    return material.id || index;
  }

  // Métodos para el modal de crear material
  openCreateModal(): void {
    this.initializeNewMaterial();
  }

  initializeNewMaterial(): void {
    this.newMaterial = {
      nombre: '',
      descripcion: '',
      tipo: '',
      precio: undefined,
      fechaCompra: '',
      estado: '' as any  // Inicializar vacío para que el usuario seleccione
    };
    this.selectedCiudadCodigo = '';
    this.createError = '';
  }

  loadCiudades(): void {
    this.materialService.getAllCiudades().subscribe({
      next: (response) => {
        if (response.status === 200) {
          this.ciudades = response.data;
        }
      },
      error: (error) => {
        console.error('Error loading ciudades:', error);
      }
    });
  }

  getMaxDate(): string {
    const today = new Date();
    return today.toISOString().split('T')[0]; // YYYY-MM-DD format
  }

  onCreateMaterial(): void {
    if (!this.selectedCiudadCodigo) {
      this.createError = 'Debe seleccionar una ciudad';
      return;
    }

    // Encontrar la ciudad seleccionada
    const selectedCiudad = this.ciudades.find(c => c.codigo === this.selectedCiudadCodigo);
    if (!selectedCiudad) {
      this.createError = 'Ciudad seleccionada no válida';
      return;
    }

    // Preparar el material para enviar
    const materialToCreate: MaterialDTO = {
      nombre: this.newMaterial.nombre!,
      descripcion: this.newMaterial.descripcion,
      tipo: this.newMaterial.tipo!,
      precio: this.newMaterial.precio!,
      fechaCompra: this.newMaterial.fechaCompra!,
      estado: this.newMaterial.estado!,
      ciudad: selectedCiudad
    };

    this.creatingMaterial = true;
    this.createError = '';

    this.materialService.createMaterial(materialToCreate).subscribe({
      next: (response) => {
        if (response.status === 201 || response.status === 200) {
          // Cerrar modal primero
          this.closeModal();
          
          // Mostrar mensaje de éxito
          this.showSuccessMessage('Material creado exitosamente');
          
          // Recargar materiales y actualizar vista
          this.refreshMaterialsList();
          
          // Reinicializar formulario para próximo uso
          this.initializeNewMaterial();
        } else {
          this.createError = response.message || 'Error al crear el material';
        }
        this.creatingMaterial = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error creating material:', error);
        this.handleCreateError(error);
        this.creatingMaterial = false;
        this.cdr.detectChanges();
      }
    });
  }

  private closeModal(): void {
    const modal = document.getElementById('nuevo_material_modal') as HTMLDialogElement;
    modal?.close();
  }

  private showSuccessMessage(message: string): void {
    // Crear un toast de éxito temporal
    const toast = document.createElement('div');
    toast.className = 'toast toast-top toast-end';
    toast.innerHTML = `
      <div class="alert alert-success">
        <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <span>${message}</span>
      </div>
    `;
    
    document.body.appendChild(toast);
    
    // Remover el toast después de 3 segundos
    setTimeout(() => {
      if (toast.parentNode) {
        toast.parentNode.removeChild(toast);
      }
    }, 3000);
  }

  private refreshMaterialsList(): void {
    // Guardar filtros actuales
    const currentFilters = {
      searchTerm: this.searchTerm,
      selectedTipo: this.selectedTipo,
      selectedEstado: this.selectedEstado,
      selectedFechaCompra: this.selectedFechaCompra
    };

    // Recargar materiales
    this.loadMateriales();

    // Restaurar filtros después de la carga (con un pequeño delay)
    setTimeout(() => {
      this.searchTerm = currentFilters.searchTerm;
      this.selectedTipo = currentFilters.selectedTipo;
      this.selectedEstado = currentFilters.selectedEstado;
      this.selectedFechaCompra = currentFilters.selectedFechaCompra;
      this.applyFilters();
      this.cdr.detectChanges();
    }, 100);
  }

  private handleCreateError(error: any): void {
    if (error.error?.message) {
      this.createError = error.error.message;
    } else if (error.error?.data) {
      // Handle validation errors
      const validationErrors = Object.values(error.error.data).join(', ');
      this.createError = validationErrors;
    } else if (error.status === 400) {
      this.createError = 'Datos inválidos. Por favor revise la información ingresada.';
    } else if (error.status === 500) {
      this.createError = 'Error interno del servidor. Por favor intente más tarde.';
    } else {
      this.createError = 'Error al crear el material. Por favor intente nuevamente.';
    }
  }

  // Métodos para el modal de edición
  openEditModal(material: MaterialDTO): void {
    this.editingMaterialId = material.id!;
    this.editMaterial = { ...material };
    this.selectedEditCiudadCodigo = material.ciudad.codigo;
    this.updateError = '';
    
    const modal = document.getElementById('edit_material_modal') as HTMLDialogElement;
    modal?.showModal();
  }

  openEditModalById(materialId: number): void {
    const material = this.materiales.find(m => m.id === materialId);
    if (material) {
      this.openEditModal(material);
    }
  }

  onUpdateMaterial(): void {
    if (!this.selectedEditCiudadCodigo) {
      this.updateError = 'Debe seleccionar una ciudad';
      return;
    }

    if (!this.editingMaterialId) {
      this.updateError = 'ID de material inválido';
      return;
    }

    // Encontrar la ciudad seleccionada
    const selectedCiudad = this.ciudades.find(c => c.codigo === this.selectedEditCiudadCodigo);
    if (!selectedCiudad) {
      this.updateError = 'Ciudad seleccionada no válida';
      return;
    }

    // Preparar el material para actualizar
    const materialToUpdate: MaterialDTO = {
      id: this.editingMaterialId,
      nombre: this.editMaterial.nombre!,
      descripcion: this.editMaterial.descripcion,
      tipo: this.editMaterial.tipo!,
      precio: this.editMaterial.precio!,
      fechaCompra: this.editMaterial.fechaCompra!,
      fechaVenta: this.editMaterial.fechaVenta,
      estado: this.editMaterial.estado!,
      ciudad: selectedCiudad
    };

    this.updatingMaterial = true;
    this.updateError = '';

    this.materialService.updateMaterial(this.editingMaterialId, materialToUpdate).subscribe({
      next: (response) => {
        if (response.status === 200) {
          // Cerrar modal
          this.closeEditModal();
          
          // Mostrar mensaje de éxito
          this.showSuccessMessage('Material actualizado exitosamente');
          
          // Recargar materiales
          this.refreshMaterialsList();
        } else {
          this.updateError = response.message || 'Error al actualizar el material';
        }
        this.updatingMaterial = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error updating material:', error);
        this.handleUpdateError(error);
        this.updatingMaterial = false;
        this.cdr.detectChanges();
      }
    });
  }

  closeEditModal(): void {
    const modal = document.getElementById('edit_material_modal') as HTMLDialogElement;
    modal?.close();
    this.editingMaterialId = null;
    this.editMaterial = {};
    this.selectedEditCiudadCodigo = '';
    this.updateError = '';
  }

  private handleUpdateError(error: any): void {
    if (error.error?.message) {
      this.updateError = error.error.message;
    } else if (error.error?.data) {
      // Handle validation errors
      const validationErrors = Object.values(error.error.data).join(', ');
      this.updateError = validationErrors;
    } else if (error.status === 400) {
      this.updateError = 'Datos inválidos. Por favor revise la información ingresada.';
    } else if (error.status === 404) {
      this.updateError = 'Material no encontrado.';
    } else if (error.status === 500) {
      this.updateError = 'Error interno del servidor. Por favor intente más tarde.';
    } else {
      this.updateError = 'Error al actualizar el material. Por favor intente nuevamente.';
    }
  }
}
