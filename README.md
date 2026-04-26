# 🐾 PawsStore - Premium Pet Supplies Platform

PawsStore is a full-stack e-commerce web application designed for pet lovers. Built with **Java Spring Boot**, **Thymeleaf**, and **Vanilla JavaScript**, it offers a seamless shopping experience with a focus on performance, accessibility, and modern UI/UX principles.

## 🌟 Key Features

*   **🛒 Persistent Shopping Cart**: Items remain in the cart even after page refreshes or closing the browser, powered by `localStorage`.
*   **🌍 Multi-language Support (i18n)**: Toggle between English and Russian. The system dynamically serves both UI text and database content (product names/descriptions) in the selected language.
*   **🌓 Smart Dark Mode**: A theme switcher that remembers user preference across sessions.
*   **🔍 Dynamic Catalog & Filtering**:
    *   Filter by price range and minimum rating.
    *   Sort by name (A-Z), price, or rating.
    *   Server-side filtering using custom JPQL queries.
*   **⭐ Interactive Reviews**: Users can post reviews and ratings. The system automatically recalculates the product's average rating and total review count in real-time.
*   **📱 Fully Responsive**: Optimized for a wide range of devices, from mobile phones (375px) to ultra-wide monitors (1440px+).
*   **🔥 Hot Deals Section**: A dedicated area for discounted products identified by price comparisons in the database.

## 🛠 Tech Stack

*   **Backend**: Java 17, Spring Boot 3.x, Spring Data JPA.
*   **Database**: H2 (In-memory for development) / Easily switchable to PostgreSQL/MySQL.
*   **Frontend**: Thymeleaf (Template Engine), Modern CSS (Flexbox & Grid), Vanilla JavaScript.
*   **Security & Logic**: LocaleChangeInterceptors, CookieLocaleResolvers.

## 🚀 Getting Started

### Prerequisites
*   JDK 17 or higher
*   Maven 3.6+

### Installation & Run
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/pawsstore.git
    ```
2.  **Navigate to project folder**:
    ```bash
    cd pawsstore
    ```
3.  **Build the project**:
    ```bash
    mvn clean install
    ```
4.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
5.  **Access the store**: Open `http://localhost:8080` in your browser.

## 📂 Project Structure

*   `src/main/java/.../config/` — Localization settings and database auto-initialization.
*   `src/main/java/.../controller/` — MVC Controllers handling catalog navigation and review posts.
*   `src/main/java/.../model/` — JPA Entities (Product and Review).
*   `src/main/java/.../repository/` — Data access layer with custom filtering logic.
*   `src/main/resources/static/` — CSS, JavaScript, and asset files.
*   `src/main/resources/templates/` — Thymeleaf HTML templates and fragments.

## 📊 Database Initialization
The project includes a `DataInitializer` class that automatically seeds the database with premium pet products, images, and random customer reviews upon the first launch. This allows for immediate testing of sorting, filtering, and localized content.

## 📜 License
This project is open-source and available under the [MIT License](LICENSE).

---
Developed with 🧡 for pets and their owners.
