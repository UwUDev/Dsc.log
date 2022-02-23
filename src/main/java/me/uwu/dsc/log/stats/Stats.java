package me.uwu.dsc.log.stats;

public class Stats {
    public static int messageReceived = 0;
    public static String graphic = "";
    private static final int[] messageReceivedHistory = new int[]{0,1,0,0,0,0};
    private static boolean running = false;
    private static final Graph2D graph = new Graph2D(100);
    private static final Thread thread = new Thread(() ->{
        graph.clear();
        while (running) {
            updateArray();
            int total = 0;
            for (int i : messageReceivedHistory)
                total += i;
            graph.addValue(total);
            graphic = graph.getGraph() + "\n";
            try {
                //noinspection BusyWait
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
        messageReceivedHistory[0] = messageReceivedHistory[1];
        messageReceivedHistory[1] = messageReceivedHistory[2];
        messageReceivedHistory[2] = messageReceivedHistory[3];
        messageReceivedHistory[3] = messageReceivedHistory[4];
        messageReceivedHistory[4] = messageReceivedHistory[5];
        messageReceivedHistory[5] = messageReceived;
        messageReceived = 0;
    }
}
