import java.io.IOException;

import database.DatabaseClass;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import login.LoginApp;

/** Main application class for the application */
public class MainClass extends Application {
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) throws IOException {

        DatabaseClass.lidhuMeDatabazen();
        Scene scene = new Scene(new StackPane());

        LoginApp loginApp = new LoginApp(scene);
        loginApp.showLoginScreen();

        stage.setScene(scene);
        stage.show();
    }
}