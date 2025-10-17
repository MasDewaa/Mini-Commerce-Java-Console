package com.ecommerce.repository;

import com.ecommerce.model.Product;
import com.ecommerce.config.Database;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ProductRepository {
    public void save(Product p) throws SQLException {
        String sql = "INSERT INTO products (name,price,stock) VALUES (?,?,?)";
        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setInt(3, p.getStock());
            pstmt.executeUpdate();
        }
    }
    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                list.add(p);
            }
        }
        return list;
    }
    public Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    public void update(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE id=?";
        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setInt(3, p.getStock());
            pstmt.setInt(4, p.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }


}
