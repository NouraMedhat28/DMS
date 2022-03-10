/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author merna ashraf
 */
public class DatabaseConnection {

    //insert into accounts
    public void Insert(String Email, String Password, String Name) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO  Accounts(Email, Password, NAME) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, Email);
            ps.setString(2, Password);
            ps.setString(3, Name);
            ps.execute();
            //System.out.println("Data has been inserted");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    //add to cartint 
    
    public void InsertAtCart(String name, String shope, String price,int quantity ) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO Cart(productName, shope, price, quantity) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, shope);
            ps.setString(3, price);
            ps.setInt(4, quantity);
            ps.execute();
            //System.out.println("Data has been inserted");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public void InsertAtContains(String name1, String name2){
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO Contains( FoodID, ComponentsID) VALUES ( ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,name1);
            ps.setString(2, name2);
            ps.execute();
            //System.out.println("Data has been inserted");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    // read from accounts
    public static boolean readAllData(String mail, String pass, String username, int a) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        boolean flag = false;
        try {
            String sql = "SELECT * FROM Accounts";
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                switch (a) {
                    //to check email and paasword in login
                    case 1:
                        if (mail.equals(res.getString("Email"))) {
                            if (pass.equals(res.getString("Password"))) {
                                flag = true;
                                break ;
                            }
                        }
                        break;
                    //to check email in sign up
                    case 2:
                        if (mail.equals(res.getString("Email"))) {
                            flag = true;
                            break ;
                        }
                        break;
                    //to check user name in sign up
                    case 3:
                        if (username.equals(res.getString("Name"))) {
                            flag = true;
                            break ;
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return flag;
    }

    // read from cart
    public static void readAllData(int i) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM Cart";
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            FileWriter fw;
            PrintWriter pw = null;
            // put the info. adout cart in file
            if (i == 1) {
                try {
                    fw = new FileWriter("Receipt.txt");
                    pw = new PrintWriter(fw);
                } catch (IOException e) {
                    System.out.println("file doesn't exist");
                }
                pw.format("---------------------------------\n");
                pw.format("%s:%s:%s:%S", "Product Name", "Place", "Price", "Quantity");
                pw.format("\n");
                while (res.next()) {
                    pw.format("%s:%s:%s:%s", res.getString("productName"), res.getString("shope"), res.getString("price"), res.getString("quantity"));
                    pw.format("\n");
                }
                Date d1 = new Date();
                String x =d1.getDate()+"/1/2022";
                pw.format("%s = %s %s", "Total Price", totalPrice(), "LE");
                pw.format("\n");
                pw.format("%s:%s,     %s:%s", "Date", x, "Hour", d1.getHours()+1);
                pw.format("\n---------------------------------");
                pw.close();    
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    //read from accounts to fall the account
    public static String readSpecificData(String email, int i) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        String xfiled = null;
        try {
            switch (i) {
                case 1: {
                    String sql = "Select Email from Accounts where Email= ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, email);
                    res = ps.executeQuery();
                    xfiled = res.getString(1);
                    break;
                }
                case 2: {
                    String sql = "Select Password from Accounts where Email= ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, email);
                    res = ps.executeQuery();
                    xfiled = res.getString(1);
                    break;
                }
                case 3: {
                    String sql = "Select Name from Accounts where Email= ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, email);
                    res = ps.executeQuery();
                    xfiled = res.getString(1);
                    break;
                }
                case 4: {
                    String sql = "Select Address from Accounts where Email= ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, email);
                    res = ps.executeQuery();
                    xfiled = res.getString(1);
                    break;
                }
                case 5: {
                    String sql = "Select phoneNumber from Phone where Email= ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, email);
                    res = ps.executeQuery();
                    xfiled = res.getString(1);
                    break;
                }
                case 6: {
                    String sql = "Select PhoneNumber from Phone where Email= ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, email);
                    res = ps.executeQuery();
                    while(res.next())
                    {
                        xfiled = res.getString(1);
                    }
                    break;
                }
                default:
                    break;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return xfiled;
    }
    
    //read selected component or food
    
    public static String readSpecificComponentData(int a,int i, int b, String probuctname) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        String xfiled = null;
        String x = null;
        try {
            // for Components
            if(b==1){
            String sql = "SELECT * FROM Components";
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            for(int y=0;y<=a;y++){
                x = res.getString(1);
//                System.out.println(x);
                res.next();
            }
                switch (i) {
                    case 1: {
                        String sql1 = "Select shope from Components where productName= ? ";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1, x);
                        res = ps.executeQuery();
                        xfiled = res.getString(1);
                        break;
                    }
                    case 2: {
                        String sql1 = "Select price from Components where productName= ? ";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1, x);
                        res = ps.executeQuery();
                        xfiled = res.getString(1);
                        break;
                    }
                    case 3: {
                        String sql1 = "Select ID from Components where productName= ? ";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1, probuctname);
                        res = ps.executeQuery();
                        xfiled = res.getString(1);
                        break;
                    }
                    case 4: {
                        xfiled = x;
                        break;
                    }
                }
            } else {
                // for food
                String sql = "SELECT * FROM Food";
                ps = con.prepareStatement(sql);
                res = ps.executeQuery();
                for (int y = 0; y <= a; y++) {
                    x = res.getString(1);
//                    System.out.println(x);
                    res.next();
                }
                switch (i) {
                    case 1: {
                        String sql1 = "Select resturant from Food where name= ? ";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1, x);
                        res = ps.executeQuery();
                        xfiled = res.getString(1);
                        break;
                    }
                    case 2: {
                        String sql1 = "Select price from Food where name= ? ";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1, x);
                        res = ps.executeQuery();
                        xfiled = res.getString(1);
                        break;
                    }
                    case 3: {
                        String sql1 = "Select ID from Food where name= ? ";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1, probuctname);
                        res = ps.executeQuery();
                        xfiled = res.getString(1);
                        break;
                    }
                    default:
                        xfiled = x;
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return xfiled;
    }

    // read from cart
    public static int totalPrice() {
        int total = 0;
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            String sql1 = "SELECT * FROM Cart";
            ps = con.prepareStatement(sql1);
            res = ps.executeQuery();
            while (res.next()) {
                total += res.getInt("price") * res.getInt("quantity");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return total;
    }

    //update accounts
    public static void updateRow(String email, String name, String password, String address1, String phonenum1, String phonenum2) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql1 = "UPDATE Accounts set Name = ?  WHERE Email = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.execute();
            String sql2 = "UPDATE Accounts set Password = ?  WHERE Email = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.execute();
            String sql3 = "UPDATE Accounts set Address = ?  WHERE Email = ?";
            ps = con.prepareStatement(sql3);
            ps.setString(1, address1);
            ps.setString(2, email);
            ps.execute();
            String sql4 = "UPDATE Phone set PhoneNumber = ?  WHERE Email = ?";
            ps = con.prepareStatement(sql4);
            ps.setString(1, phonenum1);
            ps.setString(2, email);
            ps.execute();
            String sql5 = "UPDATE phone set PhoneNumber = ?  WHERE Email = ?";
            ps = con.prepareStatement(sql5);
            ps.setString(1, phonenum2);
            ps.setString(2, email);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public static int getAvailableQuantity(String name){
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        int availableQuantity = 0;
        try {
            String sql1 = "Select availableQuantity from Components where productName= ? ";
            ps = con.prepareStatement(sql1);
            ps.setString(1, name);
            res = ps.executeQuery();
            availableQuantity = res.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return availableQuantity;
    }
    
    public static void updateRow(String n, int i) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        try {           
            String sql1 = "UPDATE Components set availableQuantity = ?  WHERE productName = ?";
            ps = con.prepareStatement(sql1);
            i = getAvailableQuantity(n) - i;
            ps.setInt(1,i);
            ps.setString(2,n);
            ps.execute();
            } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    //delete from accounts or Cart
    public static void deleteRow(String s, int i) {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        if (i == 1) {
            try {
                String sql = "delete from Accounts WHERE Email = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, s);
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                try {
                    ps.close();
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
        } else {
            try {
                String sql = "delete from Cart WHERE productName = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, s);
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                try {
                    ps.close();
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }
    
    public static void deleteAllData(){
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null ;
        try {
            String sql = "SELECT * FROM Cart";
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next())
            {
                String sql1 = "delete from Cart WHERE productName = ?";
                ps = con.prepareStatement(sql1);
                ps.setString(1,res.getString(1));
                ps.execute();
            }
        } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                try {
                    res.close();
                    ps.close();
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
    }

    public static int getNumberOfRows() {
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = null;
        ResultSet res = null;
        int size =0;
        try {
            String sql = "select count(productName) from Cart ";
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            size = res.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                res.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return size;
    }

    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:database.db");
            //System.out.println("Connect successfully ! ");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e + " ");
        }
        return con;
    }
}
