insert into inbound_receipt (
    id,
    created_at,
    updated_at,
    status,
    product_id,
    receipt_type,
    quantity,
    supplier_id,
    receipt_status,
    unit_id,
    alternate_store_id)
values
(20001, now(), now(), 'CREATED', 8001, 'CUSTOMER_RETURN', 2000, '4a6b7165-2e61-4b35-9afb-5f576ee13049', 'CREATED', 70001, 70014),
(20002, now(), now(), 'CREATED', 8002, 'SUPPLIER_RETURN', 1500, '5b7c8266-3f72-5c46-8bfc-6g687ff24150', 'CONFIRMED', 70002, 70015),
(20003, now(), now(), 'CREATED', 8003, 'WAREHOUSE_TRANSFER', 1200, '6c8d9377-4g83-6d57-9cgd-7h798gg35261', 'CANCELLED', 70003, 70016),
(20004, now(), now(), 'CREATED', 8004, 'PRODUCTION_LINE_RETURN', 1800, '7d9ea488-5h94-7e68-adhe-8i809hh46372', 'COMPLETED', 70004, 70017),
(20005, now(), now(), 'CREATED', 8005, 'CUSTOMER_RETURN', 900, '8eafb599-6i05-8f79-beif-9j910ii57483', 'PARTIALLY_COMPLETED', 70005, 70018),
(20006, now(), now(), 'CREATED', 8006, 'SUPPLIER_RETURN', 2100, '9fb0c6aa-7j16-9g80-cfjg-0k021jj68594', 'IN_PROGRESS', 70006, 70019),
(20007, now(), now(), 'CREATED', 8007, 'CUSTOMER_RETURN', 1100, '0g1ad7bb-8k27-ah91-dgkh-1l132kk79605', 'WAITING_FOR_CONFIRMATION', 70007, 70020),
(20008, now(), now(), 'CREATED', 8008, 'SUPPLIER_RETURN', 1700, '1h2be8cc-9l38-bi02-ehli-2m243ll80716', 'WAITING_FOR_APPROVAL', 70008, 70011),
(20009, now(), now(), 'CREATED', 8009, 'CUSTOMER_RETURN', 1300, '2i3cf9dd-am49-cj13-fimj-3n354mm91827', 'WAITING_FOR_PICKING', 70009, 70012),
(20010, now(), now(), 'CREATED', 8010, 'SUPPLIER_RETURN', 1600, '3j4dg0ee-bn50-dk24-gjnk-4o465nna02938', 'WAITING_FOR_PACKING', 70010, 70013);