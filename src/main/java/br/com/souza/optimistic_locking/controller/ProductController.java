package br.com.souza.optimistic_locking.controller;

import br.com.souza.optimistic_locking.database.model.Product;
import br.com.souza.optimistic_locking.dto.BuyProductRequest;
import br.com.souza.optimistic_locking.dto.NewProductRequest;
import br.com.souza.optimistic_locking.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createNewProduct(@RequestBody NewProductRequest request) throws Exception{
        productService.createNewProduct(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) throws Exception{
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Void> buyProduct(@RequestBody BuyProductRequest request) throws Exception{
        productService.buyProduct(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
