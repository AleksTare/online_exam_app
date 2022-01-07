package kursi;

import database.DatabaseClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenaxhimTestesh {

    public static List<Test> getTestsFromCourse(int courseId) {
        List<Test> testList = new ArrayList<>();
        String query = "SELECT test_id, description, course_id " +
                "FROM test " +
                "WHERE test.course_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, courseId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Test test = new Test(
                        rset.getInt("test_id"),
                        rset.getString("description"),
                        rset.getInt("course_id")
                );
                testList.add(test);
            }
        } catch (Exception e) {
            System.out.println("Error while getting test data from DB");
        }
        return testList;
    }

    public static boolean saveTest(Test test) {
        String addQuery = "INSERT INTO `test` (`description`,  `course_id`) " +
                "VALUES (?, ?)";
        try {
                PreparedStatement preparedStatement = DatabaseClass.prepareStatement(addQuery);
                preparedStatement.setString(1, test.getDescription());
                preparedStatement.setInt(2, test.getCourseId());
                preparedStatement.execute();
                System.out.println("Saved test " + test.getDescription() + " to DB");
                return true;
        } catch (Exception e) {
            System.out.println("Error while saving test to DB");
            e.printStackTrace();
        }
        return false;
    }

    public static List<Test> getFinshedTestsFromUser(int userId) {
        List<Test> testList = new ArrayList<>();
        String query = "SELECT test.test_id AS test_id, description, course_id " +
                "FROM test " +
                "INNER JOIN test_question_answer ON test.test_id = test_question_answer.test_id " +
                "WHERE test_question_answer.user_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Test test = new Test(
                        rset.getInt("test_id"),
                        rset.getString("description"),
                        rset.getInt("course_id")
                );
                testList.add(test);
            }
        } catch (Exception e) {
            System.out.println("Error while getting test data from DB");
        }
        return testList;
    }

}
