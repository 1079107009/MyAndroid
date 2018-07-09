package com.lp.solution.linked;

/**
 * @author lipin
 * @date 2018/7/6
 */
class MyLinkedList {

    int size;
    Node first;
    Node last;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {

    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (isElementIndex(index)) {
            if (index == 0) {
                return first.val;
            }
            if (index == size - 1) {
                return last.val;
            }
            return node(index).val;
        }
        return -1;
    }

    private Node node(int index) {
        if (index < (size >> 1)) {
            Node x = first;
            for (int i = 0; i < index; i++){
                x = x.next;
            }
            return x;
        } else {
            Node x = last;
            for (int i = size - 1; i > index; i--){
                x = x.pre;
            }
            return x;
        }
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node f = first;
        Node newNode = new Node(null, val, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.pre = newNode;
        }
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node l = last;
        Node newNode = new Node(l, val, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (isPositionIndex(index)) {
            if (index == size) {
                addAtTail(val);
            } else {
                Node original = node(index);
                Node pre = original.pre;
                Node newNode = new Node(pre, val, original);
                original.pre = newNode;
                if (pre == null) {
                    first = newNode;
                } else {
                    pre.next = newNode;
                }
                size++;
            }

        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (isElementIndex(index)) {
            Node x = node(index);
            Node prev = x.pre;
            Node next = x.next;
            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                x.pre = null;
            }

            if (next == null) {
                last = prev;
            } else {
                next.pre = prev;
                x.next = null;
            }
            size--;
        }
    }

    private static class Node {
        int val;
        Node pre;
        Node next;

        Node(Node prev, int element, Node next) {
            this.pre = prev;
            val = element;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MyLinkedList obj = new MyLinkedList();
        obj.addAtHead(1);
        obj.addAtTail(3);
        obj.addAtIndex(1, 2);
        obj.get(1);
        obj.deleteAtIndex(1);
        obj.get(1);
    }
}
