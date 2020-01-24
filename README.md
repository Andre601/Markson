## Markson
Markson (Mix of **Mark**down and J**SON**) is a library used for Spigot, that allows you to parse markdown-like text into JSON TextComponents.

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
Hey. Do you know [Google](on_hover:{show_text:["Click me!"]},on_click:{open_url:"https://google.com"})
```

Doesn't this already look more clean?  
Color and formatting codes can also be used. Either as the original character (`§`) or as the `&` character.

### How does it work?
Markson goes through the text and searches for specific patterns. When it finds those will it take the information in the `()`, parse it and apply it to the text.  
It then returns a TextComponent, that can be used in normal `sendMessage` methods of Spigot.

### Available options
Markson has multiple available options to use.

| Option:       | Syntax:                     | Description:                                                                                                   |
| ------------- | --------------------------- | -------------------------------------------------------------------------------------------------------------- |
| `on_hover`    | `on_hover:{<component>}`    | Sets a values to display when you hover over the text with your cursor. You can only set one value.            |
| `show_text`   | `show_text:["text","text"]` | Used in `on_hover`. Displays the provided text. Format is `["text","text"]` where each new text is a new line. |
| `show_item`   | `show_item:"item_name"`     | Used in `on_hover`. Displays an item provided.                                                                 |
|               |                             |                                                                                                                |
| `on_click`    | `on_click:{<component>}`    | Sets an action to perform when clicking the text. You can only set one action.                                 |
| `open_url`    | `open_url:"url"`            | Opens a provided URL when clicked.                                                                             |
| `suggest_cmd` | `suggest_cmd:"command"`     | Suggests a command (or any other kind of text) to the player.                                                  |
| `perform_cmd` | `perform_cmd:"command"`     | Lets the player execute a command. This has to be a valid command.                                             |