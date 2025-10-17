package com.ecommerce.service;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import java.sql.SQLException;
import java.util.List;


public class ProductService {
    private ProductRepository repo= new ProductRepository();

    public ProductService() {
        this.repo = new ProductRepository();
    }

    public List<Product> getAllProducts() throws SQLException {
        return repo.findAll();
    }

    public Product getProductById(int id) throws SQLException {
        return repo.findById(id);
    }

    public void addProduct(Product product) throws SQLException {
        repo.save(product);
    }

    public void updateProduct(Product product) throws SQLException {
        repo.update(product);
    }

    public void deleteProduct(int id) throws SQLException {
        repo.deleteById(id);
    }
}
