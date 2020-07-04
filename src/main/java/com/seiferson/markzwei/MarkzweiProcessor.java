package com.seiferson.markzwei;

import java.util.ArrayList;
import java.util.Iterator;

public class MarkzweiProcessor {

    public static ArrayList<ComponentRule> getRules() {
        ArrayList<ComponentRule> rules = new ArrayList<>();

        ComponentRule textRule = new ComponentRule("text", null, null, null, 0);
        ComponentRule listItemRule = new ComponentRule("listItem", null, "item", textRule, 0);

        rules.add(new ComponentRule("segment", "^\\s*```\\s*$", "segment", textRule, 0));
        rules.add(new ComponentRule("header1", "^#\\ .*$", "header1", textRule, 2));
        rules.add(new ComponentRule("header2", "^##\\ .*$", "header2", textRule, 3));
        rules.add(new ComponentRule("header3", "^###\\ .*$", "header3", textRule, 4));
        rules.add(new ComponentRule("horizontalRule", "^\\s*(?:\\*\\ ){2,}\\*\\s*$", "horizontalrule", null,  0));
        rules.add(new ComponentRule("blankLine", "^\\s*$", null, null,  0));
        rules.add(new ComponentRule("blockQuote", "^> .*$", "blockquote", textRule, 2));
        rules.add(new ComponentRule("unorderedList", "^\\ \\ (?:\\+|\\*|\\-)\\ .*$", "unorderedlist", listItemRule, 4));
        rules.add(new ComponentRule("orderedList", "^\\ \\ \\d*\\.\\ .*$", "orderedlist", listItemRule, 5));
        rules.add(new ComponentRule("spacedText", "\\ {4,}.*", "paragraph", textRule, 0));
        rules.add(new ComponentRule("paragraph", ".*", "paragraph", textRule, 0));

        return rules;
    }

    public static void checkForBoldAndItalics(Component component){
        component.setText(component.getText().replaceAll("\\*{3}(.*?)\\*{3}", "<bold/><italics>$1</italics></b>"));
        component.setText(component.getText().replaceAll("\\*{2}(.*?)\\*{2}", "<bold>$1</bold>"));
        component.setText(component.getText().replaceAll("\\*(.*?)\\*", "<italics>$1</italics>"));
    }

    public static void processInlineComponents(Component component) {

        checkForBoldAndItalics(component);
        if(!(
            component.getRule().getComponent().equals("header1") ||
            component.getRule().getComponent().equals("header2") ||
            component.getRule().getComponent().equals("header3"))
        ) {
            component.setText(component.getText().replaceAll("(^|\\s)(\\[)([^\\]]+?)(\\])(\\()([^\\)]+?)(\\))(?=(\\s|$))", "$1<link url=\"$6\">$3</a>"));
            component.setText(component.getText().replaceAll("(^|\\s)(!\\[)([^\\]]+?)(\\]\\()([^\\)]+?)(\\))(?=\\s|$)", "$1<image url=\"$5\" />$3<image>"));
            component.setText(component.getText().replaceAll("(^|\\s)(@[^\\s]+)(?=(\\s|$))", "$1<mention>$2</tag>"));
            component.setText(component.getText().replaceAll("(^|\\s)(#[^\\s]+)(?=(\\s|$))", "$1<tag>$2</tag>"));
            component.setText(component.getText().replaceAll("`(.*?)`", "<highlight>$1</highlight>"));

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
        ComponentRule rootRule = new ComponentRule("root", null, "document", null, 0);

        Component root = new Component(rootRule, null);
        root.setChildren(components);

        Component current = null;
        Iterator<Component> iterator = components.iterator();

        while (iterator.hasNext()) {
            Component component = iterator.next();
            String componentName = component.getRule().getComponent();

            if(current == null || !current.getRule().getComponent().equals("segment"))
                if(!componentName.equals("horizontalRule"))
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
        String text = "";

        if(component.getRule().getComponent().equals("text")){
            text += component.getText() + "\n";
        } else {
            if(component.getRule().getComponent().equals("horizontalRule")) {
                text += "<" + component.getRule().getTag() + "/> \n";
            } else {
                text += "<" + component.getRule().getTag() + "> \n";
                for (Component child : component.getChildren()) {
                    text += getText(child);
                }
                text += "</" + component.getRule().getTag() + "> \n";
            }
        }
        return text;
    }
}
