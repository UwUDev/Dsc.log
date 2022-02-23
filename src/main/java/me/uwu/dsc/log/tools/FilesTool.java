package me.uwu.dsc.log.tools;

import com.google.gson.Gson;
import me.uwu.dsc.log.database.DBManager;
import me.uwu.dsc.log.struct.Attachment;
import me.uwu.dsc.log.struct.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class FilesTool {
    public static Message getFileMessage(String media){
        String id = media.split("\\.")[0];
        String extension = media.split("\\.")[1];
        String sql = "SELECT id, attachments FROM messages";
        try (Connection conn = DBManager.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            Gson gson = new Gson();
            while (rs.next()) {
                String attachmentsJson = rs.getString("attachments");
                if (attachmentsJson.contains(id)){
                    try {
                        for (Attachment attachment : gson.fromJson(attachmentsJson, Attachment[].class)) {
                            if (attachment.getUrl().contains(id) && attachment.getUrl().toLowerCase().endsWith(extension.toLowerCase())){
                                Message message = DBManager.getMessageByID(rs.getLong("id"));
                                if (message != null) return message;
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
