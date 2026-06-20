package com.alexeycode.jsonmasker

fun mask(j: String, fs: Set<String>, m: String): String {
    val jLen = j.length
    val r = CharArray(jLen) // result

    var f = 0 // buffer for field name
    var fP = 0 // buffer pointer
    var p = 0 // pointer for original JSON
    var rP = 0 // pointer for result array
    while (p < jLen) {
        if (j[p] == ':' && fP > 0) {
            if (r.concatToString(f, fP) in fs) {
                r[rP] = j[p]; p++; rP++ // handle semicolon
                while (p < jLen && j[p] == ' ') r[rP++] = j[p++] // handle spaces
                if (j[p] == '{') {
                    var b = 1 // number of brackets
                    p++
                    while (p < jLen && b > 0) { // skip everything till object end
                        if (j[p] == '{') b++
                        else if (j[p] == '}') b--
                        p++
                    }

                    r[rP++] = '"'; for (mC in m) r[rP++] = mC; r[rP++] = '"' // insert mask
                } else if (j[p] == '[') {
                    var b = 1 // number of brackets
                    p++
                    while (p < jLen && b > 0) { // skip everything till array end
                        if (j[p] == '[') b++
                        else if (j[p] == ']') b--
                        p++
                    }

                    r[rP++] = '"'; for (mC in m) r[rP++] = mC; r[rP++] = '"' // insert mask
                } else if (j[p] == '"') {
                    p++
                    while (p < jLen && j[p] != '"') p++ // skip the string
                    p++

                    r[rP++] = '"'; for (mC in m) r[rP++] = mC; r[rP++] = '"' // insert mask
                }
                fP = 0
            } else {
                fP = 0 // field should not be masked
                r[rP++] = j[p++]
            }
        } else if (j[p] == '"') { // read field or value (place for optimization)
            r[rP++] = j[p++]
            f = rP
            while (p < jLen && j[p] != '"') r[rP++] = j[p++]
            fP = rP
            r[rP++] = j[p++] // handle " symbol also
        } else if (j[p] == ',') { // comma is a signal that we should reset our field name
            fP = 0
            r[rP++] = j[p++] // handle comma
        } else { // other symbols - just print as is
            r[rP++] = j[p++]
        }
    }

    return r.concatToString(0, rP)
}