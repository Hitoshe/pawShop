Вот полный и готовый файл README.md для твоего репозитория. Он включает все
технические особенности: Java 21, Docker (PostgreSQL 15), мультиязычность и
современный стек.

🐾 PawsStore - Premium Pet Supplies E-commerce

PawsStore is a robust, full-stack e-commerce platform built with Java 21 and
Spring Boot 3. It is designed for pet owners, providing a seamless shopping
experience with high-performance filtering, multi-language support, and a
containerized infrastructure.

🌟 Key Features

  - 🌍 Full Localization (i18n):
      - Dynamic switching between English and Russian.
      - UI translations managed via Spring Resource Bundles
        (messages.properties).
      - Product data (names, descriptions, specs) stored in both languages
        within the database.
  - 🐳 Dockerized Database:
      - Uses PostgreSQL 15 containerized via Docker Compose.
      - Persistent data storage using Docker volumes.
  - 🛒 Client-Side Shopping Cart:
      - Implemented using Vanilla JavaScript and localStorage.
      - Items are preserved even after the browser is closed or the page is
        refreshed.
  - 🌓 Theme Engine:
      - Native Dark/Light mode support with persistent user preference.
      - Smooth CSS transitions and modern UI components.
  - 🔍 Advanced Catalog System:
      - Dynamic server-side filtering (Price range, Minimum rating).
      - Flexible sorting (A-Z localized, Price, Popularity).
  - ⭐ Live Review System:
      - Users can submit ratings and comments.
      - The system automatically recalculates the product's average rating and
        review count in real-time.
  - 📱 Fully Responsive: Adaptive design optimized for devices from 375px
    to 1440px.

🛠 Tech Stack

  - Backend: Java 21, Spring Boot 3.x, Spring Data JPA.
  - Database: PostgreSQL 15 (Dockerized).
  - Frontend: Thymeleaf Template Engine, Modern CSS3 (Grid/Flexbox), Vanilla
    JavaScript (ES6+).
  - DevOps: Docker, Docker Compose, Maven.

📦 Getting Started

Prerequisites

  - JDK 21
  - Maven 3.9+
  - Docker & Docker Compose

1. Database Setup

The project uses Docker to manage the PostgreSQL database. Run the following
command to start the database container:

```
docker-compose up -d
```

Database Configuration (docker-compose.yml):

  - Image: postgres:15
  - Internal Port: 5432
  - External Port: 5436
  - Credentials: user / password
  - Database Name: pawsstore

2. Application Configuration

Ensure your src/main/resources/application.properties points to the Docker
container:
```
spring.datasource.url=jdbc:postgresql://localhost:5436/pawsstore
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```
3. Build and Run

# Install dependencies and build the JAR
```
mvn clean install
```

# Run the Spring Boot application
```
mvn spring-boot:run
```

Once started, the application will be available at: http://localhost:8080

📂 Project Structure

  - src/main/java/.../config/ — Localization (i18n) setup and Database
    auto-seeding.
  - src/main/java/.../controller/ — MVC Controllers for catalog and review
    logic.
  - src/main/java/.../model/ — JPA Entities (Product, Review) with dual-language
    fields.
  - src/main/java/.../repository/ — Data Access Layer with custom JPQL
    filtering.
  - src/main/resources/messages.properties — UI translation bundles (EN/RU).
  - src/main/resources/static/ — Assets (CSS, JS, Images).
  - src/main/resources/templates/ — Thymeleaf HTML templates.
  - docker-compose.yml — PostgreSQL 15 infrastructure.

🌍 Localization (i18n) Logic

The project handles localization at two levels:

1.  Static Content: Titles, buttons, and labels are pulled from
    messages.properties (English) or messages_ru.properties (Russian) based on
    the user's locale.
2.  Dynamic Content: The Product entity contains separate fields (e.g., nameEn
    and nameRu). The frontend intelligently displays the correct field based on
    the active language.

📜 License

Distributed under the MIT License. See LICENSE for more information.

Developed with 🧡 for pet owners and modern engineering.
