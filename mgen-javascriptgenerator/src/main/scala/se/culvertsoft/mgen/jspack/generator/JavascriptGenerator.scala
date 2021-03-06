package se.culvertsoft.mgen.jspack.generator

import scala.collection.JavaConversions.asScalaBuffer

import se.culvertsoft.mgen.api.model.GeneratedSourceFile
import se.culvertsoft.mgen.api.model.Project
import se.culvertsoft.mgen.api.plugins.Generator
import se.culvertsoft.mgen.compiler.util.SourceCodeBuffer
import se.culvertsoft.mgen.compiler.util.SourceCodeBuffer.SourceCodeBuffer2String

class JavascriptGenerator extends Generator {

  override def generate(project: Project, generatorSettings: java.util.Map[String, String]): java.util.List[GeneratedSourceFile] = {

    implicit val txtBuffer = SourceCodeBuffer.getThreadLocal()
    txtBuffer.clear()

    val modules = project.allModulesRecursively()
    val filePath = MkFilePath(generatorSettings)

    MkIntro(generatorSettings)
    MkModuleClassRegistry(modules)
    MkModuleHashRegistry(modules)
    MkOutro(generatorSettings)

    val out = new java.util.ArrayList[GeneratedSourceFile]
    out.add(new GeneratedSourceFile(filePath, txtBuffer))
    out
  }
}