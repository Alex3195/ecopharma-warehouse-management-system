insert into outbound_shipment (
    id,
    created_at,
    updated_at,
    status,
    product_id,
    shipment_type,
    quantity,
    customer_id,
    scheduled_for,
    shipment_status)
values
    (50001, now(), now(), 'CREATED', 8001, 'CUSTOMER_SHIPMENT', 200, '4a6b7165-2e61-4b35-9afb-5f576ee13049', '2025-12-12', 'CREATED'),
    (50002, now(), now(), 'CREATED', 8002, 'SUPPLIER_SHIPMENT', 150, '5b7c8266-3f72-5c46-8bfc-6g687ff24150', '2025-12-13', 'PICKED'),
    (50003, now(), now(), 'CREATED', 8003, 'RETURN_TO_SUPPLIER', 100, '6c8d9377-4g83-6d57-9cgd-7h798gg35261', '2025-12-14', 'PACKED'),
    (50004, now(), now(), 'CREATED', 8004, 'RETURN_TO_CUSTOMER', 250, '7d9ea488-5h94-7e68-adhe-8i809hh46372', '2025-12-15', 'SHIPPED'),
    (50005, now(), now(), 'CREATED', 8005, 'WAREHOUSE_TRANSFER', 300, '8eafb599-6i05-8f79-beif-9j910ii57483', '2025-12-16', 'DELIVERED'),
    (50006, now(), now(), 'CREATED', 8006, 'PRODUCTION_LINE_SHIPMENT', 120, '9fb0c6aa-7j16-9g80-cfjg-0k021jj68594', '2025-12-17', 'CANCELLED'),
    (50007, now(), now(), 'CREATED', 8007, 'PRODUCTION_LINE_RETURN', 180, '0g1ad7bb-8k27-ah91-dgkh-1l132kk79605', '2025-12-18', 'RETURNED'),
    (50008, now(), now(), 'CREATED', 8008, 'CUSTOMER_SHIPMENT', 220, '1h2be8cc-9l38-bi02-ehli-2m243ll80716', '2025-12-19', 'PARTIALLY_PICKED'),
    (50009, now(), now(), 'CREATED', 8009, 'SUPPLIER_SHIPMENT', 160, '2i3cf9dd-am49-cj13-fimj-3n354mm91827', '2025-12-20', 'PARTIALLY_PACKED'),
    (50010, now(), now(), 'CREATED', 8010, 'RETURN_TO_SUPPLIER', 140, '3j4dg0ee-bn50-dk24-gjnk-4o465nna02938', '2025-12-21', 'PARTIALLY_SHIPPED');