package kursi;

import database.DatabaseClass;
import user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenaxhimKursi {

    public static List<Kursi> getCoursesFromUserId(int userId) {
        List<Kursi> kursiList = new ArrayList<>();
        String query = "SELECT course_id, course_name " +
                "FROM course " +
                "WHERE course.user_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Kursi kursi = new Kursi(rset.getInt("course_id"),
                        rset.getString("course_name"));
                kursiList.add(kursi);
            }
        } catch (Exception e) {
            System.out.println("Error while getting course data from DB");
        }

        return kursiList;
    }


    public static List<Kursi> getEnrolledCourses(int userId){
        List<Kursi> cours = new ArrayList<>();
        String query = "SELECT course.course_id, course.course_name " +
                "FROM course INNER JOIN course_user ON course.course_id = course_user.course_id " +
                "WHERE course_user.user_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Kursi kursi = new Kursi(rset.getInt("course_id"),
                        rset.getString("course_name"));
                cours.add(kursi);
            }
        } catch (Exception e) {
            System.out.println("Error while getting course data from DB");
        }
        return cours;
    }

    public static boolean saveCourse(Kursi newKursi, int userId) {

        String addQuery = "INSERT INTO `course` (`course_name`, `user_id`) " +
                "VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(addQuery);
            preparedStatement.setString(1, newKursi.getCourseName());
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
            System.out.println("Saved course " + newKursi.getCourseName() + " to DB");
            return true;
        } catch (Exception e) {
            System.out.println("Error while saving course to DB");
            e.printStackTrace();
        }
        return false;

    }

    public static boolean addStudentsToCourse(List<User> students, int courseId) {

        String addQuery = "INSERT INTO `course_user` (`course_id`, `user_id`) ";

        try {
            clearCourseStudents(courseId);

            StringBuilder values = new StringBuilder("VALUES ");
            for(User student : students){
                values.append("(").append(courseId).append(",").append(student.getId()).append("),\n");
            }
            values.replace(values.lastIndexOf(","), values.length(),";");
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(addQuery + values);
            preparedStatement.execute();

            System.out.println("Saved course students to DB");
            return true;
        } catch (Exception e) {
            System.out.println("Error while saving students for course to DB");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clearCourseStudents(int courseId) {

        String deleteQuery = "DELETE FROM `course_user` WHERE course_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, courseId);
            preparedStatement.execute();
            System.out.println("Cleared course " + courseId );
            return true;
        } catch (Exception e) {
            System.out.println("Error while clearing course to DB");
            e.printStackTrace();
        }
        return false;
    }
}
