package br.com.souza.optimistic_locking.service;

import br.com.souza.optimistic_locking.database.model.Product;
import br.com.souza.optimistic_locking.database.repository.IProductRepository;
import br.com.souza.optimistic_locking.dto.BuyProductRequest;
import br.com.souza.optimistic_locking.dto.NewProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;

    public void createNewProduct(NewProductRequest request) throws Exception {
        Product product = productRepository.findByName(request.getName()).orElse(null);
        if(product != null) throw new Exception("Already exists a product with this name");

        productRepository.save(Product.builder()
                .price(request.getPrice())
                .name(request.getName())
                .quantity(request.getQuantity())
                .build());
    }

    public Product getProductById(Integer id) throws Exception{
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new Exception(String.format("Product with id %s was not found", id));

        return product;
    }

    public String buyProduct(BuyProductRequest request) throws Exception{
        Product product = productRepository.findById(request.getId()).orElse(null);
        if(product == null) throw new Exception(String.format("Product with id %s was not found", request.getId()));

        if(product.getQuantity() < request.getDesiredQuantity()) throw new Exception("Product quantity is lower than requested");

        product.decreaseQuantity(request.getDesiredQuantity());

        productRepository.save(product);

        return String.format("%sx %s purchased successfully", request.getDesiredQuantity(), product.getName());
    }
}
