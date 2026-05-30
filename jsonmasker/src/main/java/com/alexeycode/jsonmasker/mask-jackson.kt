package com.alexeycode.jsonmasker

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.core.util.Separators
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode

private val mapper = ObjectMapper().apply {
    val printer = DefaultPrettyPrinter()
    printer.indentObjectsWith(DefaultIndenter("    ", DefaultIndenter.SYS_LF))
    printer.indentArraysWith(DefaultIndenter("    ", DefaultIndenter.SYS_LF))
    setDefaultPrettyPrinter(
        printer.withSeparators(
            Separators()
                .withArrayValueSpacing(Separators.Spacing.AFTER)
                .withObjectEntrySpacing(Separators.Spacing.AFTER)
                .withObjectFieldValueSpacing(Separators.Spacing.AFTER)
                .withObjectEmptySeparator("")
                .withArrayEmptySeparator("")
                .withRootSeparator("")
        )
    )
}

fun maskJackson(json: String, fields: Set<String>, mask: String): String {
    val root = mapper.readTree(json)

    fun process(node: JsonNode) {
        when (node) {
            is ObjectNode -> {
                val fieldNames = node.fieldNames().asSequence().toList()

                for (fieldName in fieldNames) {
                    if (fieldName in fields) {
                        node.put(fieldName, mask)
                    } else {
                        process(node[fieldName])
                    }
                }
            }

            is ArrayNode -> {
                node.forEach(::process)
            }
        }
    }

    process(root)

    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root)
}