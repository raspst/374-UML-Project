# 374-UML-Project
Repository for CSSE374 project to parse code and create UML diagram

DESIGN

The current design has 11 classes total.

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

WHO DID WHAT

Alex Crowley - Alex did the manual UML diagrams
