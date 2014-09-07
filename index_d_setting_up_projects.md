---
layout: default
link-title: Project configuration
submenu:
  - { anchor: "a", title: "c++" }
  - { anchor: "b", title: "java (maven)" }
  - { anchor: "c", title: "java (manual)" }
  - { anchor: "d", title: "javascript" }
---

## Adding MGen to your projects

Once you have [generated classes with the MGen compiler](index_c_Generating_code.html), you need to add the MGen runtime libraries to the builds of your applications. We will explain how to do this for some common build setups in each supported language. 

We assume you have installed MGen as shown in the [Installation section](index_e1_Installation.html).


### Configuring a c++ project <a name="a">&nbsp;</a>

The MGen c++ runtime library is header-only. All you need to do is to add it to the include paths of your build. If you installed mgen as described in the [Installation section](index_e1_Installation.html), you can simply add:

 * MGEN_INSTALL_PATH/include

to the include paths of your build. 


### Configuring a java project with Maven <a name="b">&nbsp;</a>

Using Maven (or ivy/gradle/sbt or similar) is probably the easiest way is to depend on MGen for java builds. Currently only snapshot builds are available. All you need to do is att 'mgen-javalib' as a dependency:

    <dependency>
      <groupId>se.culvertsoft</groupId>
      <artifactId>mgen-javalib</artifactId>
      <version>SNAPSHOT</version>
    </dependency>

They are hosted on the following repository urls:
 
 * https://oss.sonatype.org/content/repositories/snapshots/
 * https://oss.sonatype.org/content/repositories/releases/

To see all the MGen components hosted there - check out [oss.sonatype.org/#nexus-search;quick~mgen](https://oss.sonatype.org/#nexus-search;quick~mgen)


### Configuring a java project manually <a name="c">&nbsp;</a>

If you prefer to set up your project manually, add the following libraries to your build:

 * [mgen-api.jar](index_e1_Installation.html)
 * [mgen-javalib.jar](index_e1_Installation.html)
 * [MGen's external java dependencies](index_e2_Dependencies.html) 

The mgen jar files, sources and javadoc are available in the MGEN_INSTALL_PATH/jars, once you have installed MGen. See the [Installation section](index_e1_Installation.html) for more information.



### Configuring a javascript project <a name="d">&nbsp;</a>

Coming soon!


