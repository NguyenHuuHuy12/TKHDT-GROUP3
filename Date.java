package Project;

import java.time.LocalDate;

public class Date {
    public int day;
    public int month;
    public int year;

    public Date() {
        LocalDate now = LocalDate.now();
        this.day = now.getDayOfMonth();
        this.month = now.getMonthValue();
        this.year = now.getYear();
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    public static Date fromString(String str) {
        String[] parts = str.split("/");
        Date d = new Date();
        d.day = Integer.parseInt(parts[0]);
        d.month = Integer.parseInt(parts[1]);
        d.year = Integer.parseInt(parts[2]);
        return d;
    }
}