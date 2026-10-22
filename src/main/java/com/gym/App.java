package com.gym;

public class App {
    public String getGreeting() {
        return "Gym Membership App Ready";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}