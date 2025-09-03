# Sistema de Gestión de Materiales (SysMan)

## Descripción General
Aplicación full-stack para la gestión de materiales con backend en Spring Boot y frontend en Angular.

## Estructura del Proyecto
```
├── back/                   # Backend - API REST en Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── front/                  # Frontend - Aplicación Angular
│   ├── src/
│   ├── package.json
│   └── README.md
├── docker-compose.yml      # Configuración Docker
└── README.md              # Este archivo
```

## Componentes

### Backend (Spring Boot)
- **Puerto**: 8080
- **Base de datos**: PostgreSQL
- **Funcionalidades**:
  - API RESTful completa para materiales
  - CRUD de materiales, ciudades y departamentos
  - Búsquedas avanzadas por tipo, fecha y ciudad
  - Validaciones de negocio (fechas, campos obligatorios)
  - Manejo centralizado de excepciones
  - Respuestas HTTP estandarizadas

### Frontend (Angular)
- **Puerto**: 4200
- **Framework**: Angular 18+
- **Funcionalidades**:
  - Interfaz de usuario para gestión de materiales
  - Navegación entre páginas
  - Integración con API del backend

## Entidades del Sistema

### Material
- ID (autoincremental)
- Nombre
- Descripción
- Tipo
- Precio
- Fecha de compra
- Fecha de venta (opcional)
- Estado: ACTIVO | DISPONIBLE | ASIGNADO
- Ciudad (relación)

### Ciudad
- Código (PK)
- Nombre
- Departamento (relación)

### Departamento
- Código (PK)
- Nombre

## Endpoints Principales

### Materiales
- `GET /api/materiales` - Todos los materiales
- `GET /api/materiales/{id}` - Material por ID
- `GET /api/materiales/tipo/{tipo}` - Por tipo
- `GET /api/materiales/fecha-compra/{fecha}` - Por fecha
- `GET /api/materiales/ciudad/{ciudad}` - Por ciudad
- `POST /api/materiales` - Crear material
- `PUT /api/materiales/{id}` - Actualizar material
- `DELETE /api/materiales/{id}` - Eliminar material

### Auxiliares
- `GET /api/ciudades` - Listar ciudades
- `GET /api/departamentos` - Listar departamentos

## Configuración y Ejecución

### Requisitos
- Java 17+
- Node.js 18+
- PostgreSQL 12+
- Docker (opcional)

### Variables de Entorno
```env
DATABASE_URL=jdbc:postgresql://localhost:5432/sysman_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password
```

### Ejecución con Docker
```bash
docker-compose up --build
```

### Ejecución Manual

#### Backend
```bash
cd back
mvn spring-boot:run
```

#### Frontend
```bash
cd front
npm install
npm start
```

## Características Técnicas

### Backend
- **Arquitectura**: Controller → Service → Repository
- **ORM**: Spring Data JPA
- **Validaciones**: Bean Validation
- **Excepciones**: Manejo centralizado
- **Testing**: JUnit 5
- **Documentación**: API REST documentada

### Frontend
- **Componentes**: Modulares y reutilizables
- **Servicios**: Inyección de dependencias
- **Routing**: Angular Router
- **HTTP**: HttpClient para API calls
- **Estilos**: CSS modular

## Validaciones de Negocio
- ✅ Fecha de compra ≤ fecha de venta
- ✅ Campos obligatorios validados
- ✅ Precios positivos
- ✅ Estados válidos
- ✅ Ciudades existentes

## Control de Versiones
El proyecto está configurado con Git e incluye:
- `.gitignore` completo para Java y Node.js
- Estructura modular para colaboración
- Documentación detallada

## Datos de Prueba
El sistema incluye datos iniciales:
- 4 departamentos colombianos
- 6 ciudades principales
- 8 materiales de ejemplo

## Próximas Mejoras
- [ ] Autenticación y autorización
- [ ] Paginación en listados
- [ ] Filtros avanzados en frontend
- [ ] Reportes y estadísticas
- [ ] API de notificaciones
- [ ] Pruebas de integración

## Contacto
Para más información, revisar la documentación específica en cada carpeta (`back/` y `front/`).
