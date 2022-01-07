package kursi;

import database.DatabaseClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenaxhimPyetjesh {

    public static List<Pyetje> getQuestionsFromTestId(int testId){

        List<Pyetje> pyetjeList = new ArrayList<>();
        String query = "SELECT question_id, question_text, correct " +
                "FROM question " +
                "WHERE test_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, testId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Pyetje pyetje = new Pyetje(
                        rset.getInt("question_id"),
                        rset.getString("question_text"),
                        rset.getBoolean("correct"),
                        testId
                );
                pyetjeList.add(pyetje);
            }
        } catch (Exception e) {
            System.out.println("Error while getting question data from DB");
        }
        return pyetjeList;

    }

    public static Pyetje getQuestionById(int questionId){
        String query = "SELECT question_id, question_text, correct, test_id " +
                "FROM question " +
                "WHERE question_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, questionId);
            ResultSet rset = preparedStatement.executeQuery();
            if (rset.next()) {
                return  new Pyetje(
                        rset.getInt("question_id"),
                        rset.getString("question_text"),
                        rset.getBoolean("correct"),
                        rset.getInt("test_id")
                );
            }
        } catch (Exception e) {
            System.out.println("Error while getting question data from DB");

        }
        return null;
    }

    public static boolean saveQuestion(Pyetje pyetje) {
        String addQuery = "INSERT INTO `question` (`question_text`, `correct`, `test_id`) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(addQuery);
            preparedStatement.setString(1, pyetje.getQuestionText());
            preparedStatement.setBoolean(2, pyetje.getCorrect());
            preparedStatement.setInt(3, pyetje.getTestId());
            preparedStatement.execute();
            System.out.println("Saved Question " + pyetje.getQuestionText() + " to DB");
            return true;
        } catch (Exception e) {
            System.out.println("Error while saving Question to DB");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveAnswers(List<Pyetje> pyetjeList, int userId) {
        String addQuery = "INSERT INTO `test_question_answer` (`test_id`, `question_id`, `answer`, `user_id`) ";
        try {
            StringBuilder values = new StringBuilder("VALUES ");
            for(Pyetje pyetje : pyetjeList){
                values.append("(")
                        .append(pyetje.getTestId()).append(",")
                        .append(pyetje.getQuestionId()).append(",")
                        .append(pyetje.getCorrect()).append(",")
                        .append(userId)
                        .append("),\n");
            }
            values.replace(values.lastIndexOf(","), values.length(),";");
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(addQuery + values);
            preparedStatement.execute();

            System.out.println("Saved answers of test " + pyetjeList.get(0).getTestId());
            return true;
        } catch (Exception e) {
            System.out.println("Error while saving answers to DB");
            e.printStackTrace();
        }
        return false;

    }

    public static List<Pyetje> getAnswersOfStudent(int testId, int userId) {

        List<Pyetje> answerList = new ArrayList<>();
        String query = "SELECT question.question_id, question_text, answer " +
                "FROM question " +
                "INNER JOIN test_question_answer ON question.question_id = test_question_answer.question_id " +
                "WHERE test_question_answer.test_id = ? AND test_question_answer.user_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, testId);
            preparedStatement.setInt(2, userId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Pyetje pyetje = new Pyetje(
                        rset.getInt("question_id"),
                        rset.getString("question_text"),
                        rset.getBoolean("answer"),
                        testId
                );
                answerList.add(pyetje);
            }
        } catch (Exception e) {
            System.out.println("Error while getting question data from DB");
            e.printStackTrace();
        }
        return answerList;
    }
}
