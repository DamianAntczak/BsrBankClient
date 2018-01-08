package app.controller;

import app.client.BankClient;
import bank.wsdl.GetAccountsResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountController implements FxmlController {

    private BankClient bankClient;

    @Autowired
    public AccountController(BankClient bankClient){
        this.bankClient = bankClient;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        GetAccountsResponse response = bankClient.getAccountsRequest("123");
        System.out.println("SIZE" + response.getAccounts().size());
    }

    @Override
    public void initialize() {

    }
}
