# FORO1-DSM
Aplicación para gestionar notas de estudiantes, 
con autenticación de usuarios, cálculo automático de 
promedio y validación de datos.

Aplicación Android desarrollada en **Kotlin** con **Jetpack Compose**

## Estado actual del proyecto

Ya se encuentra listo el proyecto incluyendo:

- Creación del proyecto en Android Studio
- Configuración inicial con Jetpack Compose
- Organización de paquetes
- Creación de pantallas base requeridas
- Navegación funcional entre módulos

## Funcionalidades principales
- Registro de usuarios con validación
- Inicio de sesión con autenticación local
- Ingreso de notas con validación numérica
- Cálculo automático de promedio
- Evaluación de aprobación/reprobación
- Persistencia de usuarios con base de datos local

## Estructura actual

El proyecto actualmente contiene los siguientes paquetes principales:

- `com.example.foro1dsm.login`
- `com.example.foro1dsm.welcome`
- `com.example.foro1dsm.grades`
- `com.example.foro1dsm.result`
- `com.example.foro1dsm.navigation`
- `com.example.foro1dsm.ui.theme`

## Flujo de funcionamiento 

La navegación base se desarrolla en este orden:

1. Login → validación → acceso
2. Registro → validación → creación usuario
3. Bienvenida → muestra nombre 
4. Ingreso de Notas
5. Notas → cálculo
6. Resultado → decisión (aprobado/reprobado)
7. Retorno a Login

## Puntos implementados

- `LoginScreen.kt`
- `WelcomeScreen.kt`
- `GradesScreen.kt`
- `ResultScreen.kt`
- `AppRoutes.kt`
- `AppNavigation.kt`
- `MainActivity.kt`

## Persistencia de datos
- Se usa Room
- Los usuarios se guardan localmente
- Contraseñas encriptadas con SHA-256

## Validaciones implementadas
- Email válido
- Contraseña segura
- Nombre solo letras
- Notas entre 0 y 10
- Campos obligatorios

## Requisitos para ejecutar el proyecto

Para abrir y correr el proyecto sin problemas se recomienda contar con lo siguiente:

- Android Studio instalado
- SDK de Android configurado
- Gradle Sync completado correctamente
- Emulador Android o dispositivo físico
- Conexión a internet la primera vez para descargar dependencias

## Recomendación de emulador

Configuración recomendada para pruebas:

- Pixel 6
- Android 13 / API 33
- Google APIs
