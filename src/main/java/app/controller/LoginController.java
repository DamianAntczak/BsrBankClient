package app.controller;

import app.client.BankClient;
import app.view.FxmlView;
import app.view.StageManager;
import bank.wsdl.GetAccountsResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class LoginController implements FxmlController{

    private final StageManager stageManager;

    @Autowired
    private BankClient bankClient;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @Autowired @Lazy
    public LoginController(StageManager stageManager){
        this.stageManager = stageManager;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        GetAccountsResponse accountsRequest = bankClient.getAccountsRequest("123");
        System.out.println(accountsRequest.getAccounts().size());

        if(userNameField.getText().equals("admin") && passwordField.getText().equals("admin")){
            System.out.println("Pomyślne logowanie");

            stageManager.switchScene(FxmlView.ACCOUNT);

        }
        else{
            System.out.println("Błąd logowania");
        }
    }

    @Override
    public void initialize() {

    }
}
