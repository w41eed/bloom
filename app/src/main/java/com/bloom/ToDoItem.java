package com.bloom;

public class ToDoItem {
    private String WhatToDo;
    private int IconImage;

    public ToDoItem(String wtd, int iconimg) {
        WhatToDo = wtd;
        IconImage = iconimg;
    }

    public String getWhatToDo() {
        return WhatToDo;
    }

    public int getIconImage() {
        return IconImage;
    }

}
