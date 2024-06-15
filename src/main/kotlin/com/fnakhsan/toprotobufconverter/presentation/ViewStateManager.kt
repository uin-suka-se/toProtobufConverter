package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.*
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import com.fnakhsan.toprotobufconverter.services.ViewStateService

internal class ViewStateManager(
    private val viewStateService: ViewStateService
) {

    fun restoreCommonProperties(converterForm: ConverterForm) {
        viewStateService.state.model?.let { model ->
            converterForm.apply {
//                useTabsIndentation.isSelected = model.useTabsIndentation
//                rewriteExistingClassesCheckBox.isSelected = model.rewriteClasses
                fileName.text = model.rootFileName
            }
        }
    }

    fun restoreState(properties: ControlsModel?) {
        viewStateService.state.model?.let { model ->
            properties?.apply {
                sources.filterIsInstance<SourceVM.Kotlin>().firstOrNull()?.apply {
                    applyLanguage(this, model)
                }
            }
        }
    }

    private fun applyLanguage(source: SourceVM, model: ConversionModel) {
        source.selectedLanguage = source.languages.filterIsInstance<LanguageVM.Protobuf>().firstOrNull()

        source.selectedLanguage?.let {
            applyVersion(it, model)
        }
    }

    private fun applyVersion(language: LanguageVM, model: ConversionModel) {
        language.selectedVersion =
            language.versions.firstOrNull { it.propertyName == model.versionEnum.propertyName }
        language.selectedVersion?.let {
            applyPreference(it, model)
        }
    }

    private fun applyPreference(version: VersionVW, model: ConversionModel) {
        version.selectedPreference = version.preferences.firstOrNull { it.propertyName == model.preferenceEnum.propertyName }
    }
}