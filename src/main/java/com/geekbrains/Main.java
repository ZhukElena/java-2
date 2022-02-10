package com.geekbrains;

public class Main {
    public static void main(String[] args) {
        OwnLinkedList<Integer> integerOwnLinkedList = new OwnLinkedList<>();
        integerOwnLinkedList.addNode(150);
        integerOwnLinkedList.addNode(347);
        integerOwnLinkedList.addNode(111);
        integerOwnLinkedList.addNode(789);
        integerOwnLinkedList.addNode(222);
        integerOwnLinkedList.addNode(345);

        integerOwnLinkedList.display();
        integerOwnLinkedList.remove(0);
        integerOwnLinkedList.display();

        integerOwnLinkedList.addNode(559, 3);
        integerOwnLinkedList.display();
    }
}

