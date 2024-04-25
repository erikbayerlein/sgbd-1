package models;

public class Shopping {
    private int id;
    private double value;
    private int year;

    public Shopping(int id, double value, int year) {
        setId(id);
        setValue(value);
        setYear(year);
    }

    public Shopping(int id, int year) {
        setId(id);
        setValue(1000.30);
        setYear(year);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
