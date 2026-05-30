## Task
You are a security specialist. You found an issue - HTTP logs contains 
JSON bodies which contains sensitive data in some of their fields. 
You need to mask only sensitive data and keep HTTP bodies in logs.

### Input
- **json** - a JSON string (pretty formatted)
- **fields** - field names (set of strings) which must be masked
- **mask** - and a mask (also a string)

Constraints
- **json** - length < 10^5
- **fields** - size < 10^5
- **mask** - length < 100; 
it is guaranteed that JSON length after masking will not be longer than 
the original JSON length.


### Output
JSON string (pretty formatted) with all fields masked, 
which names appear in fields names.

### Example input:
JSON string:
```json
{
  "hello": "world"
}
```
Field names:
```kotlin
"hello"
```
Mask:
```kotlin
"#"
```

### Example output:
```json
{
  "hello": "#"
}
```