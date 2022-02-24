package me.uwu.dsc.log.tools;

import me.uwu.dsc.log.art.Ascii;
import me.uwu.dsc.log.struct.Attachment;
import me.uwu.dsc.log.struct.Mention;
import me.uwu.dsc.log.struct.Message;
import me.uwu.dsc.log.utils.ConsoleUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Tools {
    public static void openToolsMenu() {
        ConsoleUtils.drawToolsMenu();
        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            if(line.equals("0"))
                getFileInfosMenu();
            if(line.equals("1"))
                openQueryMenu();
            ConsoleUtils.drawToolsMenu();
        }
    }

    public static void getFileInfosMenu() {
        Scanner sc = new Scanner(System.in);
        ConsoleUtils.clearConsole();
        System.out.print("\u001B[34mEnter file name (including extension like 012345678910111213.png): ");
        String filename = sc.nextLine();
        Message message = FilesTool.getFileMessage(filename);
        File file = new File("dump/" + filename.split("\\.")[1] + "/" + filename);
        if (message != null) {
            ConsoleUtils.clearConsole();
            System.out.println("            " + Ascii.getFloppy(file, message.getTimestamp()).replace("\n", "\n            "));
            System.out.println("\n");

            if (!message.isDeleted())
                System.out.println("\u001B[32mThe orginal messsage should not be deleted :)");
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy,,HH:mm:ss");
                Date resultdate = new Date(message.getDeletedTimestamp());
                String[] dateString = sdf.format(resultdate).split(",,");
                System.out.println("\u001B[31mThe orginal messsage has been deleted " + dateString[0] + " at " + dateString[1] + " :(");
            }
            System.out.println("\u001B[0mImage author is \u001B[36m" +
                        message.getAuthor().getTag() + "\u001B[0m  (\u001B[34m"+
                        message.getAuthor().getId() + "\u001B[0m)"
                    );

            for (Attachment attachment : message.getAttachments())
                if (attachment.getUrl().contains(filename.split("\\.")[0])){
                    System.out.println("\u001B[35mFile url: \u001B[34m" + attachment.getUrl().replace("cdn.discordapp.com", "media.discordapp.net"));
                    break;
                }

            if (message.getContent().length() > 0)
                System.out.println("\u001B[36m" + message.getAuthor().getUsername() + " \u001B[35msaid: \u001B[34m" + message.getContent());

            if (message.isMentionEveryone())
                System.out.println("\u001B[31m" + message.getAuthor().getUsername() + " mentioned @everyone >:(\u001B[0m");

            if (message.getMentions().length > 0) {
                System.out.println("\u001B[36m" + message.getAuthor().getUsername() + " \u001B[35mmentioned " + message.getMentions().length + " members:");
                for (Mention mention : message.getMentions()) {
                    System.out.println("\u001B[0m  - \u001B[36m" + mention.getTag() + "  \u001B[0m  (\u001B[34m" + mention.getId() + "\u001B[0m)");
                }
            }

            System.out.println("\u001B[35mThe message URL is: \u001B[34m" + message.getUrl());

            if (message.getMessageReference() != null)
                System.out.println("\u001B[35mThe message refers to: \u001B[34m" + message.getMessageReference().getUrl());
            System.out.println("\n\n\u001B[33mPress enter to go back\u001B[0m");
        } else {
            System.err.println("File " + filename + " has not been dumped/logged or is malformed\nTry again.\nPress enter to go back.");
        }
        sc.nextLine();
    }

    public static void openQueryMenu() {
        Scanner sc = new Scanner(System.in);
        ConsoleUtils.drawQueryMenu();
        String line = "";
        while (!line.equals("b")) {
            line = sc.nextLine();
            switch (line) {
                case "0":
                    ConsoleUtils.clearConsole();
                    System.out.print("\u001B[34mEnter text you want to search: ");
                    QueryTool.queryMessageContaining(sc.next(), false);
                    break;
                case "1":
                    ConsoleUtils.clearConsole();
                    System.out.print("\u001B[34mEnter text you want to search (ignoring case mode): ");
                    QueryTool.queryMessageContaining(sc.next(), true);
                    break;
                case "2":
                    ConsoleUtils.clearConsole();
                    System.out.print("\u001B[34mEnter regex you want to search: ");
                    QueryTool.queryMessageWithRegex(sc.next());
                    break;
                case "3":
                    ConsoleUtils.clearConsole();
                    QueryTool.extractUrls();
                    break;
                case "4":
                    ConsoleUtils.clearConsole();
                    QueryTool.extractGift();
                    break;
            }
            ConsoleUtils.drawQueryMenu();
        }
    }
}
