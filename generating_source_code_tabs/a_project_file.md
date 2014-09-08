---
---

An MGen project file is an ordinary xml file. In this example we'll create one called project.xml (the name does not matter) with the following contents:


    <Project>

      <Sources>
        <Source>com.fruitcompany.fruits.xml</Source>
      </Sources>

      <Generator name="Java">
        <output_path>src/main/java</output_path>
        <classregistry_path>com.fruitcompany</classregistry_path>
        <class_path>se.culvertsoft.mgen.javapack.generator.JavaGenerator</class_path>
      </Generator>

      <Generator name="C++">
        <output_path>src/main/cpp</output_path>
        <classregistry_path>com.fruitcompany</classregistry_path>
        <generate_unity_build>true</generate_unity_build>
        <class_path>se.culvertsoft.mgen.cpppack.generator.CppGenerator</class_path>
      </Generator>

    </Project>