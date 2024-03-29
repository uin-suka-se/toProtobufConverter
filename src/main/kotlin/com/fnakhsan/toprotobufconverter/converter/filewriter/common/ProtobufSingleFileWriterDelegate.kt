package com.fnakhsan.toprotobufconverter.converter.filewriter.common

import com.fnakhsan.toprotobufconverter.converter.filewriter.BaseWriterDelegate
import com.fnakhsan.toprotobufconverter.converter.filewriter.FileWriter
import com.fnakhsan.toprotobufconverter.converter.postprocessing.PostProcessorFactory
import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.PreWriterDelegate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel


internal class ProtobufSingleFileWriterDelegate(
    messageDelegate: MessageDelegate,
    factory: PostProcessorFactory,
    fileWriterDelegate: FileWriter,
    preWriterDelegate: PreWriterDelegate,
    private val protobufMessagePostProcessor: ProtobufMessagePostProcessor
) : BaseWriterDelegate(messageDelegate, factory, fileWriterDelegate, preWriterDelegate) {

    override fun writeFiles(
        set: Set<MessageItem>,
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    ) {
        val imports = resolveImports(set, conversionModel)
        val targets = set.toMutableList()
        targets.firstOrNull { it.messageName == conversionModel.rootFileName }?.let {
            val index = targets.indexOf(it)
            targets.removeAt(index)
            targets.add(FIRST_TARGET_POSITION, it)
        }
        val rootMessageBuilder = StringBuilder()
        targets.forEachIndexed { index, it ->
            it.apply {
                it.messageImports.clear()
                it.packagePath = null
                if (index > 0) {
                    rootMessageBuilder.append(MessageTemplate.NEW_LINE)
                }
                rootMessageBuilder.append(prepareMessage(it, conversionModel))
            }
        }
        val messageBody = kotlinDataMessagePostProcessor.createMessageItemText(
            packagePath = projectModel.packageName,
            messageTemplate = rootMessageBuilder.toString(),
            imports = kotlinDataMessagePostProcessor.proceedMessageImports(imports, conversionModel).toString()
        )
        writeFile(
            messageName = conversionModel.rootFileName,
            messageItemBody = messageBody,
            conversionModel = conversionModel,
            projectModel = projectModel
        )
    }

    private fun resolveImports(
        set: Set<MessageItem>,
        conversionModel: ConversionModel
    ): HashSet<String> {
        val imports = HashSet<String>().apply {
            set.forEach { addAll(it.messageImports) }
        }
        val universalMessageItem = MessageItem()
        kotlinDataMessagePostProcessor.applyAnnotations(conversionModel, universalMessageItem)
        imports.addAll(universalMessageItem.messageImports)
        return imports
    }
}

private const val FIRST_TARGET_POSITION = 0
