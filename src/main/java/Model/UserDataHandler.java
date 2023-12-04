package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDataHandler {
    private static final String FILE_PATH = "user.txt";

    public static void saveUser(User user) {
        List<User> users = loadUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.println(u.getFullname() + "," + u.getUsername() + "," + u.getEmail() + "," + u.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<User> loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        File file = new File(FILE_PATH);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    User user = new User();
                    user.setFullname(parts[0]);
                    user.setUsername(parts[1]);
                    user.setEmail(parts[2]);
                    user.setPassword(parts[3]);
                    if (parts.length == 5){
                        int orderCount = Integer.parseInt(parts[4]);
                        user.setOrdercount(orderCount);
                    }
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void deleteUser(User user) {
        List<User> users = loadUsers();
        users.remove(user);
        saveUsers(users);
        System.out.println("User deleted: " + user.getUsername());
    }

    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.println(u.getFullname() + "," + u.getUsername() + "," + u.getEmail() + "," + u.getPassword() + "," + u.getOrdercount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateUseroderCount(String username, int quantity) throws IOException{
        ObservableList<User> users = loadUsers();
        for (User user : users){
            if(user.getUsername().equals(username)){
                int currentOrdercount = user.getOrdercount();
                int newOrdercount = currentOrdercount + quantity;
                user.setOrdercount(newOrdercount);
                break;
            }
        }
        saveUsers(users);
    }

    public static ObservableList<Order> loadOrderHistory(String username) {
        ObservableList<Order> orderHistory = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("user-order.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[5].equals(username)) {
                    Order order = new Order();
                    order.setProductID(parts[0]);
                    order.setProductName(parts[1]);
                    order.setProductPrice(parts[2]);
                    order.setProductStock(parts[3]);
                    order.setProductTotalprice(parts[4]);
                    order.setUsername(parts[5]);

                    orderHistory.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderHistory;
    }

    public static User findUserbyUserName(String username){
        ObservableList<User> users = loadUsers();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}