package com.andre601.markson;

import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class containing methods to transform the {@link net.kyori.text.TextComponent TextComponents} into either
 * {@link #getJson(TextComponent) JSON Strings} or {@link #getBaseComponents(TextComponent) BaseComponents}.
 */
public class JsonUtil{
    private final Pattern TEXT = Pattern.compile("text:(?<text>\\[.+])", Pattern.DOTALL);
    
    public JsonUtil(){}
    
    /**
     * Translates the provided {@link net.kyori.text.TextComponent TextComponent} into a String containing the usable
     * JSON for Spigot.
     * <br>You can use Spigot's {@link net.md_5.bungee.chat.ComponentSerializer ComponentSerializer} to turn this into
     * a {@link net.md_5.bungee.api.chat.BaseComponent BaseComponent array} which in return can be used in 
     * {@link org.bukkit.entity.Player.Spigot#sendMessage(BaseComponent...) Player.spigot().sendMessage(BaseComponent...)}
     * or any other suitable method.
     * 
     * @param  component
     *         The {@link net.kyori.text.TextComponent TextComponent} to translate into JSON.
     *         
     * @return String containing the usable JSON for Spigot, BungeeCord, etc.
     */
    public String getJson(TextComponent component){
        return GsonComponentSerializer.INSTANCE.serialize(component);
    }
    
    /**
     * Transforms the provided {@link net.kyori.text.TextComponent TextComponent} into a 
     * {@link net.md_5.bungee.api.chat.BaseComponent BaseComponent array}, which is usable in methods like
     * {@link org.bukkit.entity.Player.Spigot#sendMessage(BaseComponent...) Player.spigot().sendMessage(BaseComponent...)
     * }
     * @param  component
     *         The {@link net.kyori.text.TextComponent TextComponent} to translate into a BaseComponent array.
     *         
     * @return {@link net.md_5.bungee.api.chat.BaseComponent BaseComponent array} containing the translated TextComponent.
     */
    public BaseComponent[] getBaseComponents(TextComponent component){
        return ComponentSerializer.parse(getJson(component));
    }
    
    /**
     * Converts an array of Strings to a single one, where each entry is a new line.
     * 
     * @param  strings
     *         The array of Strings to convert.
     *         
     * @return A String containing each entry of the array as a new line.
     */
    public String arrayToString(String... strings){
        return listToString(Arrays.asList(strings));
    }
    
    /**
     * Converts the provided ArrayList (Type String) to a single String, where each entry in the list is a new line.
     * <br>This is just running a {@code String.join("\n", List<String>)} operation.
     * 
     * @param  list
     *         The List to convert. Has to be of type String.
     *         
     * @return A String containing each entry of the List as a new line.
     */
    public String listToString(List<String> list){
        return String.join("\n", list);
    }
    
    /**
     * Converts any appearance of {@code text:["item","item"]} to a String, where each entry is a new line.
     * 
     * <p>This will return the provided, unformatted String in the following cases:
     * <ul>
     *     <li>The String doesn't contain {@code text:["string","string",...]}</li>
     *     <li>The part after the {@code text:} is not valid JSON (JSON Array)</li>
     * </ul>
     * @param  item
     *         The String to convert.
     *         
     * @return The converted String, or the provided String itself if invalid.
     */
    public String parseTextPattern(String item){
        Matcher matcher = TEXT.matcher(item);
        if(!matcher.find())
            return item;
        
        try{
            JSONArray array = new JSONArray(matcher.group("text").replace("\\\"", "\""));
            List<String> items = new ArrayList<>();
            for(int i = 0; i < array.length(); i++)
                items.add(array.getString(i));
            
            return listToString(items);
        }catch(JSONException ignored){
            return item;
        }
    }
}
