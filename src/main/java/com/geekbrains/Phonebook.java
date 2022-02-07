package com.geekbrains;

import java.util.*;

public class Phonebook {
    private final Map<String, Set<String>> namePhone;

    public Phonebook() {
        namePhone = new HashMap<>();
    }

    public void add(String surname, String phoneNumber) {
        if (namePhone.containsKey(surname)) {
            Set<String> phones = namePhone.get(surname);
            phones.add(phoneNumber);
            //namePhone.put(surname, phones);
        } else {
            Set<String> phones = new HashSet<>();
            phones.add(phoneNumber);
            namePhone.put(surname, phones);
        }
    }

    public Set<String> get(String surname) {
        return namePhone.get(surname);
    }
}
