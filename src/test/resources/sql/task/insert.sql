insert into task (id, created_at, updated_at, status, name, task_type, task_status, assigned_to, due_date, product_id) values
(566951, now(), now(), 'CREATED', 'Task shipment 1', 'PICK', 'IN_PROGRESS', '4a6b7165-2e61-4b35-9afb-5f576ee13049', now() + interval '1 day', 8001),
(566952, now(), now(), 'CREATED', 'Task shipment 2', 'RECEIVE', 'PENDING', '5b7c8266-3f72-5c46-8bfc-6g687ff24150', now() + interval '2 day', 8001),
(566953, now(), now(), 'CREATED', 'Task shipment 3', 'SEND', 'COMPLETED', '6c8d9377-4g83-6d57-9cgd-7h798gg35261', now() + interval '3 day', 8001),
(566954, now(), now(), 'CREATED', 'Task shipment 4', 'PACK', 'IN_PROGRESS', '7d9ea488-5h94-7e68-adhe-8i809hh46372', now() + interval '4 day', 8001),
(566955, now(), now(), 'CREATED', 'Task shipment 5', 'SHIP', 'PENDING', '8eafb599-6i05-8f79-beif-9j910ii57483', now() + interval '5 day', 8001),
(566956, now(), now(), 'CREATED', 'Task shipment 6', 'PICK', 'COMPLETED', '9fb0c6aa-7j16-9g80-cfjg-0k021jj68594', now() + interval '6 day', 8001),
(566957, now(), now(), 'CREATED', 'Task shipment 7', 'RECEIVE', 'IN_PROGRESS', '0g1ad7bb-8k27-ah91-dgkh-1l132kk79605', now() + interval '7 day', 8001),
(566958, now(), now(), 'CREATED', 'Task shipment 8', 'SEND', 'PENDING', '1h2be8cc-9l38-bi02-ehli-2m243ll80716', now() + interval '8 day', 8001),
(566959, now(), now(), 'CREATED', 'Task shipment 9', 'PACK', 'COMPLETED', '2i3cf9dd-am49-cj13-fimj-3n354mm91827', now() + interval '9 day', 8001),
(566960, now(), now(), 'CREATED', 'Task shipment 10', 'SHIP', 'IN_PROGRESS', '3j4dg0ee-bn50-dk24-gjnk-4o465nna02938', now() + interval '10 day', 8001);