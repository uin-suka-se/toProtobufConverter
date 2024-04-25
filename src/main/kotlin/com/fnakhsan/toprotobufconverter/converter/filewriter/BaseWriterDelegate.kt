package com.fnakhsan.toprotobufconverter.converter.filewriter

import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.core.FileWriteException
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.PreWriterDelegate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.fnakhsan.toprotobufconverter.converter.postprocessing.PostProcessorFactory
import java.io.IOException

internal abstract class BaseWriterDelegate(
    private val messageDelegate: MessageDelegate,
    private val factory: PostProcessorFactory,
    private val fileWriterDelegate: FileWriter,
    private val preWriterDelegate: PreWriterDelegate
) {

    abstract fun writeFiles(
        set: Set<MessageItem>,
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    )

    protected fun prepareMessage(
        messageItem: MessageItem,
        conversionModel: ConversionModel
    ) = factory.createPostProcessor(conversionModel)
        .proceed(messageItem, conversionModel)

    protected fun writeFile(
        messageItemBody: String,
        messageName: String,
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    ) {
        val fileName = "$messageName$FILE_PROTOBUF"
        try {
            if (projectModel.directory.findFile(fileName) != null) {
                if (conversionModel.rewriteClasses) {
                    messageDelegate.logEventMessage("updated $fileName")
                } else {
                    messageDelegate.logEventMessage("skipped $fileName")
                }
            } else {
                messageDelegate.logEventMessage("created $fileName")
            }
            fileWriterDelegate.writeToFile(
                preWriterDelegate.updateFileBody(conversionModel, messageItemBody), fileName,
                projectModel,
                conversionModel
            )
        } catch (e: IOException) {
            throw FileWriteException(e.message)
        }
    }
}

const val FILE_PROTOBUF = ".proto"
