package me.uwu.dsc.log.database;

import com.google.gson.Gson;
import me.uwu.dsc.log.event.impl.MessageReceived;
import me.uwu.dsc.log.struct.Author;
import me.uwu.dsc.log.struct.CompactMessage;

import java.sql.*;

public class DBManager {
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:logs.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void addNewMessage(MessageReceived message, long timestamp) {
        //                                 1   2      3              4          5           6            7         8            9            10   11    12          13         14        15         16      17        18        19
        String sql = "INSERT INTO messages(id,tts,timestamp,referencedMessage,nonce,messageReference,mentions,mentionRoles,mentionEveryone,type,flags,embeds,editedTimestamp,content,components,channelId,author,attachments,deleted) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Gson gson = new Gson();

        long refId = 0;
        if (message.getReferencedMessage() != null)
            refId = message.getReferencedMessage().getId();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, message.getId());
                pstmt.setBoolean(2, message.isTts());
                pstmt.setLong(3, timestamp);
                pstmt.setLong(4, refId);
                pstmt.setLong(5, message.getNonce());
                pstmt.setString(6, gson.toJson(message.getMessageReference()));
                pstmt.setString(7, gson.toJson(message.getMentions()));
                pstmt.setString(8, gson.toJson(message.getMentionRoles()));
                pstmt.setBoolean(9, message.isMentionEveryone());
                pstmt.setLong(10, message.getType());
                pstmt.setLong(11, message.getFlags());
                pstmt.setString(12, gson.toJson(message.getEmbeds()));
                pstmt.setLong(13, -1);
                pstmt.setString(14, message.getContent());
                pstmt.setString(15, gson.toJson(message.getComponents()));
                pstmt.setLong(16, message.getChannelId());
                pstmt.setString(17, gson.toJson(message.getAuthor()));
                pstmt.setString(18, gson.toJson(message.getAttachments()));
                pstmt.setBoolean(19, false);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteMessage(long messageId) {
        String sql = "UPDATE messages SET deleted = ? , "
                + "deletedTimestamp = ? "
                + "WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setLong(2, System.currentTimeMillis());
            pstmt.setLong(3, messageId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static CompactMessage getCompactMessage(long id) {
        String sql = "SELECT content, author FROM messages WHERE id = " + id;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                String username = new Gson().fromJson(rs.getString("author"), Author.class).getUsername();
                String content = rs.getString("content");
                return new CompactMessage(content, username);
            } else return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
