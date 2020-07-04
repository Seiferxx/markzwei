# Markzwei: A markdown alternative

**Current Version   0.1**

Markzwei is a markup language based on a **sub-set** of the rules of the popular
markup language **Markdown** created **by John Gruber**. The main idea behind this project
is to make the language strict and less ambiguous so it's easier for computers to parse it.

The name comes from the word in English "mark" referring to markup language, and "zwei" which means two in German.
Also the name makes a reference to Markdown, the language in which is based.

## Components

  * Headers
  * Horizontal rule
  * Block quotes
  * Code blocks
  * Lists
  * Paragraphs
  * Highlighting
  * Bold and Italics
  * Images
  * Links
  * Tags
  * Mentions

## Headers

Markzwei defines 6 levels of headers, the same ones as defined on the Markdown rules.
To mark a line as a header start the line with a **#** *(hash sign)* followed by an empty space character. 
Use up to 6 hash signs respectively for the different levels. Headers can only contain
text in normal, italics, or bold form. If more than 6 hash signs are used, the line will not 
be identified as a header. No blank line is required between the preceding and following lines.

```
# This is a header level 1
Will produce : <header1>This is a header level 1</header1>

## This is a header level 2
Will produce : <header2>This is a header level 2</header2>

### This is a header level 3
Will produce : <header3>This is a header level 3</header3>

#### This is a header level 4
Will produce : <header4>This is a header level 4</header4>

##### This is a header level 5
Will produce : <header5>This is a header level 5</header5>

###### This is a header level 6
Will produce : <header6>This is a header level 6</header6>

####### This is not a header
Will produce : <paragraph>####### This is not a header</paragraph>

#This is not a header
Will produce : <paragraph><tag>#This</tag> is not a header</paragraph>
(This case is not recognized as a header because of the lack of space separating the hash from the text)
```

**Identification regex**: `^#\ .*$` `^##\ .*$` `^###\ .*$`

**Valid inline elements:** `Italics` `Bold`

## Horizontal Rule

The horizontal rule is used as a separator. This element works the same as defined on the original reference for
Markdown but with the difference that it only accepts **\*** *(asterisk)*. The horizontal rule is defined as a line only 
containing asterisk and blank space characters on the following form: One or more space characters, followed by at least
three asterisks optionally separated by a space character, succeeded by any amount of space characters. No blank line is 
required between the preceding and following lines. Even when is valid to use alternated space separated and not separated
asterisks we suggest to use a consistent format per line to make not parsed text more readable.

```
 ***
Will produce: <horizontalrule />

    * * * *
Will produce: <horizontalrule />

 *  * *** * **
Will produce: <paragraph> *  * *** * **</paragraph>
(This case is not recognized as a horizontal rule because it contains a distance of two blank spaces between asterisk characters)
```

**Identification regex**: `^\s*(?:\*\ ?){2,}\*\s*$`

## Block quote

## Code block

## Lists

### Ordered List

### Unordered List

## Paragraphs

## Highlighting

## Bold and Italics

## Images

## Links

## Tags

## Mentions




