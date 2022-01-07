package pedagog;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import login.LoginApp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Controls the main application screen */
public class PedagogViewController {

    private Scene scene;

    @FXML private Button logoutButton;
    @FXML private Button editProfile;
    @FXML private Button showCourses;
    @FXML private Button showMessages;

    public void initializeView(final LoginApp loginApp, final Scene scene) {
        this.scene = scene;
        logoutButton.setOnAction(event -> {
            scene.getWindow().setHeight(150);
            scene.getWindow().setWidth(250);
            loginApp.logout();
        });
        editProfile.setOnAction(event -> showEditProfile(loginApp));
        showCourses.setOnAction(event -> showCourseList(loginApp));
        showMessages.setOnAction(event -> showMessageView(loginApp));
    }

    private void showEditProfile(final LoginApp loginApp) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("pedagogprofile.fxml")
            );
            scene.getWindow().setHeight(500);
            scene.getWindow().setWidth(700);
            scene.setRoot(loader.load());
            EditProfileView controller =
                    loader.getController();
            controller.showEditProfile(loginApp);
        } catch (IOException  ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showCourseList(final LoginApp loginApp){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("courselistview.fxml")
            );
            scene.getWindow().setHeight(500);
            scene.getWindow().setWidth(700);
            scene.setRoot((Parent) loader.load());
            PamjaKurseve controller =
                    loader.<PamjaKurseve>getController();
            controller.showCourseList(loginApp, scene);
        } catch (IOException  ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMessageView(final LoginApp loginApp){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("messageview.fxml")
            );
            scene.setRoot(loader.load());
            MessageView controller =
                    loader.getController();
            controller.showMessageView(loginApp);
        } catch (IOException  ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}