package se.culvertsoft.mgen.cpppack.generator.impl.classh

import se.culvertsoft.mgen.api.model.CustomType
import se.culvertsoft.mgen.api.model.Module
import se.culvertsoft.mgen.compiler.util.SuperStringBuffer
import se.culvertsoft.mgen.compiler.internal.BuiltInGeneratorUtil._

object MkFieldById {

  def apply(
    t: CustomType,
    module: Module)(implicit txtBuffer: SuperStringBuffer) {

    
    implicit val currentModule = module

    ln(1, s"const mgen::Field * _fieldById(const short hash) const;")
    ln(1, s"const mgen::Field * _fieldByName(const std::string& name) const;")
    
  }

}