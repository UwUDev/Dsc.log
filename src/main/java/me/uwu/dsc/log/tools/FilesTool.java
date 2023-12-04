package me.uwu.dsc.log.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.uwu.dsc.log.database.DBManager;
import me.uwu.dsc.log.struct.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class FilesTool {
    public static Message getFileMessage(long id) {
        String sql = "SELECT id, attachments FROM messages";
        try (Connection conn = DBManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Gson gson = new Gson();
            while (rs.next()) {
                String attachmentsJson = rs.getString("attachments");
                //if (attachmentsJson.contains(id)){
                try {
                    for (JsonObject attachment : gson.fromJson(attachmentsJson, JsonObject[].class)) {
                        if (attachment.get("id").getAsLong() == id) {
                            System.out.println("Found message with id " + id);
                            return DBManager.getMessageByID(rs.getLong("id"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //}
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
