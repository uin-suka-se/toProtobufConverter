package com.fnakhsan.toprotobufconverter.di

import com.fnakhsan.toprotobufconverter.controllers.GenerateProtobufActionController
import com.fnakhsan.toprotobufconverter.converter.ConversionDelegate
import com.fnakhsan.toprotobufconverter.converter.ConversionDelegateImpl
import com.fnakhsan.toprotobufconverter.converter.MessageCreator
import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.delegates.*
import com.fnakhsan.toprotobufconverter.core.delegates.EnvironmentDelegateImpl
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegateImpl
import com.fnakhsan.toprotobufconverter.core.models.ControlsModel
import com.fnakhsan.toprotobufconverter.presentation.ConverterViewFactory
import com.fnakhsan.toprotobufconverter.presentation.PropertiesFactory
import com.fnakhsan.toprotobufconverter.presentation.ViewModelMapper
import com.fnakhsan.toprotobufconverter.presentation.ViewStateManager
import com.fnakhsan.toprotobufconverter.services.ViewStateService

import org.koin.dsl.module

val appModule = module {
    single {
        GenerateProtobufActionController(get(), get(), get(), get(), get())
    }

//    presentation
    single {
        ConverterViewFactory(get(), get(), get(), get(), get())
    }

    single {
        ViewStateManager(get())
    }

    single {
        ViewModelMapper()
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

    single {
        ControlsModel(get())
    }

//    converter
    single {
        MessageCreator()
    }

    single<ConversionDelegate> {
        ConversionDelegateImpl(get(), get(), get())
    }

    single { MessageConversionHelper() }
}
