package org.vermeerlab.plugin.typescript.generator.ext;

import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.emitter.EmitterExtension;
import cz.habarta.typescript.generator.emitter.EmitterExtensionFeatures;
import cz.habarta.typescript.generator.emitter.TsModel;

/** Enumクラスからconstを作成します. */
public class EnumToConstConverter extends EmitterExtension {

  @Override
  public EmitterExtensionFeatures getFeatures() {
    var emitterExcensionFeatures = new EmitterExtensionFeatures();
    emitterExcensionFeatures.generatesRuntimeCode = true;
    return emitterExcensionFeatures;
  }

  @Override
  public void emitElements(Writer writer, Settings settings, boolean exportKeyword, TsModel model) {
    var exportStr = exportKeyword ? "export " : "";
    var tsEnums = model.getOriginalStringEnums();

    tsEnums.forEach(
        (var tsEnum) -> {
          writer.writeIndentedLine("");

          var constName =
              new StringBuilder()
                  .append(exportStr)
                  .append("const ")
                  .append(tsEnum.getName().getSimpleName())
                  .append(" = {")
                  .toString();

          writer.writeIndentedLine(constName);

          tsEnum
              .getMembers()
              .forEach(
                  (var member) -> {
                    var memberProp =
                        new StringBuilder()
                            .append(settings.indentString)
                            .append(member.getPropertyName())
                            .append(": \"")
                            .append(member.getEnumValue())
                            .append("\",")
                            .toString();
                    writer.writeIndentedLine(memberProp);
                  });

          writer.writeIndentedLine("} as const;");
        });
  }
}
