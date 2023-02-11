package com.example.junit;

import java.util.Objects;

public class Dog {
    private String name;
    private int age;
    private String gender;
    public Dog(String name){
        if(null == name){
            throw new IllegalArgumentException("이름이 이상해요.");
        }
        this.name = name;
    }
    public Dog(String name, int age){
        this(name);
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog dog)) return false;
        return name.equals(dog.name);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
