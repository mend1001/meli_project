# Marketplace API

![Java Version](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green.svg)


API REST para un marketplace que permite consultar productos y sus detalles, implementando arquitectura hexagonal con persistencia dual (SQLite + JSON).

## 📋 Tabla de Contenidos
- [Arquitectura](#-arquitectura)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [API Endpoints](#-api-endpoints)
- [Base de Datos](#-base-de-datos)
- [Pruebas](#-pruebas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Manejo de Errores](#-manejo-de-errores)
- [Licencia](#-licencia)
- [Contacto](#-contacto)

# 🧱 Arquitectura

## 🏛 Arquitectura Hexagonal (Ports & Adapters)

Se eligió Arquitectura Hexagonal porque:

- Permite aislar el dominio del framework
- Facilita pruebas unitarias reales
- Permite cambiar infraestructura sin impactar negocio
- Mejora mantenibilidad y escalabilidad
- Es ampliamente utilizada en entornos empresariales

## ✨ Características

- ✅ **Arquitectura Hexagonal/Clean** - Separación clara de capas y responsabilidades
- ✅ **Persistencia Dual** - SQLite para datos relacionales y JSON para catálogo NoSQL
- ✅ **API RESTful** - Endpoints para consulta de productos
- ✅ **Manejo Global de Excepciones** - Respuestas consistentes para errores
- ✅ **Pruebas Unitarias** - Cobertura completa con JUnit 5 y Mockito
- ✅ **Datos de Ejemplo** - Scripts SQL y JSON para pruebas
- ✅ **Documentación de API** - Endpoints documentados con swagger

## 🛠️ Tecnologías

### Core
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.2.5** - Framework principal
- **Spring Web** - APIs REST
- **Spring Data JDBC** - Persistencia en SQLite

### Base de Datos
- **SQLite** - Base de datos relacional (datos transaccionales)
- **JSON** - Almacenamiento NoSQL simulado (catálogo de productos)

### Testing
- **JUnit 5** - Framework de pruebas
- **Mockito** - Mocking para pruebas unitarias
- **Spring Boot Test** - Testing de contexto Spring

### Build y Herramientas
- **Maven** - Gestión de dependencias y build
- **Git** - Control de versiones

## 📋 Requisitos Previos

- **JDK 21** o superior 
- **Maven 3.8.x** o superior 
- **Git** - 
- (Opcional) **Postman** o **curl** para probar APIs

### Verificar instalaciones
```bash
java -version
mvn -version
git --version
```

## 🔧 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/marketplace-api.git
cd marketplace-api
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar pruebas
```bash
mvn test
```

### 4. Construir el JAR
```bash
mvn clean package
```

El JAR se generará en `target/marketplace-api-1.0.0.jar`

## ⚙️ Configuración

### application.properties
```properties
# Servidor
server.port=8080

# Base de datos SQLite
spring.datasource.url=jdbc:sqlite:marketplace.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.initialization-mode=always
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql

# Archivo JSON para datos NoSQL
app.catalog.file=products_nosql_polycards.json

# Logging
logging.level.com.marketplace=DEBUG
logging.level.org.springframework.web=INFO
```

### Variables de entorno (opcional)
| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SERVER_PORT` | Puerto del servidor | 8080 |
| `DB_PATH` | Ruta de la base de datos | marketplace.db |
| `CATALOG_FILE` | Archivo JSON de catálogo | products_nosql_polycards.json |

## 🚀 Ejecución

### Modo desarrollo
```bash
# Desde el IDE (IntelliJ, Eclipse, VS Code)
Ejecutar la clase principal: com.marketplace.MarketplaceApplication

# O con Maven
mvn spring-boot:run
```

### Modo producción
```bash
java -jar target/marketplace-api-1.0.0.jar

# Con variable de entorno personalizada
java -jar -DSERVER_PORT=9090 target/marketplace-api-1.0.0.jar
```

### Verificar que la aplicación está funcionando
```bash
curl http://localhost:8080/mp/products
```

## 📡 API Endpoints
## 📖 Documentación Interactiva con Swagger/OpenAPI

El proyecto utiliza **Springdoc OpenAPI** para generar documentación interactiva de los endpoints REST. Esto permite explorar y probar la API directamente desde el navegador.

### 🚀 Acceso a la documentación

Una vez que la aplicación esté en ejecución, puedes acceder a:

| Recurso | URL | Descripción |
|---------|-----|-------------|
| **Swagger UI** | `http://localhost:8080/swagger-ui.html` | Interfaz gráfica interactiva para explorar y probar los endpoints |
| **Especificación OpenAPI** | `http://localhost:8080/v3/api-docs` | Documentación en formato JSON (estándar OpenAPI) |

### 🛠️ Configuración implementada

#### Dependencia en `pom.xml`
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

#### Configuración en `application.properties`
```properties
# Springdoc OpenAPI Configuration
springdoc.api-docs.enabled=true
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html
```

### 📋 Anotaciones utilizadas

Para enriquecer la documentación, se han utilizado las siguientes anotaciones:

| Anotación | Propósito | Ejemplo de uso |
|-----------|-----------|----------------|
| `@Tag` | Describe un controlador (grupo de endpoints) | `@Tag(name = "Productos", description = "Endpoints para gestión de productos")` |
| `@Operation` | Describe un endpoint específico | `@Operation(summary = "Listar productos", description = "Obtiene lista resumida")` |
| `@ApiResponses` | Documenta los posibles códigos de respuesta | `@ApiResponse(responseCode = "404", description = "Producto no encontrado")` |
| `@Parameter` | Describe un parámetro de la petición | `@Parameter(description = "ID del producto", example = "MCO203412639600")` |
| `@Schema` | Define el esquema de un modelo de datos | `@Schema(implementation = ProductCard.class)` |

### 🎨 Personalización global

El proyecto incluye una configuración personalizada que define metadatos globales de la API:

```java
@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8080")
                                    .description("Servidor de Desarrollo Local")
                ))
                .info(new Info()
                        .title("Marketplace API")
                        .description("API REST para marketplace con arquitectura hexagonal")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Tu Nombre o Equipo")
                                .email("tu.email@ejemplo.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
```

### 🔍 Ejemplo de documentación generada

La documentación interactiva permite:

1. **Visualizar todos los endpoints** disponibles con sus métodos HTTP
2. **Ver los modelos de datos** (`ProductCard`, `ProductDetailResponse`)
3. **Probar los endpoints** directamente desde el navegador
4. **Descargar la especificación** OpenAPI para usar en otras herramientas

### 📸 Captura de pantalla (opcional)

```
[Swagger UI mostraría algo similar a esto:

GET /mp/products - Listar todos los productos
GET /mp/products/{id} - Obtener detalle de un producto

Con secciones desplegables para ver parámetros, respuestas y probar los endpoints]
```

### ✅ Beneficios de esta implementación

- **Documentación viva**: Siempre sincronizada con el código
- **Interactiva**: Permite probar los endpoints sin herramientas externas
- **Estandarizada**: Sigue la especificación OpenAPI 3.0
- **Profesional**: Mejora la experiencia de otros desarrolladores que consuman la API
- **Automatizada**: Se genera automáticamente a partir de las anotaciones
- 
### Base URL
```
http://localhost:8080/mp
```

### 1. Obtener lista de productos
Obtiene todos los productos del catálogo con información resumida.

**Endpoint:** `GET /products`

**Respuesta exitosa (200 OK)**
```json
{
  "data": [
    {
      "itemId": "MCO203412639600",
      "productId": "MCO18031244",
      "title": "Kit de teclado y mouse inalámbrico Logitech Español Latino de color Gris grafito",
      "priceValue": 89900,
      "currency": "COP",
      "freeShipping": true,
      "pictureId": "498382-MLA94710360983_112025",
      "badgeText": null,
      "ratingValue": null,
      "soldLabel": null,
      "attributes": {
        "marca": "Logitech",
        "distribución": "Español Latino",
        "conectividad": "Bluetooth",
        "característica": "Resistente a salpicaduras",
        "color": "Negro"
      }
    },
    {
      "itemId": "MCO289056647601",
      "productId": "MCO18659176",
      "title": "Kit de teclado y mouse inalámbrico Genius Español Latino de color Blanco",
      "priceValue": 199900,
      "currency": "COP",
      "freeShipping": true,
      "pictureId": "738720-MLA92727210979_122025",
      "badgeText": null,
      "ratingValue": null,
      "soldLabel": null,
      "attributes": {
        "marca": "Genius",
        "distribución": "Español",
        "conectividad": "Dual (USB + Bluetooth)",
        "característica": "Teclado compacto",
        "color": "Rosa"
      }
    }
  ],
  "page": {
    "number": 0,
    "size": 10,
    "totalItems": 30,
    "totalPages": 3,
    "hasNext": true,
    "hasPrev": false
  }
}
```

### 2. Obtener detalle de producto
Obtiene información detallada de un producto específico.

**Endpoint:** `GET /products/{id}`

**Parámetros:**
| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| `id` | String | ID del producto | MCO203412639600 |

**Ejemplo de llamada:**
```bash
curl http://localhost:8080/mp/products/MCO203412639600
```

**Respuesta exitosa (200 OK)**
```json
{
  "itemId": "MCO203412639600",
  "productId": "MCO18031244",
  "title": "Kit teclado y mouse Logitech Gris Grafito",
  "state": "VISIBLE",
  "availableQuantity": 50,
  "sellerId": "S_LOGITECH",
  "sellerName": "Logitech Store",
  "priceValue": 89900,
  "currency": "COP",
  "freeShipping": true,
  "pictureId": "498382-MLA94710360983_112025",
  "badgeText": null,
  "ratingValue": 4.3,
  "soldLabel": "+1mil vendidos",
  "attributes": {
    "marca": "Logitech",
    "distribución": "Español Latino",
    "conectividad": "Bluetooth",
    "característica": "Resistente a salpicaduras",
    "color": "Negro"
  }
}
```

### Códigos de estado HTTP

| Código | Descripción | Ejemplo |
|--------|-------------|---------|
| `200 OK` | Petición exitosa | Producto encontrado |
| `400 Bad Request` | Petición inválida | ID mal formado |
| `404 Not Found` | Recurso no encontrado | Producto inexistente |
| `500 Internal Server Error` | Error interno | Error en base de datos |

## 💾 Base de Datos

### Estructura SQLite

El proyecto utiliza SQLite como base de datos relacional con el siguiente esquema:

```sql
-- Tablas principales
- product: Información de productos
- seller: Información de vendedores
- item: Items/publicaciones

-- Tablas relacionadas
- price: Precios de items
- installments: Información de cuotas
- shipping: Información de envío
- picture: Imágenes de productos
- item_attribute: Atributos dinámicos
```

### Archivo JSON (NoSQL)
El archivo `products_nosql_polycards.json` contiene datos enriquecidos para el catálogo:
- Metadatos de productos
- Información de precios formateada
- Calificaciones y reseñas
- Atributos extendidos

### Datos de ejemplo
El proyecto incluye datos de prueba precargados:
- **Vendedores**: Logitech, Razer, HP, Samsung, LG
- **Productos**: Teclados, mice, monitores
- **Items**: 4 items de ejemplo con precios y atributos

## 🧪 Pruebas

### Ejecutar todas las pruebas
```bash
mvn clean test
```

### Ejecutar pruebas específicas
```bash
# Por clase
mvn test -Dtest=GetProductDetailServiceTest

# Por método
mvn test -Dtest=GetProductDetailServiceTest#getDetail_WhenProductExists_ShouldReturnResponse

# Múltiples clases
mvn test -Dtest=GetProductDetailServiceTest,ProductControllerTest
```

### Cobertura de pruebas con JaCoCo
```bash
mvn jacoco:report
```
El reporte de cobertura se genera en:
```
target/site/jacoco/index.html
```

### Estructura de pruebas
```
src/test/java/com/marketplace/
├── application/
│   ├── service/
│   │   ├── GetProductDetailServiceTest.java
│   │   └── GetProductListServiceTest.java
├── infrastructure/
│   └── adapter/
│       └── in/
│           └── rest/
│               └── ProductControllerTest.java
└── shared/
    └── GlobalExceptionHandlerTest.java
```

## 📁 Estructura del Proyecto

```
marketplace-api/
├── src/
│   ├── main/
│   │   ├── java/com/marketplace/
│   │   │   ├── MarketplaceApplication.java
│   │   │   ├── application/
│   │   │   │   └── service/
│   │   │   │       ├── GetProductDetailService.java
│   │   │   │       └── GetProductListService.java
│   │   │   ├── domain/
│   │   │   │   ├── exception/
│   │   │   │   │   ├── BadResourceRequestException.java
│   │   │   │   │   ├── ConflictException.java
│   │   │   │   │   ├── NoSuchResourceFoundException.java
│   │   │   │   │   └── UnprocessableEntityException.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── Product.java
│   │   │   │   │   ├── ProductCard.java
│   │   │   │   │   ├── ProductDetailResponse.java
│   │   │   │   │   ├── PagedResponse.java
│   │   │   │   │   └── PageMetadata.java
│   │   │   │   └── port/
│   │   │   │       ├── in/
│   │   │   │       │   ├── GetProductDetailUseCase.java
│   │   │   │       │   └── GetProductListUseCase.java
│   │   │   │       └── out/
│   │   │   │           ├── ProductCatalogRepository.java
│   │   │   │           └── ProductDetailRepository.java
│   │   │   ├── infrastructure/
│   │   │   │   ├── adapter/
│   │   │   │   │   ├── in/
│   │   │   │   │   │   └── rest/
│   │   │   │   │   │       └── ProductController.java
│   │   │   │   │   └── out/
│   │   │   │   │       └── persistence/
│   │   │   │   │           ├── JsonProductCatalogRepository.java
│   │   │   │   │           └── SqliteProductDetailRepository.java
│   │   │   │   └── config/
│   │   │   │       └── AppConfig.java
│   │   │   └── shared/
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       ├── data.sql
│   │       └── products_nosql_polycards.json
│   └── test/
│       └── java/com/marketplace/
│           ├── application/
│           │   └── service/
│           │       ├── GetProductDetailServiceTest.java
│           │       └── GetProductListServiceTest.java
│           ├── infrastructure/
│           │   └── adapter/
│           │       └── in/
│           │           └── rest/
│           │               └── ProductControllerTest.java
│           └── shared/
│               └── GlobalExceptionHandlerTest.java
├── pom.xml
├── README.md
├── LICENSE
└── .gitignore
```

## 🚨 Manejo de Errores

### Formato de respuesta de error
```json
{
  "timestamp": "2024-01-15T10:30:45.123Z",
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Product not found: MCO999999",
  "path": "/mp/products/MCO999999"
}
```

### Excepciones personalizadas

| Excepción | Código HTTP | Uso |
|-----------|-------------|-----|
| `NoSuchResourceFoundException` | 404 | Recurso no encontrado |
| `BadResourceRequestException` | 400 | Petición inválida |
| `ConflictException` | 409 | Conflicto de datos |
| `UnprocessableEntityException` | 422 | Entidad no procesable |


### Convenciones de código

- **Java**: Usar Java 21 features donde sea apropiado
- **Arquitectura**: Seguir principios SOLID y arquitectura hexagonal
- **Pruebas**: Escribir pruebas unitarias para nuevo código (mínimo 80% cobertura)
- **Documentación**: Documentar métodos públicos con JavaDoc
- **Commits**: Usar [Conventional Commits](https://www.conventionalcommits.org/)
    - `feat:` - Nueva funcionalidad
    - `fix:` - Corrección de bug
    - `test:` - Agregar o modificar pruebas
    - `docs:` - Documentación
    - `refactor:` - Refactorización de código

### Estándares de código
- **Indentación**: 4 espacios
- **Límite de línea**: 120 caracteres
- **Nombres de clases**: PascalCase
- **Nombres de métodos**: camelCase
- **Nombres de constantes**: UPPER_SNAKE_CASE

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

```
MIT License

Copyright (c) 2026 [Miguel Angel Mendigaño Arismendy]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
```

## 📧 Contacto

**Desarrollador Principal**
- Nombre: Miguel Angel Mendigaño Arismendy
- Email: mend1001mend1001@gmail.com
- GitHub: [@mend1001](https://github.com/mend1001)
- LinkedIn: [Miguel Angel Mendigaño Arismendy](https://www.linkedin.com/in/miguel-angel-mendigano-a-476b7a227/)

**Project Link**: [https://github.com/mend1001/meli_project.git)


## 📊 Roadmap

### Versión 1.1.0 (Actual)
- ✅ Consulta de lista de productos
- ✅ Consulta de detalle de producto
- ✅ Persistencia dual (SQLite + JSON)
- ✅ Manejo global de excepciones
- ✅ Pruebas unitarias (basicas)

## 📜 Historial de Cambios

El historial completo del proyecto puede consultarse en:

➡️ [CHANGELOG.md](./CHANGELOG.md)


