CREATE DATABASE smartchef;
USE smartchef;

-- ============================
-- Tabla: Usuario
-- ============================
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    foto_url VARCHAR(255)
);

-- ============================
-- Tabla: Preferencia
-- ============================
CREATE TABLE Preferencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- ============================
-- Tabla: Lista_Compra
-- ============================
CREATE TABLE Lista_Compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_creacion DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Receta
-- ============================
CREATE TABLE Receta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    tutorial VARCHAR(255),
    tiempo_preparacion INT,
    foto_url VARCHAR(255),
    num_favoritos BIGINT DEFAULT 0
);

-- ============================
-- Tabla: Ingrediente
-- ============================
CREATE TABLE Ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(50),
    imagen_url VARCHAR(255)
);

-- ============================
-- Tabla: Receta_Ingrediente (N:N)
-- ============================
CREATE TABLE Receta_Ingrediente (
    id_receta INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad DECIMAL(10,2),
    PRIMARY KEY (id_receta, id_ingrediente),
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Receta_Preferencia (N:N)
-- ============================
CREATE TABLE Receta_Preferencia (
    id_receta INT NOT NULL,
    id_preferencia INT NOT NULL,
    PRIMARY KEY (id_receta, id_preferencia),
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_preferencia) REFERENCES Preferencia(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Coleccion
-- ============================
CREATE TABLE Coleccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Coleccion_Receta (N:N)
-- ============================
CREATE TABLE Coleccion_Receta (
    id_receta INT NOT NULL,
    id_coleccion INT NOT NULL,
    PRIMARY KEY (id_receta, id_coleccion),
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_coleccion) REFERENCES Coleccion(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Historial_Cocinado
-- ============================
CREATE TABLE Historial_Cocinado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_receta INT NOT NULL,
    fecha_cocinado DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Lista_Compra_Ingrediente (N:N)
-- ============================
CREATE TABLE Lista_Compra_Ingrediente (
    id_lista INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad DECIMAL(10,2),
    PRIMARY KEY (id_lista, id_ingrediente),
    FOREIGN KEY (id_lista) REFERENCES Lista_Compra(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Inventario
-- ============================
CREATE TABLE Inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Inventario_Ingrediente (N:N)
-- ============================

CREATE TABLE Inventario_Ingrediente (
    id_inventario INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad DECIMAL(10,2),
    PRIMARY KEY (id_inventario, id_ingrediente),
    FOREIGN KEY (id_inventario) REFERENCES Inventario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Receta_Guardada (N:N)
-- ============================
CREATE TABLE Receta_Guardada (
    id_usuario INT NOT NULL,
    id_receta INT NOT NULL,
    fecha_guardado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_receta),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receta) REFERENCES Receta(id) ON DELETE CASCADE
);

-- ============================
-- Datos de Preferencia
-- ============================
INSERT INTO Preferencia (nombre) VALUES
('Vegana'),
('Vegetariana'),
('Sin Gluten'),
('Alta en Proteínas'),
('Baja en Carbohidratos'),
('Sin Lactosa'),
('Keto'),
('Mediterránea');

-- ============================
-- Datos de Receta
-- ============================
INSERT INTO Receta (titulo, descripcion, tutorial, tiempo_preparacion, foto_url) VALUES
('Pollo al Horno', 'Pollo marinado con especias al horno.', '1. Marina el pollo.\n2. Hornea 60 min.\n3. Sirve.', 60, 'https://i.imgur.com/K1x5Z7Q.jpeg'),
('Ensalada Vegana', 'Ensalada fresca con espinacas y aguacate.', '1. Lava y corta.\n2. Mezcla y aliña.', 15, 'https://i.imgur.com/8Qj9m1u.jpeg'),
('Arroz con Verduras', 'Arroz salteado con vegetales.', '1. Cocina arroz.\n2. Sofríe verduras.\n3. Mezcla todo.', 30, 'https://i.imgur.com/G4YlE5V.jpeg'),
('Sopa de Tomate', 'Sopa casera de tomate y albahaca.', '1. Sofríe ajo.\n2. Añade tomate.\n3. Tritura y sirve.', 25, 'https://i.imgur.com/FwT0r6a.jpeg'),
('Tacos de Pollo', 'Tacos con pollo, verduras y salsa.', '1. Cocina pollo.\n2. Prepara tacos.\n3. Añade salsa.', 20, 'https://i.imgur.com/w2Y9k8H.jpeg'),
('Smoothie Verde', 'Bebida saludable con espinaca y plátano.', '1. Mezcla todo en licuadora.', 5, 'https://i.imgur.com/pQ8sYk9.jpeg'),
('Pasta Carbonara', 'Clásica pasta italiana con crema y bacon.', '1. Cocina pasta.\n2. Mezcla con salsa.', 25, 'https://i.imgur.com/jG9Zz4o.jpeg'),
('Gazpacho Andaluz', 'Sopa fría española de tomate y pepino.', '1. Tritura ingredientes.\n2. Sirve frío.', 10, 'https://i.imgur.com/BfS1u3M.jpeg'),
('Panqueques de Avena', 'Panqueques saludables sin azúcar.', '1. Mezcla avena y huevo.\n2. Cocina en sartén.', 15, 'https://i.imgur.com/T0bH7qZ.jpeg'),
('Curry de Garbanzos', 'Plato vegano con garbanzos y especias.', '1. Sofríe cebolla y ajo.\n2. Añade garbanzos.\n3. Cocina con curry.', 35, 'https://i.imgur.com/Q6Kj3Zg.jpeg'),
('Pizza Margarita', 'Pizza italiana con tomate, queso y albahaca.', '1. Prepara masa.\n2. Hornea con ingredientes.', 40, 'https://i.imgur.com/1Zp7EwK.jpeg'),
('Lentejas Estofadas', 'Plato tradicional de lentejas con verduras.', '1. Sofríe ajo y cebolla.\n2. Cocina lentejas.\n3. Sirve caliente.', 50, 'https://i.imgur.com/h5vYp9c.jpeg'),
('Batido de Fresas', 'Batido natural con fresas y yogur.', '1. Mezcla todo en licuadora.', 5, 'https://i.imgur.com/A9jS0yR.jpeg'),
('Tortilla de Patatas', 'Clásica tortilla española con patata y cebolla.', '1. Fríe patatas.\n2. Añade huevo.\n3. Cocina en sartén.', 25, 'https://i.imgur.com/C7nS1uV.jpeg'),
('Quinoa con Verduras', 'Plato saludable de quinoa salteada.', '1. Cocina quinoa.\n2. Sofríe verduras.\n3. Mezcla.', 20, 'https://i.imgur.com/2Xy0gGZ.jpeg');

-- ============================
-- Datos de Ingrediente
-- ============================
INSERT INTO Ingrediente (nombre, unidad_medida, imagen_url) VALUES
('Pollo', 'gramos', 'https://i.imgur.com/k9m8j4J.jpeg'),
('Arroz', 'gramos', 'https://i.imgur.com/X4y2lH9.jpeg'),
('Aceite de oliva', 'mililitros', 'https://i.imgur.com/vH1N9sZ.jpeg'),
('Sal', 'gramos', 'https://i.imgur.com/QhD8j0B.jpeg'),
('Tomate', 'unidad', 'https://i.imgur.com/1Fh9b2V.jpeg'),
('Ajo', 'dientes', 'https://i.imgur.com/7wM1D2F.jpeg'),
('Cebolla', 'unidad', 'https://i.imgur.com/C5uM8aX.jpeg'),
('Zanahoria', 'unidad', 'https://i.imgur.com/N6sY0gU.jpeg'),
('Espinaca', 'gramos', 'https://i.imgur.com/p1z5vYJ.jpeg'),
('Queso', 'gramos', 'https://i.imgur.com/8QdGv5U.jpeg'),
('Pan', 'unidad', 'https://i.imgur.com/fL5xT7q.jpeg'),
('Pasta', 'gramos', 'https://i.imgur.com/4gSj2hL.jpeg'),
('Leche', 'mililitros', 'https://i.imgur.com/qFp4ZtZ.jpeg'),
('Huevo', 'unidad', 'https://i.imgur.com/L5xM7jP.jpeg'),
('Avena', 'gramos', 'https://i.imgur.com/R3aB0tY.jpeg'),
('Plátano', 'unidad', 'https://i.imgur.com/jE6wV8D.jpeg'),
('Fresas', 'gramos', 'https://i.imgur.com/uT0mN8q.jpeg'),
('Garbanzos', 'gramos', 'https://i.imgur.com/9kP2rNf.jpeg'),
('Lentejas', 'gramos', 'https://i.imgur.com/7gK5YpZ.jpeg'),
('Pimiento', 'unidad', 'https://i.imgur.com/YwN3L4o.jpeg'),
('Pepino', 'unidad', 'https://i.imgur.com/H1tWj6f.jpeg'),
('Quinoa', 'gramos', 'https://i.imgur.com/Z4w0XpG.jpeg'),
('Albahaca', 'hojas', 'https://i.imgur.com/D8tUj6S.jpeg');

-- ============================
-- Datos de Receta_Ingrediente
-- ============================
INSERT INTO Receta_Ingrediente (id_receta, id_ingrediente, cantidad) VALUES
-- Pollo al Horno
(1, 1, 500), (1, 3, 20), (1, 4, 5), (1, 6, 2),
-- Ensalada Vegana
(2, 8, 1), (2, 9, 100), (2, 16, 1), (2, 3, 10),
-- Arroz con Verduras
(3, 2, 200), (3, 7, 1), (3, 8, 1), (3, 4, 5),
-- Sopa de Tomate
(4, 5, 3), (4, 6, 1), (4, 7, 1), (4, 23, 5),
-- Tacos de Pollo
(5, 1, 200), (5, 7, 1), (5, 20, 1), (5, 11, 2),
-- Smoothie Verde
(6, 9, 50), (6, 16, 1), (6, 13, 200),
-- Pasta Carbonara
(7, 12, 150), (7, 10, 50), (7, 14, 1), (7, 3, 10),
-- Gazpacho
(8, 5, 4), (8, 21, 1), (8, 20, 1), (8, 4, 3),
-- Panqueques de Avena
(9, 15, 80), (9, 14, 1), (9, 13, 100),
-- Curry de Garbanzos
(10, 18, 200), (10, 7, 1), (10, 6, 2), (10, 8, 1),
-- Pizza Margarita
(11, 11, 1), (11, 5, 2), (11, 10, 50), (11, 23, 3),
-- Lentejas Estofadas
(12, 19, 200), (12, 7, 1), (12, 8, 1), (12, 4, 3),
-- Batido de Fresas
(13, 17, 100), (13, 13, 150), (13, 16, 1),
-- Tortilla de Patatas
(14, 14, 2), (14, 7, 1), (14, 4, 3),
-- Quinoa con Verduras
(15, 22, 100), (15, 8, 1), (15, 7, 1), (15, 3, 10);

-- ============================
-- Datos de Receta_Preferencia
-- ============================
INSERT INTO Receta_Preferencia (id_receta, id_preferencia) VALUES
(1, 4),                -- Pollo al Horno - Alta en Proteínas
(2, 1), (2, 6),        -- Ensalada Vegana - Vegana, Sin Lactosa
(3, 3), (3, 6),        -- Arroz con Verduras - Sin Gluten, Sin Lactosa
(4, 1), (4, 2), (4, 6),-- Sopa de Tomate - Vegana, Vegetariana, Sin Lactosa
(5, 4),                -- Tacos de Pollo - Alta en Proteínas
(6, 1), (6, 6),        -- Smoothie Verde - Vegana, Sin Lactosa
(7, 8),                -- Pasta Carbonara - Mediterránea
(8, 1), (8, 8),        -- Gazpacho - Vegana, Mediterránea
(9, 2), (9, 6),        -- Panqueques de Avena - Vegetariana, Sin Lactosa
(10, 1), (10, 2), (10, 6), -- Curry de Garbanzos - Vegana, Vegetariana, Sin Lactosa
(11, 8), (11, 6),      -- Pizza Margarita - Mediterránea, Sin Lactosa
(12, 2),               -- Lentejas Estofadas - Vegetariana
(13, 6),               -- Batido de Fresas - Sin Lactosa
(14, 8),               -- Tortilla de Patatas - Mediterránea
(15, 1), (15, 6);      -- Quinoa con Verduras - Vegana, Sin Lactosa
