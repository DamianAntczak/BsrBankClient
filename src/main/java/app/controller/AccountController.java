package app.controller;

import app.client.BankClient;
import app.service.ContextService;
import app.utils.NrbUtil;
import app.view.FxmlView;
import app.view.StageManager;
import bank.wsdl.Account;
import bank.wsdl.History;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountController implements FxmlController {


    private final StageManager stageManager;
    private BankClient bankClient;
    private ContextService contextService;

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
    public AccountController(BankClient bankClient, StageManager stageManager, ContextService contextService) {
        this.bankClient = bankClient;
        this.stageManager = stageManager;
        this.contextService = contextService;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize() {
        setAccountComboBox();
        getAccounts(contextService.getClientId());
        nbrLabel.setText(contextService.getClientName());

        initializeHistoryTable();
    }

    private void initializeHistoryTable() {
        TableColumn dateCol = new TableColumn("Data transakcji");
        dateCol.setCellValueFactory(new PropertyValueFactory("timestamp"));
        TableColumn lastNameCol = new TableColumn("Odbiorca / Nadawca");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("sourceName"));
//        lastNameCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<History, String>, ObservableValue<String>>) p -> {
//            if (p.getValue() != null) {
//                if(p.getValue().getDestinationName().equals("PAYMENT"))
//                    return new SimpleStringProperty("Wpłata własna");
//                if(p.getValue().getDestinationName().equals("WITHDRAWAL"))
//                    return new SimpleStringProperty("Wypłata własna");
//                else
//                    return new SimpleStringProperty("");
//            } else {
//                return new SimpleStringProperty("");
//            }
//        });
        TableColumn<History, String> titleCol = new TableColumn("Tytuł");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn amountCol = new TableColumn("Kwota");
        amountCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<History, String>, ObservableValue<String>>) p -> {
            if (p.getValue() != null) {
                BigDecimal amount = p.getValue().getBalanceAfter().subtract(p.getValue().getBalanceBefore());
                return new SimpleStringProperty(amount.toString());
            } else {
                return new SimpleStringProperty("<no name>");
            }
        });
        TableColumn balanceBefore = new TableColumn("Saldo przed");
        balanceBefore.setCellValueFactory(new PropertyValueFactory("balanceBefore"));
        TableColumn balanceAfter = new TableColumn("Saldo po");
        balanceAfter.setCellValueFactory(new PropertyValueFactory("balanceAfter"));
        historyListView.getColumns().addAll(dateCol, lastNameCol, titleCol, amountCol, balanceBefore, balanceAfter);
    }

    private void setHistoryTable(List<History> history) {
        ObservableList<History> items = FXCollections.observableArrayList(history);
        historyListView.setItems(items);
    }

    private void setAccountComboBox() {
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
    }

    private void getAccounts(String id) {
        List<Account> accounts = bankClient.getAccountsRequest(id);
        accountComboBox.setItems(FXCollections.observableArrayList(accounts));
    }

    @FXML
    private void handleComboBoxAction() {
        selectedAccount = accountComboBox.getSelectionModel().getSelectedItem();
        saldoLabel.setText(String.format("%.2f", selectedAccount.getBalance().floatValue()));
        contextService.setNbr(selectedAccount.getNrb());
        List<History> history = bankClient.getHistory(contextService.getNbr());
        setHistoryTable(history);
    }

    @FXML
    private void handlePaymentButtonAction(ActionEvent event) {
        if (selectedAccount != null) {
            stageManager.switchScene(FxmlView.OPERATIONS);
        }
    }

}
