package admin;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import login.LoginApp;
import user.Role;
import user.User;
import user.UserCRUD;

public class PamjaPedagog {


    @FXML
    private TableView<User> tabela;
    @FXML
    private TextField fushaEmer;
    @FXML
    private TextField fushaMbiemer;
    @FXML
    private TextField fushaEmail;
    @FXML
    public PasswordField fushaPassword;
    @FXML
    public Label error;
    @FXML
    public Button butoniMbrapa;

    void shfaqListenPedagog(final LoginApp loginApp) {
        butoniMbrapa.setOnAction(event -> loginApp.showRolePage(Role.ADMIN));
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<User> userList = tabela.getItems();
        userList.clear();
        userList.addAll(UserCRUD.getAllUsersByRole(Role.PEDAGOG));
        shtoButoninFshij();
        tabela.setItems(userList);
    }

    @FXML
    protected void shtoPerson(ActionEvent event) {

        if (fushaEmer.getText().equals("") ||
                fushaMbiemer.getText().equals("") ||
                fushaEmail.getText().equals("") ||
                fushaPassword.getText().equals("")) {
            error.setText("Te gjitha te dhenat jane te detyrueshme");
        } else {

            error.setText("");
            User newUser = new User(fushaEmer.getText(),
                    fushaMbiemer.getText(),
                    fushaEmail.getText(),
                    fushaPassword.getText(),
                    Role.PEDAGOG);

            boolean success = UserCRUD.saveUser(newUser);
            if (!success) {
                error.setText("Studenti nuk mund te shtohej ne Databaze");
            } else {
                ObservableList<User> userList = tabela.getItems();
                userList.clear();
                userList.addAll(UserCRUD.getAllUsersByRole(Role.PEDAGOG));
                tabela.setItems(userList);
            }
            fushaEmer.setText("");
            fushaMbiemer.setText("");
            fushaEmail.setText("");
            fushaPassword.setText("");
        }
    }

    private void shtoButoninFshij(){
        TableColumn actionCol = new TableColumn("Fshij");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory
                = new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell call(final TableColumn<User, String> param) {
                return new TableCell<User, String>() {

                    final Button btn = new Button("Fshij Pedagogun");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                User person = getTableView().getItems().get(getIndex());
                                boolean success = UserCRUD.deleteUser(person.getId());
                                if(success){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Pedagogu u fshi me sukses!");
                                    alert.show();
                                }
                                ObservableList<User> userList = getTableView().getItems();
                                userList.clear();
                                userList.addAll(UserCRUD.getAllUsersByRole(Role.PEDAGOG));
                                tabela.setItems(userList);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };

        actionCol.setCellFactory(cellFactory);
        tabela.getColumns().add(actionCol);
    }

}