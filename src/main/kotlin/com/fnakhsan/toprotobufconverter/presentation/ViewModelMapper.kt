package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm

internal class ViewModelMapper(
    private val converterViewBinder: ConverterViewBinder
) {

    fun map(converterForm: ConverterForm) = converterForm.apply {
        ConversionModel(
            rewriteClasses = rewriteExistingClassesCheckBox.isSelected,
            annotationEnum = resolveFramework(),
            useKotlin = isKotlinSelected(),
            content = textArea.text,
            rootClassName = className.text,
            useSetters = resolveCheckBox(SETTERS),
            useGetters = resolveCheckBox(GETTERS),
            useStrings = resolveCheckBox(TO_STRING),
            useKotlinParcelable = resolveCheckBox(KOTLIN_PARCELABLE),
            useKotlinSingleDataClass = resolveCheckBox(KOTLIN_SINGLE_DATA_CLASS),
            kotlinNullableFields = resolveCheckBox(KOTLIN_NULLABLE_FIELDS),
            javaPrimitives = resolveCheckBox(JAVA_PRIMITIVE_TYPES),
            useLombokValue = resolveCheckBox(LOMBOK_VALUE),
            useTabsIndentation = useTabsIndentation.isSelected,
            useMoshiAdapter = resolveCheckBox(MOSHI_ADAPTER),
            useKotlinDataClass = resolveCheckBox(KOTLIN_DATA_CLASSES)
        )
    }

    private fun resolveCheckBox(key: String) = with(converterViewBinder.properties) {
        with(this?.selectedSource?.selectedLanguage?.selectedFramework) {
            this?.properties?.firstOrNull { it.propertyName == key }?.selected
        }
    } ?: false

    private fun resolveFramework() = with(converterViewBinder.properties) {
        this?.selectedSource?.selectedLanguage?.selectedFramework ?: throw IllegalStateException()
    }

    private fun isKotlinSelected() = with(converterViewBinder.properties) {
        this?.selectedSource?.selectedLanguage is LanguageVM.Kotlin
    }
}