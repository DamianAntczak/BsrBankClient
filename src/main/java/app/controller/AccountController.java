package app.controller;

import app.client.BankClient;
import bank.wsdl.Account;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AccountController implements FxmlController {

    private BankClient bankClient;

    @FXML
    private ComboBox<Account> accountComboBox;

    @Autowired
    public AccountController(BankClient bankClient) {
        this.bankClient = bankClient;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        //TODO
    }

    @Override
    public void initialize() {
        accountComboBox.setCellFactory(new Callback<ListView<Account>, ListCell<Account>>() {
            @Override
            public ListCell<Account> call(ListView<Account> param) {
                return new ListCell<Account>() {
                    @Override
                    protected void updateItem(Account item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNbr());
                        } else {
                            setText(null);
                        }
                    }


                };
            }
        });
        accountComboBox.setConverter(new StringConverter<Account>() {
            @Override
            public String toString(Account object) {
                return object.getNbr();
            }

            @Override
            public Account fromString(String string) {
                return null;
            }
        });
        getAccounts("123");
    }

    private void getAccounts(String id) {
        List<Account> accounts = bankClient.getAccountsRequest(id);
        accountComboBox.setItems(FXCollections.observableArrayList(accounts));
        accountComboBox.getSelectionModel().selectFirst();
    }
}
