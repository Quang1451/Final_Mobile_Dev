package vn.edu.tdtu.finalexam;

import android.graphics.Bitmap;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Picture extends NoteItem{
    private String base64String;
    public Picture(String title, String base64String, String time) {
        super(title, time);
        this.setType("Picture");
        this.base64String = base64String;
    }
    @Override
    public String getData() {
        return base64String;
    }

    public void setData(String base64String) {
        this.base64String = base64String;
    }
}