package com.seiferson.markzwei;

import java.util.ArrayList;
import java.util.Iterator;

public class MarkzweiProcessor {

    public static ArrayList<ComponentRule> getRules() {
        ArrayList<ComponentRule> rules = new ArrayList<>();

        ComponentRule textRule = new ComponentRule("text", null, null, null, 0);
        ComponentRule listItemRule = new ComponentRule("listItem", null, "li", textRule, 0);

        rules.add(new ComponentRule("segment", "^\\s*```\\s*$", "pre", textRule, 0));
        rules.add(new ComponentRule("header1", "^#\\ .*$", "h1", textRule, 2));
        rules.add(new ComponentRule("header2", "^##\\ .*$", "h2", textRule, 3));
        rules.add(new ComponentRule("header3", "^###\\ .*$", "h3", textRule, 4));
        rules.add(new ComponentRule("horizontalRule", "^\\s*(?:\\*\\ ){2,}\\*\\s*$", "hr", null,  0));
        rules.add(new ComponentRule("blankLine", "^\\s*$", null, null,  0));
        rules.add(new ComponentRule("blockQuote", "^> .*$", "blockquote", textRule, 2));
        rules.add(new ComponentRule("unorderedList", "^\\ \\ (?:\\+|\\*|\\-)\\ .*$", "ul", listItemRule, 4));
        rules.add(new ComponentRule("orderedList", "^\\ \\ \\d*\\.\\ .*$", "ol", listItemRule, 5));
        rules.add(new ComponentRule("spacedText", "\\ {4,}.*", "p", textRule, 0));
        rules.add(new ComponentRule("paragraph", ".*", "p", textRule, 0));

        return rules;
    }

    public static void checkForBoldAndItalics(Component component){
        component.setText(component.getText().replaceAll("\\*{3}(.*?)\\*{3}", "<b><em>$1</em></b>"));
        component.setText(component.getText().replaceAll("\\*{2}(.*?)\\*{2}", "<b>$1</b>"));
        component.setText(component.getText().replaceAll("\\*(.*?)\\*", "<em>$1</em>"));
    }

    public static void processInlineComponents(Component component) {

        checkForBoldAndItalics(component);
        if(!(
            component.getRule().getComponent().equals("header1") ||
            component.getRule().getComponent().equals("header2") ||
            component.getRule().getComponent().equals("header3"))
        ) {
            component.setText(component.getText().replaceAll("(^|\\s)(\\[)([^\\]]+?)(\\])(\\()([^\\)]+?)(\\))(?=(\\s|$))", "$1<a href=\"$6\">$3</a>"));
            component.setText(component.getText().replaceAll("(^|\\s)(!\\[)([^\\]]+?)(\\]\\()([^\\)]+?)(\\))(?=\\s|$)", "$1<img src=\"$5\" alt=\"$3\" />"));
            component.setText(component.getText().replaceAll("(^|\\s)(@[^\\s]+)(?=(\\s|$))", "$1<a href=\"#$2\">$2</a>"));
            component.setText(component.getText().replaceAll("(^|\\s)(#[^\\s]+)(?=(\\s|$))", "$1<a href=\"#$2\">$2</a>"));
            component.setText(component.getText().replaceAll("`(.*?)`", "<q>$1</q>"));

        }
    }

    public static String processText(String text) {
        String lines[] = text.split("\\r?\\n");
        ArrayList<ComponentRule> rules = getRules();
        ArrayList<Component> components = new ArrayList<>();

        for (String line: lines) {
            for (ComponentRule rule: rules) {
                if (line.matches(rule.getRegex())){
                    components.add(new Component(rule, line.substring(rule.getSlice())));
                    break;
                }
            }
        }
        ComponentRule rootRule = new ComponentRule("root", null, "div", null, 0);

        Component root = new Component(rootRule, null);
        root.setChildren(components);

        Component current = null;
        Iterator<Component> iterator = components.iterator();

        while (iterator.hasNext()) {
            Component component = iterator.next();
            String componentName = component.getRule().getComponent();
            if(!component.getRule().getComponent().equals("horizontalRule"))
                processInlineComponents(component);

            if (current != null) {
                if(current.getRule().getComponent().equals(componentName)) {
                    iterator.remove();

                    if (componentName.equals("segment")) {
                        current = null;
                    } else {
                        Component child = new Component(current.getRule().getChildRule(), component.getText());
                        current.getChildren().add(child);
                        if(componentName.equals("unorderedList") || componentName.equals("orderedList")){
                            child.getChildren().add(new Component(child.getRule().getChildRule(), component.getText()));
                        }
                    }

                    continue;
                } else {
                    if (current.getRule().getComponent().equals("segment")) {
                        iterator.remove();
                        current.getChildren().add(new Component(current.getRule().getChildRule(), component.getText()));
                        continue;
                    } else if (componentName.equals("spacedText")){
                        if(current.getRule().getComponent().equals("unorderedList") || current.getRule().getComponent().equals("orderedList")) {
                            iterator.remove();
                            current.getChildren().get(current.getChildren().size() - 1).getChildren().add(new Component(current.getChildren().get(current.getChildren().size() - 1).getRule().getChildRule(), component.getText()));
                            continue;
                        } else if (current.getRule().getComponent().equals("paragraph") || current.getRule().getComponent().equals("spacedText")) {
                            iterator.remove();
                            current.getChildren().add(new Component(current.getRule().getChildRule(), component.getText()));
                            continue;
                        } else {
                            current = null;
                        }
                    } else if (current.getRule().getComponent().equals("spacedText")){
                        if (componentName.equals("paragraph")){
                            iterator.remove();
                            current.getChildren().add(new Component(current.getRule().getChildRule(), component.getText()));
                            continue;
                        } else {
                            current = null;
                        }
                    }
                    else {
                        current = null;
                    }
                }
            }

            if (
                componentName.equals("header1") ||
                componentName.equals("header2") ||
                componentName.equals("header3")
            ) {
                component.getChildren().add(new Component(component.getRule().getChildRule(), component.getText()));
            }
            else if (
                componentName.equals("segment") ||
                componentName.equals("blockQuote") ||
                componentName.equals("unorderedList") ||
                componentName.equals("orderedList") ||
                componentName.equals("paragraph") ||
                componentName.equals("spacedText")
            ) {
                current = component;
                if (!componentName.equals("segment")) {
                    Component child = new Component(current.getRule().getChildRule(), component.getText());
                    current.getChildren().add(child);
                    if(componentName.equals("unorderedList") || componentName.equals("orderedList")){
                        child.getChildren().add(new Component(child.getRule().getChildRule(), component.getText()));
                    }
                }
            } else if(!componentName.equals("horizontalRule")) {
                iterator.remove();
            }
        }

        return getText(root);

    }

    public static String getText(Component component) {
        String htmlText = "";

        if(component.getRule().getComponent().equals("text")){
            htmlText += component.getText();
        } else {
            if(component.getRule().getComponent().equals("horizontalRule")) {
                htmlText += "<" + component.getRule().getTag() + "/>";
            } else {
                htmlText += "<" + component.getRule().getTag() + ">";
                for (Component child : component.getChildren()) {
                    htmlText += getText(child);
                }
                htmlText += "</" + component.getRule().getTag() + ">";
            }
        }
        return htmlText;
    }
}
