/**
 * ГЛОБАЛЬНЫЕ ЭЛЕМЕНТЫ ИНТЕРФЕЙСА
 */
const itemsContainer = document.getElementById('cart-items');
const subtotalEl = document.getElementById('subtotal');
const taxEl = document.getElementById('tax-val');             // Добавили для налога
const totalEl = document.getElementById('final-total');
const discountRow = document.getElementById('discount-row');
const discountValEl = document.getElementById('discount-val');

// Новые элементы для счетчиков и баннера
const readyCountEl = document.getElementById('ready-count');
const summaryCountEl = document.getElementById('summary-count');
const shippingBanner = document.getElementById('shipping-banner');

let isDiscountApplied = false;

/**
 * 1. ОТРИСОВКА СПИСКА ТОВАРОВ
 */
function renderCart() {
    if (!itemsContainer) return;

    itemsContainer.innerHTML = '';

    if (cart.length === 0) {
        itemsContainer.innerHTML = `
            <div class="cart-empty" style="text-align: center; padding: 40px; grid-column: span 2;">
                <h2>${document.documentElement.lang === 'ru' ? 'Ваша корзина пуста' : 'Your bag is empty'}</h2>
                <a href="/" class="btn-primary" style="display:inline-block; margin-top:20px; text-decoration:none;">
                   ${document.documentElement.lang === 'ru' ? 'Вернуться в магазин' : 'Continue Shopping'}
                </a>
            </div>`;
        updateSummary();
        return;
    }

    cart.forEach((item, index) => {
        // Расчет цены для конкретной позиции
        const itemTotal = (item.price * item.quantity).toFixed(2);

        itemsContainer.innerHTML += `
            <div class="cart-item">
                <img src="${item.img}" alt="${item.name}">
                <div class="item-details">
                    <h4>${item.name}</h4>
                    <p style="color: #999; font-size: 13px;">${document.documentElement.lang === 'ru' ? 'Товары для животных' : 'Pet Supplies'}</p>
                </div>
                <div class="item-qty-controls">
                    <button onclick="updateQty(${index}, -1)">-</button>
                    <span>${item.quantity}</span>
                    <button onclick="updateQty(${index}, 1)">+</button>
                </div>
                <!-- Блок цены: общая сумма и цена за штуку -->
                <div class="item-price-block" style="text-align: right; padding: 0 20px; min-width: 120px;">
                    <span style="display: block; font-size: 18px; font-weight: 800; color: #FF6A00;">$${itemTotal}</span>
                    <span style="font-size: 12px; color: #999;">$${item.price.toFixed(2)} ${document.documentElement.lang === 'ru' ? 'каждый' : 'each'}</span>
                </div>
                <button class="remove-btn" onclick="removeItem(${index})">🗑️</button>
            </div>`;
    });

    updateSummary();
}

/**
 * 2. ИЗМЕНЕНИЕ КОЛИЧЕСТВА
 */
function updateQty(idx, delta) {
    cart[idx].quantity += delta;
    if (cart[idx].quantity < 1) cart[idx].quantity = 1;

    saveCart();
    updateBadge();
    renderCart();
}

/**
 * 3. УДАЛЕНИЕ ТОВАРА
 */
function removeItem(idx) {
    cart.splice(idx, 1);
    saveCart();
    updateBadge();
    renderCart();
}

/**
 * 4. РАСЧЕТ ИТОГОВ (ДОБАВЛЕН НАЛОГ И СЧЕТЧИКИ)
 */
function updateSummary() {
    const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    // Считаем скидку
    let discount = 0;
    if (isDiscountApplied) {
        discount = subtotal * 0.1;
        if (discountValEl) discountValEl.innerText = `-$${discount.toFixed(2)}`;
        if (discountRow) discountRow.style.display = 'flex';
    } else {
        if (discountRow) discountRow.style.display = 'none';
    }

    // РАСЧЕТ НАЛОГА 8%
    const tax = (subtotal - discount) * 0.08;
    const finalTotal = subtotal - discount + tax;

    // Вывод цен
    if (subtotalEl) subtotalEl.innerText = `$${subtotal.toFixed(2)}`;
    if (taxEl) taxEl.innerText = `$${tax.toFixed(2)}`; // Поле налога
    if (totalEl) totalEl.innerText = `$${finalTotal.toFixed(2)}`;

    // ОБНОВЛЕНИЕ СЧЕТЧИКОВ ТОВАРОВ
    const totalQty = cart.reduce((sum, item) => sum + item.quantity, 0);
    const itemWord = document.documentElement.lang === 'ru' ? 'товар(ов)' : (totalQty === 1 ? 'item' : 'items');

    if (readyCountEl) readyCountEl.innerText = `${totalQty} ${itemWord} ${document.documentElement.lang === 'ru' ? 'готово к оплате' : 'ready for checkout'}`;
    if (summaryCountEl) summaryCountEl.innerText = `${totalQty} ${itemWord} ${document.documentElement.lang === 'ru' ? 'в вашей корзине' : 'in your bag'}`;

    // БАННЕР ДОСТАВКИ (Qualified если > $50)
    if (shippingBanner) {
        shippingBanner.style.display = subtotal >= 50 ? 'flex' : 'none';
    }
}

/**
 * 5. ПРИМЕНЕНИЕ ПРОМОКОДА
 */
function applyPromo() {
    const input = document.getElementById('promo-input');
    if (input && input.value.trim().toUpperCase() === 'SAVE10') {
        isDiscountApplied = true;
        alert(document.documentElement.lang === 'ru' ? 'Промокод применен!' : 'Promo code applied!');
        renderCart();
    } else {
        alert(document.documentElement.lang === 'ru' ? 'Неверный код' : 'Invalid code');
    }
}

/**
 * 6. ОФОРМЛЕНИЕ ЗАКАЗА
 */
function checkout() {
    if (cart.length === 0) return;
    alert(document.documentElement.lang === 'ru' ? 'Спасибо за заказ!' : 'Thank you for your order!');
    cart = [];
    saveCart();
    updateBadge();
    window.location.href = '/';
}

/**
 * ИНИЦИАЛИЗАЦИЯ
 */
document.addEventListener('DOMContentLoaded', renderCart);