package com.example.edugorilla;

public class model_third {
    public String  date_created , amount ;

    public model_third(String date_created, String amount) {
        this.date_created = date_created;
        this.amount = amount;
    }

    public model_third() {
    }

    public String getDate_created() {
        return date_created;
    }

    public String getAmount() {
        return amount;
    }
}
