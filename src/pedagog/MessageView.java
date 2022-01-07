package pedagog;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import login.LoginApp;
import user.Message;
import user.MessageCRUD;
import user.Role;
import user.User;
import user.UserCRUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageView {


    @FXML public TableView<Map.Entry<Message,User>> tableView;
    @FXML public TableColumn col1Name;
    @FXML TableColumn col2Name;
    @FXML private TextArea messageField;
    @FXML public Button backButton;
    @FXML public Label errorLabel;

    void showMessageView(LoginApp loginApp) {
        Map<Message, User> messageLog = new HashMap<>();
        messageField.setEditable(false);
        backButton.setOnAction(event -> loginApp.showRolePage(Role.PEDAGOG));

        TableColumn<Map.Entry<Message, User>, Message> column1 = new TableColumn<>("Titulli");
        column1.setCellValueFactory(p -> {
            // for first column we use key
            return new SimpleObjectProperty<>(p.getValue().getKey());
        });

        TableColumn<Map.Entry<Message, User>, User> column2 = new TableColumn<>("Derguesi");
        column2.setCellValueFactory(p -> {
            // for second column we use value
            return new SimpleObjectProperty<User>(p.getValue().getValue());
        });

        ObservableList<Map.Entry<Message, User>> items = FXCollections.observableArrayList(messageLog.entrySet());

        tableView.setItems(items);
        tableView.getColumns().setAll(column2);


        ObservableList<Map.Entry<Message,User>> messages = tableView.getItems();
        messages.clear();
        List<Message> inboxList = MessageCRUD.getMessagesInbox(LoginApp.getUserId());
        for(Message msg: inboxList){
            User sender = UserCRUD.getUserById(msg.getFromUserId());
            messageLog.put(msg,sender);
        }
        messages.setAll(messageLog.entrySet());
        tableView.setItems(messages);

        tableView.setRowFactory(tv -> {
            TableRow<Map.Entry<Message,User>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if ((! row.isEmpty()) ) {
                    Map.Entry<Message,User> rowData = row.getItem();
                    messageField.setText(rowData.getKey().getMessageText());
                }
            });
            return row ;
        });
    }

}
