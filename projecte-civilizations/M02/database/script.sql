CREATE DATABASE IF NOT EXISTS civilizations_db;
USE civilizations_db;

-- 1. TABLA DE RECURSOS Y EDIFICIOS (Estado actual)
DROP TABLE IF EXISTS civilizacion;
CREATE TABLE civilizacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL DEFAULT 'Mi Civilizacion',
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
) ENGINE=InnoDB;

-- 2. TABLA DE EJÉRCITO
DROP TABLE IF EXISTS ejercito;
CREATE TABLE ejercito (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_unidad VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    CONSTRAINT unique_unidad UNIQUE (tipo_unidad)
) ENGINE=InnoDB;

-- 3. TABLA DE HISTORIAL DE BATALLAS 
-- (Nota: desarrollo_paso_a_paso NO lleva DEFAULT para evitar errores en MySQL)
DROP TABLE IF EXISTS batallas;
CREATE TABLE batallas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_batalla INT NOT NULL,
    resultado VARCHAR(20) NOT NULL,
    residuos_madera INT NOT NULL DEFAULT 0,
    residuos_hierro INT NOT NULL DEFAULT 0,
    coste_comida_perdido_civi INT NOT NULL DEFAULT 0,
    coste_madera_perdido_civi INT NOT NULL DEFAULT 0,
    coste_hierro_perdido_civi INT NOT NULL DEFAULT 0,
    coste_comida_perdido_enem INT NOT NULL DEFAULT 0,
    coste_madera_perdido_enem INT NOT NULL DEFAULT 0,
    coste_hierro_perdido_enem INT NOT NULL DEFAULT 0,
    desarrollo_paso_a_paso LONGTEXT NOT NULL, 
    fecha_batalla TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ==========================================
-- INSERCIONES INICIALES DE PRUEBA
-- ==========================================
INSERT INTO civilizacion (id, comida, madera, hierro) 
VALUES (1, 4870000, 45000, 429000);

INSERT INTO ejercito (tipo_unidad, cantidad) VALUES 
('Swordsman', 10),
('Spearman', 10),
('Crossbow', 10),
('Cannon', 30),
('Arrow Tower', 10),
('Catapult', 10),
('Rocket Launcher', 9),
('Magician', 0),
('Priest', 0);