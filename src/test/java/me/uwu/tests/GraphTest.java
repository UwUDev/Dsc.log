package me.uwu.tests;

import me.uwu.dsc.log.stats.Graph2D;

import java.util.Random;

public class GraphTest {
    public static void main(String[] args) {
        Graph2D graph2D = new Graph2D(100);
        Random r = new Random();
        graph2D.addValue(1);
        for (int i = 0; i < 128; i++)
            graph2D.addValue(r.nextInt(121));

        System.out.println(graph2D.getGraph());
    }
}
