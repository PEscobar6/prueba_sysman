<!-- filepath: c:\Users\Janus\Documents\prueba\sysman\prueba\README.md -->
# Sistema de Gestión de Materiales

Sistema web completo para la gestión de materiales con autenticación JWT, desarrollado con Spring Boot y Angular.

## 🛠️ Tecnologías

- **Backend**: Spring Boot 3.5.5, Spring Security, JWT, PostgreSQL
- **Frontend**: Angular 18, TailwindCSS, DaisyUI
- **Base de datos**: PostgreSQL
- **Contenedores**: Docker (solo para PostgreSQL)

## 📋 Requisitos

### Backend
- Java 17+
- Maven 3.6+
- PostgreSQL 12+ (o Docker)

### Frontend
- Node.js 18+
- npm 9+
- Angular CLI 18+

## 🚀 Instalación y Despliegue Local

### 1. Base de Datos PostgreSQL

**Si tienes PostgreSQL instalado:**
```sql
-- Crear base de datos
CREATE DATABASE materiales_db;
CREATE USER admin WITH PASSWORD 'admin123';
GRANT ALL PRIVILEGES ON DATABASE materiales_db TO admin;
```

**Si no tienes PostgreSQL, usar Docker:**
```bash
docker run --name postgres-db -e POSTGRES_DB=materiales_db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin123 -p 5432:5432 -d postgres:15
```

### 2. Poblar la Base de Datos

El proyecto incluye un archivo `data.sql` en la raíz que contiene queries de ejemplo para poblar la base de datos con datos de prueba:

```bash
# Ejecutar el archivo data.sql en PostgreSQL
psql -h localhost -U admin -d materiales_db -f data.sql
```

### 3. Backend (Puerto 8080)

```bash
cd back
mvn clean install
mvn spring-boot:run
```

### 4. Frontend (Puerto 4200)

```bash
cd front
npm install
ng serve
```

## 🔐 Credenciales de Prueba

- **Usuario**: `admin`
- **Contraseña**: `admin123`

## 📡 Endpoints del Backend

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/logout` - Cerrar sesión
- `GET /api/auth/user` - Información del usuario actual

### Materiales
- `GET /api/materiales` - Listar materiales
- `GET /api/materiales/{id}` - Obtener material por ID
- `POST /api/materiales` - Crear material
- `PUT /api/materiales/{id}` - Actualizar material
- `DELETE /api/materiales/{id}` - Eliminar material

### Ubicaciones
- `GET /api/departamentos` - Listar departamentos
- `GET /api/ciudades` - Listar ciudades
- `GET /api/ciudades/departamento/{codigo}` - Ciudades por departamento

### Pruebas
- `GET /api/test/hash/{password}` - Generar hash de contraseña

## 🌐 URLs de Acceso

- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **Base de datos**: localhost:5432

## 📁 Estructura del Proyecto

```
├── back/              # Backend Spring Boot
│   ├── src/main/java/ # Código fuente Java
│   └── pom.xml        # Dependencias Maven
├── front/             # Frontend Angular
│   ├── src/app/       # Código fuente Angular
│   └── package.json   # Dependencias npm
├── data.sql           # Queries para poblar la base de datos
├── docker-compose.yml # Solo para PostgreSQL si no está instalado localmente
└── README.md          # Este archivo
```

## 🏗️ Características

- ✅ Autenticación JWT
- ✅ Gestión de materiales CRUD
- ✅ Interfaz responsiva
- ✅ Validación de formularios
- ✅ Manejo de errores
- ✅ Guardias de ruta
- ✅ Base de datos relacional

## 🔧 Configuración

### Variables de Entorno (Backend)

```properties
# Base de datos
DATABASE_URL=jdbc:postgresql://localhost:5432/materiales_db
DATABASE_USERNAME=admin
DATABASE_PASSWORD=admin123

# JWT
app.jwtSecret=your-secret-key
app.jwtExpirationMs=86400000
```

## 📝 Notas de Desarrollo

- **Despliegue Local**: Este proyecto está configurado para ejecutarse localmente
- **Docker**: Solo se usa para PostgreSQL si no tienes la base de datos instalada localmente
- **Datos de Prueba**: Utiliza el archivo `data.sql` para poblar la base de datos con información inicial
- Las rutas protegidas requieren autenticación
- El frontend maneja automáticamente el token JWT
- Consultar logs en consola para debugging

---
