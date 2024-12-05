package mainmenu;

import java.util.Scanner;

public class OverAllReport {
    public void allreport(){
        
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter Customer ID: ");
        int id = sc.nextInt();
        
        while (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select Product ID Again: ");
            id = sc.nextInt();
        }
        
        
        String query = "SELECT o_id, last_name, p_name, o_due, o_date, o_status FROM tbl_order "
                + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_order.c_id "
                + "LEFT JOIN tbl_product ON tbl_product.p_id = tbl_order.p_id "
                + "WHERE tbl_order.c_id = " + id;
        
        String[] headers = {"OID", "Customer", "Product", "Due", "Date", "Status"};
        String[] columns = {"o_id", "last_name", "p_name", "o_due", "o_date", "o_status"};
        
        conf.viewRecords(query, headers, columns);
                
    }
}
