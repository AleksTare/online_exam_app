package login;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import user.Role;

/** Controls the login screen */
public class PamjaLogin {
    @FXML public Label errorLabel;
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Button loginButton;

    public void initManager(final LoginApp loginApp) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Role role = authorize(loginApp);
                if (role != null) {
                    loginApp.showRolePage(role);
                } else {
                    errorLabel.setText("Username/ Password Incorrect");
                }
            }
        });
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a Role for the authorized session
     * otherwise, return null.
     */
    private Role authorize(LoginApp loginApp) {
        return loginApp.authorize(user.getText(), password.getText());
    }

}