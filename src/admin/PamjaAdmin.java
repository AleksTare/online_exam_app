package admin;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import login.LoginApp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Controls the main application screen */
public class PamjaAdmin {

    private Scene scene;

    @FXML private Button butonLogout;
    @FXML private Button shfaqStudent;
    @FXML private Button shfaqPedagog;

    public void pamjaFillestare(final LoginApp loginApp, final Scene scene) {
        this.scene = scene;
        butonLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                scene.getWindow().setHeight(150);
                scene.getWindow().setWidth(250);
                loginApp.logout();
            }
        });
        shfaqStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shfaqPamjenStudent(loginApp);
            }
        });
        shfaqPedagog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shfaqPamjenPedagog(loginApp);
            }
        });
    }

    private void shfaqPamjenStudent(final LoginApp loginApp) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("student.fxml")
            );
            scene.setRoot((Parent) loader.load());
            PamjaStudent controller =
                    loader.<PamjaStudent>getController();
            controller.shfaqListenEStudenteve(loginApp);
        } catch (IOException  ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void shfaqPamjenPedagog(final LoginApp loginApp) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("pedagog.fxml")
            );
            scene.setRoot(loader.load());
            PamjaPedagog controller =
                    loader.<PamjaPedagog>getController();
            controller.shfaqListenPedagog(loginApp);
        } catch (IOException  ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}