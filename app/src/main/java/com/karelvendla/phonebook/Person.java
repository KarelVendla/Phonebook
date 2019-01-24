package com.karelvendla.phonebook;

import java.io.Serializable;

public class Person implements Serializable {

    private int id;
    private String firstname, lastname, address, phone, mail , date ,title, text;
    private Zipcode zipcode;

    public Person() {}

    public Person(String firstname, String lastname, String address, Zipcode zipcode,
                  String phone, String mail, String date, String title, String text) {
        this(0, firstname, lastname, address, zipcode, phone, mail, date, title, text);
    }

    public Person(int id, String firstname, String lastname, String address, Zipcode zipcode, String phone, String mail, String date, String title, String text) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.zipcode = zipcode;
        this.phone = phone;
        this.mail = mail;
        this.date = date;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public Zipcode getZipcode() {
        return zipcode;
    }

    public String getText() {
        return text;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZipcode(Zipcode zipcode) {
        this.zipcode = zipcode;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {return firstname + " " + lastname;}
}


