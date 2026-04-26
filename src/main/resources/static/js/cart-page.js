/**
 * ГЛОБАЛЬНЫЕ ЭЛЕМЕНТЫ ИНТЕРФЕЙСА
 * Выбираем элементы из cart.html, куда мы будем вставлять данные.
 */
const itemsContainer = document.getElementById('cart-items'); // Контейнер для списка товаров
const subtotalEl = document.getElementById('subtotal');       // Поле "Промежуточный итог"
const totalEl = document.getElementById('final-total');       // Поле "Итоговая сумма"
const discountRow = document.getElementById('discount-row');   // Строка с информацией о скидке (скрыта по умолчанию)
const discountValEl = document.getElementById('discount-val'); // Значение скидки в долларах

// Состояние промокода (действует только пока открыта страница)
let isDiscountApplied = false;

/**
 * 1. ОТРИСОВКА СПИСКА ТОВАРОВ
 * Основная функция, которая строит HTML-код корзины на основе массива 'cart'.
 */
function renderCart() {
    // Если мы не на странице корзины (контейнер не найден), выходим из функции
    if (!itemsContainer) return;

    // Очищаем текущий контент перед новой отрисовкой
    itemsContainer.innerHTML = '';

    // ПРОВЕРКА: Если корзина пуста
    if (cart.length === 0) {
        itemsContainer.innerHTML = `
            <div class="cart-empty" style="text-align: center; padding: 40px;">
                <h2>${document.documentElement.lang === 'ru' ? 'Ваша корзина пуста' : 'Your bag is empty'}</h2>
                <a href="/" class="btn-primary" style="display:inline-block; margin-top:20px; text-decoration:none;">
                   ${document.documentElement.lang === 'ru' ? 'Вернуться в магазин' : 'Continue Shopping'}
                </a>
            </div>`;
        updateSummary(); // Обнуляем итоги
        return;
    }

    // ГЕНЕРАЦИЯ КАРТОЧЕК ТОВАРОВ
    // Проходим по каждому элементу массива cart
    cart.forEach((item, index) => {
        itemsContainer.innerHTML += `
            <div class="cart-item">
                <img src="${item.img}" alt="${item.name}">
                <div class="item-details">
                    <h4>${item.name}</h4>
                    <p class="item-price">$${item.price.toFixed(2)}</p>
                </div>
                <!-- Управление количеством -->
                <div class="item-qty-controls">
                    <button onclick="updateQty(${index}, -1)">-</button>
                    <span>${item.quantity}</span>
                    <button onclick="updateQty(${index}, 1)">+</button>
                </div>
                <!-- Кнопка удаления (иконка корзины) -->
                <button class="remove-btn" onclick="removeItem(${index})">🗑️</button>
            </div>`;
    });

    // После отрисовки списка обновляем итоговые цифры (сумму)
    updateSummary();
}

/**
 * 2. ИЗМЕНЕНИЕ КОЛИЧЕСТВА (+ / -)
 * @param {number} idx - индекс товара в массиве cart
 * @param {number} delta - на сколько изменить (1 или -1)
 */
function updateQty(idx, delta) {
    cart[idx].quantity += delta;

    // Не позволяем количеству быть меньше 1
    if (cart[idx].quantity < 1) cart[idx].quantity = 1;

    saveCart();    // Сохраняем в localStorage (функция из cart.js)
    updateBadge(); // Обновляем кружок в шапке (функция из cart.js)
    renderCart();  // Перерисовываем список на странице
}

/**
 * 3. УДАЛЕНИЕ ТОВАРА
 * @param {number} idx - индекс удаляемого элемента
 */
function removeItem(idx) {
    // Удаляем 1 элемент по указанному индексу
    cart.splice(idx, 1);

    saveCart();    // Сохраняем изменения
    updateBadge(); // Обновляем кружок в шапке
    renderCart();  // Перерисовываем корзину
}

/**
 * 4. РАСЧЕТ ИТОГОВ И СКИДКИ
 * Считает общую сумму всех товаров и применяет промокод, если он есть.
 */
function updateSummary() {
    // Считаем сумму: цена каждого товара * его количество
    const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    let finalTotal = subtotal;

    // Логика скидки
    if (isDiscountApplied) {
        const discount = subtotal * 0.1; // Фиксированная скидка 10%
        finalTotal = subtotal - discount;

        // Показываем строку скидки и записываем значение
        if (discountValEl) discountValEl.innerText = `-$${discount.toFixed(2)}`;
        if (discountRow) discountRow.style.display = 'flex';
    } else {
        // Скрываем строку скидки, если промокод не введен
        if (discountRow) discountRow.style.display = 'none';
    }

    // Выводим данные в соответствующие поля HTML
    if (subtotalEl) subtotalEl.innerText = `$${subtotal.toFixed(2)}`;
    if (totalEl) totalEl.innerText = `$${finalTotal.toFixed(2)}`;
}

/**
 * 5. ПРИМЕНЕНИЕ ПРОМОКОДА
 * Срабатывает при клике на кнопку "Apply" в корзине.
 */
function applyPromo() {
    const input = document.getElementById('promo-input');

    // Проверяем код (в данном случае жестко прописан 'SAVE10')
    if (input.value.trim().toUpperCase() === 'SAVE10') {
        isDiscountApplied = true;
        alert(document.documentElement.lang === 'ru' ? 'Промокод применен!' : 'Promo code applied!');
        renderCart(); // Перерисовываем, чтобы обновить итоговую сумму
    } else {
        alert(document.documentElement.lang === 'ru' ? 'Неверный код' : 'Invalid code');
    }
}

/**
 * 6. ОФОРМЛЕНИЕ ЗАКАЗА
 * Финальное действие: очистка данных и имитация покупки.
 */
function checkout() {
    if (cart.length === 0) return;

    alert(document.documentElement.lang === 'ru' ? 'Спасибо за заказ! Мы свяжемся с вами.' : 'Thank you for your order!');

    // Полностью очищаем массив корзины
    cart = [];
    saveCart();    // Перезаписываем пустую корзину в localStorage
    updateBadge(); // Обнуляем кружок в шапке

    // Редирект на главную страницу
    window.location.href = '/';
}

/**
 * ИНИЦИАЛИЗАЦИЯ
 * Запускаем отрисовку корзины сразу после того, как браузер загрузил HTML.
 */
document.addEventListener('DOMContentLoaded', renderCart);