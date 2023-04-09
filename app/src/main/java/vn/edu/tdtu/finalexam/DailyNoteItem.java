package vn.edu.tdtu.finalexam;

public class DailyNoteItem {
    private String id;
    private String date;
    private String time;
    private String data;

    public DailyNoteItem(String id, String date, String time, String data) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.data = data;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setData(String data) {
        this.data = data;
    }
}
