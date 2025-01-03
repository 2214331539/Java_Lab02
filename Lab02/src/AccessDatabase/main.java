package AccessDatabase;

import java.sql.*;
import java.io.*;
import java.util.Scanner;


public class main {
    

    static void add(Connection con,String bookName, String pubDate,String author,int id) {
        try {
            String cmd = "INSERT INTO Books (id,title, pubDate, author) VALUES ('"+id+"','"+bookName+"', '"+pubDate+"', '"+author+"')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(cmd);
            System.out.println("Book inserted successfully");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void update(Connection con,String bookName, String pubDate,String author,int id) {
        try {
            String cmd = "UPDATE Books SET id = '"+id+"', title = '"+bookName+"', pubDate = '"+pubDate+"', author = '"+author+"' WHERE title = '"+bookName+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(cmd);
            System.out.println("Book updated successfully");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void delete(Connection con,String bookName) {
        try {
            String cmd = "DELETE FROM Books WHERE title = '"+bookName+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(cmd);
            System.out.println("Book deleted successfully");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void retrieveBooks(Connection con) {
        try {
            String cmd = "SELECT * FROM Books";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author );
            }
            System.out.println("Books retrieved successfully");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void viewBooks(Connection con,String bookName) {
        try {
            String cmd = "SELECT * FROM Books WHERE title = '"+bookName+"'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author + ", Published Date: " + rs.getString("pubDate"));
            }
            System.out.println("Books retrieved successfully");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
    public static void main(String[] args) throws Exception {
        String mysqlDriverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(mysqlDriverName);
        System.out.println("load successfully");        //检查驱动加载
        int id = 4;  
        
        String url = "jdbc:mysql://localhost/Books ? useSSL=false & allowPublicKeyRetrieval=true";
        Connection con = DriverManager.getConnection(url, "root", "15258223655Ptf.");
        System.out.println("Connected to database successfully");     //检查连接
        
        
        boolean flag = true;
        while(flag) {
            System.out.println("Enter your choice: ");
            System.out.println("1. Add Book(example:Harry Potter,2005.12.12,JK Rowling)" );
            System.out.println("2. Update Book(example:Harry Potter,2005.12.12,JK Rowling)");
            System.out.println("3. Delete Book(example:Harry Potter)");
            System.out.println("4. Retrieve Books");
            System.out.println("5. View Books(example:Harry Potter)");
            System.out.println("6. Exit");
            Scanner input = new Scanner(System.in);
            
            int choice = input.nextInt();
            input.nextLine();           // 这一步用来消耗换行符，在使用nextint的时候不会读取到换行符 会导致下面的第一个name为空
            switch(choice) {
                case 1:
                    System.out.println("Enter Book Name: ");
                    String bookName = input.nextLine();
                    System.out.println("Enter Published Date: ");
                    String pubDate = input.nextLine();
                    System.out.println("Enter Author: ");
                    String author = input.nextLine();
                    add(con, bookName, pubDate, author,id);
                    id++; 
                    break;
                case 2:
                    System.out.println("Enter Book Name: ");
                    bookName = input.nextLine();
                    System.out.println("Enter Published Date: ");
                    pubDate = input.nextLine();
                    System.out.println("Enter Author: ");
                    author = input.nextLine();
                    update(con, bookName, pubDate, author,id);
                    break;
                case 3:
                    System.out.println("Enter Book Name: ");
                    bookName = input.nextLine();
                    delete(con,bookName);
                    break;
                case 4:
                    retrieveBooks(con);
                    break;
                case 5:
                    System.out.println("Enter Book Name: ");
                    bookName = input.nextLine();
                    viewBooks(con,bookName);
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("chose again");
                    break;
            }

        }
        con.close();

    }
}
