package user;

import database.DatabaseClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageCRUD {

    public static List<Message> getMessagesInbox(int userId) {
        List<Message> messageList = new ArrayList<>();
        String query = "SELECT from_uid, to_uid, message " +
                "FROM message " +
                "WHERE to_uid = ?";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rset = preparedStatement.executeQuery();
            while (rset.next()) {
                Message course = new Message(
                        rset.getInt("from_uid"),
                        rset.getInt("to_uid"),
                        rset.getString("message")
                );
                messageList.add(course);
            }
        } catch (Exception e) {
            System.out.println("Error while getting message data from DB");
        }

        return messageList;
    }


    public static boolean saveMessage(Message newMessage) {

        String addQuery = "INSERT INTO `message` (`from_uid`, `to_uid`, `message`) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = DatabaseClass.prepareStatement(addQuery);
            preparedStatement.setInt(1, newMessage.getFromUserId());
            preparedStatement.setInt(2, newMessage.getToUserId());
            preparedStatement.setString(3, newMessage.getMessageText());
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error while sending message to DB");
            e.printStackTrace();
        }
        return false;

    }

}
