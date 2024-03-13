package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.*
import com.fnakhsan.toprotobufconverter.core.models.LanguageVM.*
import com.fnakhsan.toprotobufconverter.core.models.VersionVW.*
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.*

internal class PropertiesFactory {
    fun createControls(): ControlsModel {
        val result = ControlsModel(
            sources = listOf(
                SourceVM.Kotlin(languages = createKotlinLanguages())
            )
        )
        result.selectedSource = result.sources.first()
        result.selectedSource?.let { source ->
            source.selectedLanguage = source.languages.first()
            source.selectedLanguage?.let { language ->
                language.selectedVersion = language.versions.first()
            }
        }
        return result
    }

    private fun createKotlinLanguages() = listOf(
        Protobuf(
            versions = listOf(
                Proto2(
                    preferences = listOf(
                        UseDefault(),
                        UseUInt(),
                        UseSInt(),
                        UseFixed(),
                        UseSFixed()
                    )
                ),
                Proto3(
                    listOf(
                        UseDefault(),
                        UseUInt(),
                        UseSInt(),
                        UseFixed(),
                        UseSFixed()
                    )
                )
            ),
        )
    )
}