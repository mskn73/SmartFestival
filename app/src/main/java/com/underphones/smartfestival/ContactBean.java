package com.underphones.smartfestival;

import java.io.Serializable;

/**
 * Created by Juan on 07/03/2015.
 */
public class ContactBean implements Serializable {

    private String name;

    private String image;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
