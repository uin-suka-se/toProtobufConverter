package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm

internal class ViewModelMapper(
    private val converterViewBinder: ConverterViewBinder
) {

    fun map(converterForm: ConverterForm) = with(converterForm) {
        ConversionModel(
            rewriteClasses = true,
            versionEnum = resolveVersion(),
            preferenceEnum = resolvePreference(),
            content = kotlinTextArea.text,
            rootFileName = fileName.text,
        )
    }

    private fun resolveVersion() = with(converterViewBinder.properties) {
        this?.selectedSource?.selectedLanguage?.selectedVersion ?: throw IllegalStateException()
    }

    private fun resolvePreference() = with(converterViewBinder.properties) {
        this?.selectedSource?.selectedLanguage?.selectedVersion?.selectedPreference ?: throw IllegalStateException()
    }
}