package com.rbc.rbcmobileapplication.data;

public class TransactionsDateItems extends TransactionsItems {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }

}
