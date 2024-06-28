package com.fnakhsan.toprotobufconverter.presentation.form

import com.fnakhsan.toprotobufconverter.core.models.ControlsModel
import com.fnakhsan.toprotobufconverter.presentation.PropertiesFactory
import com.fnakhsan.toprotobufconverter.presentation.ViewStateManager
import javax.swing.*

internal abstract class ConverterForm(
    internal val propertiesFactory: PropertiesFactory,
    internal val viewStateManager: ViewStateManager
) {
    abstract var panelRoot: JPanel
    abstract var tfFileName: JTextField
    abstract var btnConvert: JButton
    abstract var rbDefaultNum: JRadioButton
    abstract var rbUnsignedNum: JRadioButton
    abstract var rbSignedNum: JRadioButton
    abstract var rbFixedNum: JRadioButton
    abstract var rbSignedFixedNum: JRadioButton
    abstract var labelFileName: JLabel
    abstract var labelNumericPref: JLabel
    abstract var rbNumeric: ButtonGroup

    abstract var properties: ControlsModel?
    internal lateinit var content: String

    abstract fun bindView(converterForm: ConverterForm)

    //    Developed later when plugin already support proto2 and proto3
    abstract fun bindAdditionalProperties(converterForm: ConverterForm)

    //    Developed later when plugin already support proto2 and proto3 (bindLanguageVersion)
    abstract fun bindLanguage(converterForm: ConverterForm)

    abstract fun bindPreferences(converterForm: ConverterForm)
}