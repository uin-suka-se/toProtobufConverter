package com.fnakhsan.toprotobufconverter.presentation.form

import com.fnakhsan.toprotobufconverter.core.models.ControlsModel
import com.fnakhsan.toprotobufconverter.core.models.LanguageVM
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.fnakhsan.toprotobufconverter.core.models.VersionVW
import com.fnakhsan.toprotobufconverter.presentation.PropertiesFactory
import com.fnakhsan.toprotobufconverter.presentation.ViewStateManager
import java.awt.event.ItemEvent
import javax.swing.*

internal class KotlinConverterForm(
    propertiesFactory: PropertiesFactory,
    viewStateManager: ViewStateManager
) : ConverterForm(propertiesFactory, viewStateManager) {
    override lateinit var panelRoot: JPanel
    private lateinit var panelProtobuf: JPanel
    private lateinit var labelPlugin: JLabel
    override lateinit var tfFileName: JTextField
    override lateinit var btnConvert: JButton
    override lateinit var rbDefaultNum: JRadioButton
    override lateinit var rbUnsignedNum: JRadioButton
    override lateinit var rbSignedNum: JRadioButton
    override lateinit var rbFixedNum: JRadioButton
    override lateinit var rbSignedFixedNum: JRadioButton
    override lateinit var labelFileName: JLabel
    override lateinit var labelNumericPref: JLabel
    override lateinit var rbNumeric: ButtonGroup

    override var properties: ControlsModel? = null

    override fun bindView(converterForm: ConverterForm) {
        properties = propertiesFactory.createControls()
        viewStateManager.restoreState(properties)
        viewStateManager.restoreCommonProperties(converterForm)
        resolveLanguage(LanguageVM.PROTOCOL_BUFFERS)
        resolveLanguageVersion(VersionVW.PROTO3)
//        bindLanguage(converterForm)
        bindPreferences(converterForm)
//        bindFrameworksAndProperties(converterForm)
    }

    override fun bindAdditionalProperties(converterForm: ConverterForm) {
        TODO("Not yet implemented")
    }

    override fun bindLanguage(converterForm: ConverterForm) {
        TODO("Not yet implemented")
    }

    override fun bindPreferences(converterForm: ConverterForm) {
        converterForm.apply {
            rbDefaultNum.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolvePreference(NumericPreferencesVM.INT)
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
