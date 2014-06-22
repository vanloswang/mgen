package se.culvertsoft.mgen.cpppack.generator.impl.classcpp

import se.culvertsoft.mgen.api.model.Module
import se.culvertsoft.mgen.compiler.util.SuperStringBuffer
import scala.collection.JavaConversions._
import se.culvertsoft.mgen.compiler.internal.BuiltInStaticLangGenerator._
import se.culvertsoft.mgen.compiler.internal.BuiltInGeneratorUtil._
import se.culvertsoft.mgen.api.model.CustomType
import se.culvertsoft.mgen.cpppack.generator.CppConstruction
import se.culvertsoft.mgen.cpppack.generator.impl.Alias._
import se.culvertsoft.mgen.cpppack.generator.CppGenUtils
import se.culvertsoft.mgen.cpppack.generator.CppTypeNames._

object MkValidate {

  def apply(
    t: CustomType,
    module: Module)(implicit txtBuffer: SuperStringBuffer) {

    implicit val currentModule = module

    txtBuffer.tabs(0).textln(s"bool ${t.shortName()}::_validate(const mgen::FieldSetDepth depth) const { ")
    txtBuffer.tabs(1).textln(s"if (depth == mgen::SHALLOW) {")
    txtBuffer.tabs(2).text(s"return true")
    for (field <- t.getAllFieldsInclSuper().filter(_.isRequired()))
      txtBuffer.endl().tabs(4).text(s"&& ${isFieldSet(field, "mgen::SHALLOW")}")
    txtBuffer.textln(s";")
    txtBuffer.tabs(1).textln(s"} else {")
    txtBuffer.tabs(2).text(s"return true")
    for (field <- t.getAllFieldsInclSuper()) {
      if (field.isRequired())
        txtBuffer.endl().tabs(4).text(s"&& ${isFieldSet(field, "mgen::DEEP")}")
      else if (field.typ().containsMgenCreatedType())
        txtBuffer.endl().tabs(4).text(s"&& (!${isFieldSet(field, "mgen::SHALLOW")} || ${isFieldSet(field, "mgen::DEEP")})")
    }
    txtBuffer.textln(s";")
    txtBuffer.tabs(1).textln(s"}")
    txtBuffer.tabs(0).textln(s"}")
    txtBuffer.endl()

  }

}