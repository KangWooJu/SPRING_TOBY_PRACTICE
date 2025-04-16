package org.kangwooju.spring_toby_practice.domain.user.Entity;

public class User {

    String id;
    String name;
    String password;

    // Getter
    public String getId(){
        return this.id;

    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public void setId(String id){
        this.id = id;
    }

    // Setter
    public void setName(String name){
        this.name = name;

    }

    public void setPassword(String password){
        this.password = password;
    }


}
