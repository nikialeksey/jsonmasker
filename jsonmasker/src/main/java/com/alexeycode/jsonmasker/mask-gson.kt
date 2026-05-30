package com.alexeycode.jsonmasker

import com.google.gson.FormattingStyle
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser

private val gson = GsonBuilder()
    .setFormattingStyle(FormattingStyle.PRETTY.withIndent("    "))
    .create()

fun maskGson(json: String, fields: Set<String>, mask: String): String {
    val root = JsonParser.parseString(json)

    fun process(element: JsonElement) {
        when {
            element.isJsonObject -> {
                val obj = element.asJsonObject

                for ((name, value) in obj.entrySet()) {
                    if (name in fields) {
                        obj.addProperty(name, mask)
                    } else {
                        process(value)
                    }
                }
            }

            element.isJsonArray -> {
                element.asJsonArray.forEach(::process)
            }
        }
    }

    process(root)

    return gson.toJson(root)
}