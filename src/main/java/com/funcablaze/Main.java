package com.funcablaze;

import com.funcablaze.frame.Frame;

public class Main {

    private static final String title = "E.communication";
    private static Frame main;

    public static void main(String[] args) {
        main = new Frame(300, 700, -10, 0);
        showMainWindow();
    }

    public static void showMainWindow() {
        if (main != null) {
            main.setVisible(true);
            main.setAlwaysOnTop(true);
        }
    }
}