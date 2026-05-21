# Proyecto-Civilitation_grupo2
M03 – Programación
Proyecto: Civilizations
Este módulo contiene la lógica interna del juego Civilizations, implementada mediante programación orientada a objetos. Aquí se desarrollan las clases, métodos y estructuras que permiten gestionar recursos, unidades, edificios, batallas y amenaza sin necesidad de interfaz gráfica.

1. Objetivo del módulo
Implementar la lógica del juego.

Crear las clases principales del sistema.

Gestionar datos internos (recursos, ejército, edificios…).

Resolver batallas y generar informes.

Aplicar principios de POO (herencia, interfaces, abstracción).

2. Funcionalidades implementadas
2.1 Gestión de recursos
Control de comida, madera, hierro y maná.

Métodos para aumentar, consumir y validar recursos.

2.2 Gestión de unidades
Creación de unidades militares según su tipo.

Cálculo de daño, defensa y salud.

Organización del ejército mediante colecciones.

2.3 Sistema de edificios
Construcción de edificios.

Aplicación de efectos sobre la civilización.

2.4 Sistema de batallas
Resolución de combates entre jugador y enemigo.

Eliminación de unidades derrotadas.

Generación de informes de batalla.

2.5 Amenaza y enemigos
Aumento progresivo de la amenaza.

Generación automática de ejércitos enemigos.

3. Clases principales
Civilization: núcleo del juego; gestiona recursos, ejército y edificios.

Battle: resuelve combates y genera informes.

MilitaryUnit (interfaz): comportamiento básico de cualquier unidad.

AttackUnit / DefenseUnit / SpecialUnit: clases abstractas por rol.

Unidades concretas: implementan estadísticas específicas.

Excepciones:

ResourceException

BuildingException

4. Ejecución del módulo
Abrir el proyecto en un IDE compatible con Java.

Usar Java 17 o superior.

Ejecutar las clases de prueba o la clase principal.

5. Pruebas realizadas
Creación de unidades.

Consumo y validación de recursos.

Construcción de edificios.

Resolución de batallas.

Generación de enemigos.

Manejo de excepciones.

6. Conclusión
El módulo M03 implementa toda la lógica necesaria para que Civilizations funcione correctamente. La estructura es modular, clara y preparada para integrarse con la interfaz gráfica desarrollada en otros módulos.