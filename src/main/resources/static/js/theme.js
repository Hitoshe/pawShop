/**
 * ЛОГИКА ПЕРЕКЛЮЧЕНИЯ ТЕМЫ (DARK / LIGHT MODE)
 * Скрипт использует атрибут 'data-theme' на теге <body>, который
 */
document.addEventListener('DOMContentLoaded', () => {
    // Находим кнопку переключения и основной элемент body
    const themeBtn = document.getElementById('theme-toggle');
    const body = document.body;

    /**
     * 1. ИНИЦИАЛИЗАЦИЯ ПРИ ЗАГРУЗКЕ
     * Достаем сохраненную тему из localStorage (локальная память браузера).
     * Если там ничего нет (первый вход), по умолчанию ставим 'light'.
     */
    const savedTheme = localStorage.getItem('paws_theme') || 'light';

    // Применяем сохраненную тему сразу при загрузке страницы
    if (savedTheme === 'dark') {
        // Устанавливаем специальный атрибут, который заставит CSS применить темные цвета
        body.setAttribute('data-theme', 'dark');
        // Меняем иконку на кнопке на Солнце
        if (themeBtn) themeBtn.innerText = '☀️';
    } else {
        // Убираем атрибут (возврат к стандартным CSS стилям)
        body.removeAttribute('data-theme');
        // Ставим иконку Луны для светлой темы
        if (themeBtn) themeBtn.innerText = '🌙';
    }

    /**
     * 2. ЛОГИКА НАЖАТИЯ НА КНОПКУ (TOGGLE)
     * Слушатель событий срабатывает каждый раз, когда пользователь кликает по кнопке.
     */
    if (themeBtn) {
        themeBtn.addEventListener('click', () => {
            // Проверяем текущее состояние темы
            const currentTheme = body.getAttribute('data-theme');

            if (currentTheme === 'dark') {
                /**
                 * ПЕРЕКЛЮЧЕНИЕ НА СВЕТЛУЮ ТЕМУ
                 */
                body.removeAttribute('data-theme');             // Убираем атрибут из HTML
                localStorage.setItem('paws_theme', 'light');    // Запоминаем выбор в браузере
                themeBtn.innerText = '🌙';                       // Меняем иконку
            } else {
                /**
                 * ПЕРЕКЛЮЧЕНИЕ НА ТЕМНУЮ ТЕМУ
                 */
                body.setAttribute('data-theme', 'dark');        // Добавляем атрибут [data-theme='dark']
                localStorage.setItem('paws_theme', 'dark');     // Запоминаем выбор
                themeBtn.innerText = '☀️';                       // Меняем иконку
            }
        });
    }
});