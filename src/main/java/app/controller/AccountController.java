package app.controller;

import app.client.BankClient;
import app.utils.NrbUtil;
import app.view.FxmlView;
import app.view.StageManager;
import bank.wsdl.Account;
import bank.wsdl.History;
import bank.wsdl.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AccountController implements FxmlController {


    private final StageManager stageManager;

    private BankClient bankClient;

    @FXML
    private ComboBox<Account> accountComboBox;

    @FXML
    private Label nbrLabel;

    @FXML
    private Label saldoLabel;

    @FXML
    private Button paymentButton;

    @FXML
    private TableView<History> historyListView;

    @FXML
    private ObservableList historyCollection;

    final ObservableList<String> listItems = FXCollections.observableArrayList("Add Items here");

    private Account selectedAccount;

    @Autowired
    @Lazy
    public AccountController(BankClient bankClient, StageManager stageManager) {
        this.bankClient = bankClient;
        this.stageManager = stageManager;
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
                            setText(item.getNrb());
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
                return NrbUtil.format(object.getNrb());
            }

            @Override
            public Account fromString(String string) {
                return null;
            }
        });
        getAccounts("123");


        List<History> history = bankClient.getHistory("28001168690649124429753628");


        ObservableList<History> items = FXCollections.observableArrayList(history);

        TableColumn dateCol = new TableColumn("Data transakcji");
        dateCol.setCellValueFactory(new PropertyValueFactory("timestamp"));
        TableColumn lastNameCol = new TableColumn("Odbiorca / Nadawca");
        TableColumn<History,String> titleCol = new TableColumn("Tytuł");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn amountCol = new TableColumn("Kwota");
        amountCol.setCellValueFactory(new PropertyValueFactory("amount"));

        historyListView.getColumns().addAll(dateCol, lastNameCol, titleCol, amountCol);

        historyListView.setItems(items);

    }

    private void getAccounts(String id) {
        List<Account> accounts = bankClient.getAccountsRequest(id);
        accountComboBox.setItems(FXCollections.observableArrayList(accounts));
        accountComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleComboBoxAction() {
        selectedAccount = accountComboBox.getSelectionModel().getSelectedItem();

        nbrLabel.setText(selectedAccount.getNrb());
        saldoLabel.setText(String.valueOf(selectedAccount.getAmount()));

    }

    @FXML
    private void handlePaymentButtonAction(ActionEvent event) {
        if (selectedAccount != null) {
            stageManager.switchScene(FxmlView.PAYMENT);
        }
    }
}
