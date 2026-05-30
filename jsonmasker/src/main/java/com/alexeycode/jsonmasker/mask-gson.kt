package com.alexeycode.jsonmasker

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

private val gson = Gson()

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