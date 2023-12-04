package me.uwu.dsc.log.stats;

/*
 * Copyright (C) 2021 UwUDev https://github.com/UwUDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Graph2D {
    private final int size;
    private final List<Long> values = new ArrayList<>();

    public Graph2D(int size) {
        this.size = size;
        clear();
    }

    public long getMax() {
        return Collections.max(values);
    }

    public void addValue(long value) {
        values.remove(0);
        values.add(value);
    }

    public String getGraph() {
        StringBuilder sb = new StringBuilder();
        long increment = getMax() / 12;
        if (getMax() % 12 != 0) increment++;

        int intSize = String.valueOf(increment*12).length();
        StringBuilder blank = new StringBuilder();
        for (int i = 0; i < intSize; i++)
            blank.append(" ");


        sb.append(getScaleString(increment*12, intSize)).append("┤").append(getLine(increment*12)).append("\n");
        sb.append(blank).append("│").append(getLine(increment*11)).append("\n");
        sb.append(getScaleString(increment*10, intSize)).append("┤").append(getLine(increment*10)).append("\n");
        sb.append(blank).append("│").append(getLine(increment*9)).append("\n");
        sb.append(getScaleString(increment*8, intSize)).append("┤").append(getLine(increment*8)).append("\n");
        sb.append(blank).append("│").append(getLine(increment*7)).append("\n");
        sb.append(getScaleString(increment*6, intSize)).append("┤").append(getLine(increment*6)).append("\n");
        sb.append(blank).append("│").append(getLine(increment*5)).append("\n");
        sb.append(getScaleString(increment*4, intSize)).append("┤").append(getLine(increment*4)).append("\n");
        sb.append(blank).append("│").append(getLine(increment*3)).append("\n");
        sb.append(getScaleString(increment*2, intSize)).append("┤").append(getLine(increment*2)).append("\n");
        sb.append(blank).append("│").append(getLine(increment)).append("\n");

        StringBuilder bottom = new StringBuilder("└");
        for (int i = 0; i < size; i++)
            bottom.append("─");

        sb.append(blank).append(bottom);
        return sb.toString();
    }

    private String getScaleString(long scale, int intSize) {
        StringBuilder scaleString = new StringBuilder();
        int scaleSize = String.valueOf(scale).length();
        for (int i = 0; i < intSize - scaleSize; i++)
            scaleString.append(" ");
        scaleString.append(scale);
        return scaleString.toString();
    }

    private String getLine(long lineValue) {
        StringBuilder line = new StringBuilder();
        values.forEach(v -> {
            if (v >= lineValue)
                line.append("\u001B[34m█");
            else line.append("\u001B[0m░");
        });
        return line.append("\u001B[0m").toString();
    }

    public void clear() {
        values.clear();
        for (int i = 0; i < size; i++)
            values.add(0L);
    }
}
