package com.bloom;

public class locationItem {
    private String text_label;
    private String text_location;
    public locationItem(String t_label, String t_location){
        this.text_label = t_label;
        this.text_location = t_location;
    }
    public String getText_label(){
        return this.text_label;
    }
    public String getText_location(){
        return this.text_location;
    }

}
