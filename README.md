# PersistenceUtils

> A tiny Java library for file-based persistence: a generic wrapper that serializes an object to disk
> and loads it back with a couple of calls, checking that what comes back really is what you stored.

It is the library [Pingmaster](https://github.com/blacknoize404/Pingmaster) uses to keep user settings
between runs.

## What it does

- **Save and load in one call.** `save()` writes the object with Java serialization; `load()` reads it
  back and returns the wrapper itself, so calls can be chained.
- **Generic over any `Serializable`.** `PersistentWrapper<E>` is typed on the content, so you get the
  object back without casts.
- **Type checking on read.** When loading, the stored bytes are checked against the expected class —
  a file written for another type fails loudly instead of handing you garbage.
- **`serialVersionUID` awareness.** `getContentSerialID()` exposes the UID of the wrapped class, handy
  when migrating saved files across versions.
- **Java module** (`module PersistenceUtils`) with no runtime dependencies.

## Using it

```java
import PersistencyUtils.PersistentWrapper;

// A wrapper for Settings objects, persisted to settings.dat
PersistentWrapper<Settings> store = PersistentWrapper.of(Settings.class, "settings.dat");

// Or start from an existing object
PersistentWrapper<Settings> seeded = PersistentWrapper.of(new Settings(), Settings.class, "settings.dat");

// Mutate, save, load
seeded.setContent(currentSettings);
seeded.save();

PersistentWrapper<Settings> reloaded = seeded.load();
Settings settings = reloaded.getContent();
```

`save()` throws `IOException`; `load()` throws `IOException` and `ClassNotFoundException`, so callers
decide how to recover — Pingmaster, for example, falls back to default settings when the file is missing
or incompatible.

## Getting it

```xml
<dependency>
    <groupId>io.github.blacknoize404</groupId>
    <artifactId>PersistenceUtils</artifactId>
    <version>0.8-RELEASE</version>
</dependency>
```

On the module path: `requires PersistenceUtils;`. Requires Java 17 or newer (built for 27).

## License

Apache License 2.0.
