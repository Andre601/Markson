[links]: https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#links
[docs]: https://Andre601.github.io/Markson
[dev]: https://ci.codemc.io/job/Andre601/job/Markson

[versionBadge]: https://jitpack.io/v/Andre601/Markson.svg
[jitpack]: https://jitpack.io/#Andre601/Markson

## Markson
Markson (Combination of the names **Mark**down and J**SON**) is a library used to parse Strings into JSON TextComponents using patterns similar to [markdown's embedded links][links].

### Syntax
The syntax used in Markson is kind of similar to that of Markdown's embedded links, with some additional JSON parts.

Let's make an example.  
I want to display the text "Hey. Do you know Google?" where hovering over the word Google displays the text "Click me!" and clicking it opens the website google.com.  
In the normal JSON would the text look like this:  
```
["",{"text":"Hey. Do you know "},{"text":"Google","clickEvent":{"action":"open_url","value":"https://google.com"},"hoverEvent":{"action":"show_text","value":"Click me!"}},{"text":"?"}]
```

In Markson would this now look like this:  
```
Hey. Do you know [Google](hover:{text:["Click me!"]},click:{url:"https://google.com"})?
```

Doesn't this already look more clean?  
Color and formatting codes can also be used. Either as the original character (`§`) or as the `&` character.

### How does it work?
Markson goes through the text and searches for specific patterns. When it finds those will it take the information in the `()`, parse it and apply it to the text.  
It then returns a TextComponent, that can be used in normal `sendMessage` methods of Spigot.

### Available options
Markson has multiple available options to use.

#### `hover`
`hover` is used to add an action to the text, when you hover over it with your cursor.  
Currently supported are the following options:

| Option:  | Syntax:                | Description:                                                              |
| -------- | ---------------------- | ------------------------------------------------------------------------- |
| `text`   | `text:["text","text"]` | Displays the provided String(s). Each separate String is a new line.      |
| `item`   | `item:"item_name"`     | Displays a specific item. Needs to be a valid item name (IDs don't count) |
| `entity` | `entity:"entity_name"` | Displays the defined achievement.                                         |

### `click`
`click` is used to set actions that are performed when clicking the text.  
Currently supported are the following options:

| Option:       | Syntax:                 | Description:                                                                 |
| ------------- | ----------------------- | ---------------------------------------------------------------------------- |
| `url`         | `url:"url"`             | Opens a provided URL when clicked.                                           |
| `suggest_cmd` | `suggest_cmd:"command"` | Puts a command (or any other provided text) into the chat bar of the player. |
| `perform_cmd` | `perform_cmd:"command"` | Executes a command as the player who performs it.                            |
| `copy`        | `copy:"text"`           | Copies the provided text into the player's clipboard (1.15+ only)            |

## Installation
We use [jitpack] to provide the jar files through Maven and Gradle.  
To install Markson to your IDE of choice, follow the below instructions:

> #### Latest Release
> [![versionBadge]][jitpack]

Please replace `{version}` in the below examples with the above displayed version.

### Gradle
Put this in your build.gradle file:  
```gradle
reositories {
    maven{ url = "https://jitpack.io" }
}

dependencies{
    implementation 'com.github.Andre601:Markson:{version}'
}
```

### Maven
Put this in your pom.xml file:  
```xml
<repositories>
    <repository>
	    <id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.Andre601</groupId>
        <artifactId>Markson</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
```

## Links
- [Javadocs][docs]
- [Development Builds][dev]