package se.culvertsoft.mgen.cpppack.generator.impl.classh

import scala.collection.JavaConversions.asScalaBuffer

import se.culvertsoft.mgen.api.model.ClassType
import se.culvertsoft.mgen.compiler.internal.BuiltInGeneratorUtil.endl
import se.culvertsoft.mgen.compiler.internal.BuiltInGeneratorUtil.ln
import se.culvertsoft.mgen.compiler.util.SourceCodeBuffer
import se.culvertsoft.mgen.cpppack.generator.impl.Alias.fieldIdString
import se.culvertsoft.mgen.cpppack.generator.impl.Alias.fieldMetaString
import se.culvertsoft.mgen.cpppack.generator.impl.Alias.getMutable

object MkReadField {

  def apply(t: ClassType)(implicit txtBuffer: SourceCodeBuffer) {

    val allFields = t.fieldsInclSuper()

    ln(1, s"template<typename ReaderType, typename ReadContextType>")
    ln(1, s"void _readField(const short fieldId, ReadContextType& context, ReaderType& reader) {")

    if (allFields.nonEmpty) {
      ln(2, s"switch (fieldId) {")
      for (field <- allFields) {
        ln(2, s"case ${fieldIdString(field)}:")
        ln(3, s"reader.readField(${fieldMetaString(field)}, context, ${getMutable(field)});")
        ln(3, s"break;")
      }
      ln(2, s"default:")
      ln(3, s"reader.handleUnknownField(fieldId, context);");
      ln(3, s"break;");
      ln(2, s"}")
    } else {
      ln(2, s"reader.handleUnknownField(fieldId, context);");
    }

    ln(1, s"}")
    endl()

  }

}