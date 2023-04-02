package vn.edu.tdtu.finalexam;

import android.graphics.Bitmap;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NoteItem {
    private String type;
    private String title;
    private String time;


    public NoteItem(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return null;
    }
}

