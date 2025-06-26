insert into users(id, created_at, updated_at, status, username, first_name, last_name)
values ('4a6b7165-2e61-4b35-9afb-5f576ee13049', now(), now(), 'CREATED', 'bingo', 'admin', 'admin');

truncate table default_permission cascade;
INSERT INTO default_permission(name, description)
VALUES
-- Address permissions
('ADDRESS_CREATE', 'Create address permission'),
('ADDRESS_UPDATE', 'Update address permission'),
('ADDRESS_GET', 'Get address permission'),
('ADDRESS_DELETE', 'Delete address permission'),

-- Characteristics permissions
('CHARACTERISTICS_CREATE', 'Create characteristics permission'),
('CHARACTERISTICS_GET', 'Get characteristics permission'),
('CHARACTERISTICS_UPDATE', 'Update characteristics permission'),
('CHARACTERISTICS_DELETE', 'Delete characteristics permission'),

-- Product permissions
('PRODUCT_CREATE', 'Create product permission'),
('PRODUCT_GET', 'Get product permission'),
('PRODUCT_UPDATE', 'Update product permission'),
('PRODUCT_DELETE', 'Delete product permission'),
('PRODUCT_METADATA_CREATE', 'Create product metadata permission'),
('PRODUCT_METADATA_GET', 'Get product metadata permission'),
('PRODUCT_METADATA_UPDATE', 'Update product metadata permission'),
('PRODUCT_METADATA_DELETE', 'Delete product metadata permission'),

-- Rack permissions
('RACK_CREATE', 'Create rack permission'),
('RACK_GET', 'Get rack permission'),
('RACK_UPDATE', 'Update rack permission'),
('RACK_DELETE', 'Delete rack permission'),

-- Sector permissions
('SECTOR_CREATE', 'Create sector permission'),
('SECTOR_GET', 'Get sector permission'),
('SECTOR_UPDATE', 'Update sector permission'),
('SECTOR_DELETE', 'Delete sector permission'),

-- Setting permissions
('SETTING_CREATE', 'Create setting permission'),
('SETTING_GET', 'Get setting permission'),
('SETTING_UPDATE', 'Update setting permission'),
('SETTING_DELETE', 'Delete setting permission'),

-- Task permissions
('TASK_CREATE', 'Create task permission'),
('TASK_GET', 'Get task permission'),
('TASK_UPDATE', 'Update task permission'),
('TASK_DELETE', 'Delete task permission'),

-- Warehouse permissions
('WAREHOUSE_CREATE', 'Create warehouse permission'),
('WAREHOUSE_GET', 'Get warehouse permission'),
('WAREHOUSE_UPDATE', 'Update warehouse permission'),
('WAREHOUSE_DELETE', 'Delete warehouse permission'),

-- Unit Conversion permissions
('CONVERSION_ADD', 'Add unit conversion permission'),
('CONVERSION_UPDATE', 'Update unit conversion permission'),
('CONVERSION_DELETE', 'Delete unit conversion permission'),
('CONVERSION_GET_BY_FROM_ID_AND_TO_ID', 'Get unit conversion by from and to IDs permission'),
('CONVERSION_GET_BY_MAIN_UNIT_ID', 'Get unit conversion by main unit ID permission'),

-- Inbound Receipt permissions
('INBOUND_RECEIPT_DELETE', 'Delete inbound receipt permission'),
('INBOUND_RECEIPT_UPDATE', 'Update inbound receipt permission'),
('INBOUND_RECEIPT_CREATE', 'Create inbound receipt permission'),
('INBOUND_RECEIPT_GET', 'Get inbound receipt permission'),

-- Unit permissions
('UNIT_CREATE', 'Create unit permission'),
('UNIT_GET', 'Get unit permission'),
('UNIT_UPDATE', 'Update unit permission'),
('UNIT_DELETE', 'Delete unit permission'),

-- User permissions
('USER_READ', 'Read user permission'),
('USER_CREATE', 'Create user permission'),
('USER_UPDATE', 'Update user permission'),
('USER_DELETE', 'Delete user permission'),

-- Audit permissions
('PERFORM_AUDIT', 'Perform audit permission'),

-- Inventory permissions
('INVENTORY_READ', 'Read inventory permission'),
('INVENTORY_CREATE', 'Create inventory permission'),
('INVENTORY_UPDATE', 'Update inventory permission'),

-- Transport Label permissions
('GENERATE_TRANSPORT_LABEL', 'Generate transport label permission'),
('TRANSPORT_LABEL_READ', 'Read transport label permission'),
('UPDATE_TRANSPORT_LABEL', 'Update transport label permission'),
('DELETE_TRANSPORT_LABEL', 'Delete transport label permission'),

-- Product Return permissions
('PRODUCT_RETURN_READ', 'Read product return permission'),
('PRODUCT_RETURN_CREATE', 'Create product return permission'),
('PRODUCT_RETURN_UPDATE', 'Update product return permission'),
('PRODUCT_RETURN_DELETE', 'Delete product return permission'),

-- Cross Docking permissions
('CROSS_DOCKING_CREATE', 'Create cross docking permission'),
('CROSS_DOCKING_GET', 'Get cross docking permission'),
('CROSS_DOCKING_UPDATE', 'Update cross docking permission'),
('CROSS_DOCKING_DELETE', 'Delete cross docking permission'),

-- Inventory Snapshot permissions
('INVENTORY_SNAPSHOT_CREATE', 'Create inventory snapshot permission'),
('INVENTORY_SNAPSHOT_GET', 'Get inventory snapshot permission'),
('INVENTORY_SNAPSHOT_UPDATE', 'Update inventory snapshot permission'),
('INVENTORY_SNAPSHOT_DELETE', 'Delete inventory snapshot permission'),

-- Location permissions
('LOCATION_CREATE', 'Create location permission'),
('LOCATION_GET', 'Get location permission'),
('LOCATION_UPDATE', 'Update location permission'),
('LOCATION_DELETE', 'Delete location permission'),

-- Sector Characteristics permissions
('SECTOR_CHARACTERISTICS_CREATE', 'Create sector characteristics permission'),
('SECTOR_CHARACTERISTICS_GET', 'Get sector characteristics permission'),
('SECTOR_CHARACTERISTICS_UPDATE', 'Update sector characteristics permission'),
('SECTOR_CHARACTERISTICS_DELETE', 'Delete sector characteristics permission'),

-- Printer Setting permissions
('WMS_PRINTER_SETTING_CREATE', 'Create printer setting permission'),
('WMS_PRINTER_SETTING_READ', 'Read printer setting permission'),
('WMS_PRINTER_SETTING_UPDATE', 'Update printer setting permission'),
('WMS_PRINTER_SETTING_DELETE', 'Delete printer setting permission'),

-- Department permissions
('WMS_DEPARTMENT_CREATE', 'Create department permission'),
('WMS_DEPARTMENT_READ', 'Read department permission'),
('WMS_DEPARTMENT_UPDATE', 'Update department permission'),
('WMS_DEPARTMENT_DELETE', 'Delete department permission');

truncate table user_permissions cascade;
insert into user_permissions (id, created_at, updated_at, status, created_by, updated_by, user_id, permission)
select row_number() over (),
       now(),
       now(),
       'CREATED',
       1,
       1,
       '4a6b7165-2e61-4b35-9afb-5f576ee13049',
       name
from default_permission;

-- increment user_permission_sequence
SELECT setval('user_permission_seq', (SELECT COALESCE(MAX(id), 1) FROM "user_permissions"));

