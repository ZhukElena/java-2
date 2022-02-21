package com.geekbrains;

public class OwnLinkedList<T> {
    private long size;
    private Node head;
    private Node tail;

    public OwnLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void addNode(T data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    public void addNode(T data, int index) {
        if (index > size) {
            System.out.println("Размер списка меньше чем заданный индекс");
            return;
        }

        if (index == size) {
            addNode(data);
            return;
        }

        Node node = new Node(data);
        if (index == 0) {
            node.next = head;
            head = node;
            size++;
            return;
        }

        Node current = head;
        for (int i = 1; i < index; i++) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;
        size++;
    }

    public void remove(int index) {
        if (index >= size) {
            System.out.println("Размер списка меньше чем заданный индекс");
            return;
        }

        Node current = head;

        for (int i = 1; i < index; i++) {
            current = current.next;
        }

        if (index == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            current.next = current.next.next;
        }

        if (current.next == null && head != null) {
            tail = current;
        }
        size--;
    }

    public void display() {
        Node current = head;

        if (head == null) {
            System.out.println("Односвязный список пуст");
            return;
        }

        while (current != null) {
            System.out.println(current.data + " ");
            current = current.next;
        }

        System.out.println();
    }

    public void display(int index) {
        if (index >= size) {
            System.out.println("Размер списка меньше чем заданный индекс");
            return;
        }

        Node current = head;

        for (int i = 1; i <= index; i++) {
            current = current.next;
        }

        System.out.println(current.data);
    }


    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
