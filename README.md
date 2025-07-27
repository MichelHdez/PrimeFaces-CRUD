# CRUD en Java Web con JSF (Jakarta Server Faces), PrimeFaces y JPA (Hibernate)

Este proyecto es una aplicación web de ejemplo que demuestra la implementación de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la gestión de usuarios, utilizando las siguientes tecnologías:

-   **Jakarta Server Faces (JSF)**: Para la construcción de la interfaz de usuario.
-   **PrimeFaces**: Un conjunto de componentes UI enriquecidos para JSF.
-   **JPA (Java Persistence API) con Hibernate**: Para la persistencia de datos y la interacción con la base de datos.

## Características

-   Gestión completa de usuarios (Crear, Leer, Actualizar, Eliminar).
-   Interfaz de usuario intuitiva desarrollada con PrimeFaces.
-   Persistencia de datos robusta utilizando JPA y Hibernate.

## Tecnologías Utilizadas

-   **Java**: Versión 17 o superior.
-   **PostgreSQL**: Versión 12 o superior, como motor de base de datos.
-   **Jakarta Server Faces (JSF)**: Versión 4.0.
-   **PrimeFaces**: Versión 14.0.0.
-   **WildFly**: Servidor de aplicaciones (versiones 27.0.0.FINAL a 34.0.0.FINAL).
-   **Maven**: Herramienta de gestión de proyectos y construcción.

## Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

-   JDK (Java Development Kit) 17 o superior.
-   Apache Maven.
-   PostgreSQL.
-   Un servidor de aplicaciones compatible con Jakarta EE, como WildFly.

## Configuración de la Base de Datos

1.  Asegúrate de tener una instancia de PostgreSQL en ejecución.
2.  Crea una base de datos llamada `crud-jsf`. Puedes usar el siguiente script SQL:

    ```sql
    CREATE DATABASE "crud-jsf";
    ```

3.  Dentro de la base de datos `crud-jsf`, crea la tabla `usuario` ejecutando el siguiente script:

    ```sql
    CREATE TABLE public.usuario (
        id_usuario serial PRIMARY KEY,
        nombre character varying(50) NOT NULL,
        nick_name character varying(50) NOT NULL
    );
    ```

4.  Verifica la configuración de la unidad de persistencia en `src/main/resources/META-INF/persistence.xml` para asegurar que los datos de conexión a tu base de datos PostgreSQL sean correctos.

## Cómo Ejecutar el Proyecto

1.  **Clonar el repositorio** (si aún no lo has hecho).
2.  **Construir el proyecto con Maven**:
    Navega hasta el directorio raíz del proyecto (`primefaces-crud`) en tu terminal y ejecuta:

    ```bash
    mvn clean install
    ```
    Esto compilará el código y generará el archivo `.war` en el directorio `target/`.

3.  **Desplegar en WildFly**:
    -   Inicia tu servidor WildFly.
    -   Copia el archivo `primefaces-crud.war` (o el nombre que tenga tu archivo .war generado) desde el directorio `target/` a la carpeta `standalone/deployments/` de tu instalación de WildFly.
    -   WildFly detectará automáticamente el archivo y desplegará la aplicación.

4.  **Acceder a la aplicación**:
    Una vez desplegada, podrás acceder a la aplicación desde tu navegador web en la siguiente URL:

    `http://localhost:8080/primefaces-crud/` (o la URL base de tu servidor WildFly seguida del contexto de la aplicación).

## Actualizaciones Recientes

-   Se actualizó el proyecto en el archivo `pom.xml` con las últimas versiones de las librerías.
-   Se agregó la funcionalidad para mostrar la lista de todos los usuarios en la página principal.
-   Se corrigió un detalle en la tabla en las columnas de `nick_name` y `nombre`.
-   Se agregaron comentarios en el código para mejorar la legibilidad.

## Créditos

Este proyecto está basado en el trabajo original de Walter Rosero. Puedes ver una explicación detallada del proyecto en su canal de YouTube: [https://youtu.be/UfVNi1pkRTw](https://youtu.be/UfVNi1pkRTw)
