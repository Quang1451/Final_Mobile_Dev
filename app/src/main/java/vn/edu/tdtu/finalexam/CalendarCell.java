package vn.edu.tdtu.finalexam;

public class CalendarCell {
    private String day;
    private int numberEvent;

    public int getNumberEvent() {
        return numberEvent;
    }

    public void setNumberEvent(int numberEvent) {
        this.numberEvent = numberEvent;
    }
    public void addNumberEvent(int value) {
        this.numberEvent += value;
    }

    public CalendarCell(String day, int numberEvent) {
        this.day = day;
        this.numberEvent = numberEvent;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


}
