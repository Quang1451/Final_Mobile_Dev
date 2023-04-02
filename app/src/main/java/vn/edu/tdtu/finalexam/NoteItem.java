package vn.edu.tdtu.finalexam;

import android.graphics.Bitmap;

import java.sql.Time;

public class NoteItem {
    private int id;
    private String type;
    private String title;
    private Time time;
    private Bitmap bitmap;

    public NoteItem(int id, String type, String title, Time time, Bitmap bitmap) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.time = time;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Time getTime() {
        return time;
    }

    public Bitmap getBitmap() {
        if(type.equals("Note")) return  null;
        return bitmap;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setBitmap(Bitmap bitmap) {
        if(type.equals("Note")) return;
        this.bitmap = bitmap;
    }
}
