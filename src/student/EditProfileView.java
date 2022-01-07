package student;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.LoginApp;
import user.Role;
import user.User;
import user.UserCRUD;

public class EditProfileView {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;
    @FXML
    public Button backButton;
    private int loggedInUserId;

    public void showEditProfile(LoginApp loginApp) {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginApp.showRolePage(Role.STUDENT);
            }
        });
        loggedInUserId = LoginApp.getUserId();
        User loggedInUser = UserCRUD.getUserById(loggedInUserId);
        firstNameField.setText(loggedInUser.getName());
        lastNameField.setText(loggedInUser.getSurname());
        emailField.setText(loggedInUser.getUsername());
        passwordField.setText(loggedInUser.getPassword());
    }

    @FXML
    protected void saveButton(ActionEvent event) {

        if (firstNameField.getText().equals("") ||
                lastNameField.getText().equals("") ||
                emailField.getText().equals("") ||
                passwordField.getText().equals("")) {
            errorLabel.setText("Te gjitha te dhenat jane te detyrueshme");
        } else {
            errorLabel.setText("");
            User newUser = new User(loggedInUserId,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    Role.STUDENT);

            boolean success = UserCRUD.updateUser(newUser);
            if (!success) {
                errorLabel.setText("Ndryshimet nuk mund te ruhen ne Databaze");
            }
        }
    }


}
