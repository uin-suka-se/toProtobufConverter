package com.fnakhsan.toprotobufconverter.converter.filewriter.common

import com.fnakhsan.toprotobufconverter.converter.filewriter.BaseWriterDelegate
import com.fnakhsan.toprotobufconverter.converter.filewriter.FileWriter
import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.PreWriterDelegate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.fnakhsan.toprotobufconverter.converter.postprocessing.PostProcessorFactory

internal class CommonFileWriterDelegate(
    messageDelegate: MessageDelegate,
    factory: PostProcessorFactory,
    fileWriterDelegate: FileWriter,
    preWriterDelegate: PreWriterDelegate
) : BaseWriterDelegate(
    messageDelegate,
    factory,
    fileWriterDelegate,
    preWriterDelegate
) {

    override fun writeFiles(
        set: Set<MessageItem>,
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    ) = set.forEach { messageItem ->
        messageItem.messageName?.let { messageName ->
            writeFile(
                messageItemBody = prepareClass(
                    messageItem.apply {
                        packagePath = projectModel.packageName
                    },
                    conversionModel
                ),
                messageName = messageName,
                conversionModel = conversionModel,
                projectModel = projectModel
            )
        }
    }
}
