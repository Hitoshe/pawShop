
-- psql -f src/main/resources/db/seed/insert_products.sql


TRUNCATE TABLE products RESTART IDENTITY CASCADE;

INSERT INTO products (title, description, price, rating, images, stock_quantity)
VALUES
    -- ========== КОШКИ ==========
    (
        'Кошачий корм Royal Canin Adult',
        'Полнорационный сухой корм для взрослых кошек. Курица, рис, таурин. 2 кг.',
        1899.00, 4.75,
        '["https://picsum.photos/seed/rc1/600/400","https://picsum.photos/seed/rc2/600/400","https://picsum.photos/seed/rc3/600/400"]'::jsonb,
        45
    ),
    (
        'Влажный корм Whiskas с говядиной',
        'Паучи 85 г, 12 штук. Нежное мясо в соусе для взрослых кошек.',
        549.00, 4.20,
        '["https://picsum.photos/seed/wh1/600/400","https://picsum.photos/seed/wh2/600/400","https://picsum.photos/seed/wh3/600/400"]'::jsonb,
        120
    ),
    (
        'Лежанка для кошек с подогревом',
        'Мягкая лежанка 50x40 см. Чехол съёмный, машинная стирка.',
        2499.00, 4.90,
        '["https://picsum.photos/seed/bed1/600/400","https://picsum.photos/seed/bed2/600/400","https://picsum.photos/seed/bed3/600/400"]'::jsonb,
        15
    ),
    (
        'Игрушка «Мышка» на верёвке',
        'Интерактивная игрушка для кошек. Натуральный мех, звонкий бубенчик.',
        249.00, 4.50,
        '["https://picsum.photos/seed/mouse1/600/400","https://picsum.photos/seed/mouse2/600/400","https://picsum.photos/seed/mouse3/600/400"]'::jsonb,
        200
    ),
    (
        'Наполнитель для кошачьего туалета',
        'Комкующийся бентонит, 5 кг. Без запаха, экономичный.',
        799.00, 4.30,
        '["https://picsum.photos/seed/litter1/600/400","https://picsum.photos/seed/litter2/600/400","https://picsum.photos/seed/litter3/600/400"]'::jsonb,
        80
    ),
    (
        'Когтеточка-столбик 60 см',
        'Джутовая верёвка, устойчивое основание. Для средних и крупных кошек.',
        1199.00, 4.60,
        '["https://picsum.photos/seed/scratch1/600/400","https://picsum.photos/seed/scratch2/600/400","https://picsum.photos/seed/scratch3/600/400"]'::jsonb,
        30
    ),
    (
        'Переноска для кошек 45x30x30',
        'Пластиковая переноска с металлической дверцей. До 7 кг.',
        1599.00, 4.40,
        '["https://picsum.photos/seed/carrier1/600/400","https://picsum.photos/seed/carrier2/600/400","https://picsum.photos/seed/carrier3/600/400"]'::jsonb,
        25
    ),
    (
        'Шампунь для кошек 250 мл',
        'Гипоаллергенный, без слёз. С алоэ вера.',
        449.00, 4.70,
        '["https://picsum.photos/seed/shampoo1/600/400","https://picsum.photos/seed/shampoo2/600/400","https://picsum.photos/seed/shampoo3/600/400"]'::jsonb,
        60
    ),

    -- ========== СОБАКИ ==========
    (
        'Сухой корм Pedigree для взрослых собак',
        'Полнорационный корм с говядиной и овощами. 3 кг.',
        1299.00, 4.30,
        '["https://picsum.photos/seed/ped1/600/400","https://picsum.photos/seed/ped2/600/400","https://picsum.photos/seed/ped3/600/400"]'::jsonb,
        55
    ),
    (
        'Игрушка «Мяч» резиновый',
        'Прочный мяч диаметром 7 см. Для активных игр.',
        349.00, 4.20,
        '["https://picsum.photos/seed/ball1/600/400","https://picsum.photos/seed/ball2/600/400","https://picsum.photos/seed/ball3/600/400"]'::jsonb,
        120
    ),
    (
        'Поводок-рулетка 5 м',
        'Нейлоновый поводок с тормозом. Для собак до 30 кг.',
        899.00, 4.60,
        '["https://picsum.photos/seed/leash1/600/400","https://picsum.photos/seed/leash2/600/400","https://picsum.photos/seed/leash3/600/400"]'::jsonb,
        0
    ),
    (
        'Ошейник кожаный с заклёпками',
        'Натуральная кожа, регулировка 35–50 см. Для средних пород.',
        749.00, 4.80,
        '["https://picsum.photos/seed/collar1/600/400","https://picsum.photos/seed/collar2/600/400","https://picsum.photos/seed/collar3/600/400"]'::jsonb,
        40
    ),
    (
        'Лежанка-домик для собак 60x50',
        'Мягкий домик с подушкой. Чехол снимается. Для мелких пород.',
        3299.00, 4.90,
        '["https://picsum.photos/seed/dogbed1/600/400","https://picsum.photos/seed/dogbed2/600/400","https://picsum.photos/seed/dogbed3/600/400"]'::jsonb,
        12
    ),
    (
        'Корм для щенков Hills 1.5 кг',
        'Специализированный корм для щенков до 1 года. С DHA.',
        2199.00, 4.85,
        '["https://picsum.photos/seed/hills1/600/400","https://picsum.photos/seed/hills2/600/400","https://picsum.photos/seed/hills3/600/400"]'::jsonb,
        28
    ),
    (
        'Лакомство «Костик» для собак',
        'Жевательная косточка из жил. 5 штук в упаковке.',
        599.00, 4.50,
        '["https://picsum.photos/seed/bone1/600/400","https://picsum.photos/seed/bone2/600/400","https://picsum.photos/seed/bone3/600/400"]'::jsonb,
        150
    ),
    (
        'Щётка для вычёсывания собак',
        'Пуходёрка с мягкой щетиной. Для длинной шерсти.',
        699.00, 4.40,
        '["https://picsum.photos/seed/brush1/600/400","https://picsum.photos/seed/brush2/600/400","https://picsum.photos/seed/brush3/600/400"]'::jsonb,
        90
    ),

    -- ========== ПТИЦЫ ==========
    (
        'Корм для попугаев Vitakraft 1 кг',
        'Зерновая смесь для средних попугаев. Просо, овёс, семена.',
        399.00, 4.40,
        '["https://picsum.photos/seed/parrot1/600/400","https://picsum.photos/seed/parrot2/600/400","https://picsum.photos/seed/parrot3/600/400"]'::jsonb,
        200
    ),
    (
        'Клетка для попугая 40x40x60',
        'Металлическая клетка с деревянными жердями. Дверца сверху.',
        3499.00, 4.30,
        '["https://picsum.photos/seed/cage1/600/400","https://picsum.photos/seed/cage2/600/400","https://picsum.photos/seed/cage3/600/400"]'::jsonb,
        10
    ),
    (
        'Игрушка-зеркальце для птиц',
        'Безопасное зеркальце с колокольчиком. Для мелких попугаев.',
        199.00, 4.20,
        '["https://picsum.photos/seed/mirror1/600/400","https://picsum.photos/seed/mirror2/600/400","https://picsum.photos/seed/mirror3/600/400"]'::jsonb,
        300
    ),
    (
        'Минеральный камень для птиц',
        'Источник кальция. Крепление в клетку.',
        149.00, 4.60,
        '["https://picsum.photos/seed/stone1/600/400","https://picsum.photos/seed/stone2/600/400","https://picsum.photos/seed/stone3/600/400"]'::jsonb,
        250
    ),
    (
        'Поилка для птиц 100 мл',
        'Автоматическая поилка. Прозрачный пластик.',
        299.00, 4.10,
        '["https://picsum.photos/seed/drinker1/600/400","https://picsum.photos/seed/drinker2/600/400","https://picsum.photos/seed/drinker3/600/400"]'::jsonb,
        180
    ),

    -- ========== РЫБЫ ==========
    (
        'Аквариум 30 литров с фильтром',
        'Стеклянный аквариум с LED-подсветкой и фильтром. Для начинающих.',
        4999.00, 4.50,
        '["https://picsum.photos/seed/aqua1/600/400","https://picsum.photos/seed/aqua2/600/400","https://picsum.photos/seed/aqua3/600/400"]'::jsonb,
        8
    ),
    (
        'Корм для аквариумных рыб TetraMin',
        'Хлопья для всех видов рыб. 100 г.',
        599.00, 4.70,
        '["https://picsum.photos/seed/tetra1/600/400","https://picsum.photos/seed/tetra2/600/400","https://picsum.photos/seed/tetra3/600/400"]'::jsonb,
        100
    ),
    (
        'Фильтр для аквариума 200 л/ч',
        'Внутренний фильтр с аэрацией. Для аквариумов до 50 л.',
        1499.00, 4.40,
        '["https://picsum.photos/seed/filter1/600/400","https://picsum.photos/seed/filter2/600/400","https://picsum.photos/seed/filter3/600/400"]'::jsonb,
        35
    ),
    (
        'Термометр для аквариума',
        'Стеклянный термометр с присоской. 0–50 °C.',
        249.00, 4.30,
        '["https://picsum.photos/seed/thermo1/600/400","https://picsum.photos/seed/thermo2/600/400","https://picsum.photos/seed/thermo3/600/400"]'::jsonb,
        75
    ),
    (
        'Грунт для аквариума 2 кг',
        'Натуральная галька 3–5 мм. Не влияет на жёсткость.',
        399.00, 4.20,
        '["https://picsum.photos/seed/ground1/600/400","https://picsum.photos/seed/ground2/600/400","https://picsum.photos/seed/ground3/600/400"]'::jsonb,
        60
    ),
    (
        'Декоративный замок для аквариума',
        'Керамический декор 15 см. Безопасен для рыб.',
        899.00, 4.60,
        '["https://picsum.photos/seed/castle1/600/400","https://picsum.photos/seed/castle2/600/400","https://picsum.photos/seed/castle3/600/400"]'::jsonb,
        22
    ),
    (
        'Сачок для аквариумных рыб',
        'Мягкая сетка, ручка 30 см. Для пересадки рыб.',
        199.00, 4.10,
        '["https://picsum.photos/seed/net1/600/400","https://picsum.photos/seed/net2/600/400","https://picsum.photos/seed/net3/600/400"]'::jsonb,
        140
    ),

    -- ========== ГРЫЗУНЫ ==========
    (
        'Корм для хомяков 500 г',
        'Зерновая смесь с орехами. Для всех видов хомяков.',
        299.00, 4.50,
        '["https://picsum.photos/seed/hamster1/600/400","https://picsum.photos/seed/hamster2/600/400","https://picsum.photos/seed/hamster3/600/400"]'::jsonb,
        180
    ),
    (
        'Колесо для хомяка 16 см',
        'Бесшумное колесо. Безопасное покрытие.',
        899.00, 4.70,
        '["https://picsum.photos/seed/wheel1/600/400","https://picsum.photos/seed/wheel2/600/400","https://picsum.photos/seed/wheel3/600/400"]'::jsonb,
        45
    ),
    (
        'Домик для грызунов деревянный',
        'Деревянный домик 15x12x10 см. Для хомяков и мышей.',
        599.00, 4.40,
        '["https://picsum.photos/seed/house1/600/400","https://picsum.photos/seed/house2/600/400","https://picsum.photos/seed/house3/600/400"]'::jsonb,
        30
    ),
    (
        'Наполнитель для грызунов 2 кг',
        'Древесные опилки без пыли. Впитывающий.',
        249.00, 4.20,
        '["https://picsum.photos/seed/sawdust1/600/400","https://picsum.photos/seed/sawdust2/600/400","https://picsum.photos/seed/sawdust3/600/400"]'::jsonb,
        220
    );