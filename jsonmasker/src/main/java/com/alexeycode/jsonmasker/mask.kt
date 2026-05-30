package com.alexeycode.jsonmasker

fun mask(j: String, fs: Set<String>, m: String): String {
    val r = j.toCharArray() // result

    val f = StringBuilder() // buffer for field name
    var p = 0 // pointer for original JSON
    var rP = 0 // pointer for result array
    while (p < j.length) {
        if (j[p] == ':' && f.isNotEmpty()) {
            if (f.toString() in fs) {
                r[rP] = j[p]; p++; rP++ // handle semicolon
                while (p < j.length && j[p] == ' ') { r[rP] = j[p]; p++; rP++ } // handle spaces
                if (j[p] == '{') {
                    var b = 1 // number of brackets
                    p++
                    r[rP++] = '"'; for (mC in m) r[rP++] = mC; r[rP++] = '"' // insert mask
                    while (p < j.length && b > 0) { // skip everything till object end
                        if (j[p] == '{') b++
                        else if (j[p] == '}') b--
                        p++
                    }
                } else if (j[p] == '[') {
                    var b = 1 // number of brackets
                    p++
                    r[rP++] = '"'; for (mC in m) r[rP++] = mC; r[rP++] = '"' // insert mask
                    while (p < j.length && b > 0) { // skip everything till array end
                        if (j[p] == '[') b++
                        else if (j[p] == ']') b--
                        p++
                    }
                } else if (j[p] == '"') {
                    p++
                    r[rP++] = '"'; for (mC in m) r[rP++] = mC; r[rP++] = '"' // insert mask
                    while (p < j.length && j[p] != '"') p++ // skip the string
                    p++
                }
                f.clear()
            } else {
                f.clear() // field should not be masked
                r[rP] = j[p]; p++; rP++
            }
        } else if (j[p] == '"') { // read field or value (place for optimization)
            r[rP] = j[p]; p++; rP++
            f.clear()
            while (p < j.length && j[p] != '"') {
                f.append(j[p])
                r[rP] = j[p]; p++; rP++ // don't forget to mirror it to the result
            }
            r[rP] = j[p]; p++; rP++ // handle " symbol also
        } else if (j[p] == ',') { // comma is a signal that we should reset our field name
            f.clear()
            r[rP] = j[p]; p++; rP++ // handle comma
        } else { // other symbols - just print as is
            r[rP] = j[p]; p++; rP++
        }
    }

    return r.concatToString(0, rP)
}