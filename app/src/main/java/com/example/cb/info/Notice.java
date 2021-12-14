package com.example.cb.info;

import java.io.Serializable;
import java.time.LocalDate;

public class Notice implements Serializable
{
    private String title;
    private String contents;
    private String date;

    public Notice() { }

    public Notice(String title, String contents)
    {
        this.title = title;
        this.contents = contents;
        this.date = LocalDate.now().toString();
    }

    public String getTitle() { return title; }
    public String getContents() { return contents; }
    public String getDate() { return date; }
}
