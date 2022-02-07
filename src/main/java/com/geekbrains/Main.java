package com.geekbrains;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String text = "Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur Excepteur sint occaecat cupidatat non proident sunt in culpa qui officia deserunt mollit anim id est laborum" +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed " +
                "Lorem ipsum dolor sit amet consectetur adipiscing elit sed ";
        String[] arr = text.toLowerCase(Locale.ROOT).split(" ");
        Map<String, Integer> map = new HashMap<>();
        for (String s : arr) {
            map.merge(s, 1, Integer::sum);
        }

        for (String s : map.keySet()) {
            System.out.printf("Слово %s повторяется %d раз\n", s, map.get(s));
        }

        Set<String> uniqueStrings = new HashSet<>();
        for (String s : map.keySet()) {
            if (map.get(s) == 1) {
                uniqueStrings.add(s);
            }
        }

        System.out.println("Уникальные слова: ");

        for (String uniqueString : uniqueStrings) {
            System.out.println(uniqueString);
        }


        Phonebook phonebook = new Phonebook();
        phonebook.add("Жук", "802512312312");
        phonebook.add("Жук", "802512312312");
        phonebook.add("Жук", "802512345312");
        phonebook.add("Жук", "802513312312");
        phonebook.add("Жук", "801212312312");
        phonebook.add("Жук", "804512312312");
        phonebook.add("Жук", "802572312312");
        phonebook.add("Жук", "802512892312");
        for (String phoneNumber : phonebook.get("Жук")) {
            System.out.println(phoneNumber);
        }
    }
}

