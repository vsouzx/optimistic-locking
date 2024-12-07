package br.com.souza.optimistic_locking.database.repository;

import br.com.souza.optimistic_locking.database.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);
}
