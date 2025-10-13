CREATE TABLE categoria(
                          idcategoria     BIGSERIAL PRIMARY KEY,
                          nombre          VARCHAR(50) NOT NULL UNIQUE,
                          descripcion     TEXT NULL,
                          estado          BOOLEAN DEFAULT TRUE
);

CREATE TABLE articulo(
                         idarticulo      BIGSERIAL PRIMARY KEY,
                         idcategoria     BIGINT NOT NULL,
                         codigo          VARCHAR(50) NULL,
                         nombre          VARCHAR(100) NOT NULL UNIQUE,
                         precio_venta    DECIMAL(11,2) NOT NULL,
                         stock           INTEGER NOT NULL,
                         descripcion     TEXT NULL,
                         estado          BOOLEAN DEFAULT TRUE,
                         FOREIGN KEY (idcategoria) REFERENCES categoria(idcategoria)
);

CREATE TABLE persona(
                        idpersona       BIGSERIAL PRIMARY KEY,
                        tipo_persona    VARCHAR(20) NOT NULL, -- Cambiado de enum a VARCHAR
                        nombre          VARCHAR(100) NOT NULL,
                        tipo_documento  VARCHAR(20) NULL,
                        num_documento   VARCHAR(20) NULL,
                        direccion       VARCHAR(70) NULL,
                        telefono        VARCHAR(20) NULL,
                        email           VARCHAR(50) NULL
);

CREATE TABLE rol(
                    idrol           BIGSERIAL PRIMARY KEY,
                    nombre          VARCHAR(30) NOT NULL,
                    descripcion     TEXT NULL,
                    estado          BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuario(
                        idusuario       BIGSERIAL PRIMARY KEY,
                        nombre          VARCHAR(100) NOT NULL,
                        tipo_documento  VARCHAR(20) NULL,
                        num_documento   VARCHAR(20) NULL,
                        direccion       VARCHAR(70) NULL,
                        telefono        VARCHAR(20) NULL,
                        email           VARCHAR(50) NOT NULL UNIQUE,
                        password        BYTEA NOT NULL,
                        estado          BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuario_rol (
                             idusuario   BIGINT NOT NULL,
                             idrol       BIGINT NOT NULL,
                             FOREIGN KEY (idusuario) REFERENCES usuario(idusuario) ON DELETE CASCADE,
                             FOREIGN KEY (idrol) REFERENCES rol(idrol) ON DELETE CASCADE,
                             PRIMARY KEY (idusuario, idrol)
);

CREATE TABLE ingreso(
                        idingreso           BIGSERIAL PRIMARY KEY,
                        idproveedor         BIGINT NOT NULL,
                        idusuario           BIGINT NOT NULL,
                        tipo_comprobante    VARCHAR(20) NOT NULL, -- Cambiado de enum a VARCHAR
                        serie_comprobante   VARCHAR(7) NULL,
                        num_comprobante     VARCHAR(10) NOT NULL,
                        fecha               TIMESTAMP NOT NULL,
                        impuesto            DECIMAL(4,2) NOT NULL,
                        total               DECIMAL(11,2) NOT NULL,
                        estado              VARCHAR(20) NOT NULL, -- Cambiado de enum a VARCHAR
                        FOREIGN KEY (idproveedor) REFERENCES persona(idpersona),
                        FOREIGN KEY (idusuario) REFERENCES usuario(idusuario)
);

CREATE TABLE detalle_ingreso(
                                iddetalle_ingreso   BIGSERIAL PRIMARY KEY,
                                idingreso           BIGINT NOT NULL,
                                idarticulo          BIGINT NOT NULL,
                                cantidad            INTEGER NOT NULL,
                                precio              DECIMAL(11,2) NOT NULL,
                                FOREIGN KEY (idingreso) REFERENCES ingreso(idingreso) ON DELETE CASCADE,
                                FOREIGN KEY (idarticulo) REFERENCES articulo(idarticulo)
);

CREATE TABLE venta(
                      idventa             BIGSERIAL PRIMARY KEY,
                      idcliente           BIGINT NOT NULL,
                      idusuario           BIGINT NOT NULL,
                      tipo_comprobante    VARCHAR(20) NOT NULL, -- Cambiado de enum a VARCHAR
                      serie_comprobante   VARCHAR(7) NULL,
                      num_comprobante     VARCHAR(10) NOT NULL,
                      fecha_hora          TIMESTAMP NOT NULL,
                      impuesto            DECIMAL(4,2) NOT NULL,
                      total               DECIMAL(11,2) NOT NULL,
                      estado              VARCHAR(20) NOT NULL, -- Cambiado de enum a VARCHAR
                      FOREIGN KEY (idcliente) REFERENCES persona(idpersona),
                      FOREIGN KEY (idusuario) REFERENCES usuario(idusuario)
);

CREATE TABLE detalle_venta(
                              iddetalle_venta     BIGSERIAL PRIMARY KEY,
                              idventa             BIGINT NOT NULL,
                              idarticulo          BIGINT NOT NULL,
                              cantidad            INTEGER NOT NULL,
                              precio              DECIMAL(11,2) NOT NULL,
                              descuento           DECIMAL(11,2) NOT NULL,
                              FOREIGN KEY (idventa) REFERENCES venta(idventa) ON DELETE CASCADE,
                              FOREIGN KEY (idarticulo) REFERENCES articulo(idarticulo)
);