package vn.edu.tdtu.finalexam;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Note extends NoteItem {
    private String content;

    public Note(String title, String content, String time) {
        super(title, time);
        this.setType("Note");
        this.content = content;
    }

    public Note(String id, String title, String content, String time) {
        super(id, title, time);
        this.setType("Note");
        this.content = content;
    }
    @Override
    public String getData() {
        return content;
    }

    public void setData(String content) {
        this.content = content;
    }
}
