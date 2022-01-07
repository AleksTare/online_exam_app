package user;

import database.DatabaseClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCRUD {

    public static List<User> getAllUsersByRole(Role role) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT  user_id, name, surname, username, password, role.role_name AS role_name " +
                "FROM useri INNER JOIN role ON role.role_id = useri.role_id " +
                "WHERE role.role_name = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setString(1, role.toString());
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                User user = new User(
                        rset.getInt("user_id"),
                        rset.getString("name"),
                        rset.getString("surname"),
                        rset.getString("username"),
                        rset.getString("password"),
                        role
                );
                userList.add(user);
            }
        } catch (Exception e) {
            System.out.println("Error while getting user data from DB");
        }
        return userList;
    }

    public static boolean saveUser(User user) {
        String addQuery = "INSERT INTO `useri` (`username`, `password`, `name`, `surname`, `role_id`) " +
                "VALUES (?, ?, ?, ?, ?)";
        String roleQuery = "SELECT * FROM role";
        Map<String, Integer> roleMap = new HashMap<>();
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(roleQuery);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                roleMap.put(rset.getString("role_name"),
                        rset.getInt("role_id"));
            }
            if (!roleMap.isEmpty()) {
                preparedStatement = DatabaseClass.prepareStatement(addQuery);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getSurname());
                preparedStatement.setInt(5, roleMap.get(user.getRole().toString()));
                preparedStatement.execute();
                System.out.println("Saved user " + user.getUsername() + " to DB");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error while saving user to DB");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUser(int userId) {
        String deleteQuery = "DELETE FROM useri WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
            System.out.println("Removed user with ID = " + userId + " from DB");
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting user");
            e.printStackTrace();
        }
        return false;
    }

    public static User getUserById(int userId) {
        User user = null;
        String query = "SELECT name, surname, username, password, role.role_name AS role_name " +
                "FROM useri INNER JOIN role ON role.role_id = useri.role_id " +
                "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rset = preparedStatement.executeQuery();
            if (rset.next()) {
                user = new User(
                        userId,
                        rset.getString("name"),
                        rset.getString("surname"),
                        rset.getString("username"),
                        rset.getString("password"),
                        Role.valueOf(rset.getString("role_name"))
                );
            }
        } catch (Exception e) {
            System.out.println("Error while getting user data from DB");
        }
        return user;
    }


    public static boolean updateUser(User user) {
        String updateQuery = "UPDATE `useri` t SET t.`username` = ?, t.`password` = ?, t.`name` = ?, t.`surname` = ? WHERE t.`user_id` = ?";
        try {
            int userId = user.getId();
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(updateQuery);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setInt(5, userId);
            preparedStatement.execute();
            System.out.println("Updated user " + user.getUsername() + " to DB");
            return true;
        } catch (Exception e) {
            System.out.println("Error while saving user to DB");
            e.printStackTrace();
        }
        return false;
    }

}
