# Simulador de Creditos

Backend REST para registrar clientes, simular creditos con amortizacion francesa y generar tablas de amortizacion.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

## Requisitos

- JDK 21
- PostgreSQL ejecutandose localmente
- Base de datos `simulador_creditos_db`

La configuracion de conexion se encuentra en `src/main/resources/application.yml`.

## Preparar, compilar y ejecutar

Sigue estos pasos desde la raiz del proyecto en Windows PowerShell.

### 1. Verificar requisitos

Confirma que esten instalados y ejecutandose:

- JDK 21.
- PostgreSQL.
- Maven 

### 2. Crear la base de datos

La base de datos utilizada por el proyecto es `simulador_creditos_db` y se ejecuta localmente en `localhost:5432`.

Si aun no existe, creala desde `psql` o PgAdmin:

```sql
CREATE DATABASE simulador_creditos_db;
```

### 3. Ejecutar el script SQL o importa

El script se encuentra dentro del proyecto en:

```text
src/main/resources/bd/simulador_creditos_db.sql
```

### 4. Revisar la conexion

Verifica que `src/main/resources/application.yml` tenga el mismo nombre de base, usuario, contrasena y puerto de PostgreSQL:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/simulador_creditos_db
    username: TU_USERNAME
    password: TU_PASSWORD
```

### 5. Compilar el proyecto

```powershell
./mvnw.cmd clean compile
```

Este comando valida y compila el codigo Java. La base de datos no necesita estar disponible para esta etapa.

### 6. Ejecutar la aplicacion

```powershell
./mvnw.cmd spring-boot:run
```

En este paso Spring Boot se conecta a PostgreSQL y Hibernate crea o actualiza las tablas configuradas.

La API queda disponible en:

```text
http://localhost:8080
```

## Modulos

```text
clients      Gestion de clientes
simulation   Simulacion y resumen del credito
installment  Cuotas y tabla de amortizacion
shared       Respuestas comunes, errores y CORS
```

## Arquitectura DDD basica

El proyecto aplica una arquitectura orientada al dominio (DDD) a nivel basico. Cada modulo representa una responsabilidad del negocio y se divide en capas:

```text
domain          Modelos del negocio y contratos de repositorio
application     DTOs y servicios que ejecutan los casos de uso
infrastructure Entidades JPA, interfaces y controladores REST
```


## Endpoints de clientes

### Crear cliente

```http
POST /clients/create
```

```json
{
  "fullName": "Esperanza Sosa",
  "documentNumber": 79528405,
  "email": "esperanza@gmail.com",
  "phone": 3213641611
}
```

### Consultar clientes

```http
GET /clients
GET /clients/{id}
```

### Actualizar cliente

```http
PUT /clients/update/{id}
```

Usa el mismo formato JSON del endpoint de creacion.

### Eliminar cliente

```http
DELETE /clients/update/{id}
```

No se elimina un cliente que tenga simulaciones asociadas. En ese caso la API responde `409 Conflict` para proteger el historial.

## Simular un credito

```http
POST /simulations/generate
```

```json
{
  "clientId": 4,
  "requestedAmount": 2000000,
  "annualInterestRate": 20,
  "termInMonths": 12
}
```

El endpoint:

1. Verifica que exista el cliente.
2. Convierte la tasa efectiva anual a mensual.
3. Calcula la cuota fija con amortizacion francesa.
4. Guarda la simulacion relacionada mediante `clientId`.
5. Genera automaticamente sus cuotas.
6. Devuelve el resumen de la simulacion.

La respuesta incluye el `id` generado de la simulacion, el cliente, la cuota mensual, el total a pagar y los intereses totales.

## Tabla de amortizacion

Las cuotas se generan automaticamente al crear la simulacion. Para consultarlas:

```http
GET /installments/getInstallments/{simulationId}
```

Cada cuota contiene:

- Numero de cuota.
- Abono a capital.
- Interes del mes.
- Valor total de la cuota.
- Saldo pendiente.

## Historial

```http
GET /simulations/history
```

Devuelve todas las simulaciones almacenadas, ordenadas desde la mas reciente. Incluye fecha de registro, cliente, monto, plazo, tasa anual, tasa mensual, cuota mensual, total a pagar e intereses.


## Relaciones de persistencia

```text
clients 1 ---- N simulations 1 ---- N installments
```

- `simulations.clientid` referencia `clients.id`.
- `installments.simulationid` referencia `simulations.id`.

## Formato de respuestas

Las respuestas exitosas usan:

```json
{
  "message": "Operacion realizada correctamente",
  "data": {}
}
```

Los errores se manejan de forma centralizada desde `shared`:

- `400 Bad Request`: datos invalidos o documento duplicado.
- `404 Not Found`: cliente o simulacion inexistente.
- `409 Conflict`: intento de eliminar un cliente con simulaciones.
- `500 Internal Server Error`: error inesperado controlado.

## CORS

En desarrollo se permiten los origenes locales:

- `http://localhost:3000`
- `http://localhost:5173`
- `http://localhost:4200`
