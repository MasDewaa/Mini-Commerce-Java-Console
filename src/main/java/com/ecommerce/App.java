package com.ecommerce;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.TransactionService;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        ProductService productService = new ProductService();
        TransactionService transactionService = new TransactionService();

        while (true) {
            System.out.println("\n=== MINI E-COMMERCE ===");
            System.out.println("1. Seller Mode");
            System.out.println("2. Buyer Mode");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1 -> sellerMenu(scanner, productService);
                case 2 -> buyerMenu(scanner, productService, transactionService);
                case 3 -> System.exit(0);
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    @SuppressWarnings({"UseSpecificCatch", "UnnecessaryReturnStatement"})
    private static void sellerMenu(Scanner scanner, ProductService productService) {
        System.out.println("\n--- SELLER MENU ---");
        System.out.println("Available Products:");
        System.out.println("1. Create Product");
        System.out.println("2. Read Product");
        System.out.println("3. Update Product");
        System.out.println("2. Delete Product");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt(); scanner.nextLine();

        switch (choice) {
            case 1 -> {
                try {
                    System.out.print("Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Product Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Product Stock: ");
                    int stock = scanner.nextInt();
                    productService.addProduct(new Product(0, name, price, stock));
                    System.out.println("Product added successfully!");
                } catch (Exception e) {
                    System.out.println("Error adding product: " + e.getMessage());
                }
            }

            case 2 -> {
                try {
                    System.out.println("Product List:");
                    for (Product p : productService.getAllProducts()) {
                        System.out.println(p);
                    }
                } catch (Exception e) {
                    System.out.println("Error retrieving products: " + e.getMessage());
                }
            }
            case 3 -> {
                try {
                    System.out.print("Enter Product ID to update: ");
                    int id = scanner.nextInt(); scanner.nextLine();
                    Product p = productService.getProductById(id);
                    if (p != null) {
                        System.out.print("New Name (" + p.getName() + "): ");
                        String name = scanner.nextLine();
                        System.out.print("New Price (" + p.getPrice() + "): ");
                        double price = scanner.nextDouble();
                        System.out.print("New Stock (" + p.getStock() + "): ");
                        int stock = scanner.nextInt();
                        p.setName(name);
                        p.setPrice(price);
                        p.setStock(stock);
                        productService.updateProduct(p);
                        System.out.println("Product updated successfully!");
                    } else {
                        System.out.println("Product not found.");
                    }
                } catch (Exception e) {
                    System.out.println("Error updating product: " + e.getMessage());
                }
            }
            case 4 -> {
                try {
                    System.out.print("Enter Product ID to delete: ");
                    int id = scanner.nextInt();
                    productService.deleteProduct(id);
                    System.out.println("Product deleted successfully!");
                } catch (Exception e) {
                    System.out.println("Error deleting product: " + e.getMessage());
                }
            }
            case 5 -> {
                return;
            }
            default -> System.out.println("Pilihan tidak valid.");
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private static void buyerMenu(Scanner scanner, ProductService productService, TransactionService transactionService) {
        System.out.println("Enter Your Name: ");
        String buyerName = scanner.nextLine();
        while (true) {
            System.out.println("\n--- BUYER MENU ---");
            System.out.println("1. View Products");
            System.out.println("2. Buy Product");
            System.out.println("3. View Transactions");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    try {
                        for (Product p : productService.getAllProducts()) {
                            System.out.println(p);
                        }
                    } catch (Exception e) {
                        System.out.println("Error retrieving products: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        System.out.print("Enter Product ID to buy: ");
                        int productId = scanner.nextInt(); scanner.nextLine();
                        Product p = productService.getProductById(productId);
                        if (p != null) {
                            System.out.print("Enter quantity to buy: ");
                            int quantity = scanner.nextInt();
                            transactionService.buy(buyerName, p, quantity);
                        } else {
                            System.out.println("Product not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error during purchase: " + e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        System.out.println("Transaction History:");
                        transactionService.showAll();
                    } catch (Exception e) {
                        System.out.println("Error retrieving transactions: " + e.getMessage());
                    }
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
