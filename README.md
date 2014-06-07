MGen
====

MGen is a toolkit for generating source code and creating data models easy to communicate with other software, across language-, version- and system barriers. 

Inspired by Protocol Buffers, Thrift, Avro, ICE, HLA, WtDbo, our goal is to reduce the work required to build, maintain and extend cross-langauge data models.

This is done by defining models in an IDL and generate readable source code that feel simple, natural and fast enough to use in application and library code.

MGen's own source code is designed to be open: IDL parsers, code generators, runtime libraries can all be extended and/or replaced. There is nothing stopping you from plugging in a protobuf IDL parser, thrift wire protocol serializer, and adding custom functionality to any of the supplied code generators, or attach a code generator for your own proprietary system.

Components
----

MGen's core components consist of:
 * The MGen API
 * The MGen Compiler
 * The MGen Runtime libraries

Most of MGen's build tools and build interfaces are written in Java, although under the hood much of the compiler is written in Scala.

If you prefer not to use command line tools, we also provide visual tools and editors, though these can never fully replace manual IDL editing when it comes to advanced usage: extending MGen parsers and generators beyond the standard implementation.

MGen Compiler
----

The compiler is divided into two parts, the parser and the code generators, which are responsible for:
 * Interpreting the data model definitions (parsing IDLs)
 * Generating code (c++, java, javascript, etc..)

Parsers and code generators are what we call plug-ins, that is, they are loaded dynamically on compiler startup. If the user wants to extend or replace parsers or code generators, he/she provides command line arguments to the compiler for where to search for JAR files containing his own compilers. The only requirement is that the custom classes fulfils the basic Parser and Generator Java interfaces specified in the MGen API.

Key features for the compiler are:
 * Support for generic types 
 * Heterogenous containers
 * Support for polymorphic types (maps directly to c++/java class hierarchies)
 * Customizable generators (generated code contents and functionality)
 * Customizable parsers
 * Plug-in architecture for loading custom parsers and generators
 * Ability to extend to new languages

MGen Runtime Libraries
----
Out of the box, MGen supplies standard code generators for C++, Java and JavaScript. These produce two things:
 * Classes
 * ClassRegistries

By default classes are java-beans and C++ data containers with getters and setters. Generated classes work together with the runtime libraries to provide utilities for:
 * Serialization
 * Deep Copying
 * Equality Testing
 * Automatic type instantiation
 * Type metadata

The runtime libraries provides two default serializers:
 * The MGen Binary format Serializer
 * The MGen JSON format Serializer

You can can of course modify, extend and/or replace these by just extending the runtime libraries.

ClassRegistries are used to dynamically instantiate and identify data types over data streams during deserialization.
In some cases they may also be used during serialization. Some language implementations rely on ClassRegistries more than others. One could argue that Java for example  does not really need one (because of reflection), however for performance reasons and API uniformity, they are created for Java as well.

One feature that ClassRegistries provide in C++ is the ability to call template methods with dynamic dispatch. This means you for example can use the ClassRegistry to access a derived visitor method through a call on the base type with a templated argument, such as a custom serializer, and the method will be called with full type preservation on the most derived class. What this type preservation also means is that during (de)serialization, once the serializer has been dispatched to the visitor method of the object it should serialize (which isn't much more than a switch-table) there's not much need for virtual calls - which can substantially improve performance.

MGen does not rely on Java reflection or C++ RTTI.

We have measured binary serialization performances of some polymorphic objects in C++ on a single ivy bridge i7 core to more than 1,5 Gbit/s (g++ 4.8.1 o3). Performance in the other direction was roughly 30% less. Performance is important to us, however it should be made clear that performance is NOT the primary focus of MGen.


MGen Standard IDL
----
- TODO -


What MGen is and is not
----
MGen serializers and utilities are designed to be state-less. There is no common object graph preservation or node-to-node implementation with synchronization. This is a concious design choice and also implies that the standard implementation of serializers do not support circular object graphs. 

However, what this does is it gives us the advantage of supporting lossy and reordering protocols, priority based messaging etc without worrying about objects having all the necessary information to be reconstructed on receiving side. 

We believe supporting circular object graphs should be the responsibility of the synchronizing layer of applications talking with each other - not the data layer. We do have ideas to build such systems (ESBs, ORMs and common object graphs among application nodes and the like) on that support the MGen data model - but not as a part of MGen.

In short - we chose to separate the concerns of data representation from data transport method - the latter is not something MGen is concerned with whatsoever.


License
----
Dual-Licensed under:
 * GPL v2
 * Commercial License (will be available after initial release)

Version
----
 - Alpha v0.x -
(pre-release experimental code)

Help
----
Build Requirements:
  * Java >= 1.6
  * CMAKE >= 2.10
  * g++/MinGW >= 4 (Visual studio support coming! The library already work with VS, but currently not building the tests)
  * make (use gnuwin32 on windows)
  * SBT >= 1.3.5 (Use the installer from http://www.scala-sbt.org/download.html)

Build Instructions:
  * "clone the repo"
  * cd mgen
  * make all

Target dirs:
  * mgen/mgen-...../target
 
