package mainmenu;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MAINMENU {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;

        do { 
            System.out.println("\n----------------------------------------");
            System.out.println("-- MCDONALD'S DRIV-THRU SERVICE --");
            System.out.println("----------------------------------------");
            
            System.out.println("\n-- MAIN MENU --");
            System.out.println("1. Product");
            System.out.println("2. Customer");
            System.out.println("3. Order");
            System.out.println("4. Individual Reports");
            System.out.println("5. Exit");
            
            int action = 0;
            boolean validAction = false;

           
            while (!validAction) {
                System.out.print("Enter Action (1-5): ");
                try {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 5) {
                        validAction = true;
                    } else {
                        System.out.println("Invalid action. Please choose between 1 and 3.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    sc.next();
                }
            }
            
            switch (action) {
                case 1:
                    
                Product p = new Product();
                p.prodtransaction();

                    break;
                    
                case 2:
                    Customer c = new Customer();
                    c.customerTransaction();
                    break;
                    
                case 3:
                    Order o = new Order();
                    o.ordertransaction();
                    break;
                    
                case 4:
                    Customer cv = new Customer();
                    cv.viewCustomer();
                    OverAllReport oar = new OverAllReport();
                    oar.allreport();                       
                    break;
                    
                case 5:
                    boolean validResponse = false;
                    while (!validResponse) {
                        System.out.print("Exit Selected... Type 'yes' to confirm: ");
                        String res = sc.next().trim();
                        if (res.equalsIgnoreCase("yes")) {
                            exit = false;
                            validResponse = true;
                            System.out.println("-- THANK YOU FOR USING THE PROGRAM --");
                        } else {
                            System.out.println("Exit canceled. Returning to main menu.");
                            validResponse = true; 
                        }
                    }
                    break;
            }
            
        } while (exit);

        sc.close(); 
    }
}

        
    

