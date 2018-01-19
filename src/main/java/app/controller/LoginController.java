package app.controller;

import app.client.BankClient;
import app.service.ContextService;
import app.view.FxmlView;
import app.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class LoginController implements FxmlController{

    private final StageManager stageManager;

    @Autowired
    private BankClient bankClient;
    @Autowired
    private ContextService contextService;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessage;

    @Autowired @Lazy
    public LoginController(StageManager stageManager){
        this.stageManager = stageManager;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        if(bankClient.loginToSystem(userNameField.getText(), passwordField.getText())){
            stageManager.switchScene(FxmlView.ACCOUNT);
        }
        else{
            loginMessage.setTextFill(Color.RED);
            loginMessage.setText("Błąd logowania");
            passwordField.setText("");
        }
    }

    @Override
    public void initialize() {

    }
}
