package com.julia;

public class Person {
    protected String name;
    protected String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String fullName() {
        return name + " " + surname + " ";
    }
}
