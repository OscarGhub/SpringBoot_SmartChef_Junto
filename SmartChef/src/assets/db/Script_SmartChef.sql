CREATE DATABASE smartchef;
USE smartchef;

-- ============================
-- Tabla: Usuario
-- ============================
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT null,
    fecha_nacimiento DATE
);

-- ============================
-- Tabla: Preferencia
-- ============================
CREATE TABLE Preferencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Insertar datos de Preferencia
INSERT INTO Preferencia (nombre) VALUES
('Vegana'),
('Sin Gluten'),
('Alta en Proteínas'),
('Baja en Carbohidratos'),
('Sin Lactosa');

-- ============================
-- Tabla: Receta
-- ============================
CREATE TABLE Receta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    tiempo_preparacion INT,
    foto_url VARCHAR(255),
    num_favoritos INT DEFAULT 0
);

-- Insertar datos de Receta
INSERT INTO Receta (titulo, descripcion, tiempo_preparacion, foto_url, num_favoritos) VALUES
('Pollo al Horno', 'Pollo marinado al horno con especias.', 60, 'https://example.com/pollo.jpg', 10),
('Ensalada Vegana', 'Ensalada fresca con espinacas y zanahoria.', 15, 'https://example.com/ensalada.jpg', 5),
('Arroz con Verduras', 'Arroz salteado con verduras.', 30, 'https://example.com/arroz.jpg', 8),
('Sopa de Tomate', 'Sopa casera con tomate y albahaca.', 25, 'https://example.com/sopa.jpg', 3);

-- ============================
-- Tabla: Ingrediente
-- ============================
CREATE TABLE Ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(50)
);

-- Insertar datos de Ingrediente
INSERT INTO Ingrediente (nombre, unidad_medida) VALUES
('Pollo', 'gramos'),
('Arroz', 'gramos'),
('Aceite de oliva', 'mililitros'),
('Sal', 'gramos'),
('Tomate', 'unidad'),
('Ajo', 'dientes'),
('Cebolla', 'unidad'),
('Zanahoria', 'unidad'),
('Espinaca', 'gramos'),
('Queso', 'gramos');

-- ============================
-- Tabla: Receta_Ingrediente (N:N)
-- ============================
CREATE TABLE Receta_Ingrediente (
    id_receta INT,
    id_ingrediente INT,
    cantidad DECIMAL(10,2),
    PRIMARY KEY (id_receta, id_ingrediente),
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id) ON DELETE CASCADE
);

-- Insertar datos en Receta_Ingrediente
-- Pollo al Horno (id=1)
INSERT INTO Receta_Ingrediente (id_receta, id_ingrediente, cantidad) VALUES
(1, 1, 500),  -- Pollo
(1, 3, 30),   -- Aceite de oliva
(1, 4, 5),    -- Sal
(1, 6, 2),    -- Ajo
(1, 7, 1);    -- Cebolla

-- Ensalada Vegana (id=2)
INSERT INTO Receta_Ingrediente (id_receta, id_ingrediente, cantidad) VALUES
(2, 8, 1),    -- Zanahoria
(2, 9, 100),  -- Espinaca
(2, 3, 10);   -- Aceite de oliva

-- Arroz con Verduras (id=3)
INSERT INTO Receta_Ingrediente (id_receta, id_ingrediente, cantidad) VALUES
(3, 2, 200),  -- Arroz
(3, 7, 1),    -- Cebolla
(3, 8, 1),    -- Zanahoria
(3, 4, 5);    -- Sal

-- Sopa de Tomate (id=4)
INSERT INTO Receta_Ingrediente (id_receta, id_ingrediente, cantidad) VALUES
(4, 5, 2),    -- Tomate
(4, 6, 1),    -- Ajo
(4, 7, 1),    -- Cebolla
(4, 4, 5);    -- Sal

-- ============================
-- Tabla: Receta_Preferencia (N:N)
-- ============================
CREATE TABLE Receta_Preferencia (
    id_receta INT,
    id_preferencia INT,
    PRIMARY KEY (id_receta, id_preferencia),
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_preferencia) REFERENCES Preferencia(id) ON DELETE CASCADE
);

-- Insertar datos en Receta_Preferencia
-- Ensalada Vegana
INSERT INTO Receta_Preferencia (id_receta, id_preferencia) VALUES
(2, 1),  -- Vegana
(2, 5);  -- Sin Lactosa

-- Arroz con Verduras
INSERT INTO Receta_Preferencia (id_receta, id_preferencia) VALUES
(3, 2),  -- Sin Gluten
(3, 5);  -- Sin Lactosa

-- Pollo al Horno
INSERT INTO Receta_Preferencia (id_receta, id_preferencia) VALUES
(1, 3);  -- Alta en Proteínas

-- ============================
-- Tabla: Receta_Favorito (N:N)
-- ============================
CREATE TABLE Receta_Favorito (
    id_usuario INT,
    id_receta INT,
    PRIMARY KEY (id_usuario, id_receta),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Coleccion
-- ============================
CREATE TABLE Coleccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Coleccion_Receta (N:N)
-- ============================
CREATE TABLE Coleccion_Receta (
    id_receta INT,
    id_coleccion INT,
    PRIMARY KEY (id_receta, id_coleccion),
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_coleccion) REFERENCES Coleccion(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Historial_Cocinado
-- ============================
CREATE TABLE Historial_Cocinado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_receta INT,
    fecha_cocinado DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: ListaCompra
-- ============================
CREATE TABLE ListaCompra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    fecha_creacion DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Lista_Compra_Ingrediente (N:N)
-- ============================
CREATE TABLE Lista_Compra_Ingrediente (
    id_lista INT,
    id_ingrediente INT,
    cantidad DECIMAL(10,2),
    PRIMARY KEY (id_lista, id_ingrediente),
    FOREIGN KEY (id_lista) REFERENCES ListaCompra(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Inventario
-- ============================
CREATE TABLE Inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ingrediente INT,
    cantidad DECIMAL(10,2),
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id) ON DELETE CASCADE
);
