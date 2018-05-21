# Java 10 Basic Side-Effect Assertions with Hibernate and GSON
This project provides a basic template for how you might go about testing a side-effect heavy service method.  It takes entity objects (as JSON), persists them to the database, tests a service method which is pure side-effect (meaning that it either alters the input object, or it only affects downstream things that the caller can't see), and then uses SQL to assert that the method did what it was supposed to (in this case, setting bank account numbers to zero, kind of like me walking into an [REI](https://www.rei.com)).

Salient things going on here:
* JUnit
* [GSON](https://github.com/google/gson)
* Java 10 `var` (!!)
* Lambda Expressions
* Streams 
* Hibernate

The mini-stack above is intended to collapse the amount of boilerplate required to convey the idea that you can still write unit tests against the service tier and treat it as a black box.
