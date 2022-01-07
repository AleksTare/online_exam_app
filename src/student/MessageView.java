package student;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import login.LoginApp;
import user.Message;
import user.MessageCRUD;
import user.Role;
import user.User;
import user.UserCRUD;

public class MessageView {


    @FXML public TableView<User> tableView;
    @FXML public TextArea messageField;
    @FXML public Button backButton;
    @FXML public Label errorLabel;
    @FXML public Label successLabel;

    private int loggedInUserId;

    void showMessageView(LoginApp loginApp) {
        backButton.setOnAction(event -> loginApp.showRolePage(Role.STUDENT));
        loggedInUserId = LoginApp.getUserId();
        ObservableList<User> pedagoget = tableView.getItems();
        pedagoget.clear();
        pedagoget.addAll(UserCRUD.getAllUsersByRole(Role.PEDAGOG));
        tableView.setItems(pedagoget);
    }

    public void sendMessage(ActionEvent actionEvent) {
        errorLabel.setText("");
        successLabel.setText("");
        if (messageField.getText().equals("")) {
            errorLabel.setText("Te gjitha te dhenat jane te detyrueshme");
            return;
        }
        errorLabel.setText("");
        User recipient = tableView.getSelectionModel().getSelectedItem();
        if (recipient == null) {
            errorLabel.setText("Zgjidhni nje marres per mesazhin");
            return;
        }

        boolean success = MessageCRUD.saveMessage(new Message(loggedInUserId, recipient.getId(),messageField.getText()));
        if (!success) {
            errorLabel.setText("Ndryshimet nuk mund te ruhen ne Databaze");
        } else {
            successLabel.setText("Mesazhi u dergua me sukses!");
        }
        messageField.setText("");
    }
}
