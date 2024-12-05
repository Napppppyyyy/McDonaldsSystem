package mainmenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Product {
    
    public void prodtransaction() {
        Scanner sc = new Scanner(System.in);
        String response = null;
        do {
            System.out.println("\n--------------------------");
            System.out.println("-- PRODUCT'S PANEL --");
            System.out.println("1. ADD PRODUCT");
            System.out.println("2. VIEW PRODUCT");
            System.out.println("3. UPDATE PRODUCT");
            System.out.println("4. DELETE PRODUCT");
            System.out.println("5. EXIT PRODUCT'S PANEL");
            
            System.out.print("Enter Selection: ");
            int action;
            try {
                action = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid selection number.");
                sc.nextLine(); 
                continue;
            }

            Product p = new Product();
            switch (action) {
                case 1:
                    p.viewProduct();
                    p.addProduct();
                    p.viewProduct();
                    break;
                case 2:
                    p.viewProduct();
                    break;
                case 3:
                    p.viewProduct();
                    p.updateProduct();
                    p.viewProduct();
                    break;
                case 4:
                    p.viewProduct();
                    p.deleteProduct();
                    p.viewProduct();
                    break;
                case 5:
                    System.out.println("Exiting PRODUCT'S PANEL.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 5.");
            }
            System.out.print("Do you want to make another transaction?: ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }
    
    public void addProduct() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Product Name: ");
        String pname = sc.next();

        double pprice;
        do {
            System.out.print("Product Price: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value for the price.");
                sc.next();
            }
            pprice = sc.nextDouble();
        } while (pprice < 0);

        double stocks;
        do {
            System.out.print("Product Stocks: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value for stocks.");
                sc.next();
            }
            stocks = sc.nextDouble();
        } while (stocks < 0);

        System.out.print("Product Status: ");
        String status = sc.next();

        String qry = "INSERT INTO tbl_product(p_name, p_price, p_stocks, p_status) VALUES(?, ?, ?, ?)";
        conf.addRecord(qry, pname, pprice, stocks, status);
        System.out.println("Product added successfully.");
    }
    
    public void viewProduct() {
        String query = "SELECT * FROM tbl_product";
        String[] headers = {"ID", "Product Name", "Product Price", "Product Stocks", "Product Status"};
        String[] columns = {"p_id", "p_name", "p_price", "p_stocks", "p_status"};
        
        config conf = new config();
        conf.viewRecords(query, headers, columns);
    }

    public void updateProduct() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Product ID to Update: ");
        int p_id = sc.nextInt();

        while (conf.getSingleValue("SELECT p_id FROM tbl_product WHERE p_id = ?", p_id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select Product ID Again: ");
            p_id = sc.nextInt();
        }

        System.out.print("New Product's Name: ");
        String prodname = sc.next();

        double price;
        do {
            System.out.print("New Product's Price: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value for the price.");
                sc.next();
            }
            price = sc.nextDouble();
        } while (price < 0);

        double stocks;
        do {
            System.out.print("New Product's Stocks: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value for stocks.");
                sc.next();
            }
            stocks = sc.nextDouble();
        } while (stocks < 0);

        System.out.print("New Product's Status: ");
        String status = sc.next();

        String qry = "UPDATE tbl_product SET p_name = ?, p_price = ?, p_stocks = ?, p_status = ? WHERE p_id = ?";
        conf.updateRecord(qry, prodname, price, stocks, status, p_id);
        System.out.println("Product updated successfully.");
    }
    
    public void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter ID to Delete: ");
        int id;
        while (true) {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric value for the ID.");
                sc.next();
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT p_id FROM tbl_product WHERE p_id = ?", id) != 0) {
                break;
            }
            System.out.println("Selected ID doesn't exist! Please try again.");
            System.out.print("Enter ID to Delete: ");
        }

        String qry = "DELETE FROM tbl_product WHERE p_id = ?";
        conf.deleteRecord(qry, id);
        System.out.println("Product deleted successfully.");
    }
}
