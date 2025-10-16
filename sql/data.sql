INSERT INTO rol (nombre, descripcion, estado)
VALUES ('ROLE_ADMIN', 'Administrador del sistema con acceso total', true),
       ('ROLE_GERENTE', 'Gerente con permisos de gestión y reportes', true),
       ('ROLE_VENDEDOR', 'Vendedor con permisos de ventas y clientes', true),
       ('ROLE_ALMACENERO', 'Encargado de inventario y ingresos', true),
       ('ROLE_INVITADO', 'Usuario con permisos limitados de lectura', true);

INSERT INTO usuario (nombre, tipo_documento, num_documento, direccion, telefono, email, password, estado)
VALUES ('Juan Admin', 'DNI', '12345678', 'Av. Principal 123', '999888777', 'admin@tienda.com',
        '\x2432612431302446684a6f6b6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e', true),
       ('Maria Gerente', 'DNI', '87654321', 'Av. Los Gerentes 456', '999777666', 'gerente@tienda.com',
        '\x2432612431302446684a6f6b6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e', true),
       ('Carlos Vendedor', 'DNI', '11223344', 'Calle Vendedores 789', '999666555', 'vendedor@tienda.com',
        '\x2432612431302446684a6f6b6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e', true),
       ('Ana Almacenera', 'DNI', '44332211', 'Jr. Almacén 321', '999555444', 'almacen@tienda.com',
        '\x2432612431302446684a6f6b6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e', true),
       ('Luis Invitado', 'DNI', '55667788', 'Av. Visitantes 654', '999444333', 'invitado@tienda.com',
        '\x2432612431302446684a6f6b6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e4a6f6d6f4b4f6a4e6e', true);

-- 3. Asignar roles a usuarios
INSERT INTO usuario_rol (idusuario, idrol)
VALUES (1, 1), -- Admin tiene ROLE_ADMIN
       (2, 2), -- Gerente tiene ROLE_GERENTE
       (3, 3), -- Vendedor tiene ROLE_VENDEDOR
       (4, 4), -- Almacenera tiene ROLE_ALMACENERO
       (5, 5), -- Invitado tiene ROLE_INVITADO
       (2, 3), -- Gerente también tiene ROLE_VENDEDOR
       (1, 2);
-- Admin también tiene ROLE_GERENTE

-- 4. Insertar categorías
INSERT INTO categoria (nombre, descripcion, estado)
VALUES ('Electrónicos', 'Dispositivos electrónicos y gadgets', true),
       ('Ropa', 'Prendas de vestir para hombres y mujeres', true),
       ('Hogar', 'Artículos para el hogar y decoración', true),
       ('Deportes', 'Equipos y accesorios deportivos', true),
       ('Libros', 'Libros de diversos géneros', true);

-- 5. Insertar artículos
INSERT INTO articulo (idcategoria, codigo, nombre, precio_venta, stock, descripcion, estado)
VALUES (1, 'ELEC001', 'Smartphone Samsung Galaxy', 899.99, 50, 'Teléfono inteligente de última generación', true),
       (1, 'ELEC002', 'Laptop HP Pavilion', 1299.99, 25, 'Laptop para trabajo y entretenimiento', true),
       (2, 'ROPA001', 'Camisa Casual Hombre', 49.99, 100, 'Camisa de algodón para caballero', true),
       (2, 'ROPA002', 'Vestido Verano Mujer', 79.99, 75, 'Vestido ligero para temporada de verano', true),
       (3, 'HOG001', 'Juego de Sábanas Queen', 89.99, 40, 'Juego de sábanas de algodón egipcio', true),
       (4, 'DEP001', 'Pelota de Fútbol', 29.99, 60, 'Pelota oficial tamaño 5', true),
       (5, 'LIB001', 'Cien Años de Soledad', 24.99, 30, 'Novela clásica de Gabriel García Márquez', true);

-- 6. Insertar personas (clientes y proveedores)
INSERT INTO persona (tipo_persona, nombre, tipo_documento, num_documento, direccion, telefono, email)
VALUES ('Proveedor', 'TecnoImport S.A.', 'RUC', '20123456789', 'Av. Industrial 123', '014567890',
        'ventas@tecnoimport.com'),
       ('Proveedor', 'Textiles Peruanos S.A.C.', 'RUC', '20234567890', 'Calle Manufactura 456', '014567891',
        'contacto@textilesperu.com'),
       ('Cliente', 'Roberto Silva', 'DNI', '76543210', 'Jr. Los Clientes 789', '999111222', 'roberto.silva@email.com'),
       ('Cliente', 'Laura Mendoza', 'DNI', '65432109', 'Av. Compradores 321', '999222333', 'laura.mendoza@email.com'),
       ('Cliente', 'Miguel Torres', 'DNI', '54321098', 'Calle Consumidores 654', '999333444',
        'miguel.torres@email.com');