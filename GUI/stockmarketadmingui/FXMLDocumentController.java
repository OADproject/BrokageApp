/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

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
}
