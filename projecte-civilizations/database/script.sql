CREATE DATABASE IF NOT EXISTS civilizations_db;
USE civilizations_db;

-- 1. TABLA DE RECURSOS Y ESTADO GENERAL DE LA CIVILIZACIÓN
CREATE TABLE IF NOT EXISTS civilizacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) DEFAULT 'Mi Civilización',
    comida INT NOT NULL DEFAULT 4870000,
    madera INT NOT NULL DEFAULT 45000,
    hierro INT NOT NULL DEFAULT 429000,
    mana INT NOT NULL DEFAULT 0,
    tech_defensa INT NOT NULL DEFAULT 0,
    tech_ataque INT NOT NULL DEFAULT 0,
    granjas INT NOT NULL DEFAULT 0,
    carpinterias INT NOT NULL DEFAULT 0,
    herrerias INT NOT NULL DEFAULT 0,
    torres_magicas INT NOT NULL DEFAULT 0,
    iglesias INT NOT NULL DEFAULT 0,
    total_batallas INT NOT NULL DEFAULT 0,
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. TABLA PARA EL EJÉRCITO (ESTADO ACTUAL DE UNIDADES)
-- Almacena la cantidad actual de cada tipo de unidad que posee la civilización
CREATE TABLE IF NOT EXISTS ejercito (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_unidad VARCHAR(30) UNIQUE NOT NULL, -- 'Swordsman', 'Spearman', 'Arrow Tower', etc.
    cantidad INT NOT NULL DEFAULT 0
);

-- 3. TABLA DE HISTÓRICO DE BATALLAS (Para los reportes dinámicos)
CREATE TABLE IF NOT EXISTS batallas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_batalla INT NOT NULL,
    resultado VARCHAR(20) NOT NULL, -- 'Ganada' o 'Perdida'
    residuos_madera INT NOT NULL DEFAULT 0,
    residuos_hierro INT NOT NULL DEFAULT 0,
    coste_comida_perdido_civi INT NOT NULL DEFAULT 0,
    coste_madera_perdido_civi INT NOT NULL DEFAULT 0,
    coste_hierro_perdido_civi INT NOT NULL DEFAULT 0,
    coste_comida_perdido_enem INT NOT NULL DEFAULT 0,
    coste_madera_perdido_enem INT NOT NULL DEFAULT 0,
    coste_hierro_perdido_enem INT NOT NULL DEFAULT 0,
    desarrollo_paso_a_paso LONGTEXT NOT NULL, -- Almacena todo el log detallado de la batalla
    fecha_batalla TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- INSERCIONES INICIALES DE PRUEBA
INSERT INTO civilizacion (id, comida, madera, hierro, mana, tech_defensa, tech_ataque, granjas, carpinterias, herrerias, torres_magicas, iglesias, total_batallas) 
VALUES (1, 4870000, 45000, 429000, 0, 0, 0, 0, 0, 0, 0, 0, 0)
ON DUPLICATE KEY UPDATE id=id;

INSERT INTO ejercito (tipo_unidad, cantidad) VALUES 
('Swordsman', 10),
('Spearman', 10),
('Crossbow', 10),
('Cannon', 30),
('Arrow Tower', 10),
('Catapult', 10),
('Rocket Launcher', 9),
('Magician', 0),
('Priest', 0)
ON DUPLICATE KEY UPDATE tipo_unidad=tipo_unidad;