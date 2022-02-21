package com.geekbrains;

public class Main {
    public static void main(String[] args) {
        OwnLinkedList<String> linkedList = new OwnLinkedList<>();
        linkedList.addNode("123");
        linkedList.addNode("456");
        linkedList.addNode( "789", 0);
        linkedList.display();
        linkedList.addNode(   "901", 1);
        linkedList.display();
        linkedList.remove(1);
        linkedList.display();
    }
}

