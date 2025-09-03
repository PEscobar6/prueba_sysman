-- Datos de prueba para la base de datos
-- Insertar departamentos
INSERT INTO departamentos (codigo, nombre) VALUES 
('ANT', 'Antioquia'),
('CUN', 'Cundinamarca'),
('VAL', 'Valle del Cauca'),
('ATL', 'Atlántico')
ON CONFLICT (codigo) DO NOTHING;

-- Insertar ciudades
INSERT INTO ciudades (codigo, nombre, departamento_codigo) VALUES 
('MED', 'Medellín', 'ANT'),
('BOG', 'Bogotá', 'CUN'),
('CAL', 'Cali', 'VAL'),
('BAQ', 'Barranquilla', 'ATL'),
('ENV', 'Envigado', 'ANT'),
('CHI', 'Chía', 'CUN')
ON CONFLICT (codigo) DO NOTHING;

-- Insertar materiales de ejemplo
INSERT INTO materiales (nombre, descripcion, tipo, precio, fecha_compra, fecha_venta, estado, ciudad_codigo) VALUES 
('Cemento Portland', 'Cemento de alta calidad para construcción', 'CONSTRUCCION', 25000.00, '2024-01-15', NULL, 'DISPONIBLE', 'MED'),
('Acero de refuerzo', 'Varillas de acero corrugado 12mm', 'CONSTRUCCION', 150000.00, '2024-02-10', '2024-03-15', 'ASIGNADO', 'BOG'),
('Computador portátil', 'Laptop HP Pavilion 15 pulgadas', 'TECNOLOGIA', 2500000.00, '2024-01-20', NULL, 'ACTIVO', 'CAL'),
('Mesa de oficina', 'Mesa ejecutiva de madera', 'MUEBLES', 450000.00, '2024-02-05', NULL, 'DISPONIBLE', 'BAQ'),
('Silla ergonómica', 'Silla de oficina con soporte lumbar', 'MUEBLES', 320000.00, '2024-01-30', '2024-02-28', 'ASIGNADO', 'ENV'),
('Impresora láser', 'Impresora HP LaserJet Pro', 'TECNOLOGIA', 800000.00, '2024-03-01', NULL, 'ACTIVO', 'CHI'),
('Ladrillos', 'Ladrillos cerámicos rojos', 'CONSTRUCCION', 1200.00, '2024-02-15', NULL, 'DISPONIBLE', 'MED'),
('Monitor 24 pulgadas', 'Monitor LED Full HD', 'TECNOLOGIA', 650000.00, '2024-01-25', NULL, 'DISPONIBLE', 'BOG')
ON CONFLICT DO NOTHING;
