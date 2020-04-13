package com.example.beerpongclub.Database;

public class DatabaseInterface {
    private DatabaseElement element;

    public DatabaseInterface() {
        this.element = null;
    }

    public DatabaseInterface(DatabaseElement element) {
        this.element = element;
    }

    public void setElement(DatabaseElement element) {
        this.element = element;
    }

    public void push_Element() {
        if(this.element != null) {

        }
    }
}
