package com.supercoders.androidsqlitetutorial;

public class ProductModel {
    String id="";
    String name="";
    String price="";
    String city="";
    String description="";
    String created_at="";

    public ProductModel(String id, String name, String email, String phone, String dob, String created_at) {
        this.id = id;
        this.name = name;
        this.price = email;
        this.city = phone;
        this.description = dob;
        this.created_at = created_at;
    }

    public ProductModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
