package vn.edu.tdtu.finalexam;

public class CalendarCell {
    private String day;
    private String color;

    public CalendarCell(String day, String color) {
        this.day = day;
        this.color = color;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
