package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.SEMICOLON
import com.fnakhsan.toprotobufconverter.converter.template.FieldOptionTemplate.DEPRECATED
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.BOOL
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.BYTES
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.DOUBLE
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FIXED32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FIXED64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FLOAT
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.INT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.INT64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.OPTIONAL
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.REPEATED
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SFIXED32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SFIXED64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SINT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SINT64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.STRING
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.UINT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.UINT64
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.FIXED
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.SFIXED
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.SINT
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.UINT
import com.intellij.ide.structureView.impl.java.PsiFieldTreeElement
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiField
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.intellij.psi.util.elementType
import com.jetbrains.rd.util.string.printToString
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase
import org.jetbrains.kotlin.asJava.elements.KtLightField
import org.jetbrains.kotlin.idea.completion.argList
import org.jetbrains.kotlin.idea.testIntegration.framework.KotlinPsiBasedTestFramework.Companion.asKtClassOrObject
import org.jetbrains.kotlin.idea.util.resolveToKotlinType
import org.jetbrains.kotlin.j2k.ast.declarationIdentifier
import org.jetbrains.kotlin.konan.properties.suffix
import org.jetbrains.kotlin.load.java.structure.impl.convertCanonicalNameToQName


class KotlinDataTypeParser : DataTypeParser {
    private var fieldLabels = mutableListOf<String>()
    private var fieldOptions = mutableListOf<String>()
    private lateinit var fieldType: String
    override fun parseField(id: Int, field: PsiField, numericPref: NumericPreferencesVM): String {
        val prefix = StringBuilder()
        val suffix = StringBuilder()
        (field as KtLightField).also {
            if (it.declarationIdentifier().isNullable) {
                fieldLabels.add(OPTIONAL)
            }
            if (it.isDeprecated) {
                fieldOptions.add(String().format(DEPRECATED, true))
            }
            println("start")
//            it.asKtClassOrObject()
//            println((it.type as PsiClassType).resolveType().toString())
//            println((it.type::class as PsiClassType).resolve()?.qualifiedName)
//            println((it.type as PsiClassType).resolve()?.name)
//            println((it.type as PsiClassType).resolve()?.typeParameters?.size)
//            println(it.type.arrayDimensions)
//            println(it.children.toString())
//            println(it.type::class.java.kotlin.isInner)
//            println(it.type::class.java.kotlin is Collection<*>)
//            println(it.asKtClassOrObject()?.getSuperTypeList())
//            println(it.asKtClassOrObject()?.instanceOf(type = KClass<Collection<*>>()))
//            println(it.type.javaClass.kotlin.declaredMemberProperties.size)
//            println(it.type.javaClass.kotlin.typeParameters.size)
//            println((it.argList as KtTypeArgumentList?)?.arguments?.size)
//            println(it.type::class.members.size)
//            println(it.type::class.java.superclass.isMemberClass)
//            println(List::class.java.name)
//            println((it.typeElement?.type as PsiClassType).resolve()?.qualifiedName)
//            println((it.type as PsiClassType).resolve()?.qualifiedName)
//            println((it.type as PsiClassType).resolve())
//            println(it.type::class.java.typeName)
//            println(it.type::class.java.name)
//            println(it.type.resolveScope?.javaClass?.name)
//            println(it.type.resolveScope?.javaClass?.typeName)
//            println(it.type.resolveScope?.displayName)
//            println(it.type.javaClass.name)
//            println(List::class.java.name)
//            println(List::class.java.typeName)
            it.type.apply {
                println(this.canonicalText)
                if (this.presentableText.contains("[]")) {
                    println("array")
                    fieldLabels.add(REPEATED)
                    parseDataType(fieldTypeName = this.presentableText.removeSuffix("[]"), numericPref= numericPref)
                } else if(this.presentableText.contains("List")) {
                    println("List")
                    fieldLabels.add(REPEATED)
                    println("woi")
                    println(it.asKtClassOrObject()?.name)
                    fieldType = this.presentableText.removePrefix("List<").removeSuffix(">")
                } else if(this.presentableText.contains("Set")) {
                    println("Set")
                    fieldLabels.add(REPEATED)
                    fieldType = this.presentableText.removePrefix("Set<").removeSuffix(">")
                } else if(this.presentableText.contains("Map")) {
                    println("Map")
                    val mapValue = this.presentableText.removePrefix("Map<").removeSuffix(">").split(",")
                    println( it.elementType?.javaClass.printToString())
                    println("value ${mapValue.first()} & ${mapValue.last()}")
                    parseDataType(fieldTypeName = mapValue.first(), numericPref = numericPref)
                    val key = fieldType
                    parseDataType(fieldTypeName = mapValue.last(), numericPref = numericPref)
                    val value = fieldType
                    fieldType = "map<$key, $value>"
                } else {
                    println("Object")
                    parseDataType(fieldTypeName = this.presentableText, numericPref = numericPref)
                }
            }
            println("end")
        }
        if (fieldLabels.isNotEmpty()) {
            prefix.append(fieldLabels.joinToString(" ")).append(" ")
        }
        if (fieldOptions.isNotEmpty()) {
            suffix.append(" [").append(fieldOptions.joinToString(", ")).append("]")
        }
        return prefix.toString() + fieldType + " " + field.name + " = " + id.toString() + suffix.toString() + SEMICOLON
    }

    override fun parseDataType(fieldTypeName: String, numericPref: NumericPreferencesVM) {
        println("typeName $fieldTypeName")
        fieldType = when (fieldTypeName) {
            "boolean" -> BOOL
            "short" -> parseNumericDataType(is64 = false, numericPref = numericPref)
            "int" -> parseNumericDataType(is64 = false, numericPref = numericPref)
            "long" -> parseNumericDataType(is64 = true, numericPref = numericPref)
            "float" -> FLOAT
            "double" -> DOUBLE
            "char" -> STRING
            "String" -> STRING
            "byte" -> BYTES
            else -> fieldTypeName
        }
    }

    override fun parseNumericDataType(is64: Boolean, numericPref: NumericPreferencesVM): String =
        when (numericPref.propertyName) {
            UINT -> if (is64) UINT64 else UINT32
            SINT -> if (is64) SINT64 else SINT32
            FIXED -> if (is64) FIXED64 else FIXED32
            SFIXED -> if (is64) SFIXED64 else SFIXED32
            else -> if (is64) INT64 else INT32
        }

//    private fun isKotlinCollection(field: PsiField): Boolean {
//        // Get the field type element
//        val type = field.typeElement?.type
//
//        // Check for common collection interfaces
//        if (type is PsiClassType) {
//
//            val aClass = type.resolve()
//            if (aClass != null) {
//                val qualifiedName = aClass.qualifiedName
//                return qualifiedName != null && (qualifiedName == Collection::class.java.name || qualifiedName == List::class.java.name || qualifiedName == Set::class.java.name || qualifiedName == Map::class.java.name)
//            }
//        }
//
//        // Check for Kotlin specific collections (optional)
//        // You can add checks for specific Kotlin collection types here
//        // (e.g., MutableList, MutableSet)
//        // ...
//        return false
//    }
//
//    private fun getInnerTypeFromListField(field: PsiField): PsiTypeParameter? {
//        val fieldType = field.typeElement?.type
//        if (fieldType is PsiClassType) {
//            val resolvedClass = fieldType.resolve() ?: return null
//            if (resolvedClass.qualifiedName == Collection::class.java.name) {
//                // Check for parameterized type (generics)
//                val typeParameters = resolvedClass.typeParameters
//                if (typeParameters.isNotEmpty()) {
//                    return typeParameters[0] // Assuming the first type parameter is the element type
//                }
//            }
//        }
//        return null
//    }
}