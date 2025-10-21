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

-- ============================
-- Tabla: Ingrediente
-- ============================
CREATE TABLE Ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(50)
);

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
