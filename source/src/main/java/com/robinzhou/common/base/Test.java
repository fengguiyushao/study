package com.robinzhou.common.base;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by robinzhou on 2015/9/9.
 */
public class Test {
    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("夜间没有路灯");
        commands.add("夜间路灯不良");
        commands.add("紧跟前车行驶");
        commands.add("夜间通过急弯");
        commands.add("夜间通过坡路");
        commands.add("夜间通过拱桥");
        commands.add("夜间通过人行横道");
        commands.add("没有交通信号灯的路口");
        commands.add("两车会车");
        commands.add("窄路/窄桥");
        commands.add("雾天");
        commands.add("夜间车辆发成故障");
        Collections.shuffle(commands);
        for (String command : commands) {
            System.out.println(command);
        }

    }
}
