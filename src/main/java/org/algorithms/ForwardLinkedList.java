package org.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class ForwardLinkedList<T> implements Iterable<T> {

    private Node<T> head;

    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = newNode;
    }

    public void reverse() {
        Node<T> forward = null;
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null) {
            forward = current.next;
            current.next = previous;
            previous = current;
            current = forward;
        }
        head = previous;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("value=" + value)
                    .add("next=" + next)
                    .toString();
        }
    }

    public static void main(String[] args) {
        ForwardLinkedList<String> linkedList = new ForwardLinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.add("Значение: ".concat(String.valueOf(i)));
        }
        System.out.println("Original list:");
        linkedList.forEach(System.out::println);

        linkedList.reverse();
        System.out.println("Reversed list:");
        linkedList.forEach(System.out::println);
    }
}
