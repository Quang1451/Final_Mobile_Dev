package vn.edu.tdtu.finalexam;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NoteItem implements Serializable {
    private String id;
    private String type;
    private String title;
    private String time;
    private boolean canceled;
    public NoteItem() {}
    public NoteItem(String id, String title, String time) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.canceled = false;
    }

    public NoteItem(String title, String time) {
        this.title = title;
        this.time = time;
        this.canceled = false;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
    public boolean isCanceled() {
        return canceled;
    }
}

