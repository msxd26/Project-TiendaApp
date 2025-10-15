INSERT INTO categoria (idcategoria, nombre, descripcion) VALUES
                                                             (1, 'Bebidas', 'Gaseosas, jugos, rehidratantes'),
                                                             (2, 'Abarrotes', 'Productos no perecibles como arroz, azúcar, menestras'),
                                                             (3, 'Lácteos', 'Leche, queso, yogur y derivados'),
                                                             (4, 'Limpieza', 'Artículos para la limpieza del hogar');

INSERT INTO rol (idrol, nombre, descripcion) VALUES
                                                 (1, 'Administrador', 'Control total del sistema'),
                                                 (2, 'Vendedor', 'Registra ventas y gestiona clientes'),
                                                 (3, 'Almacenista', 'Controla el inventario y registra ingresos');

INSERT INTO persona (idpersona, tipo_persona, nombre, direccion, telefono, email) VALUES
                                                                                      (1, 'Cliente', 'Ana Torres', 'Av. El Sol 123, Villa el Salvador', '987654321', 'ana.torres@email.com'),
                                                                                      (2, 'Cliente', 'Carlos Luna', 'Jr. Pumacahua 456, SJM', '912345678', 'carlos.luna@email.com'),
                                                                                      (3, 'Proveedor', 'Distribuidora del Sur S.A.C.', 'Calle Los Geranios 789, Lurín', '012884567', 'ventas@disur.com.pe'),
                                                                                      (4, 'Proveedor', 'Abarrotes del Perú S.A.', 'Av. Industrial 101, VES', '012998765', 'contacto@abaperu.com');

INSERT INTO articulo (idarticulo, idcategoria, codigo, nombre, precio_venta, stock, descripcion) VALUES
                                                                                                     (1, 1, 'GK3L', 'Gaseosa Inka Kola 3L', 10.50, 50, 'Botella de plástico no retornable'),
                                                                                                     (2, 2, 'ARRC1K', 'Arroz Costeño Graneadito 1Kg', 4.80, 120, 'Bolsa de 1 kilogramo'),
                                                                                                     (3, 3, 'LGL1L', 'Leche Gloria Evaporada Lata', 3.50, 80, 'Lata de 400g'),
                                                                                                     (4, 4, 'DTGA1K', 'Detergente Ace 1Kg', 9.20, 60, 'Bolsa de 1 kilogramo aroma limón');

INSERT INTO usuario (idusuario, nombre, email, password) VALUES
                                                             (1, 'Admin General', 'admin@sistema.com', 'superclave'::bytea),
                                                             (2, 'Lucía Fernández', 'lucia.f@sistema.com', 'clave123'::bytea),
                                                             (3, 'Mario Rojas', 'mario.r@sistema.com', 'clave456'::bytea);

INSERT INTO usuario_rol (idusuario, idrol) VALUES
                                               (1, 1),
                                               (2, 2),
                                               (3, 2),
                                               (3, 3);

INSERT INTO ingreso (idingreso, idproveedor, idusuario, tipo_comprobante, num_comprobante, fecha, impuesto, total, estado) VALUES
    (1, 4, 3, 'Factura', 'F001-00123', '2025-10-09 10:00:00', 18.00, 236.00, 'Aceptado');

INSERT INTO detalle_ingreso (iddetalle_ingreso, idingreso, idarticulo, cantidad, precio) VALUES
    (1, 1, 2, 50, 4.00);

UPDATE articulo SET stock = stock + 50 WHERE idarticulo = 2;

INSERT INTO venta (idventa, idcliente, idusuario, tipo_comprobante, num_comprobante, fecha_hora, impuesto, total, estado) VALUES
    (1, 1, 2, 'Boleta', 'B001-00456', '2025-10-10 15:30:00', 18.00, 31.50, 'Aceptado');

INSERT INTO detalle_venta (iddetalle_venta, idventa, idarticulo, cantidad, precio, descuento) VALUES
                                                                                                  (1, 1, 1, 2, 10.50, 0),
                                                                                                  (2, 1, 3, 3, 3.50, 0);