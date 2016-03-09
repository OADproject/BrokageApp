/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 *
 * @author Prateek
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab viewMarketTab;


    @FXML
    private TextField usernameInput;
    StringProperty usernameInputProp = new SimpleStringProperty();

    @FXML
    private TextField passwordInput;
    StringProperty passwordInputProp = new SimpleStringProperty();

    @FXML
    private Label LoginError;

    @FXML

    private TextArea currentMarketStockPricesArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void validateLogin(ActionEvent actionEvent) {
        LoginError.setText("");
        if (usernameInput.getText().equals("admin") && passwordInput.getText().equals("admin")){
            mainTabPane.getSelectionModel().select(viewMarketTab);
        } else {
            LoginError.setText("User Name or Password is Incorrect");
        }

    }

    public void deleteUser(ActionEvent actionEvent) {
    }

    public void editUsedInfo(ActionEvent actionEvent) {
    }

    public void viewUserStocks(ActionEvent actionEvent) {
    }

    public void viewUserBalance(ActionEvent actionEvent) {
    }

    public void viewCurrentStocks(ActionEvent actionEvent) {
    }

    public void updateMarket(ActionEvent actionEvent) {
//        currentMarketStockPricesArea.setText("Market Started");
        Market m =  Market.getMarket();
        StringBuilder sb = new StringBuilder();
        Map<String,Double> vals =  m.getCurrentStockValues();
        for (String s: vals.keySet()
             ) {
            sb.append(s);
            sb.append(" : ");
            sb.append(vals.get(s));
            sb.append("\n");
        }
        currentMarketStockPricesArea.setText(sb.toString());
    }

    public void startMarket(ActionEvent actionEvent) {
        Market m = Market.getMarket();
        System.out.println("marketStarted");
        updateMarket(new ActionEvent());


    }

    public void stopMarket(ActionEvent actionEvent) {
        currentMarketStockPricesArea.setText("Market Stopped");
    }
}
