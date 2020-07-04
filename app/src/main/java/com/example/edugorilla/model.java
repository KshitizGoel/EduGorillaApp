package com.example.edugorilla;

public class model {

    public String id, Name, email;

    public model(String id, String Name, String email) {
        this.id = id;
        this.Name = Name;
        this.email = email;
    }

    public model() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return email;
    }
}
