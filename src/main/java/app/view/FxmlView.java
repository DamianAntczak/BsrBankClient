package app.view;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FxmlView {

    ACCOUNT("Widok konta", "../../account.fxml"),
    LOGIN("Logowanie do banku", "../../sample.fxml");

    String title;
    String fxmlFile;
}