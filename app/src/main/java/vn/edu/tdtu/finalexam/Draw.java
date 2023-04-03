package vn.edu.tdtu.finalexam;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Draw extends NoteItem {
    private String base64String;
    public Draw(String title, String base64String, String time) {
        super(title, time);
        this.setType("Draw");
        this.base64String = base64String;
    }
    public Draw(String id, String title, String base64String, String time) {
        super(id, title, time);
        this.setType("Draw");
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