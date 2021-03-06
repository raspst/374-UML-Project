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

MethodInvokation - holds items needed to print out SDEdit representations
of methods.

SequenceVisitor - visits the method instructions and such to
get the information about methods out of the bytecode.

MethodContainerVisitor - similar to the ClassContainerVisitor for the UML
parsing.

WHO DID WHAT

Alex Crowley - Alex did the manual UML diagrams, coming up with the
main ideas for the structure of the program in the progress. He also
added in a couple of classes (i.e. ClassContainer). Alex  also
refactored a lot of code after it was written to make it more efficient.
Added a file parser and a recursive package visitor that gets all classes in a package. Alex did most of the heavy ASM lifting for Milestone 3 and added
the visitors and classes required to do so.

Steven Rasp - Steven implemented the classes that hold the information about
classes, fields, methods, etc. He also handled the code dealing with outputting
the text necessary for GraphViz UML diagrams. He also added some test code
to run the parser on some test classes to ensure that the various items
were being read in correctly. For Milestone 3, Steven mostly caught small
mistakes, and then wrote the code to print out the SDEdit formatted text,
though it's not perfect at all.

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

To use the Sequence Diagram creator, the parameters (Class name, method name,
method signature) are passed in main for now.

MILESTONE 5 ADDITIONS

WHO DID WHAT:

Alex did the work with ASM to actually detect the Adapter and Decorator patterns, and did some refactoring of previous code to
help accomodate changes. He also updated the manual UML diagram.

Steven reworked the annotation system to make it more extensible. Now, when a new detector is written,
patterns, colors, and annotations are added to JClasses, and these items are now
automatically printed by the PrintFactory, so adding the patterns etc. is the only thing required.

MILESTONE 6 ADDITIONS

Milestone 6 first involved fixing some things from Milestone 5. Alex reworked the detection logic for the Adapter and Decorator
detectors, and Steven then reworked the way association arrow labels were made to ensure only one label was printed (instead
of the four that were cropping up).

Alex also implemented the detection logic for the Composite pattern, while Steven added the code to store the patterns
and display the colors.
