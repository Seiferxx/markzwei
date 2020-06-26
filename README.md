# Markzwei: A markdown alternative

**Current Version   0.1**

Markzwei is a markup language based on a **sub-set** of the rules of the popular
markup language **Markdown** created **by John Gruber**. The main idea behind this project
is to make the language strict and less ambiguous so it's easier for computers to parse it.

The name comes from the word in English "mark" referring to markup language and
to Markdown, the language in which this is based, and zwei which means two in German

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

Markzwei only allows 3 levels of headers, instead of the 6 defined on the Markdown rules.
To mark a line as a header start the line with a **#** followed by an empty space character. 
Use two and three hash signs respectively for level 2 and 3 headers. Headers can only contain
text in normal, italics, or bold form. If more than 3 hash signs are used, the line will not 
be identified as a header. No blank line is required between the preceding and following lines.

```
# This is a header level 1
Will produce : <h1>This is a header level 1</h1>

## This is a header level 2
Will produce : <h2>This is a header level 2</h2>

### This is a header level 3
Will produce : <h3>This is a header level 3</h3>

#### This is not a header
Will produce : <p>#### This is not a header</p>
```

**Identification regex**: `^#\ .*$` `^##\ .*$` `^###\ .*$`

## Horizontal Rule



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




