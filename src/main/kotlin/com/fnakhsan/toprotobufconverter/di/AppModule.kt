package com.fnakhsan.toprotobufconverter.di

import com.fnakhsan.toprotobufconverter.controllers.GenerateProtobufActionController
import com.fnakhsan.toprotobufconverter.converter.ConversionDelegate
import com.fnakhsan.toprotobufconverter.converter.ConversionDelegateImpl
import com.fnakhsan.toprotobufconverter.converter.MessageCreator
import com.fnakhsan.toprotobufconverter.converter.ProtobufConverter
import com.fnakhsan.toprotobufconverter.converter.filewriter.FileDelegateFactory
import com.fnakhsan.toprotobufconverter.converter.filewriter.FileWriter
import com.fnakhsan.toprotobufconverter.converter.filewriter.common.CommonFileWriterDelegate
import com.fnakhsan.toprotobufconverter.converter.filewriter.common.ProtobufSingleFileWriterDelegate
import com.fnakhsan.toprotobufconverter.converter.postprocessing.PostProcessorFactory
import com.fnakhsan.toprotobufconverter.converter.postprocessing.common.ProtobufMessagePostProcessor
import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.converter.utils.MessageTemplateHelper
import com.fnakhsan.toprotobufconverter.converter.utils.ProcessingModelResolver
import com.fnakhsan.toprotobufconverter.core.delegates.*
import com.fnakhsan.toprotobufconverter.core.delegates.EnvironmentDelegateImpl
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegateImpl
import com.fnakhsan.toprotobufconverter.presentation.ConverterViewBinder
import com.fnakhsan.toprotobufconverter.presentation.ConverterViewFactory
import com.fnakhsan.toprotobufconverter.presentation.PropertiesFactory
import com.fnakhsan.toprotobufconverter.presentation.ViewModelMapper
import com.fnakhsan.toprotobufconverter.presentation.ViewStateManager
import com.fnakhsan.toprotobufconverter.services.ViewStateService
import com.robohorse.robopojogenerator.parser.InputDataParser
import com.robohorse.robopojogenerator.parser.JsonArrayParser
import com.robohorse.robopojogenerator.parser.JsonObjectParser
import org.koin.dsl.module

val appModule = module {
    single {
        GenerateProtobufActionController(get(), get(), get(), get(), get())
    }

//    presentation
    single {
        ConverterViewFactory(get(), get(), get(), get())
    }

    single {
        ViewStateManager(get())
    }

    single {
        ViewModelMapper(get())
    }

    single {
        ConverterViewBinder(get(), get())
    }

    single {
        PropertiesFactory()
    }

    single {
        ViewStateService()
    }

//    core
    single<EnvironmentDelegate> {
        EnvironmentDelegateImpl()
    }

    single<MessageDelegate> {
        MessageDelegateImpl()
    }

    single<PreWriterDelegate> {
        PreWriterDelegateImpl(get())
    }

    single {
        IndentationDelegate()
    }

//    converter
    single {
        MessageCreator(get(), get())
    }

    single {
        FileDelegateFactory(get(), get())
    }

    single {
        ProtobufSingleFileWriterDelegate(get(), get(), get(), get(), get())
    }

    single<ConversionDelegate> {
        ConversionDelegateImpl(get(), get(), get())
    }

    single {
        JavaRecordsPostProcessor(get(), get())
    }

    single {
        FileWriter()
    }

    single {
        CommonFileWriterDelegate(get(), get(), get(), get())
    }

    single {
        PostProcessorFactory(get())
    }

    single {
        ProtobufMessagePostProcessor(get(), get())
    }

    single {
        MessageTemplateHelper(get())
    }

    single {
        ProtobufConverter(get(), get())
    }

    single {
        ProcessingModelResolver()
    }

    single {
        InputDataParser(get(), get(), get())
    }

    single {
        JsonObjectParser(get())
    }

    single {
        JsonArrayParser(get())
    }

    single { MessageConversionHelper() }
}
