package com.ecommerce.repository;

import com.ecommerce.model.Transaction;
import com.ecommerce.config.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public void save(Transaction txn, int product_id, int quantity) throws SQLException {
        String insertTxn = "INSERT INTO transactions (buyer_name, product_id, quantity, total) VALUES (?,?,?,?)";
        String updateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertTxn);
             PreparedStatement pstmtUpdate = conn.prepareStatement(updateStock)) {
            pstmt.setString(1, txn.toString());
            pstmt.setInt(2, product_id);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, txn.getTotalPrice());
            pstmt.executeUpdate();

            pstmtUpdate.setInt(1, quantity);
            pstmtUpdate.setInt(2, product_id);
            pstmtUpdate.executeUpdate();
        }
    }

    public List<Transaction> findAll() throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT transactions.id, buyer_name, products.name AS product, transactions.quantity, transactions.total FROM transactions JOIN products ON transactions.product_id = products.id ORDER BY transactions.id ASC";
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        rs.getString("buyer_name"),
                        rs.getInt("product"),
                        rs.getInt("quantity"),
                        rs.getDouble("total")
                );
                list.add(t);
            }
        }
        return list;
    }
    public Transaction findById(int id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id=?";
        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Transaction(
                        rs.getInt("id"),
                        rs.getString("buyer_name"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total")
                    );
                }
            }
        }
        return null;
    }
    
}
