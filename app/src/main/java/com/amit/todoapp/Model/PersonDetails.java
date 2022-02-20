package com.amit.todoapp.Model;

public class PersonDetails {



    public PersonDetails(String title, String description, String datepicker) {
        this.title = title;
        this.description = description;
        this.datepicker = datepicker;
    }
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatepicker() {
        return datepicker;
    }

    public void setDatepicker(String datepicker) {
        this.datepicker = datepicker;
    }

    private String datepicker;

}
