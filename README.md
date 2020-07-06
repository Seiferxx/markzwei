# Markzwei: A markdown alternative

**Current Version   0.1**

Markzwei is a markup language based on a **sub-set** of the rules of the popular markup language **Markdown** created 
**by John Gruber**. The main idea behind this project is to make the language strict and less ambiguous so it's easier 
for computers to parse it.

The name comes from the word in English "mark" referring to markup language, and "zwei" which means two in German. Also 
the name makes a reference to Markdown, the language in which is based.

## Components

  * Headers
  * Horizontal rule
  * Block quotes
  * Code blocks
  * Lists
  * Paragraphs
  * Inline code
  * Bold and Italics
  * Images
  * Links

## Headers

Markzwei defines 6 levels of headers, the same ones as defined on the Markdown rules. To mark a line as a header start 
the line with a **#** *(hash sign)* followed by an empty space character. Use up to 6 hash signs respectively for the 
different levels. Headers can only contain text in normal, italics, or bold form. If more than 6 hash signs are used, 
the line will not be identified as a header. No blank line is required between the preceding and following lines.

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

**Valid inline elements:** `Italics` `Bold` `Inline code` `Link`

## Horizontal Rule

The horizontal rule is used as a separator. This element works the same as defined on the original reference for
Markdown but with the difference that it only accepts **\*** *(asterisk)*. The horizontal rule is defined as a line only 
containing asterisk and blank space characters on the following form: Any or 0 amount of space characters, followed by 
at least three asterisks optionally separated by a single space character , succeeded by any amount of space characters. 
No blank line is required between the preceding and following lines.

```
 ***
Will produce: <horizontalrule />

    * * * *
Will produce: <horizontalrule />

 * * *** * **
Will produce: <paragraph> *  * *** * **</paragraph>
(This case is not recognized as a horizontal rule because it contains a mix of both rules)

* *    *
Wil produce: <paragraph>* *    *</paragraph>
(Max 1 space character is allowed between asterisks)
```

**Identification regex**: `^\s*((\*\ ){2,}|\*{2,})\*\s*$`

## Block quote



## Code block

Code blocks are text un-formatted that keeps break line and spaces, and shows text as if. To define a code block start 
with a line of only three backtick characters, preceded and succeeded by any or 0 amount of space characters. Then any 
amount of lines containing text, followed by another line with only three backtick characters, preceded and succeeded by 
any amount of space characters. Basically any amount of lines wrapped by lines containing three backticks and space
characters. No blank line is required between the preceding and following wrapping lines. Code blocks cannot contain any
other element since any markzwei marks will be ignored.

```
\```
Line unformatted
Other line unformmated *italics* **bold** 
(link)[http:\\google.com]
\```

Will produce: <codeblock>Line unformatted
Other line unformmated *italics* **bold** 
(link)[http:\\google.com]</codeblock>
```

**Identification regex**: `^[^\S\r\n]*```\s*(.*)s*```[^\S\r\n]*$` *(Where dot includes line breaks)*

## Lists

### Ordered List

### Unordered List

## Paragraphs 

## Inline code

To include pieces of code or un-formatted text within another block element, wrap them with a single backtick character.

```
Text including inline unformatted text or code `function (){}`
Will produce: <paragraph>Text including inline unformatted text or code <inlinecode>function (){}</inlinecode></paragraph>
```
**Identification regex**: `\`(.*?)\``


## Bold and Italics

## Images

## Links




