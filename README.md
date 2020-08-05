[![YAPION Version V1.0.0](https://img.shields.io/badge/YAPION%20Version-1.0.0-red)](https://github.com/yoyosource/YAPIOn/tree/master/)
[![Java Version V1.8.0](https://img.shields.io/badge/Java%20Version-1.8.0-blue.svg)](https://github.com/yoyosource/YAPION/tree/master/)
[![License: Apache 2.0](https://img.shields.io/badge/license-Apache%202-blue)](http://www.apache.org/licenses/LICENSE-2.0)

# YAPION
**YAPION** is a slim and fast Object Notation specifically for Java Objects.
It can handle recursive Object structures and state specific serialization and deserialization (SsS/D).
This SsS/D is achieved by a self build annotation system used by YAPION. This object notation is designed for easy usage with complex Object structures and can do even more complex stuff fairly easy.   

## YAPION's naming
```
Y   -> @yoyosource   
API -> application programming interface
ON  -> object notation
```

## License

## Using in other Projects

# APIs used
- easymock/objenesis (https://github.com/easymock/objenesis)
  - [V] 3.1
  - [L] Apache-2.0

* [V] Version
* [L] License

## Tasks
### Datatypes
- Short
- Byte

### Serialization/Deserialization
- Serialize Java Object
- Deserialize Java Object

### Annotaion
- YAPIONInclude (Remove Null)
- YAPIONExcludeNull? [@JsonInclude(JsonInclude.Include.NON_NULL)]

## Structure
YAPION is heavily inspired by JSON and you can see some similarities between those object notations.
Both have objects as a key, value map and arrays. An YAPION structure can only be an object. An object starts with '{' and ends with '}'. Complementary an array starts with '\[' ends with ']'.
The String representation of an YAPION Object or Array is mostly shorter than the JSON equivalent. This is achieved by eliminating most quotation marks and building the key value pairs in a enum like fashion.
YAPION as a object notation has 5 types. Those are value, array, object, map (see: Map) and pointer (see: Pointer).
Values are indicated by '(...)', arrays by '\[...]', objects by '{...}', map by '<...> and pointers by '->...', the 3 dots are representing the value, or the key value pairs.
## JSON Equivalent
### Objects, Key-Value pairs
```
Example for an empty YAPION object
JSON:   {}
YAPION: {}

Example for an YAPION object with "name":"yoyosource" as value pair
JSON:   {"name":"yoyosource"}
YAPION: {name(yoyosource)}

Example for an YAPION object with "owner":true as value pair
JSON:   {"owner":true}
YAPION: {owner(true)}

Example for an YAPION object with "owner":"true" as value pair
JSON:   {"owner":"true"}
YAPION: {owner("true")}

Example for an YAPION object with "price":10 as value pair
JSON:   {"price":10}
YAPION: {price(10)}

Example for an YAPION object with "   price":10 as value pair
JSON:   {"   price":10}
YAPION: {\   price(10)}

Example for an YAPION object with "name":"yoyosource" and "owner":true as value pairs
JSON:   {"name":"yoyosource","owner":true}
YAPION: {name(yoyosource)owner(true)}
```
### Arrays
```
Example for an empty YAPION array
JSON:   []
YAPION: []

Example for an YAPION array with numbers
JSON:   [0,1,2,3,4,5,6,7,8,9,10]
YAPION: [0,1,2,3,4,5,6,7,8,9,10]

Example for an YAPION array with values
JSON:   [true,false,null,"Hello"]
YAPION: [true,false,null,Hello]

Example for an YAPION array with objects
JSON:   [{"name":"yoyosource","owner":true},{"name":"chaoscaot444","owner":"false"}]
YAPION: [{name(yoyosource),owner(true)},{name(chaoscaot444)owner("false")}]

Example for embedding an YAPION array in another object.
JSON:   {"contributor":[{"name":"yoyosource","owner":true},{"name":"chaoscaot444","owner":"false"}]}
YAPION: {contributor[{name(yoyosource),owner(true)},{name(chaoscaot444)owner("false")}]}
```
## YAPION Specific
### Maps
```
Example for an empty YAPION map
<>

Example for an YAPION map with String to String mapping
<0:1,#0{@(hello)},#1{@("1")}>

Example for an YAPION map with Integer to String mapping
<0:1,#0{@(1)},#1{@("1")}>

Example for an YAPION map with Object to Object mapping
<0:1,#0{@{}},#1{@{}}>
```
### Pointer
These pointers point to another YAPION Object in the same serialization. For reconstructing the recursion this pointer points to the object it is referring to.
A pointer is not a hash and is not intended to be secure. If 2 objects in your tree have the same pointer it will point to the first by default.
All pointer shown in here are valid pointers but are mostly meaningless because a pointer gets constructed by your current object state.
The list of prefixes for pointers is as follows: '0123456789ABCDEF'
```
Example for an YAPION pointer
{hello->7FFFFFE53E6CBDFE}
```