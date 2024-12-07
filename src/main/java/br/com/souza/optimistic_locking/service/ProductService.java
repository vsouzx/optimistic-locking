package br.com.souza.optimistic_locking.service;

import br.com.souza.optimistic_locking.database.model.Product;
import br.com.souza.optimistic_locking.database.repository.IProductRepository;
import br.com.souza.optimistic_locking.dto.BuyProductRequest;
import br.com.souza.optimistic_locking.dto.NewProductRequest;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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

    public void buyProduct(BuyProductRequest request) throws Exception{
        int attempts = 0;

        while (attempts < 3) {
            try{
                process(request);
                return;
            }catch (OptimisticLockingFailureException e){
                System.out.println("Lock exception. Retrying.");
                attempts++;
                Thread.sleep(1000);
            } catch (Exception e){
                System.out.println("Error while buying product: " + e.getMessage());
                throw e;
            }
        }
        throw new Exception("Lock exception");
    }

    private void process(BuyProductRequest request) throws Exception {
        Product product = productRepository.findById(request.getId()).orElse(null);
        if(product == null) throw new Exception(String.format("Product with id %s was not found", request.getId()));

        if(product.getQuantity() < request.getDesiredQuantity()) throw new Exception("Product quantity is lower than requested");

        product.decreaseQuantity(request.getDesiredQuantity());

        productRepository.save(product);
    }
}
