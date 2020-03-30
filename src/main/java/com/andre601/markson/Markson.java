package com.andre601.markson;

import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class containing the {@link #parse(String, boolean) parser} for parsting Strings to TextComponents.
 */
public class Markson{
    private final Pattern PATTERN = Pattern.compile("\\[(?<text>.+)]\\((?<values>.+)\\)", Pattern.DOTALL);
    
    // Patterns to check, if hover or click patterns exist.
    private final Pattern HOVER = Pattern.compile("hover:\\{(?<type>.+):(?<values>.+)}", Pattern.DOTALL);
    private final Pattern CLICK = Pattern.compile("click:\\{(?<type>.+):(?<values>.+)}", Pattern.DOTALL);
    
    private JsonUtil jsonUtil = new JsonUtil();
    
    public Markson(){}
    
    /**
     * Parses the provided String and returns a usable {@link net.kyori.text.TextComponent TextComponent}.
     * <br>The parser uses {@literal \\[.+]\\(.+\\)} as pattern to find every appearance of the Markson pattern to then
     * parse.
     * <br>This also automatically translates all color codes it can find. If you don't want that, use
     * {@link #parse(String, boolean) parse(String, boolean)} instead.
     *
     * <p><b>Notes</b>
     * <ul>
     *     <li>At no point will Markson try to "validate" entity and item names and just passes them to KyoriPowered's Text library.</li>
     *     <li>The returned TextComponent is <b>not</b> usable by Spigot.
     *     <br>You need to use {@link com.andre601.markson.JsonUtil#getJson(TextComponent) JsonUtil.getJson(TextComponent)}
     *     or {@link com.andre601.markson.JsonUtil#getBaseComponents(TextComponent) JsonUtil.getBaseComponents(TextComponent)}
     *     to create usable instances.</li>
     * </ul>
     * 
     * @param  text
     *         The String to parse.
     *
     * @return A {@link net.kyori.text.TextComponent TextComponet} that can be used.
     *         <br>The TextComponent will be {@link net.kyori.text.TextComponent#empty() empty} when no pattern was found.
     */
    public TextComponent parse(String text){
        return parse(text, true);
    }
    
    /**
     * Parses the provided String and returns a usable {@link net.kyori.text.TextComponent TextComponent}.
     * <br>The parses uses {@literal \\[.+]\\(.+\\)} as pattern to find every appearance of the Markson pattern to then
     * parse.
     *
     * <p><b>Notes</b>
     * <ul>
     *     <li>At no point will Markson try to "validate" entity and item names and just passes them to KyoriPowered's Text library.</li>
     *     <li>The returned TextComponent is <b>not</b> usable by Spigot.
     *     <br>You need to use {@link com.andre601.markson.JsonUtil#getJson(TextComponent) JsonUtil.getJson(TextComponent)}
     *     or {@link com.andre601.markson.JsonUtil#getBaseComponents(TextComponent) JsonUtil.getBaseComponents(TextComponent)}
     *     to create usable instances.</li>
     * </ul>
     * 
     * @param  text
     *         The String to parse.
     * @param  translateColors
     *         If the parser should also translate color codes.
     * 
     * @return A {@link net.kyori.text.TextComponent TextComponet} that can be used.
     *         <br>The TextComponent will be {@link net.kyori.text.TextComponent#empty() empty} when no pattern was found.
     */
    public TextComponent parse(String text, boolean translateColors){
        if(translateColors)
            text = parseColors(text);
        
        Matcher matcher = PATTERN.matcher(text);
        if(matcher.find()){
            TextComponent.Builder builder = TextComponent.builder(text.substring(0, matcher.start()));
            
            builder.append(parseComponent(matcher.group("text"), matcher.group("values")));
            
            parse(builder, text.substring(matcher.end()));
            
            return builder.build();
        }else{
            return TextComponent.empty();
        }
    }
    
    private void parse(TextComponent.Builder builder, String text){
        Matcher matcher = PATTERN.matcher(text);
        if(matcher.find()){
            builder.append(text.substring(0, matcher.start()));
            builder.append(parseComponent(matcher.group("text"), matcher.group("values")));
            
            parse(builder, text);
        }else{
            if(!text.isEmpty())
                builder.append(text);
        }
    }
    
    private TextComponent parseComponent(String text, String values){
        
        TextComponent.Builder builder = TextComponent.builder()
                .content(text);
        
        Matcher hover = HOVER.matcher(values);
        Matcher click = CLICK.matcher(values);
        
        if(hover.find())
            builder.hoverEvent(getHover(hover.group("type"), hover.group("values")));
        
        if(click.find())
            builder.clickEvent(getClick(hover.group("type"), hover.group("values")));
        
        return builder.build();
    }
    
    private HoverEvent getHover(String type, String values){
        switch(type.toLowerCase()){
            case "text":
                return HoverEvent.showText(TextComponent.builder(jsonUtil.parseTextPattern("text:" + values)).build());
            
            case "item":
                return HoverEvent.showItem(TextComponent.builder(values).build());
            
            case "entity":
                return HoverEvent.showEntity(TextComponent.builder(values).build());
            
            default:
                return null;
        }
    }
    
    private ClickEvent getClick(String type, String values){
        switch(type.toLowerCase()){
            case "url":
                return ClickEvent.openUrl(values);
            
            case "suggest_cmd":
                return ClickEvent.suggestCommand(values);
            
            case "perform_cmd":
                return ClickEvent.runCommand(values);
            
            // ClickEvent doesn't support "copy" yet.
            case "copy":
            default:
                return null;
        }
    }
    
    private String parseColors(String input){
        char[] c = input.toCharArray();
        
        for(int i = 0; i < c.length - 1; ++i){
            if(c[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(c[i+1]) > -1){
                c[i] = 167;
                c[i + 1] = Character.toLowerCase(c[i + 1]);
            }
        }
        
        return new String(c);
    }
}
