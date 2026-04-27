document.addEventListener('DOMContentLoaded', () => {
    const filterForm = document.getElementById('filterForm');
    const grid = document.getElementById('products-grid');
    const countLabel = document.getElementById('product-count');
    const sortBySelect = document.getElementById('sortBySelector');
    const directionSelect = document.getElementById('directionSelector');

    if (!filterForm) return;

    /**
     * ГЛАВНАЯ ФУНКЦИЯ ОБНОВЛЕНИЯ
     */
    async function updateCatalog() {
        const params = new URLSearchParams();

        // 1. Собираем данные из формы (Рейтинг и Цена)
        const formData = new FormData(filterForm);
        formData.forEach((value, key) => {
            if (value) {
                // Заменяем запятую на точку для бэкенда Java (Double)
                let cleanValue = value.toString().replace(',', '.');
                params.append(key, cleanValue);
            }
        });

        // 2. Добавляем данные сортировки
        if (sortBySelect) params.append('sortBy', sortBySelect.value);
        if (directionSelect) params.append('direction', directionSelect.value);

        const url = `/?${params.toString()}`;

        // Эффект загрузки (затемнение)
        grid.style.opacity = '0.5';

        try {
            const response = await fetch(url);
            const html = await response.text();

            // Парсим HTML и достаем только нужные куски
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            const newGrid = doc.getElementById('products-grid');
            const newCount = doc.getElementById('product-count');

            if (newGrid) grid.innerHTML = newGrid.innerHTML;
            if (newCount) countLabel.innerText = newCount.innerText;

            // Обновляем URL в браузере без перезагрузки
            window.history.pushState({}, '', url);
        } catch (e) {
            console.error("Ошибка при обновлении каталога:", e);
        } finally {
            grid.style.opacity = '1';
        }
    }

    /**
     * СЛУШАТЕЛИ СОБЫТИЙ
     */

    // Изменения в форме (чекбоксы, радио, ввод текста)
    filterForm.addEventListener('input', (e) => {
        if (e.target.type === 'text') {
            // Для текстовых полей цен делаем задержку 0.5 сек, чтобы не частить
            clearTimeout(window.filterTimer);
            window.filterTimer = setTimeout(updateCatalog, 500);
        } else {
            // Для радио-кнопок рейтинга обновляем мгновенно
            updateCatalog();
        }
    });

    // Изменения в выпадающих списках сортировки
    if (sortBySelect) sortBySelect.addEventListener('change', updateCatalog);
    if (directionSelect) directionSelect.addEventListener('change', updateCatalog);
});