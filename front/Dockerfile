# Dockerfile para aplicación Angular con Tailwind CSS y DaisyUI
# Etapa 1: Build de la aplicación
FROM node:22-alpine AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de dependencias
COPY package*.json ./

# Instalar dependencias (incluyendo devDependencies para el build)
RUN npm ci

# Copiar código fuente (excluyendo node_modules)
COPY src ./src
COPY public ./public
COPY angular.json ./
COPY tsconfig*.json ./
COPY .editorconfig ./
COPY .postcssrc.json ./

# Construir la aplicación para producción
RUN npm run build

# Etapa 2: Servir la aplicación con Nginx
FROM nginx:alpine

# Crear usuario no root para mayor seguridad
RUN addgroup -g 1001 -S nodejs && \
    adduser -S angular -u 1001

# Copiar archivos construidos desde la etapa de build
COPY --from=build /app/dist/front /usr/share/nginx/html

# Configurar permisos
RUN chown -R angular:nodejs /usr/share/nginx/html && \
    chown -R angular:nodejs /var/cache/nginx && \
    chown -R angular:nodejs /var/log/nginx && \
    chown -R angular:nodejs /etc/nginx/conf.d

RUN touch /var/run/nginx.pid && \
    chown -R angular:nodejs /var/run/nginx.pid

# Cambiar a usuario no root
USER angular

# Exponer puerto 80
EXPOSE 80

# Comando por defecto
CMD ["nginx", "-g", "daemon off;"]
