package app.controller;


import app.client.BankClient;
import app.utils.NrbUtil;
import bank.wsdl.MakeTransferResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class OperationsController implements FxmlController {

    @Autowired
    private BankClient bankClient;

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


    @Override
    public void initialize() {

    }

    @FXML
    public void handleAcceptButton() {
        boolean isValid = true;
        String errorText = "";

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
        }
        responseLabel.setTextFill(Color.RED);
        responseLabel.setText(errorText);

        if (isValid) {
            BigDecimal amount = new BigDecimal(transferAmountFild.getText());
            amount = amount.multiply(BigDecimal.valueOf(100));

            MakeTransferResponse response = bankClient.makeTransfer(transferAccountNumber.getText(), transferAccountName.getText(), transferTitle.getText(), amount.intValue());
            if (response.getStatus().equals("S")) {
                responseLabel.setTextFill(Color.GREEN);
                responseLabel.setText("Pomyślnie wykonano przelew!");
            } else {
                responseLabel.setTextFill(Color.RED);
                responseLabel.setText("Błąd podczas wykonywania przelewu:" +
                        System.lineSeparator() + response.getStatus());
            }
        }
    }

    @FXML
    public void handlePaymentAcceptButton(){

        Double amount = Double.parseDouble(paymentAmountFild.getText());

        paymentTitle.getText();

        bankClient.addPayment(amount,paymentTitle.getText());
    }
}
