package me.uwu.dsc.log.stats;

import me.uwu.dsc.log.utils.ConsoleUtils;

public class Stats {
    public static int messageReceived = 0;
    public static String graphic = "";
    private static int[] messageRecievedHistory = new int[]{0,1,0,0,0,0};
    private static boolean running = false;
    private static final Graph2D graph = new Graph2D(100);
    private static final Thread thread = new Thread(() ->{
        graph.clear();
        while (running) {
            updateArray();
            int total = 0;
            for (int i : messageRecievedHistory)
                total += i;
            graph.addValue(total);
            graphic = graph.getGraph() + "\n";
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void start() {
        if (!running) {
            running = true;
            thread.start();
        }
    }

    public static void stop() {
        running = false;
        thread.interrupt();
    }

    private static void updateArray() {
        messageRecievedHistory[0] = messageRecievedHistory[1];
        messageRecievedHistory[1] = messageRecievedHistory[2];
        messageRecievedHistory[2] = messageRecievedHistory[3];
        messageRecievedHistory[3] = messageRecievedHistory[4];
        messageRecievedHistory[4] = messageRecievedHistory[5];
        messageRecievedHistory[5] = messageReceived;
        messageReceived = 0;
    }
}
