package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm

internal class ViewModelMapper {

    fun map(converterForm: ConverterForm) = with(converterForm) {
        ConversionModel(
            rewriteClasses = true,
            versionEnum = resolveVersion(this),
            preferenceEnum = resolvePreference(this),
            rootFileName = tfFileName.text,
            content = content
        )
    }

    private fun resolveVersion(converterForm: ConverterForm) = with(converterForm.properties) {
        this?.selectedSource?.selectedLanguage?.selectedVersion ?: throw IllegalStateException()
    }

    private fun resolvePreference(converterForm: ConverterForm) = with(converterForm.properties) {
        this?.selectedSource?.selectedLanguage?.selectedVersion?.selectedPreference ?: throw IllegalStateException()
    }
}