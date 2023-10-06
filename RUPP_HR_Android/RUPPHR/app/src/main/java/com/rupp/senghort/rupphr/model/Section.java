package com.rupp.senghort.rupphr.model;

import java.util.ArrayList;

public class Section {

    private String sectionLabel;
    private ArrayList<String> itemArrayList;

    public Section(String sectionLabel, ArrayList<String> itemArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public ArrayList<String> getItemArrayList() {
        return itemArrayList;
    }

}
