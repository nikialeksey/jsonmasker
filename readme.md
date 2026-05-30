## Task
Input:
- JSON string (pretty formatted)
- set of strings - field names which must be masked
- and mask (also a string)

Output:

JSON string (pretty formatted) with all fields masked,
which names appear in fields names.

Example input:
JSON string:
```json
{
  "hello": "world"
}
```
Field names:
```kotlin
setOf(
    "hello"
)
```
Mask:
```kotlin
"#"
```

Example output:
```json
{
  "hello": "#"
}
```