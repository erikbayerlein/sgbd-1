package models;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private String name;
    private List<Integer> inData;

    public Bucket(String name, List<Integer> inData) {
        this.setName(name);
        this.setInData(inData);
    }

    public Bucket(){
        inData = new ArrayList<Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getInData() {
        return inData;
    }

    public void setInData(List<Integer> inData) {
        this.inData = inData;
    }

}