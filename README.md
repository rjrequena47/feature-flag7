# 🚀 Feature Flag 7 (Equipo #7)

[![Java](https://img.shields.io/badge/Java-21+-red?logo=java)](https://www.oracle.com/java/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=springboot)](https://spring.io/projects/spring-boot)  
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)  
[![Swagger](https://img.shields.io/badge/API-Docs-yellow?logo=swagger)](http://localhost:8080/swagger-ui/index.html)  
[![Docker](https://img.shields.io/badge/Docker-Compose-informational?logo=docker)](https://www.docker.com/)  

Backend para la **Gestión de Feature Flags dinámicos** en entornos reales. Permite crear, activar o desactivar funcionalidades según **entornos (dev, staging, prod)** o **clientes específicos**.  

---

## 📖 Índice
1. [🎯 Objetivo General](#-objetivo-general)  
2. [🛠️ Tecnologías y herramientas](#️-tecnologías-y-herramientas)  
3. [✅ Funcionalidades](#-funcionalidades)  
4. [🔐 Seguridad](#-seguridad)  
5. [🧠 Lógica de activación](#-lógica-de-activación)  
6. [🧱 Modelo de datos](#-modelo-de-datos)  
7. [🔁 Endpoints](#-endpoints)  
8. [📂 Estructura del Proyecto](#-estructura-del-proyecto)  
9. [📝 Prerrequisitos](#-prerrequisitos)  
10. [🚀 Ejecución](#-ejecución)  
11. [📌 Ejemplos de uso](#-ejemplos-de-uso)
12. [🤝 Contribución](#-contribución)  
13. [🤝 Colaboradores](#-colaboradores)
14. [📄 Licencia](#-licencia)  

---

## 🎯 Objetivo General
Construir un backend que permita:  
- Crear funcionalidades con nombre, descripción y estado (`on/off`).  
- Activarlas o desactivarlas por entorno o cliente.  
- Consultar qué funcionalidades están activas según el contexto.  

---

## 🛠️ Tecnologías y herramientas
- **Lenguaje**: Java 21+  
- **Framework**: Spring Boot (MVC, Security, Data JPA, Validation)  
- **Base de datos**: PostgreSQL + Flyway  
- **Seguridad**: Spring Security + JWT  
- **Documentación**: Swagger UI + OpenAPI  
- **Testing**: Postman  
- **Infraestructura**: Docker + Docker Compose  
- **Gestión del proyecto**: GitHub + Taiga  

---

## ✅ Funcionalidades
### 1. Gestión de usuarios
- Registro → `POST /api/auth/register`  
- Login → `POST /api/auth/login`  
- Endpoints seguros con JWT  

### 2. Gestión de features
- Crear → `POST /api/features`  
- Listar → `GET /api/features`  
- Detalle → `GET /api/features/{id}`  

### 3. Activación personalizada
- Activar/desactivar por entorno o cliente  
- Consultar si una feature está activa → `GET /api/features/check`  

---

## 🔐 Seguridad
- Autenticación vía JWT.  
- Autorización mediante roles.  
- Endpoints `/api/auth/**` públicos, el resto requiere token válido.  

---

## 🧠 Lógica de activación
1. Verificar config específica del cliente.  
2. Verificar config por entorno.  
3. Usar `enabledByDefault` si no hay configuración.  

---

## 🧱 Modelo de datos

```java
Feature {
  UUID id;
  String name;
  String description;
  Boolean enabledByDefault;
  List<FeatureConfig> configs;
}

FeatureConfig {
  UUID id;
  Environment environment; // DEV, STAGING, PROD
  String clientId; // opcional
  Boolean enabled;
}
```

---

## 🔁 Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/auth/register` | Registro de usuario |
| `POST` | `/api/auth/login` | Login y JWT |
| `POST` | `/api/features` | Crear nueva feature |
| `GET` | `/api/features` | Listar todas las features |
| `GET` | `/api/features/{id}` | Detalle de una feature |
| `POST` | `/api/features/{id}/enable` | Activar feature |
| `POST` | `/api/features/{id}/disable` | Desactivar feature |
| `GET` | `/api/features/check` | Verificar si está activa |

---

## 📂 Estructura del Proyecto
```
feature-flag7/
├── src/main/java/com/bytes7/feature_flag7/
│   ├── backend/
│   │   └── enums/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── exception/
│   ├── model/
│   ├── repository/
│   ├── security/
│   ├── service/
│   └── FeatureFlag7Application.java
├── src/main/resources/
│   ├── db/
│   ├── static/
│   ├── templates/
│   └── application.yml
├── docker-compose.yml
├── FeatureFlag7.postman_collection.json (Colección Postman)
├── pom.xml
└── README.md
```

---

## 📝 Prerrequisitos
- Java 21+  
- Maven 3.9+  
- Docker & Docker Compose  
- Git  

---

## 🚀 Ejecución

1. Levantar la BD:
```bash
docker compose up -d
```
2. Compilar:
```bash
./mvnw clean install
```
3. Ejecutar:
```bash
./mvnw spring-boot:run
```
4. Swagger:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📌 Ejemplos de uso

**Registro de usuario**  
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "ricardo",
  "password": "123456"
}
```

**Crear feature**  
```http
POST /api/features
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "dark_mode",
  "description": "Activa modo oscuro",
  "enabledByDefault": true
}
```

**Consultar si está activa**  
```http
GET /api/features/check?feature=dark_mode&clientId=acme123&env=staging
```

---

## 🤝 Contribución
1. Haz un fork.  
2. Crea una rama `feature/nueva-funcionalidad`.  
3. Commit con mensaje claro.  
4. Abre un Pull Request.  

---

## 💻 Colaboradores
- rjrequena47
- rodrigonewgm
- diadhor
- jtrindadel1707

---

## 📄 Licencia
Este proyecto está bajo la licencia **Apache-2.0 license**.  
