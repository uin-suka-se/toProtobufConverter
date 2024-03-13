package com.fnakhsan.toprotobufconverter.core.models

data class ControlsModel(
    val sources: List<SourceVM>,
    var selectedSource: SourceVM? = null
)

sealed class SourceVM(
    val languages: List<LanguageVM>,
    var selectedLanguage: LanguageVM? = null,
    val propertyName: String
) {
    class Kotlin(
        languages: List<LanguageVM>,
        selected: LanguageVM? = null
    ) : SourceVM(languages, selected, KOTLIN)

    companion object {
        const val KOTLIN = "Kotlin"
    }
}

sealed class LanguageVM(
    val versions: List<VersionVW>,
    var selectedVersion: VersionVW? = null,
    val propertyName: String
) {
    class Protobuf(
        versions: List<VersionVW>,
        selected: VersionVW? = null
    ) : LanguageVM(versions, selected, PROTOCOL_BUFFERS)

    companion object {
        const val PROTOCOL_BUFFERS = "Protobuf"
    }
}

sealed class VersionVW(
    val preferences: List<NumericPreferencesVM>,
    val selectedPreference: NumericPreferencesVM? = null,
    val propertyName: String
) {
    class Proto2(
        preferences: List<NumericPreferencesVM>,
        selectedPreferences: NumericPreferencesVM? = null
    ) : VersionVW(preferences, selectedPreferences, PROTO2)

    class Proto3(
        preferences: List<NumericPreferencesVM>,
        selectedPreferences: NumericPreferencesVM? = null
    ) : VersionVW(preferences, selectedPreferences,  PROTO3)

    companion object {
        const val PROTO2 = "Proto2"
        const val PROTO3 = "Proto3"
    }
}

sealed class NumericPreferencesVM(
    val propertyName: String = DEFAULT
) {
    class UseDefault(
        propertyName: String
    ) : NumericPreferencesVM(DEFAULT)

    class UseUInt(
        propertyName: String
    ) : NumericPreferencesVM(UINT)

    class UseSInt(
        propertyName: String
    ) : NumericPreferencesVM(SINT)

    class UseFixed(
        propertyName: String
    ) : NumericPreferencesVM(FIXED)

    class UseSFixed(
        propertyName: String
    ) : NumericPreferencesVM(SFIXED)

    companion object {
        const val DEFAULT = "use default (int)"
        const val UINT = "use uint"
        const val SINT = "use sint"
        const val FIXED = "use fixed"
        const val SFIXED = "use sfixed"
    }
}

