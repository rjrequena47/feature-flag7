# feature-flag7
Backend para gestión de flags de funcionalidades en entornos reales. Desarrollar una API REST que permita crear, activar o desactivar funcionalidades (feature flags) de manera dinámica según entornos (dev, staging, prod) o clientes específicos.

# 🛠️ Tecnologías y herramientas

- Lenguaje: Java 
- Framework: Spring Boot (MVC)
- Base de datos: PostgreSQL
- Seguridad y autenticación: Spring Security + JWT
- Documentación de APIs: Swagger UI
- Testing: Postman (funcional) + JUnit/Mockito (opcional)
- Control de versiones: Git + GitHub
- Gestión del proyecto: Trello (opcional)

# 🎯 Objetivo General

Construir un backend que permita:
Crear funcionalidades (features) con nombre, descripción y estado (on/off).
Activarlas o desactivarlas por entorno (dev, staging, prod) o por cliente.
Consultar qué funcionalidades están activas según el entorno o cliente.

# ✅ Requisitos funcionales

1. Gestión de usuarios (autenticación)
POST /api/auth/register — Registro de usuario con rol USER.
POST /api/auth/login — Login y entrega de JWT.
Todos los endpoints (salvo los de auth) deben requerir autenticación.

2. Gestión de funcionalidades (features)
Crear nueva feature → POST /api/features       
Campos → name, description, enabledByDefault
Listar todas las features existentes → GET /api/features       
Obtener detalle de una feature → GET /api/features/{id}

3. Activación y personalización por entorno o cliente
Activar una feature para un entorno o cliente → POST /api/features/{id}/enable       
Cuerpo → { "environment": "dev", "clientId": "acme123" }
Desactivar una feature para un entorno o cliente → POST /api/features/{id}/disable       
Consultar si una feature está activa para un cliente y entorno → GET /api/features/check?feature=dark_mode&clientId=acme123&env=staging

# 🔐 Seguridad

Uso de Spring Security + JWT.
Protección de rutas mediante filtros y anotaciones.
Solo usuarios autenticados pueden consultar y modificar features.

# 🧠 Lógica de activación

Al consultar si una feature está activa para un entorno y cliente, el sistema debe verificar:
Si existe una configuración específica para ese cliente.
Si existe una configuración por entorno.
Si no hay configuración específica, usar enabledByDefault.

# 🧱 Modelo de datos

// Feature.java
UUID id  
String name  
String description  
Boolean enabledByDefault  
List<FeatureConfig> configs  

// FeatureConfig.java
UUID id  
Environment environment // Enum: DEV, STAGING, PROD  
String clientId // opcional  
Boolean enabled  
Feature feature

# 📌 Enumerados sugeridos

public enum Environment {
    DEV, STAGING, PROD
}

# 🔁 Endpoints sugeridos

Método
Ruta
Descripción

POST
/api/auth/register
Registro de usuario

POST
/api/auth/login
Login y JWT

POST
/api/features
Crear nueva feature

GET
/api/features
Listar features

GET
/api/features/{id}
Detalle de feature

POST
/api/features/{id}/enable
Activar feature para cliente/entorno

POST
/api/features/{id}/disable
Desactivar feature para cliente/entorno

GET
/api/features/check
Verificar si una feature está activa

# 💡 Ideas de ampliación futura

Sistema de logs de activaciones/desactivaciones.
Dashboard visual con métricas de uso (si tuviera front).
Soporte para fechas de activación programadas.
Roles diferenciados: Admin y Usuario Viewer.

# 📁 Estructura recomendada

controller → endpoints REST
service → lógica de negocio y reglas de prioridad
repository → interfaces de JPA
dto → objetos de transferencia para requests/responses
model → entidades JPA
security → JWT y configuración de filtros

# 📌 Objetivos pedagógicos

Practicar modelado de relaciones simples (1:N).
Refuerzo de validaciones, enums y lógica condicional.
Seguridad básica con JWT.

Ejemplo de arquitectura limpia con Spring Boot.
