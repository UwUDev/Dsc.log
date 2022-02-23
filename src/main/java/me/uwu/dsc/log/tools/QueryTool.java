package me.uwu.dsc.log.tools;

import me.uwu.dsc.log.database.DBManager;
import me.uwu.dsc.log.struct.Message;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryTool {
    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static void queryMessageContaining(String text, boolean ignoreCase) {
        System.out.println();
        printData(DBManager.getMessagesContaining(text, ignoreCase));

    }

    public static void queryMessageWithRegex(String regex) {
        printData(DBManager.getMessagesWithRegex(Pattern.compile(regex)));
    }

    public static void extractUrls() {
        List<Message> messages = DBManager.getMessagesWithRegex(urlPattern);
        StringBuilder links = new StringBuilder();
        messages.forEach(message -> {
            Matcher matcher = urlPattern.matcher(message.getContent());
            while (matcher.find()) {
                int matchStart = matcher.start(1);
                int matchEnd = matcher.end();

                final String replace = message.getContent().substring(matchStart, matchEnd).replace("0", "");

                if (!links.toString().contains(replace)) {
                    links.append(replace).append("\n");
                }
            }
        });
        System.out.println(links);
        System.out.println("\n\u001B[33mPress ENTER to go back.");
        new Scanner(System.in).nextLine();
    }

    public static void extractGift() {
        List<Message> messages = DBManager.getMessagesWithRegex(urlPattern);
        StringBuilder links = new StringBuilder();
        messages.forEach(message -> {
            Matcher matcher = urlPattern.matcher(message.getContent());
            while (matcher.find()) {
                int matchStart = matcher.start(1);
                int matchEnd = matcher.end();

                final String replace = message.getContent().substring(matchStart, matchEnd).replace("0", "");

                if (replace.contains("discord.gift") || replace.contains("discord.com/gifts")) {
                    if (!links.toString().contains(replace)) {
                        links.append(replace).append("\n");
                    }
                }
            }
        });
        System.out.println(links);
        System.out.println("\n\u001B[33mPress ENTER to go back.");
        new Scanner(System.in).nextLine();
    }

    private static void printData(List<Message> messages) {
        messages.forEach(
                message -> System.out.println(
                        "\u001B[34m" +
                                message.getAuthor().getTag() +
                                "\u001B[0m :  \u001B[36m" +
                                message.getContent()
                )
        );
        System.out.println("\n\u001B[35mEnter E to export.\n\u001B[33mPress ENTER to go back.");

        if(new Scanner(System.in).nextLine().equalsIgnoreCase("E")) {
            // TODO: 23/02/2022 export
            System.err.println("Export not implemented yet :(");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
