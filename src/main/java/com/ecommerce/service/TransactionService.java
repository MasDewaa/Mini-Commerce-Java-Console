package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.model.Transaction;
import com.ecommerce.repository.TransactionRepository;
import java.sql.SQLException;
import java.util.List;


public class TransactionService {
    private TransactionRepository repo = new TransactionRepository();

    public void buy(String buyerName, Product p, int quantity) throws SQLException {
        if (quantity > p.getStock()) {
            throw new IllegalArgumentException("Stok tidak mencukupi");
        }
        double total = p.getPrice() * quantity;
        Transaction txn = new Transaction(0, buyerName, p.getId(), quantity, total);
        try {
            repo.save(txn, p.getId(), quantity);
            System.out.println("Pembelian berhasil!");
        } catch (SQLException e) {
            System.out.println("Gagal beli: " + e.getMessage());
        }
    }
    public void showAll() throws SQLException {
        List<Transaction> transactions = repo.findAll();
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
