insert into unit_conversion(
    id,
    created_at,
    updated_at,
    status,
    base_unit_id,
    alternative_unit_id,
    base_conversion_factor,
    alternative_conversion_factor,
    product_id)
VALUES
    (80001, now(), now(), 'CREATED', 70016, 70014, 48, 1, 8002),
    (80002, now(), now(), 'CREATED', 70017, 70015, 24, 1, 8003),
    (80003, now(), now(), 'CREATED', 70018, 70016, 12, 1, 8004),
    (80004, now(), now(), 'CREATED', 70019, 70017, 6, 1, 8005),
    (80005, now(), now(), 'CREATED', 70020, 70018, 3, 1, 8006),
    (80006, now(), now(), 'CREATED', 70011, 70019, 2, 1, 8007),
    (80007, now(), now(), 'CREATED', 70012, 70020, 3, 1, 8008),
    (80008, now(), now(), 'CREATED', 70013, 70001, 5, 1, 8009),
    (80009, now(), now(), 'CREATED', 70004, 70002, 20, 1, 8010),
    (80010, now(), now(), 'CREATED', 70005, 70003, 16, 1, 8011);