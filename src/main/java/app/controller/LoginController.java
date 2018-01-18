package app.controller;

import app.client.BankClient;
import app.service.ContextService;
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
    @Autowired
    private ContextService contextService;

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


        if(userNameField.getText().equals("admin") && passwordField.getText().equals("admin")){
            System.out.println("Pomyślne logowanie");

            contextService.setClientId("1234");
            contextService.setToken("ScdX34d");
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
