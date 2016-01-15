# 374-UML-Project
Repository for CSSE374 project to parse code and create UML diagram

DESIGN

The current design has 13 classes total.

DesignParser is the class containing the main method, which handles
initiating the code parsing and prints the GraphViz output to the console.

Class<noun>Visitor - these classes handle visiting the class, fields, and
methods of the classes given to them.

JClass, JField, JInterface, JMethod, JType - these are container classes
holding the relevant items that need to be included in the UML diagram.
JType is the parent of all of the other classes in this group. JClass
inherits from JInterface, since they are relatively similar.

ClassContainer - ClassContainer holds all of the various class information 
as classes are created so that they can be accessed elsewhere and prevent
duplication of JClasses.

Design - Design objects handle which packages are used and whitelisted for
the UML generation by parsing a text file

PrintFactory - PrintFactory handles printing out all of the various
elements of the GraphViz output

WHO DID WHAT

Alex Crowley - Alex did the manual UML diagrams, coming up with the
main ideas for the structure of the program in the progress. He also
added in a couple of classes (i.e. ClassContainer). Alex  also
refactored a lot of code after it was written to make it more efficient.
Added a file parser and a recursive package visitor that gets all classes in a package.

Steven Rasp - Steven implemented the classes that hold the information about
classes, fields, methods, etc. He also handled the code dealing with outputting
the text necessary for GraphViz UML diagrams. He also added some test code
to run the parser on some test classes to ensure that the various items
were being read in correctly.

INSTRUCTIONS FOR USE

To use the tool, begin by specifying which packages to whitelist and use
in in/parser.txt, according to the file format section below. This removed
the need for arguments, so you just run the program without any arguments.
This outputs GraphViz code to the console, which can then be pasted
into a dot file and turned into a png UML diagram using the command
dot -T png -o file.png file.dot

FILE FORMAT
-w <the/package/.../> --Whitelists a package so it association arrows are drawn to all Classes in package.
<package/.../>* --Adds all files in package to be parsed.
<packge/.../Class> --Adds the class to be parsed.
