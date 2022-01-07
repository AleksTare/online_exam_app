package student;

import kursi.Kursi;
import kursi.MenaxhimKursi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import login.LoginApp;
import user.Role;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseListView {

    private Scene scene;

    @FXML
    private TableView<Kursi> tableView;
    @FXML
    private TextField courseNameField;
    @FXML
    public Label errorLabel;
    @FXML
    public Button backButton;


    public void showCourseList(final LoginApp loginApp, final Scene scene) {
        this.scene = scene;
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginApp.showRolePage(Role.STUDENT);
            }
        });
        ObservableList<Kursi> kursiList = tableView.getItems();
        kursiList.clear();
        kursiList.addAll(MenaxhimKursi.getEnrolledCourses(LoginApp.getUserId()));
        viewTestsButton(loginApp);
        tableView.setItems(kursiList);
    }


    private void viewTestsButton(LoginApp loginApp){
        TableColumn actionCol = new TableColumn("Teste");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Kursi, String>, TableCell<Kursi, String>> cellFactory
                = new Callback<TableColumn<Kursi, String>, TableCell<Kursi, String>>() {
            @Override
            public TableCell call(final TableColumn<Kursi, String> param) {
                return new TableCell<Kursi, String>() {

                    final Button btn = new Button("Shiko Teste");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Kursi kursi = getTableView().getItems().get(getIndex());
                                showTestList(loginApp, kursi.getCourseId());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };

        actionCol.setCellFactory(cellFactory);
        tableView.getColumns().add(actionCol);
    }

    private void showTestList(final LoginApp loginApp, int courseId){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("testlistview.fxml")
            );
            scene.getWindow().setHeight(500);
            scene.getWindow().setWidth(700);
            scene.setRoot((Parent) loader.load());
            TestListView controller =
                    loader.<TestListView>getController();
            controller.showTestList(loginApp, courseId);
        } catch (IOException ex) {
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
