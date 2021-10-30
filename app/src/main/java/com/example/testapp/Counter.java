package com.example.testapp;

import java.io.Serializable;
import java.util.Date;

public class Counter implements Serializable{

    private String name;
    private String date;
    private int currValue;
    private int initValue;
    private String comment;

    //Default Constructor
    public Counter(){
        name = "Counter";
        date = new Date().toString();
        currValue = 0;
        initValue = 0;
        comment = "";

    }

    public Counter(String name, String date, int currValue, int initValue){
        this.name = name;
        this.date = date;
        this.currValue = currValue;
        this.initValue = initValue;
        comment = "";
    }

    public Counter(String name, String date, int currValue, int initValue, String comment){
        this.name = name;
        this.date = date;
        this.currValue = currValue;
        this.initValue = initValue;
        this.comment = comment;
    }

    public Counter(String name, int currValue, int initValue){
        this.name = name;
        date = new Date().toString();
        this.currValue = currValue;
        this.initValue = initValue;
        comment = "";
    }

    public Counter(String name, int currValue, int initValue, String comment){
        this.name = name;
        date = new Date().toString();
        this.currValue = currValue;
        this.initValue = initValue;
        this.comment = comment;
    }

    //Getters
    public String getName()
    {
        return name;
    }
    public int getCurrValue()
    {
        return currValue;
    }
    public int getInitValue()
    {
        return initValue;
    }
    public String getComment()
    {
        return comment;
    }
    public String getDate(){
        return date;
    }

    //Setters
    public void setName(String name)
    {
        this.name = name;
    }
    public void setCurrValue(int currValue)
    {
        this.currValue = currValue;
    }
    public void setInitValue(int initValue)
    {
        this.initValue = initValue;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public void setNewDate(){
        this.date = new Date().toString();
    }
    public void setDate(String date){
        this.date = date;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name.toString() + "       " + this.currValue + "\n" + this.date;
    }
}
