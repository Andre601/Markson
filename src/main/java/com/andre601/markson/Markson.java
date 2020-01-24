package com.andre601.markson;

import net.kyori.text.TextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Markson{
    private Pattern pattern = Pattern.compile("\\[(?<text>.+)]\\((?<values>.+)\\)");
    
    public Markson(){}
    
    /**
     * Parses the provided String and gives back a {@link net.kyori.text.TextComponent TextComponent} to use.
     * <br>The parser looks for any appearance of {@code [text](options)} to parse.
     * 
     * @param  text
     *         The String to parse.
     *         
     * @return A {@link net.kyori.text.TextComponent TextComponent} containing the parsed String.
     *         <br>If nothing to parse was found does this return the TextComponent without modified text.
     */
    public TextComponent parse(String text){
        Matcher matcher = pattern.matcher(text);
        if(!matcher.find())
            return TextComponent.of(text);
        
        
        
        return null;
    }
}
