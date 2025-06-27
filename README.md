# PersistenceUtils 📦

PersistenceUtils is a persistence module designed to simplify the serialization and deserialization of data in Java. This module provides a generic wrapper that allows you to save and load persistent objects easily, ensuring that data is stored and retrieved correctly from files. 💾✨

## Key Features 🌟

- **Simplified persistence** 📂: Provides easy-to-use methods (`save` and `load`) for serializing and deserializing objects.
- **Generic type compatibility** 🔄: Uses a generic approach that works with any data type implementing the `Serializable` interface. 
- **Class validation** ✅: Includes internal mechanisms to verify compatibility between stored data and expected classes during deserialization.
- **Unique identification** 🔑: Provides a method to retrieve the `serialVersionUID` of the managed class, helping to ensure data integrity when working with different versions of a class.
- **Modular and reusable design** 🧩: The module's design is clean and optimized for use in projects where data persistence is required.

## Basic Usage 🛠️

### Creating an Instance 🆕
```java
// Create a wrapper to persist an object of type MyClass
PersistentWrapper<MyClass> wrapper = PersistentWrapper.of(MyClass.class, "path/to/file.dat");

// Optionally, you can initialize it with an existing object
MyClass myObject = new MyClass();
PersistentWrapper<MyClass> wrapperWithData = PersistentWrapper.of(myObject, MyClass.class, "path/to/file.dat");
```

### Saving Data 💾
```java
wrapper.setContent(myObject); // Set the object to be saved
wrapper.save(); // Serialize and save to the file
```

### Loading Data 📂
```java
wrapper.load(); // Deserialize and load from the file
MyClass loadedObject = wrapper.getContent(); // Get the loaded object
```

### Getting the serialVersionUID 🔑
```java
long serialID = wrapper.getContentSerialID();
System.out.println("SerialVersionUID: " + serialID);
```

## Main Methods 📋

- 💾 `save()`: Saves the current object to the specified file.
- 📂 `load()`: Loads the object from the specified file.
- 🎁 `getContent()`: Retrieves the wrapped object.
- ✍️ `setContent(E content)`: Sets a new object in the wrapper.
- 🔑 `getContentSerialID()`: Returns the `serialVersionUID` of the object's class.

## Requirements ⚙️

- Java 8 ☕ or higher (compatible with the standard serialization API).

## Contributions 🤝

Contributions are welcome! If you find any issues, have suggestions for improvements, or want to add new features, feel free to open an issue or submit a pull request. 🚀

## License 📄

This project is licensed under the MIT License. 📜

---

**Author**: Barnés Inside 👨‍💻  
**Version**: 0.6 🆕  

If you need a robust and flexible solution for handling data persistence in your Java projects, PersistenceUtils is the ideal tool! 💪✨
