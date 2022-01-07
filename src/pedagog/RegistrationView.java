package pedagog;

import kursi.MenaxhimKursi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import login.LoginApp;
import user.Role;
import user.User;
import user.UserCRUD;

import java.util.List;

public class RegistrationView {

    @FXML public Label errorLabel;
    @FXML public Button backButton;
    @FXML public ListView<User> studentsListView;
    @FXML public ListView<User> registeredListView;
    @FXML public Button unregister;
    @FXML public Button register;

    private int loggedInUserId;
    private int selectedCourseId;

    public void showStudentsLists(LoginApp loginApp, int courseId) {
        studentsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        registeredListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginApp.showRolePage(Role.PEDAGOG);
            }
        });
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<User> selection = studentsListView.getSelectionModel().getSelectedItems();
                ObservableList<User> totalStudents = studentsListView.getItems();
                ObservableList<User> registered = registeredListView.getItems();
                registered.addAll(selection);
                totalStudents.removeAll(selection);
                studentsListView.setItems(totalStudents);
                registeredListView.setItems(registered);
            }
        });
        unregister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<User> selection = registeredListView.getSelectionModel().getSelectedItems();
                ObservableList<User> totalStudents = studentsListView.getItems();
                ObservableList<User> registered = registeredListView.getItems();
                totalStudents.addAll(selection);
                registered.removeAll(selection);
                studentsListView.setItems(totalStudents);
                registeredListView.setItems(registered);
            }
        });
        loggedInUserId = LoginApp.getUserId();
        selectedCourseId = courseId;
        ObservableList<User> studentsListViewItems = studentsListView.getItems();
        studentsListViewItems.clear();
        studentsListViewItems.addAll(UserCRUD.getAllUsersByRole(Role.STUDENT));
        studentsListView.setItems(studentsListViewItems);
    }

    @FXML
    protected void saveButton(ActionEvent event) {

        errorLabel.setText("");
        List<User> students = registeredListView.getItems();
        boolean success;
        if(students.isEmpty()){
            success = MenaxhimKursi.clearCourseStudents(selectedCourseId);
        } else {
            success = MenaxhimKursi.addStudentsToCourse(students, selectedCourseId);
        }

        if (!success) {
            errorLabel.setText("Ndryshimet nuk mund te ruhen ne Databaze");
        }

    }
}
