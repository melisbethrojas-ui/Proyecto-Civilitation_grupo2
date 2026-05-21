# Proyecto-Civilitation_grupo2

# 🏛️ Civilizations 2526

## 1. Descripción del proyecto
Civilizations 2526 es una aplicación desarrollada en Java y JavaFX que simula la gestión completa de una civilización: recursos, edificios, tecnologías, unidades militares y batallas automáticas contra ejércitos enemigos.

El proyecto incluye:

- Motor de juego completo (recursos, unidades, edificios, tecnologías, batallas).  
- Interfaz gráfica en JavaFX.  
- Automatización mediante `TimerTask` (recursos y ataques).  
- Persistencia en base de datos Oracle (según requisitos del módulo).  
- Web dinámica (Node.js + EJS) para reportes e informes.  
- Documentación técnica y manual de usuario.

---

## 2. Estructura del repositorio

Proyecto-Civilitation_grupo2/
M01/        → Manual de usuario (PDF)
M03/        → Lógica del juego (Java)
M05/        → Control de versiones + documentación
projecte-civilization/
M02/        → Base de datos (scripts SQL, modelo ER)
M04/        → Web dinámica (Node.js + EJS)



### M03 – Lógica del juego
Contiene:

- Jerarquía completa de unidades  
- Clase `Civilization`  
- Clase `Battle`  
- Excepciones (`ResourceException`, `BuildingException`)  
- Variables y constantes del juego  

### M03 – GUI JavaFX
```
src/main/java/com/project/
src/main/resources/assets/gui/
```

Incluye:

- Controladores  
- FXML  
- CSS  
- Imágenes  

---

## 3. Instalación

### Requisitos
- Java 22 o 17
- Maven  
- MySQL
- Node.js 

### Clonar el repositorio
```bash
git clone https://github.com/melisbethrojas-ui/Proyecto-Civilitation_grupo2.git
```

## ▶️ Ejecución de la GUI (JavaFX)

### Primera vez
```bash
cd M03
mvn clean
dos2unix pom.xml
dos2unix run.sh
chmod +x run.sh
mvn install
./run.sh com.project.Main

### Automatización
- `ResourceTimerTask` → genera recursos periódicamente  
- `AttackTimerTask` → crea enemigos y lanza batallas cada X minutos  

---

## 5. Web dinámica (M04)

Incluye:

- Portada  
- Listado de batallas  
- Informe detallado  
- Estadísticas de la civilización  
- Créditos del equipo  

Para ejecutarla:

```bash
node app .js
```

---

## 6. Autores y roles

Alumnos 
Melisbeth Rojas:  M05 + GUI (JavaFX) Interfaz gráfica, controladores, documentación, control de versiones 
Ariadna M02 + M04  Base de datos, web dinámica, despliegue 
Anna  M03  Motor del juego, jerarquía de unidades, batallas 

---

## 7. Release final
La entrega final incluye:

- Código completo  
- Manual de usuario (M01)  
- Scripts SQL (M02)  
- Motor del juego (M03)  
- Web dinámica (M04)  
- Documentación de control de versiones (M05)  
- Diagrama UML  
- Ejecutable / JAR  
