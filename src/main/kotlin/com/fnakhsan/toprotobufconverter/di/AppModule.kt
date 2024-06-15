package com.fnakhsan.toprotobufconverter.di

import org.koin.dsl.module

val appModule = module {
    single {
        GeneratePOJOActionController(get(), get(), get(), get(), get())
    }

    single {
        GeneratorViewFactory(get(), get(), get(), get())
    }

    single {
        ViewStateManager(get())
    }

    single {
        ViewModelMapper(get())
    }

    single {
        GeneratorViewBinder(get(), get())
    }

    single {
        PropertiesFactory()
    }

    single {
        ViewStateService()
    }
}
