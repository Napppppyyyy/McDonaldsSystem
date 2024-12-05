package mainmenu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Order {

    public void ordertransaction() {
        Scanner sc = new Scanner(System.in);
        String response = null;

        do {
            System.out.println("\n--------------------------");
            System.out.println("-- ORDER'S PANEL --");
            System.out.println("1. ADD ORDER");
            System.out.println("2. VIEW ORDER");
            System.out.println("3. UPDATE ORDER");
            System.out.println("4. DELETE ORDER");
            System.out.println("5. EXIT ORDER'S PANEL");

            System.out.print("Enter Selection: ");
            int action;
            try {
                action = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid selection number.");
                sc.nextLine(); 
                continue;
            }

            Order o = new Order();
            switch (action) {
                case 1:
                    
                    o.addOrder();
                    o.viewOrders();
                    
                    break;
                case 2:
                    
                    o.viewOrders();
                    
                    break;
                case 3:
                    
                    o.viewOrders();
                    o.updateOrder();
                    o.viewOrders();

                    break;
                case 4:
                    
                    o.viewOrders();                  
                    o.deleteOrder();
                    o.viewOrders();

                    break;
                case 5:
                    System.out.println("Exiting ORDER'S PANEL.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 5.");
            }

            System.out.print("Do you want to make another transaction?: ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }

    public void addOrder() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Customer cs = new Customer();
        Product p = new Product();

        cs.viewCustomer();
        System.out.print("Enter the ID of the Customer: ");
        int cid = getValidID(sc, conf, "tbl_customer", "c_id");

        p.viewProduct();
        System.out.print("Enter the ID of the Product: ");
        int pid = getValidID(sc, conf, "tbl_product", "p_id");

        double quantity;
        do {
            System.out.print("Enter Quantity: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value for quantity.");
                sc.next();
            }
            quantity = sc.nextDouble();
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
            }
        } while (quantity <= 0);

        String priceqry = "SELECT p_price FROM tbl_product WHERE p_id = ?";
        double price = conf.getSingleValue(priceqry, pid);
        double due = price * quantity;

        System.out.println("-----------------");
        System.out.printf("Due: %.2f\n", due);
        System.out.println("-----------------");

        double o_cash;
        do {
            System.out.print("Enter the received Cash: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value for cash.");
                sc.next();
            }
            o_cash = sc.nextDouble();
            if (o_cash < due) {
                System.out.println("Insufficient cash! Cash received must be at least equal to the due amount.");
            }
        } while (o_cash < due);

        double change = o_cash - due;
        System.out.println("----------------------");
        System.out.printf("Change: %.2f\n", change);
        System.out.println("----------------------");

        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);
        String o_status = "Paid";

        String orderqry = "INSERT INTO tbl_order (c_id, p_id, o_quantity, o_due, o_cash, o_date, o_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(orderqry, cid, pid, quantity, due, o_cash, date, o_status);
        System.out.println("Order added successfully.");
    }

    public void viewOrders() {
        String query = "SELECT o_id, last_name, p_name, o_due, o_date, o_status FROM tbl_order "
                + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_order.c_id "
                + "LEFT JOIN tbl_product ON tbl_product.p_id = tbl_order.p_id";
        
        String[] headers = {"OID", "Customer", "Product", "Due", "Date", "Status"};
        String[] columns = {"o_id", "last_name", "p_name", "o_due", "o_date", "o_status"};
        
        config conf = new config();
        conf.viewRecords(query, headers, columns);
    }

    public void updateOrder() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        viewOrders();
        System.out.print("Enter the Order ID to update: ");
        int oid = getValidID(sc, conf, "tbl_order", "o_id");

        System.out.print("Enter new quantity: ");
        double newQuantity = sc.nextDouble();

        String priceQuery = "SELECT p_price FROM tbl_product JOIN tbl_order ON tbl_product.p_id = tbl_order.p_id WHERE o_id = ?";
        double price = conf.getSingleValue(priceQuery, oid);
        double newDue = price * newQuantity;

        System.out.print("Enter new cash received: ");
        double newCash = sc.nextDouble();

        if (newCash < newDue) {
            System.out.println("Insufficient cash! Please make sure the new cash received is equal or greater than the due.");
            return;
        }

        double newChange = newCash - newDue;
        System.out.printf("New Due: %.2f, New Change: %.2f\n", newDue, newChange);

        String updateQuery = "UPDATE tbl_order SET o_quantity = ?, o_due = ?, o_cash = ? WHERE o_id = ?";
        conf.updateRecord(updateQuery, newQuantity, newDue, newCash, oid);
        System.out.println("Order updated successfully.");
    }

    public void deleteOrder() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        viewOrders();
        System.out.print("Enter the Order ID to delete: ");
        int oid = getValidID(sc, conf, "tbl_order", "o_id");

        String deleteQuery = "DELETE FROM tbl_order WHERE o_id = ?";
        conf.deleteRecord(deleteQuery, oid);
        System.out.println("Order deleted successfully.");
    }

    private int getValidID(Scanner sc, config conf, String tableName, String columnName) {
        int id;
        while (true) {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                sc.next();
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?", id) != 0) {
                break;
            }
            System.out.println("Selected ID doesn't exist! Please try again.");
            System.out.print("Enter ID again: ");
        }
        return id;
    }
}
