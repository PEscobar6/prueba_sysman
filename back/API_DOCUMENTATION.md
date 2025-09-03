# API RESTful de Materiales - Documentación

## Descripción
API para la administración de materiales que permite realizar operaciones CRUD y búsquedas específicas.

## Base URL
```
http://localhost:8080/api/materiales
```

## Endpoints

### 1. Obtener todos los materiales
```http
GET /api/materiales
```

**Respuesta exitosa (200):**
```json
{
    "status": 200,
    "message": "Se encontraron 8 materiales",
    "data": [
        {
            "id": 1,
            "nombre": "Cemento Portland",
            "descripcion": "Cemento de alta calidad para construcción",
            "tipo": "CONSTRUCCION",
            "precio": 25000.00,
            "fechaCompra": "2024-01-15",
            "fechaVenta": null,
            "estado": "DISPONIBLE",
            "ciudad": {
                "codigo": "MED",
                "nombre": "Medellín",
                "departamento": {
                    "codigo": "ANT",
                    "nombre": "Antioquia"
                }
            }
        }
    ],
    "timestamp": "2024-12-20T10:30:00"
}
```

### 2. Obtener material por ID
```http
GET /api/materiales/{id}
```

**Ejemplo:**
```http
GET /api/materiales/1
```

### 3. Buscar materiales por tipo
```http
GET /api/materiales/tipo/{tipo}
```

**Ejemplo:**
```http
GET /api/materiales/tipo/CONSTRUCCION
```

### 4. Buscar materiales por fecha de compra
```http
GET /api/materiales/fecha-compra/{fechaCompra}
```

**Ejemplo:**
```http
GET /api/materiales/fecha-compra/2024-01-15
```

### 5. Buscar materiales por tipo y/o fecha (query parameters)
```http
GET /api/materiales/buscar?tipo={tipo}&fechaCompra={fechaCompra}
```

**Ejemplos:**
```http
GET /api/materiales/buscar?tipo=TECNOLOGIA
GET /api/materiales/buscar?fechaCompra=2024-01-15
GET /api/materiales/buscar?tipo=CONSTRUCCION&fechaCompra=2024-01-15
```

### 6. Buscar materiales por ciudad
```http
GET /api/materiales/ciudad/{ciudadIdentifier}
```

**Ejemplos:**
```http
GET /api/materiales/ciudad/MED
GET /api/materiales/ciudad/Medellín
```

### 7. Crear nuevo material
```http
POST /api/materiales
Content-Type: application/json
```

**Body:**
```json
{
    "nombre": "Nuevo Material",
    "descripcion": "Descripción del material",
    "tipo": "CONSTRUCCION",
    "precio": 15000.00,
    "fechaCompra": "2024-03-15",
    "fechaVenta": null,
    "estado": "DISPONIBLE",
    "ciudad": {
        "codigo": "MED",
        "nombre": "Medellín",
        "departamento": {
            "codigo": "ANT",
            "nombre": "Antioquia"
        }
    }
}
```

### 8. Actualizar material
```http
PUT /api/materiales/{id}
Content-Type: application/json
```

**Ejemplo:**
```http
PUT /api/materiales/1
```

**Body:** (mismo formato que crear)

### 9. Eliminar material
```http
DELETE /api/materiales/{id}
```

**Ejemplo:**
```http
DELETE /api/materiales/1
```

## Códigos de Respuesta

- **200**: Operación exitosa
- **201**: Recurso creado exitosamente
- **400**: Error en la validación de datos
- **404**: Recurso no encontrado
- **500**: Error interno del servidor

## Estados de Material

- `ACTIVO`: Material activo en uso
- `DISPONIBLE`: Material disponible para asignación
- `ASIGNADO`: Material asignado a un proyecto/persona

## Validaciones

### Fechas
- La fecha de compra no puede ser posterior a la fecha de venta
- La fecha de compra no puede ser futura

### Campos obligatorios
- nombre
- tipo
- precio
- fechaCompra
- estado
- ciudad

## Ejemplos de respuestas de error

### Material no encontrado (404)
```json
{
    "status": 404,
    "message": "Material no encontrado con ID: 999",
    "data": null,
    "timestamp": "2024-12-20T10:30:00"
}
```

### Error de validación (400)
```json
{
    "status": 400,
    "message": "Errores de validación",
    "data": {
        "nombre": "El nombre del material es obligatorio",
        "precio": "El precio debe ser mayor a 0"
    },
    "timestamp": "2024-12-20T10:30:00"
}
```

### Error de fechas (400)
```json
{
    "status": 400,
    "message": "La fecha de compra no puede ser posterior a la fecha de venta",
    "data": null,
    "timestamp": "2024-12-20T10:30:00"
}
```

## Configuración de Base de Datos

La aplicación requiere las siguientes variables de entorno:

- `DATABASE_URL`: URL de conexión a PostgreSQL
- `DATABASE_USERNAME`: Usuario de la base de datos
- `DATABASE_PASSWORD`: Contraseña de la base de datos

Ejemplo:
```
DATABASE_URL=jdbc:postgresql://localhost:5432/sysman_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password
```
