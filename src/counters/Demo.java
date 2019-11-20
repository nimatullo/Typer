package counters;

import links.MasterLinkedList;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        MasterLinkedList mll = new MasterLinkedList();
        mll.addToList("Sherzod", "Nimatullo");
        mll.addToList("Nimatullo", "Doniyor");
        mll.addToList("Doniyor", "Josh");
        mll.addToList("Josh", "Natis");
        mll.addToList("Natis", "Daniel");
//        mll.search("Nimatullo");

    }
}
