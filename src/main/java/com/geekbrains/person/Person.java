package com.geekbrains.person;

public abstract class Person {
    private int cash;

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = (int) cash;
    }
}
