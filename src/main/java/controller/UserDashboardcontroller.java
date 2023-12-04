package controller;

import Model.Product;
import com.example.myjava.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UserDashboardcontroller implements Initializable {
    private static final String FILE_PATH = "Product.txt";
    private final ObservableList<Product> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> productData;

    @FXML
    private TextField txtProdId;

    @FXML
    private TextField txtProdName;

    @FXML
    private TextField txtProdPrice;

    @FXML
    private TextField txtProdCat;

    @FXML
    private TextField txtProdStock;

    @FXML
    private TableColumn<Product, String> ProdId;

    @FXML
    private TableColumn<Product, String> ProdName;

    @FXML
    private TableColumn<Product, String> ProdPrice;

    @FXML
    private TableColumn<Product, String> ProdCat;

    @FXML
    private TableColumn<Product, String> ProdStock;

    public void handleuserlogoutbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("LoginPage.fxml");
    }

    public void onhistorybtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("UserHistory.fxml");
    }

    public void onOrderbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("UserBuyProduct.fxml");
    }

    public void onProductbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("UserDashboard.fxml");
    }
    @FXML
    protected void searchOnUserClick() throws IOException{
        searchProduct(txtProdId.getText(),txtProdName.getText(),txtProdPrice.getText(),txtProdCat.getText(),txtProdStock.getText());
        txtProdId.setText("");
        txtProdName.setText("");
        txtProdPrice.setText("");
        txtProdCat.setText("");
        txtProdStock.setText("");
    }

    public void searchProduct(String id,String name, String price, String catagory, String stock) throws FileNotFoundException {
        list.clear();
        ArrayList<Product> search = new ArrayList<>();
        File file = new File(FILE_PATH);
        if(!file.exists()){
            return;
        }
        Scanner input = new Scanner(file);
        while (input.hasNextLine()){
            String[] st = input.nextLine().split(",");
            if(st[0].equalsIgnoreCase(txtProdId.getText()) || st[1].equalsIgnoreCase(txtProdName.getText()) || st[2].equalsIgnoreCase(txtProdPrice.getText()) || st[3].equalsIgnoreCase(txtProdCat.getText()) || st[4].equalsIgnoreCase(txtProdStock.getText()) ){
                list.add(new Product(st[0],st[1],st[2],st[3],st[4]));
            }
        }
        productData.setItems(list);
        input.close();
    }




    @FXML
    protected void displayuserTable() throws FileNotFoundException {
        initeCols();
        loadData();
    }
    private void initeCols(){
        ProdId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProdName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProdCat.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        ProdStock.setCellValueFactory(new PropertyValueFactory<>("productStocks"));
    }

    public void loadData() throws FileNotFoundException{
        list.clear();
        Scanner input = new Scanner(new File(FILE_PATH));
        while (input.hasNextLine()){
            String[] str = input.nextLine().split(",");
            if (str.length == 5) {
                list.add(new Product(str[0], str[1], str[2], str[3], str[4]));
            } else {
                // Handle the case where the array doesn't have enough elements
                System.err.println("Invalid data format: " );
            }
        }
        productData.setItems(list);
        input.close();
    }



    private ArrayList<Product> readProduct() throws IOException
    {
        ArrayList<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);

        if(!file.exists()){
            return products;
        }

        try (Scanner input = new Scanner(file)){
            while(input.hasNextLine()){
                String line = input.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 5 ){
                    String productID = parts[0];
                    String productName = parts[1];
                    String productPrice = parts[2];
                    String productCatagory = parts[3];
                    String productStock = parts[4];
                    products.add(new Product(productID, productName, productPrice, productCatagory, productStock));
                }
            }

        }
        return products;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initeCols();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

