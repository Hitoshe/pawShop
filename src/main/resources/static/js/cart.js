/**
 * ГЛОБАЛЬНАЯ ПЕРЕМЕННАЯ КОРЗИНЫ
 * Пытаемся достать данные из localStorage браузера под ключом 'paws_cart'.
 * Если данных нет (первый визит), создаем пустой массив [].
 * JSON.parse превращает строку из хранилища обратно в массив объектов JS.
 */
let cart = JSON.parse(localStorage.getItem('paws_cart')) || [];

/**
 * ФУНКЦИЯ ДОБАВЛЕНИЯ ТОВАРА
 * @param {number} id - уникальный ID товара из базы данных
 * @param {string} name - название товара (уже локализованное)
 * @param {number} price - цена товара
 * @param {string} img - путь к изображению товара
 */
function addToCart(id, name, price, img) {
    // Пытаемся найти поле выбора количества на странице товара
    const qtyInput = document.getElementById('qnt');
    // Если поле есть — берем значение, если нет (например, кнопка в каталоге) — считаем за 1
    const quantity = qtyInput ? parseInt(qtyInput.value) : 1;

    // Ищем, есть ли уже такой товар в корзине
    const existingItem = cart.find(item => item.id === id);

    if (existingItem) {
        // Если товар найден, просто увеличиваем его количество
        existingItem.quantity += quantity;
    } else {
        // Если товара нет, добавляем новый объект в массив корзины
        cart.push({
            id: id,
            name: name,
            price: parseFloat(price),
            img: img,
            quantity: quantity
        });
    }

    // Сохраняем изменения в локальную память браузера
    saveCart();
    // Обновляем цифру на иконке корзины в шапке
    updateBadge();

    // Вывод уведомления пользователю (на основе языка страницы)
    const msg = document.documentElement.lang === 'ru' ? 'Добавлено!' : 'Added to cart!';
    alert(msg);
}

/**
 * СОХРАНЕНИЕ В LOCAL STORAGE
 * localStorage поддерживает только строки, поэтому превращаем массив объектов
 * в строку формата JSON с помощью JSON.stringify.
 */
function saveCart() {
    localStorage.setItem('paws_cart', JSON.stringify(cart));
}

/**
 * ОБНОВЛЕНИЕ ИКОНКИ КОРЗИНЫ
 * Рассчитывает общее количество товаров и показывает его в кружке (badge).
 */
function updateBadge() {
    const badge = document.getElementById('cart-badge');
    if (badge) {
        // Метод reduce суммирует поле quantity всех объектов в массиве корзины
        const total = cart.reduce((sum, item) => sum + item.quantity, 0);

        // Устанавливаем число внутрь кружка
        badge.innerText = total;

        // Если товаров 0 — скрываем кружок, если больше 0 — показываем (flex)
        badge.style.display = total > 0 ? 'flex' : 'none';
    }
}

/**
 * ЗАПУСК ПРИ ЗАГРУЗКЕ
 * Как только HTML-документ полностью загружен, вызываем обновление иконки,
 * чтобы пользователь сразу увидел состояние своей корзины.
 */
document.addEventListener('DOMContentLoaded', updateBadge);