# Sistema de Gestión de Materiales - API RESTful

## Descripción
API RESTful desarrollada en Spring Boot para la gestión de materiales, ciudades y departamentos.

## Características
- ✅ CRUD completo de materiales
- ✅ Búsquedas por tipo, fecha de compra y ciudad
- ✅ Validación de fechas (fecha de compra ≤ fecha de venta)
- ✅ Manejo centralizado de excepciones
- ✅ Respuestas HTTP estandarizadas (200, 404, 500)
- ✅ Arquitectura en capas (Controller → Service → Repository)
- ✅ DTOs para transferencia de datos
- ✅ Entidades JPA con relaciones

## Tecnologías
- Java 17
- Spring Boot 3.5.5
- Spring Data JPA
- PostgreSQL
- Maven
- Bean Validation

## Estructura del Proyecto
```
src/main/java/com/example/prueba_syman/
├── controller/          # Endpoints REST
├── service/            # Lógica de negocio
├── repository/         # Acceso a datos
├── entities/           # Entidades JPA
├── model/dto/          # Data Transfer Objects
├── exception/          # Manejo de excepciones
└── service/mapper/     # Mappers entidad ↔ DTO
```

## Entidades
- **Material**: nombre, descripción, tipo, precio, fecha de compra, fecha de venta, estado, ciudad
- **Ciudad**: código, nombre, departamento
- **Departamento**: código, nombre

## Estados de Material
- `ACTIVO`: Material en uso activo
- `DISPONIBLE`: Material disponible para asignación
- `ASIGNADO`: Material asignado a proyecto/persona

## Endpoints Principales

### Búsquedas
- `GET /api/materiales` - Todos los materiales
- `GET /api/materiales/{id}` - Material por ID
- `GET /api/materiales/tipo/{tipo}` - Por tipo
- `GET /api/materiales/fecha-compra/{fecha}` - Por fecha de compra
- `GET /api/materiales/ciudad/{ciudad}` - Por ciudad
- `GET /api/materiales/buscar?tipo=X&fechaCompra=Y` - Búsqueda combinada

### CRUD
- `POST /api/materiales` - Crear material
- `PUT /api/materiales/{id}` - Actualizar material
- `DELETE /api/materiales/{id}` - Eliminar material

## Configuración

### Variables de Entorno
```env
DATABASE_URL=jdbc:postgresql://localhost:5432/sysman_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password
```

### Ejecución
```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar aplicación
mvn spring-boot:run
```

## Datos de Prueba
El archivo `data.sql` incluye datos de ejemplo:
- 4 departamentos (Antioquia, Cundinamarca, Valle, Atlántico)
- 6 ciudades (Medellín, Bogotá, Cali, Barranquilla, Envigado, Chía)
- 8 materiales de diferentes tipos

## Validaciones Implementadas
- Fechas: fecha de compra ≤ fecha de venta
- Campos obligatorios validados
- Longitud máxima de strings
- Precios positivos
- Estados válidos

## Manejo de Errores
Todas las excepciones están capturadas y retornan respuestas estandarizadas:
- `MaterialNotFoundException` → 404
- `CiudadNotFoundException` → 404
- `InvalidDateException` → 400
- `MethodArgumentNotValidException` → 400
- `Exception` → 500

## Documentación API
Ver archivo `API_DOCUMENTATION.md` para ejemplos detallados de uso.

## Control de Versiones
El proyecto está preparado para versionado con Git. Asegúrate de hacer commits regulares de los cambios.
