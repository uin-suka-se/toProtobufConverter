package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.ControlsModel
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import java.util.*
import javax.swing.BoxLayout

internal class ConverterViewBinder(
    private val propertiesFactory: PropertiesFactory,
    private val viewStateManager: ViewStateManager
) {
    var properties: ControlsModel? = null

    fun bindView(converterForm: ConverterForm) {
        properties = propertiesFactory.createControls()
        viewStateManager.restoreState(properties)
        viewStateManager.restoreCommonProperties(converterForm)
        bindSource(converterForm)
        bindLanguage(converterForm)
        bindFrameworksAndProperties(converterForm)
    }

    private fun bindFrameworksAndProperties(converterForm: ConverterForm) {
        bindFrameworks(converterForm)
        bindAdditionalProperties(converterForm)
    }

    private fun bindAdditionalProperties(converterForm: ConverterForm) {
        converterForm.propertiesPanel.apply {
            removeAll()
            layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
            with(properties?.selectedSource?.selectedLanguage) {
                this?.selectedFramework?.properties?.let { properties ->
                    properties.forEach { itemProperty ->
                        add(
                            JCheckBox(itemProperty.propertyName).apply {
                                isSelected = itemProperty.selected
                                addItemListener { itemEvent ->
                                    properties.firstOrNull {
                                        it.propertyName == text
                                    }?.let { targetProperty ->
                                        targetProperty.selected = itemEvent.stateChange == ItemEvent.SELECTED
                                    }
                                }
                            }
                        )
                    }
                }
            }
            revalidate()
            repaint()
        }
    }

    private fun bindSource(converterForm: ConverterForm) {
        with(converterForm) {
            JSONRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.JSON)
                }
            }
            JSONSchemaRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.JSON_SCHEMA)
                }
            }
            // TODO: disable when Json Schema support will be added
            sourcePanel.isVisible = false
        }
    }

    private fun bindLanguage(converterForm: ConverterForm) {
        with(converterForm) {
            javaRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveLanguage(LanguageVM.JAVA)
                    bindFrameworksAndProperties(converterForm)
                }
            }
            kotlinRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveLanguage(LanguageVM.KOTLIN)
                    bindFrameworksAndProperties(converterForm)
                }
            }
            kotlinRadioButton.isSelected = properties?.selectedSource?.selectedLanguage is LanguageVM.Kotlin
        }
    }

    private fun resolveSource(key: String) {
        with(properties) {
            this?.selectedSource = this?.sources?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun resolveLanguage(key: String) {
        with(properties?.selectedSource) {
            this?.selectedLanguage = this?.languages?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun bindFrameworks(converterForm: ConverterForm) {
        converterForm.frameworkList.apply {
            removeAll()
            setListData(
                Vector<String>().apply {
                    properties?.selectedSource?.selectedLanguage?.frameworks?.let { list ->
                        addAll(list.map { it.propertyName })
                    }
                }
            )
            selectedIndex = with(properties?.selectedSource?.selectedLanguage) {
                this?.selectedFramework?.let {
                    this.frameworks.indexOf(selectedFramework as FrameworkVW)
                } ?: 0
            }
            addListSelectionListener { selectionEvent ->
                if (!selectionEvent.valueIsAdjusting) {
                    with(properties?.selectedSource?.selectedLanguage) {
                        this?.frameworks?.firstOrNull {
                            it.propertyName == selectedValue
                        }?.let {
                            selectedFramework = it
                            bindAdditionalProperties(converterForm)
                        }
                    }
                }
            }
        }
    }
}