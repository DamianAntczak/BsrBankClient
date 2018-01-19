package app.controller;


import app.client.BankClient;
import app.service.ContextService;
import app.utils.NrbUtil;
import app.view.FxmlView;
import app.view.StageManager;
import bank.wsdl.MakeTransferResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class OperationsController implements FxmlController {

    @Autowired
    private BankClient bankClient;
    @Autowired
    private ContextService contextService;
    private StageManager stageManager;

    @FXML
    private TextField transferAccountNumber;
    @FXML
    private TextField transferAccountName;
    @FXML
    private TextArea transferTitle;
    @FXML
    private TextField transferAmountFild;
    @FXML
    private Label responseLabel;
    @FXML
    private Button account;

    @FXML
    private TextArea paymentTitle;
    @FXML
    private TextField paymentAmountFild;
    @FXML
    private Label paymentMessageLabel;

    @FXML
    private TextArea withdrawalTitle;
    @FXML
    private TextField withdrawalAmountFild;
    @FXML
    private Label withdrawalMessageLabel;

    @Autowired
    @Lazy
    public OperationsController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize() {

    }

    @FXML
    public void handleAcceptButton() {
        boolean isValid = true;
        String errorText = "";
        BigDecimal amount = null;

        if (transferAccountNumber.getText().replace(" ", "").equals(contextService.getNbr())) {
            errorText += "Nie można dokonać przelewu na to samo konto";
            isValid = false;
        }

        if (!NrbUtil.validateNrb(transferAccountNumber.getText())) {
            errorText += "Niepoprawny numer konta odbiorcy";
            isValid = false;
        }

        if (transferAccountName.getText().isEmpty()) {
            errorText += System.lineSeparator() + "Nazwa odbiorcy nie może być pusta";
            isValid = false;
        }
        if (transferTitle.getText().isEmpty()) {
            errorText += System.lineSeparator() + "Tytuł przelewu nie może być pusty";
            isValid = false;
        }
        if (transferAmountFild.getText().isEmpty()) {
            errorText += System.lineSeparator() + "Kwota przelewu musi zostać podana";
            isValid = false;
        } else {
            amount = new BigDecimal(transferAmountFild.getText().replace(",", "."));
            amount = amount.multiply(BigDecimal.valueOf(100));

            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                errorText += System.lineSeparator() + "Kwota przelewu nie może być ujemna";
                isValid = false;
            }
        }
        responseLabel.setTextFill(Color.RED);
        responseLabel.setText(errorText);

        if (isValid) {

            MakeTransferResponse response = bankClient.makeTransfer(transferAccountNumber.getText(), transferAccountName.getText(), transferTitle.getText(), amount.intValue());
            if (response.getStatus().equals("S")) {
                responseLabel.setTextFill(Color.GREEN);
                responseLabel.setText("Pomyślnie wykonano przelew!");
                transferAccountName.setText("");
                transferAccountNumber.setText("");
                transferAmountFild.setText("");
                transferTitle.setText("");
            } else {
                responseLabel.setTextFill(Color.RED);
                responseLabel.setText("Błąd podczas wykonywania przelewu:" +
                        System.lineSeparator() + response.getMassage());
            }
        }
    }

    @FXML
    public void handlePaymentAcceptButton() {
        String message = "";
        boolean isValid = true;

        if (paymentTitle.getText().isEmpty()) {
            message += "Tytuł operacji nie może być pusty";
            isValid = false;
        }

        if (!paymentAmountFild.getText().replace(",", ".").matches("\\d+(\\.\\d{1,2})?")) {
            message += System.lineSeparator() + "Nie poprawny fromat kwoty";
            isValid = false;
        }

        Double amount = Double.parseDouble(paymentAmountFild.getText().replace(",", "."));
        if (amount < 0) {
            message += System.lineSeparator() + "Kwota wpłaty nie może być ujemna";
            isValid = false;
        }

        try {
            if (isValid) {
                bankClient.addPayment(amount, paymentTitle.getText());
                paymentMessageLabel.setTextFill(Color.GREEN);
                paymentMessageLabel.setText("Pomyślnie dokonano wpłaty");
                paymentAmountFild.setText("");
                paymentTitle.setText("");
            } else {
                paymentMessageLabel.setTextFill(Color.RED);
                paymentMessageLabel.setText(message);
            }
        } catch (NumberFormatException e) {
            message += System.lineSeparator() + "Nie poprawna wartość kwoty";
            paymentMessageLabel.setTextFill(Color.RED);
            paymentMessageLabel.setText(message);
        }

    }

    @FXML
    public void handleWithdrawalAcceptButton() {
        String message = "";
        boolean isValid = true;

        try {

            if (withdrawalTitle.getText().isEmpty()) {
                message += "Tytuł operacji nie może być pusty";
                isValid = false;
            }
            if (!paymentAmountFild.getText().replace(",", ".").matches("\\d+(\\.\\d{1,2})?")) {
                message += System.lineSeparator() + "Nie poprawny fromat kwoty";
                isValid = false;
            }

            Double amount = Double.parseDouble(paymentAmountFild.getText().replace(",", "."));
            if (amount < 0) {
                message += System.lineSeparator() + "Kwota wypłaty nie może być ujemna";
                isValid = false;
            }

            if (isValid) {
                bankClient.addWithdrawal(amount, withdrawalTitle.getText());
                withdrawalMessageLabel.setTextFill(Color.GREEN);
                withdrawalMessageLabel.setText("Pomyślnie dokonano wypłaty");
                withdrawalAmountFild.setText("");
                withdrawalTitle.setText("");
            } else {
                withdrawalMessageLabel.setTextFill(Color.RED);
                withdrawalMessageLabel.setText(message);
            }
        } catch (NumberFormatException e) {
            message += System.lineSeparator() + "Nie poprawna wartość kwoty";
            withdrawalMessageLabel.setTextFill(Color.RED);
            withdrawalMessageLabel.setText(message);
        }
    }

    @FXML
    private void handleBackButton() {
        stageManager.switchScene(FxmlView.ACCOUNT);
    }
}
