package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.core.models.*
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import java.awt.event.ItemEvent
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
        bindSources(converterForm)
        resolveLanguage(LanguageVM.PROTOCOL_BUFFERS)
        resolveLanguageVersion(VersionVW.PROTO3)
//        bindLanguage(converterForm)
        bindPreferences(converterForm)
//        bindFrameworksAndProperties(converterForm)
    }

//    private fun bindFrameworksAndProperties(converterForm: ConverterForm) {
//        bindFrameworks(converterForm)
//        bindAdditionalProperties(converterForm)
//    }

//    Developed later when plugin already support proto2 and proto3
    private fun bindAdditionalProperties(converterForm: ConverterForm) {
//        converterForm.propertiesPanel.apply {
//            removeAll()
//            layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
//            with(properties?.selectedSource?.selectedLanguage) {
//                this?.selectedFramework?.properties?.let { properties ->
//                    properties.forEach { itemProperty ->
//                        add(
//                            JCheckBox(itemProperty.propertyName).apply {
//                                isSelected = itemProperty.selected
//                                addItemListener { itemEvent ->
//                                    properties.firstOrNull {
//                                        it.propertyName == text
//                                    }?.let { targetProperty ->
//                                        targetProperty.selected = itemEvent.stateChange == ItemEvent.SELECTED
//                                    }
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//            revalidate()
//            repaint()
//        }
    }

//    For now, only support kotlin data class
    private fun bindSources(converterForm: ConverterForm) {
        converterForm.apply {
            rbKotlin.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.KOTLIN)
                }
            }
//            JSONSchemaRadioButton.addItemListener { event ->
//                if (event.stateChange == ItemEvent.SELECTED) {
//                    resolveSource(SourceVM.JSON_SCHEMA)
//                }
//            }
            // TODO: disable when Json Schema support will be added
//            sourcePanel.isVisible = false
        }
    }

//    Developed later when plugin already support proto2 and proto3 (bindLanguageVersion)
//    private fun bindLanguage(converterForm: ConverterForm) {
//        with(converterForm) {
//            javaRadioButton.addItemListener { event ->
//                if (event.stateChange == ItemEvent.SELECTED) {
//                    resolveLanguage(LanguageVM.Protobuf.JAVA)
//                    bindFrameworksAndProperties(converterForm)
//                }
//            }
//            kotlinRadioButton.addItemListener { event ->
//                if (event.stateChange == ItemEvent.SELECTED) {
//                    resolveLanguage(LanguageVM.KOTLIN)
//                    bindFrameworksAndProperties(converterForm)
//                }
//            }
//            kotlinRadioButton.isSelected = properties?.selectedSource?.selectedLanguage is LanguageVM.Kotlin
//        }
//    }

    private fun bindPreferences(converterForm: ConverterForm) {
        converterForm.apply {
            rbDefaultNum.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolvePreference(NumericPreferencesVM.DEFAULT)
                }
            }
            rbUnsignedNum.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolvePreference(NumericPreferencesVM.UINT)
                }
            }
            rbSignedNum.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolvePreference(NumericPreferencesVM.SINT)
                }
            }
            rbFixedNum.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolvePreference(NumericPreferencesVM.FIXED)
                }
            }
            rbSignedFixedNum.addItemListener {event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolvePreference(NumericPreferencesVM.SFIXED)
                }
            }
        }

//        converterForm.frameworkList.apply {
//            removeAll()
//            setListData(
//                Vector<String>().apply {
//                    properties?.selectedSource?.selectedLanguage?.frameworks?.let { list ->
//                        addAll(list.map { it.propertyName })
//                    }
//                }
//            )
//            selectedIndex = with(properties?.selectedSource?.selectedLanguage) {
//                this?.selectedFramework?.let {
//                    this.frameworks.indexOf(selectedFramework as FrameworkVW)
//                } ?: 0
//            }
//            addListSelectionListener { selectionEvent ->
//                if (!selectionEvent.valueIsAdjusting) {
//                    with(properties?.selectedSource?.selectedLanguage) {
//                        this?.frameworks?.firstOrNull {
//                            it.propertyName == selectedValue
//                        }?.let {
//                            selectedFramework = it
//                            bindAdditionalProperties(converterForm)
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun resolveSource(key: String) {
        properties.apply {
            this?.selectedSource = this?.sources?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun resolveLanguage(key: String) {
        properties?.selectedSource.apply {
            this?.selectedLanguage = this?.languages?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun resolveLanguageVersion(key: String) {
        properties?.selectedSource?.selectedLanguage.apply {
            this?.selectedVersion = this?.versions?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun resolvePreference(key: String) {
        properties?.selectedSource?.selectedLanguage?.selectedVersion.apply {
            this?.selectedPreference = this?.preferences?.firstOrNull {
                it.propertyName == key
            }
        }
    }
}