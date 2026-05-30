package com.alexeycode.jsonmasker

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

private val JSON = Json { prettyPrint = true }

fun maskKotlinx(json: String, fields: Set<String>, mask: String): String {
    val root = Json.parseToJsonElement(json)
    return JSON.encodeToString(root.mask(fields, mask))
}

private fun JsonElement.mask(fields: Set<String>, mask: String): JsonElement {
    return when (this) {
        is JsonArray -> this.mask(fields, mask)
        is JsonObject -> this.mask(fields, mask)
        is JsonPrimitive -> this
        JsonNull -> this
    }
}

private fun JsonObject.mask(fields: Set<String>, mask: String): JsonObject {
    return buildJsonObject {
        this@mask.forEach { (key, value) ->
            if (key in fields) {
                put(key, mask)
            } else {
                put(key, value.mask(fields, mask))
            }
        }
    }
}

private fun JsonArray.mask(fields: Set<String>, mask: String): JsonArray {
    return buildJsonArray {
        this@mask.forEach { element ->
            this.add(element.mask(fields, mask))
        }
    }
}