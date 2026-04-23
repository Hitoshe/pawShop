package com.pawsstore.shop.repository;

import com.pawsstore.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Здесь уже есть методы findAll(), save(), deleteById() и т.д.
}