package app;

import app.view.FxmlView;
import app.view.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BsrClientApplication extends Application {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    public static void main(String[] args) {
        launch(BsrClientApplication.class, args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stageManager = springContext.getBean(StageManager.class, stage);
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(BsrClientApplication.class);
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
    }
}
