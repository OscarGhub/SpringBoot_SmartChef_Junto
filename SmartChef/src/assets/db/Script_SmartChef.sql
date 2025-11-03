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
    tutorial LONGTEXT,
    tiempo_preparacion INT,
    foto_url VARCHAR(255)
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
-- Tabla: ListaCompra
-- ============================
CREATE TABLE ListaCompra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_creacion DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- ============================
-- Tabla: Lista_Compra_Ingrediente (N:N)
-- ============================
CREATE TABLE Lista_Compra_Ingrediente (
    id_lista INT NOT NULL,
    id_ingrediente INT NOT NULL,
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
    id_ingrediente INT NOT NULL,
    cantidad DECIMAL(10,2),
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
-- VISTA: Recetas con número de favoritos (dinámica)
-- ============================
CREATE OR REPLACE VIEW Vista_Receta_Favoritos AS
SELECT 
    r.id,
    r.titulo,
    r.descripcion,
    r.tutorial,
    r.tiempo_preparacion,
    r.foto_url,
    COUNT(rg.id_usuario) AS num_favoritos
FROM Receta r
LEFT JOIN Receta_Guardada rg ON r.id = rg.id_receta
GROUP BY r.id;

-- ============================
-- DATOS: Preferencia
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
-- DATOS: Receta
-- ============================
INSERT INTO Receta (titulo, descripcion, tutorial, tiempo_preparacion, foto_url) VALUES
('Pollo al Horno', 'Pollo marinado con especias al horno.', '1. Marina el pollo.\n2. Hornea 60 min.\n3. Sirve.', 60, 'https://example.com/pollo.jpg'),
('Ensalada Vegana', 'Ensalada fresca con espinacas y aguacate.', '1. Lava y corta.\n2. Mezcla y aliña.', 15, 'https://example.com/ensalada.jpg'),
('Arroz con Verduras', 'Arroz salteado con vegetales.', '1. Cocina arroz.\n2. Sofríe verduras.\n3. Mezcla todo.', 30, 'https://example.com/arroz.jpg'),
('Sopa de Tomate', 'Sopa casera de tomate y albahaca.', '1. Sofríe ajo.\n2. Añade tomate.\n3. Tritura y sirve.', 25, 'https://example.com/sopa.jpg'),
('Tacos de Pollo', 'Tacos con pollo, verduras y salsa.', '1. Cocina pollo.\n2. Prepara tacos.\n3. Añade salsa.', 20, 'https://example.com/tacos.jpg'),
('Smoothie Verde', 'Bebida saludable con espinaca y plátano.', '1. Mezcla todo en licuadora.', 5, 'https://example.com/smoothie.jpg'),
('Pasta Carbonara', 'Clásica pasta italiana con crema y bacon.', '1. Cocina pasta.\n2. Mezcla con salsa.', 25, 'https://example.com/pasta.jpg'),
('Gazpacho Andaluz', 'Sopa fría española de tomate y pepino.', '1. Tritura ingredientes.\n2. Sirve frío.', 10, 'https://example.com/gazpacho.jpg'),
('Panqueques de Avena', 'Panqueques saludables sin azúcar.', '1. Mezcla avena y huevo.\n2. Cocina en sartén.', 15, 'https://example.com/panqueques.jpg'),
('Curry de Garbanzos', 'Plato vegano con garbanzos y especias.', '1. Sofríe cebolla y ajo.\n2. Añade garbanzos.\n3. Cocina con curry.', 35, 'https://example.com/curry.jpg'),
('Pizza Margarita', 'Pizza italiana con tomate, queso y albahaca.', '1. Prepara masa.\n2. Hornea con ingredientes.', 40, 'https://example.com/pizza.jpg'),
('Lentejas Estofadas', 'Plato tradicional de lentejas con verduras.', '1. Sofríe ajo y cebolla.\n2. Cocina lentejas.\n3. Sirve caliente.', 50, 'https://example.com/lentejas.jpg'),
('Batido de Fresas', 'Batido natural con fresas y yogur.', '1. Mezcla todo en licuadora.', 5, 'https://example.com/batido.jpg'),
('Tortilla de Patatas', 'Clásica tortilla española con patata y cebolla.', '1. Fríe patatas.\n2. Añade huevo.\n3. Cocina en sartén.', 25, 'https://example.com/tortilla.jpg'),
('Quinoa con Verduras', 'Plato saludable de quinoa salteada.', '1. Cocina quinoa.\n2. Sofríe verduras.\n3. Mezcla.', 20, 'https://example.com/quinoa.jpg');

-- ============================
-- DATOS: Ingrediente
-- ============================
INSERT INTO Ingrediente (nombre, unidad_medida, imagen_url) VALUES
('Pollo', 'gramos', 'https://png.pngtree.com/png-vector/20250321/ourmid/pngtree-fresh-raw-chicken-leg-piece-with-skin-png-image_15816101.png'),
('Arroz', 'gramos', 'https://png.pngtree.com/png-clipart/20240327/original/pngtree-traditional-cooked-white-rice-in-brazilian-food-png-image_14691888.png'),
('Aceite de oliva', 'mililitros', 'https://rafaelsalgado.com/wp-content/uploads/2018/04/garrita.png'),
('Sal', 'gramos', 'https://www.pngarts.com/files/4/Salt-PNG-Image-Background.png'),
('Tomate', 'unidad', 'https://png.pngtree.com/png-clipart/20210530/original/pngtree-tomatoes-tomatoes-vegetables-png-image_6370280.jpg'),
('Ajo', 'dientes', 'https://png.pngtree.com/png-vector/20250105/ourmid/pngtree-garlic-logo-png-image_15053088.png'),
('Cebolla', 'unidad', 'https://png.pngtree.com/png-vector/20240928/ourmid/pngtree-red-onion-png-image_13646454.png'),
('Zanahoria', 'unidad', 'https://png.pngtree.com/png-vector/20240203/ourmid/pngtree-fresh-orange-carrot-png-image_11501567.png'),
('Espinaca', 'gramos', 'https://png.pngtree.com/png-vector/20240517/ourmid/pngtree-spinach-vegetable-png-image_12549834.png'),
('Queso', 'gramos', 'https://png.pngtree.com/png-clipart/20231115/original/pngtree-cheddar-cheese-png-image_13537960.png'),
('Pan', 'unidad', 'https://png.pngtree.com/png-vector/20240126/ourmid/pngtree-sliced-bread-png-image_11429225.png'),
('Pasta', 'gramos', 'https://png.pngtree.com/png-vector/20231204/ourmid/pngtree-raw-spaghetti-pasta-png-image_11352820.png'),
('Leche', 'mililitros', 'https://png.pngtree.com/png-clipart/20230817/original/pngtree-glass-of-milk-isolated-png-image_9159652.png'),
('Huevo', 'unidad', 'https://png.pngtree.com/png-vector/20231227/ourmid/pngtree-raw-eggs-png-image_11383935.png'),
('Avena', 'gramos', 'https://png.pngtree.com/png-clipart/20230818/original/pngtree-oat-flakes-in-bowl-png-image_9163065.png'),
('Plátano', 'unidad', 'https://png.pngtree.com/png-vector/20231221/ourmid/pngtree-fresh-banana-png-image_11377204.png'),
('Fresas', 'gramos', 'https://png.pngtree.com/png-vector/20231124/ourmid/pngtree-fresh-strawberry-png-image_13545019.png'),
('Garbanzos', 'gramos', 'https://png.pngtree.com/png-vector/20230817/original/pngtree-dried-chickpeas-in-bowl-png-image_9160303.png'),
('Lentejas', 'gramos', 'https://png.pngtree.com/png-vector/20230818/original/pngtree-green-lentils-in-bowl-png-image_9163028.png'),
('Pimiento', 'unidad', 'https://png.pngtree.com/png-vector/20231121/ourmid/pngtree-fresh-red-bell-pepper-png-image_13543118.png'),
('Pepino', 'unidad', 'https://png.pngtree.com/png-vector/20240131/ourmid/pngtree-cucumber-vegetable-png-image_11441856.png'),
('Quinoa', 'gramos', 'https://png.pngtree.com/png-vector/20230818/original/pngtree-quinoa-seeds-in-wooden-bowl-png-image_9163029.png'),
('Albahaca', 'hojas', 'https://png.pngtree.com/png-clipart/20231115/original/pngtree-basil-leaves-png-image_13537727.png');


-- ============================
-- DATOS: Receta_Ingrediente
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
-- DATOS: Receta_Preferencia
-- ============================
INSERT INTO Receta_Preferencia (id_receta, id_preferencia) VALUES
(1, 3),  -- Pollo al Horno - Alta en proteínas
(2, 1), (2, 5),  -- Ensalada Vegana - Vegana, Sin lactosa
(3, 2), (3, 5),  -- Arroz con Verduras - Sin Gluten, Sin Lactosa
(4, 1), (4, 2), (4, 5),  -- Sopa de Tomate
(5, 4),  -- Tacos de Pollo - Alta en proteínas
(6, 1), (6, 5),  -- Smoothie Verde - Vegana
(7, 8),  -- Pasta Carbonara - Mediterránea
(8, 1), (8, 8),  -- Gazpacho - Vegana, Mediterránea
(9, 5), (9, 2),  -- Panqueques de Avena - Sin Lactosa
(10, 1), (10, 2), (10, 5),  -- Curry Vegano
(11, 8), (11, 5),  -- Pizza Margarita
(12, 2),  -- Lentejas Estofadas
(13, 5),  -- Batido de Fresas - Sin Lactosa
(14, 8),  -- Tortilla - Mediterránea
(15, 1), (15, 5);  -- Quinoa - Vegana, Sin Lactosa
