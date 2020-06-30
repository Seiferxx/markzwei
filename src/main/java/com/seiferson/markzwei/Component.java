package com.seiferson.markzwei;

import java.util.ArrayList;

public class Component {

    public ComponentRule rule;
    public String text;
    private ArrayList<Component> children;

    public Component(ComponentRule rule, String text) {
        this.rule = rule;
        this.text = text;
        this.children = new ArrayList<>();
    }

    public ComponentRule getRule() {
        return rule;
    }

    public void setRule(ComponentRule rule) {
        this.rule = rule;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

     public ArrayList<Component> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Component> children) {
        this.children = children;
    }
}
