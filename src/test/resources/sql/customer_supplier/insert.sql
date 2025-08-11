insert into customer_supplier (id, created_at, updated_at, status, created_by, updated_by, username, first_name, last_name, phone, user_type, partner_category)
values ('1-id', now(), now(), 'CREATED', 'system', 'system', 'jdoe', 'John', 'Doe', '+1234567890', 'CUSTOMER', 'ONE_TIME'),
       ('2-id', now(), now(), 'CREATED', 'system', 'system', 'asmith', 'Alice', 'Smith', '+0987654321', 'SUPPLIER', 'PHARMACY'),
       ('3-id', now(), now(), 'CREATED', 'system', 'system', 'bwhite', 'Bob', 'White', '+1122334455', 'CUSTOMER', 'ONE_TIME'),
       ('4-id', now(), now(), 'CREATED', 'system', 'system', 'cblack', 'Charlie', 'Black', '+5566778899', 'SUPPLIER', 'PHARMACY'),
       ('5-id', now(), now(), 'CREATED', 'system', 'system', 'dgreen', 'Diana', 'Green', '+9988776655', 'CUSTOMER', 'ONE_TIME');