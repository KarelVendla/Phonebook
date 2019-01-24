package com.karelvendla.phonebook;

import java.io.Serializable;

public class Zipcode implements Serializable {
    private String code,city;

    public Zipcode(String code, String city) {
        this.code = code;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public String toString() {
        return getCode()+ " " + getCity();
    }
}

