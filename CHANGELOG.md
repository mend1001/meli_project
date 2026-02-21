# Changelog

Todos los cambios relevantes de este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/)
y este proyecto sigue [Semantic Versioning](https://semver.org/lang/es/).

---

## [1.1.0] - 2026-02-17

### Added
- Implementación de paginación en `GET /mp/products`
- Nuevo modelo `PagedResponse<T>` para respuestas paginadas
- Modelo `PageMetadata` con:
  - number
  - size
  - totalItems
  - totalPages
  - hasNext
  - hasPrev
- Actualización de pruebas unitarias para soportar paginación
- Documentación Swagger actualizada para parámetros `page` y `size`

### Changed
- `GetProductListUseCase` ahora retorna `PagedResponse<ProductCard>`
- `GetProductListService` implementa lógica de paginación manual
- `ProductController` acepta parámetros opcionales `page` y `size`
- README actualizado con estructura paginada

### Technical Notes
La paginación fue implementada en la capa de aplicación sin afectar el dominio,
manteniendo intacta la arquitectura hexagonal.

---

## [1.0.0] - 2026-02-15

### Added
- Arquitectura Hexagonal (Ports & Adapters)
- Dominio desacoplado de infraestructura
- Implementación de persistencia dual:
  - SQLite (detalle y consistencia transaccional)
  - JSON (datos UI dinámicos)
- Endpoints REST:
  - `GET /mp/products`
  - `GET /mp/products/{id}`
- Manejo global de excepciones con `GlobalExceptionHandler`
- Modelo estándar de error `ApiError`
- Documentación con Swagger/OpenAPI
- Pruebas unitarias:
  - Servicios
  - Controladores
  - Repositorios JSON
  - Persistencia SQLite

### Technical Highlights
- Separación estricta de capas
- Uso de puertos de entrada y salida
- Independencia del dominio respecto a frameworks
- Uso de records para modelos inmutables
- Configuración explícita de JPA con `PersistenceConfig`
