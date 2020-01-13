package com.example.sma.Model;

public class Contact {

    // Model til de atributter en bruger skal kende til sine kontakter.

    private String name;
    private String email;
    private String phone;
    private String company;


    public Contact(String name, String email, String company, String phone) {
        this.name = name;
        this.email = email;
        this.company = company;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
