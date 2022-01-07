package pedagog;

import kursi.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import login.LoginApp;
import user.Role;
import java.sql.Date;
import java.time.LocalDate;

public class TestListView {

    @FXML public TableView<Pyetje> questionTableView;
    @FXML public TextField newQuestionText;
    @FXML public ComboBox newQuestionAnswer;
    @FXML private TableView<Test> testTableView;
    @FXML private TextField description;
    @FXML public Label errorLabel;
    @FXML public Button backButton;

    private int courseId;


    public void showTestList(LoginApp loginApp, int courseId) {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginApp.showRolePage(Role.PEDAGOG);
            }
        });
        testTableView.setRowFactory(tv -> {
            TableRow<Test> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if ((! row.isEmpty()) ) {
                    Test rowData = row.getItem();
                    ObservableList<Pyetje> pyetjes = questionTableView.getItems();
                    pyetjes.clear();
                    pyetjes.addAll(MenaxhimPyetjesh.getQuestionsFromTestId(rowData.getTestId()));
                    questionTableView.setItems(pyetjes);
                }
            });
            return row ;
        });
        this.courseId = courseId;
        ObservableList<Test> testList = testTableView.getItems();
        testList.clear();
        testList.addAll(MenaxhimTestesh.getTestsFromCourse(courseId));
        testTableView.setItems(testList);
    }

    public void saveTest(ActionEvent actionEvent) {
        if (description.getText().equals("")) {
            errorLabel.setText("Te gjitha te dhenat jane te detyrueshme");
        } else {

            errorLabel.setText("");
            Test newTest = new Test(description.getText(),
                    courseId);

            boolean success = MenaxhimTestesh.saveTest(newTest);
            if (!success) {
                errorLabel.setText("Testi nuk mund te shtohej ne Databaze");
            } else {
                ObservableList<Test> testList = testTableView.getItems();
                testList.clear();
                testList.addAll(MenaxhimTestesh.getTestsFromCourse(courseId));
                testTableView.setItems(testList);
            }
            description.setText("");
        }
    }

    public void saveQuestion(ActionEvent actionEvent) {
        if (newQuestionText.getText().equals("")) {
            errorLabel.setText("Te gjitha te dhenat jane te detyrueshme");
        } else {
            errorLabel.setText("");
            Test selectedTest = testTableView.getSelectionModel().getSelectedItem();
            if(selectedTest != null){
                Pyetje pyetje = new Pyetje(newQuestionText.getText(), newQuestionAnswer.getValue().equals("Yes"), selectedTest.getTestId());
                boolean success = MenaxhimPyetjesh.saveQuestion(pyetje);
                if (!success) {
                    errorLabel.setText("Pyetja nuk mund te shtohej ne Databaze");
                } else {
                    ObservableList<Pyetje> pyetjes = questionTableView.getItems();
                    pyetjes.clear();
                    pyetjes.addAll(MenaxhimPyetjesh.getQuestionsFromTestId(selectedTest.getTestId()));
                    questionTableView.setItems(pyetjes);
                }
                newQuestionText.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Duhet zgjedhur nje test te cilit i perket pyetja");
                alert.show();
            }
        }
    }
}
