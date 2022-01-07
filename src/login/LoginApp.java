package login;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.*;

import admin.PamjaAdmin;
import database.DatabaseClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import pedagog.PedagogViewController;
import student.StudentViewController;
import user.Role;

/**
 * Manages control flow for logins
 */
public class LoginApp {
    private Scene scene;
    private static int userId;

    public LoginApp(Scene scene) {
        this.scene = scene;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void showRolePage(Role role) {
        switch (role) {
            case ADMIN: {
                showMainView();
                break;
            }
            case PEDAGOG:{
                showPedagogView();
                break;
            }
            case STUDENT:{
                showStudentView();
                break;
            }
        }
    }



    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            scene.setRoot((Parent) loader.load());
            PamjaLogin controller =
                    loader.<PamjaLogin>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../admin/admin.fxml")
            );
            scene.getWindow().setHeight(500);
            scene.getWindow().setWidth(700);
            scene.setRoot((Parent) loader.load());
            PamjaAdmin controller =
                    loader.<PamjaAdmin>getController();
            controller.pamjaFillestare(this, scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showStudentView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../student/studentview.fxml")
            );
            scene.getWindow().setHeight(500);
            scene.getWindow().setWidth(700);
            scene.setRoot((Parent) loader.load());
            StudentViewController controller =
                    loader.<StudentViewController>getController();
            controller.initializeView(this, scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showPedagogView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../pedagog/pedagogview.fxml")
            );
            scene.getWindow().setHeight(500);
            scene.getWindow().setWidth(700);
            scene.setRoot((Parent) loader.load());
            PedagogViewController controller =
                    loader.<PedagogViewController>getController();
            controller.initializeView(this, scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Role authorize(String username, String password) {
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement("select role_name, user_id from useri inner join role on role.role_id = useri.role_id  where username = ? and password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rset = preparedStatement.executeQuery();
            if (rset.next()) {
                String role = rset.getString(1);
                userId = rset.getInt(2);
                return Role.valueOf(role);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static int getUserId() {
        return userId;
    }
}