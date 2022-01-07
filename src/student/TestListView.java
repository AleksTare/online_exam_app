package student;

import kursi.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.LoginApp;
import user.Role;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TestListView {

    @FXML public TableView<Pyetje> questionTableView;
    @FXML public Button finishTestButton;
    @FXML private TableView<Test> testTableView;
    @FXML public Label errorLabel;
    @FXML public Button backButton;

    private int loggedInUserId;
    private int selectedCourseId;


    void showTestList(LoginApp loginApp, int courseId) {
        finishTestButton.setDisable(true);
        backButton.setOnAction(event -> loginApp.showRolePage(Role.STUDENT));
        testTableView.setRowFactory(tv -> {
            TableRow<Test> row = new TableRow<>();
            row.setOnMouseClicked(addRowData(row));
            return row;
        });
        questionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loggedInUserId = LoginApp.getUserId();
        selectedCourseId = courseId;
        ObservableList<Test> testList = testTableView.getItems();
        testList.clear();
        testList.addAll(MenaxhimTestesh.getTestsFromCourse(courseId));
        testTableView.setItems(testList);
    }


    private void addCheckboxToQuestion() {
        TableColumn<Pyetje, Boolean> checkBoxCol = new TableColumn<>("Pergjigje");
        checkBoxCol.setCellValueFactory(new PropertyValueFactory<>("correct"));
        checkBoxCol.setCellFactory(p -> new CheckBoxTableCell<Pyetje, Boolean>());
        checkBoxCol.setEditable(true);
        questionTableView.getColumns().add(checkBoxCol);
        questionTableView.setEditable(true);
    }

    private void addResultsColumn() {
        TableColumn<Pyetje, String> questionResult = new TableColumn<>("Rezultati");
        questionResult.setCellValueFactory(new PropertyValueFactory<>("correct"));
        //Set results column logic
        questionResult.setCellValueFactory(cellData -> {
            Pyetje studentPyetje = cellData.getValue();
            Pyetje actualQuestion = MenaxhimPyetjesh.getQuestionById(studentPyetje.getQuestionId());
            return Bindings.createStringBinding(
                    () -> {
                        assert actualQuestion != null;
                        if (studentPyetje.getCorrect().equals(actualQuestion.getCorrect())) {
                            return "Sakte";
                        } else {
                            return "Gabim";
                        }
                    }
            );
        });
        questionTableView.getColumns().add(questionResult);
    }


    public void finishTest(ActionEvent actionEvent) {
        ObservableList<Pyetje> answers = questionTableView.getItems();
        errorLabel.setText("");
        boolean success = MenaxhimPyetjesh.saveAnswers(answers, loggedInUserId);
        if (success) {
            updateTableAfterFinishing(answers.get(0).getTestId());
        } else {
            errorLabel.setText("Testi nuk u prefundua me sukses");
        }
    }

    private EventHandler<MouseEvent> addRowData(TableRow<Test> row) {
        return event -> {
            if ((!row.isEmpty())) {
                Test testInProgress = row.getItem();
                List<Test> userTests = MenaxhimTestesh.getFinshedTestsFromUser(loggedInUserId);

                ObservableList<Pyetje> pyetjes = questionTableView.getItems();
                List<Pyetje> unansweredPyetjes = MenaxhimPyetjesh.getQuestionsFromTestId(testInProgress.getTestId());
                for (Pyetje pyetje : unansweredPyetjes) { // Clear all questions
                    pyetje.setCorrect(false);
                }
                pyetjes.clear();

                Optional<TableColumn<Pyetje, ?>> resultsColumnExists = questionTableView.getColumns().stream()
                        .filter(p -> p.getText().equals("Rezultati"))
                        .findAny();

                // check if test is active and user has not submitted this test before
                Date now = new Date(System.currentTimeMillis());
                if (!userTests.contains(testInProgress)) {

                    finishTestButton.setDisable(false); // Enable Finish test button
                    questionTableView.setEditable(true); // Disable Test editing

                    //Add unanswered questions to table
                    pyetjes.addAll(unansweredPyetjes);
                    //Remove column which displays result
                    resultsColumnExists.ifPresent(questionTableColumn -> questionTableView.getColumns().remove(questionTableColumn));

                } else {

                    //Test is done
                    //Show Results column
                    if (!resultsColumnExists.isPresent()) {
                        addResultsColumn();
                    }

                    //Display student answers
                    List<Pyetje> answeredQuestions = MenaxhimPyetjesh.getAnswersOfStudent(testInProgress.getTestId(), loggedInUserId);
                    if (answeredQuestions.isEmpty()) {
                        pyetjes.addAll(unansweredPyetjes);
                    } else {
                        pyetjes.addAll(answeredQuestions);
                    }


                    //Disable finish button
                    finishTestButton.setDisable(true);
                    questionTableView.setEditable(false);
                }

                questionTableView.setItems(pyetjes);

                //Logic to add checkbox column
                Optional<TableColumn<Pyetje, ?>> pergjigjeColumExists = questionTableView.getColumns().stream()
                        .filter(p -> p.getText().equals("Pergjigje"))
                        .findAny();
                if (!pergjigjeColumExists.isPresent()) {
                    addCheckboxToQuestion();
                }
            }
        };
    }


    private void updateTableAfterFinishing(int testId){
        Optional<TableColumn<Pyetje, ?>> resultsColumnExists = questionTableView.getColumns().stream()
                .filter(p -> p.getText().equals("Rezultati"))
                .findAny();
        ObservableList<Pyetje> pyetjes = questionTableView.getItems();
        List<Pyetje> unansweredPyetjes = MenaxhimPyetjesh.getQuestionsFromTestId(testId);
        for (Pyetje pyetje : unansweredPyetjes) { // Clear all questions
            pyetje.setCorrect(false);
        }
        pyetjes.clear();
        //Test is done
        //Show Results column
        if (!resultsColumnExists.isPresent()) {
            addResultsColumn();
        }

        //Display student answers
        List<Pyetje> answeredQuestions = MenaxhimPyetjesh.getAnswersOfStudent(testId, loggedInUserId);
        if (answeredQuestions.isEmpty()) {
            pyetjes.addAll(unansweredPyetjes);
        } else {
            pyetjes.addAll(answeredQuestions);
        }


        //Disable finish button
        finishTestButton.setDisable(true);
        questionTableView.setEditable(false);
        questionTableView.setItems(pyetjes);
    }

}
