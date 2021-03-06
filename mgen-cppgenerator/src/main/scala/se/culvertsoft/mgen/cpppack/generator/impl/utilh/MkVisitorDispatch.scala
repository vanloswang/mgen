package se.culvertsoft.mgen.cpppack.generator.impl.utilh

import scala.collection.JavaConversions.asScalaBuffer

import se.culvertsoft.mgen.api.model.ClassType
import se.culvertsoft.mgen.api.model.Module
import se.culvertsoft.mgen.compiler.internal.BuiltInGeneratorUtil.ln
import se.culvertsoft.mgen.compiler.util.SourceCodeBuffer

object MkVisitorDispatch {

  def fullName(t: ClassType): String = {
    MkLongTypeName.cpp(t)
  }

  def apply(
    referencedModules: Seq[Module],
    generatorSettings: Map[String, String])(implicit txtBuffer: SourceCodeBuffer) {

    val nTabs = 1
    val allClasses = referencedModules.flatMap(_.classes)
    val topLevelClasses = allClasses.filterNot(_.hasSuperType)

    for (constString <- List("", "const ")) {

      ln(1, s"template<typename VisitorType>")
      ln(1, s"void visitObject(${constString}mgen::MGenBase& o, VisitorType& visitor, const mgen::FieldVisitSelection selection) const {")

      txtBuffer.endl()

      ln(nTabs + 1, "const std::vector<short>& ids = o._typeIds16Bit();").endl()

      ln(nTabs + 1, "std::size_t i = 0;")
      MkTypeIdSwitch.apply(
        s => s,
        true,
        nTabs + 1,
        "return;",
        topLevelClasses,
        t => s"${MkLongTypeName.cpp(t)}::_type_id_16bit",
        t => s"static_cast<${constString}${fullName(t)}&>(o)._accept<VisitorType>(visitor, selection);")

      ln(nTabs + 1, "return;")

      ln(1, "}").endl()

    }

  }
}