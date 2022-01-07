package student;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import login.LoginApp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentViewController {


    private Scene scene;

    @FXML
    private Button logoutButton;
    @FXML private Button editProfile;
    @FXML private Button showCourses;
    @FXML public Button showMessages;

    public void initializeView(final LoginApp loginApp, final Scene scene) {
        this.scene = scene;
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                scene.getWindow().setHeight(150);
                scene.getWindow().setWidth(250);
                loginApp.logout();
            }
        });
        editProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showEditProfile(loginApp);
            }
        });
        showCourses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showCourseList(loginApp);
            }
        });
        showMessages.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showMessageView(loginApp);
            }
        });
    }

    private void showEditProfile(final LoginApp loginApp) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("studentprofile.fxml")
            );
            scene.setRoot((Parent) loader.load());
           EditProfileView controller =
                    loader.<EditProfileView>getController();
            controller.showEditProfile(loginApp);
        } catch (IOException ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showCourseList(final LoginApp loginApp){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("courselistview.fxml")
            );
            scene.setRoot((Parent) loader.load());
            CourseListView controller =
                    loader.<CourseListView>getController();
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
            scene.setRoot((Parent) loader.load());
            MessageView controller =
                    loader.<MessageView>getController();
            controller.showMessageView(loginApp);
        } catch (IOException  ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
