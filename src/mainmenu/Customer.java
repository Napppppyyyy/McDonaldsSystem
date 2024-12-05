package mainmenu;

import java.util.Scanner;

public class Customer {

    public void customerTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("\n--------------------------");
            System.out.println("-- CUSTOMER'S PANEL --");
            System.out.println("1. ADD CUSTOMER");
            System.out.println("2. VIEW CUSTOMER");
            System.out.println("3. UPDATE CUSTOMER");
            System.out.println("4. DELETE CUSTOMER");
            System.out.println("5. EXIT CUSTOMER'S PANEL");

            int action = -1;
            boolean validInput = false;
            
            while (!validInput) {
                System.out.print("Enter Selection (1-5): ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 5) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid selection. Please enter a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next(); 
                }
            }

            Customer cs = new Customer();
            switch (action) {
                case 1:
                    cs.addCustomer();
                    break;
                case 2:
                    cs.viewCustomer();
                    break;
                case 3:
                    cs.viewCustomer();
                    cs.updateCustomer();
                    cs.viewCustomer();
                    break;
                case 4:
                    cs.viewCustomer();
                    cs.deleteCustomer();
                    cs.viewCustomer();
                    break;
                case 5:
                    System.out.println("Exiting Customer Panel.");
                    break;
            }

            System.out.print("Do you want to make another transaction? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }

    public void addCustomer() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Customer's First Name: ");
        String cname = sc.nextLine().trim();
        while (cname.isEmpty()) {
            System.out.print("First name cannot be empty. Please enter Customer's First Name: ");
            cname = sc.nextLine().trim();
        }

        System.out.print("Customer's Last Name: ");
        String clname = sc.nextLine().trim();
        while (clname.isEmpty()) {
            System.out.print("Last name cannot be empty. Please enter Customer's Last Name: ");
            clname = sc.nextLine().trim();
        }

        String qry = "INSERT INTO tbl_customer(first_name, last_name) VALUES (?, ?)";
        config conf = new config();
        conf.addRecord(qry, cname, clname);
    }

    public void viewCustomer() {
        String votersQuery = "SELECT * FROM tbl_customer";
        String[] votersHeaders = {"ID", "First Name", "Last Name"};
        String[] votersColumns = {"c_id", "first_name", "last_name"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    }

    public void updateCustomer() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        int c_id = -1;
        boolean validId = false;

        while (!validId) {
            System.out.print("Enter Customer ID to Update: ");
            if (sc.hasNextInt()) {
                c_id = sc.nextInt();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", c_id) > 0) {
                    validId = true;
                } else {
                    System.out.println("Selected ID doesn't exist!");
                }
            } else {
                System.out.println("Invalid ID. Please enter a numeric ID.");
                sc.next(); 
            }
        }

        sc.nextLine(); 
        
        System.out.print("New Customer's First Name: ");
        String fname = sc.nextLine().trim();
        while (fname.isEmpty()) {
            System.out.print("First name cannot be empty. Please enter Customer's First Name: ");
            fname = sc.nextLine().trim();
        }

        System.out.print("New Customer's Last Name: ");
        String lname = sc.nextLine().trim();
        while (lname.isEmpty()) {
            System.out.print("Last name cannot be empty. Please enter Customer's Last Name: ");
            lname = sc.nextLine().trim();
        }

        String qry = "UPDATE tbl_customer SET first_name = ?, last_name = ? WHERE c_id = ?";
        conf.updateRecord(qry, fname, lname, c_id);
    }

    public void deleteCustomer() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = -1;
        boolean validId = false;

        while (!validId) {
            System.out.print("Enter ID to Delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) > 0) {
                    validId = true;
                } else {
                    System.out.println("Selected ID doesn't exist!");
                }
            } else {
                System.out.println("Invalid ID. Please enter a numeric ID.");
                sc.next(); 
            }
        }

        String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
        conf.deleteRecord(qry, id);
    }
}
