package com.alexeycode.jsonmasker

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode

private val mapper = ObjectMapper()

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

    return mapper.writeValueAsString(root)
}