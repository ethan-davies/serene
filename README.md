# Serene
Serene is a simple and easy to use utility library for Minecraft Paper plugins. It is designed to make plugin development easier and more efficient by providing a set of tools and utilities that can be used to simplify common tasks and reduce boilerplate and messy code.

### Installation
Serene is available on JitPack, so you can easily add it to your project by adding the following repository to your `pom.xml` file:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

And then add the following dependency:

```xml
<dependency>
  <groupId>com.github.ethan-davies</groupId>
  <artifactId>Serene</artifactId>
  <version>1.1.1</version>
</dependency>
```

Lastly, using the `maven-shade` plugin is recommended to shade the Serene dependency into your plugin jar.
So add the following to your `pom.xml` file:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.6.0</version>
    <configuration>
        <relocations>
            <relocation>
                <pattern>dev.ethann.serene</pattern>
                <shadedPattern>*your package*.libs.serene</shadedPattern>
            </relocation>
        </relocations>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Usage
Serene can easily be initialised by having your `onEnable` method look something like this:

```java
import dev.ethann.serene.Serene;

@Override
public void onEnable() {
    Serene.init(this);
}
```