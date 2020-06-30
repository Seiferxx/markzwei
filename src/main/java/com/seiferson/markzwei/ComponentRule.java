package com.seiferson.markzwei;

public class ComponentRule {

    private String component;
    private String regex;
    private String tag;
    private ComponentRule childRule;
    private int slice;

    public ComponentRule(String component, String regex, String tag, ComponentRule childRule, int slice) {
        this.component = component;
        this.regex = regex;
        this.tag = tag;
        this.slice = slice;
        this.childRule = childRule;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSlice() {
        return slice;
    }

    public void setSlice(int slice) {
        this.slice = slice;
    }

    public ComponentRule getChildRule() {
        return childRule;
    }

    public void setChildRule(ComponentRule childRule) {
        this.childRule = childRule;
    }
}
