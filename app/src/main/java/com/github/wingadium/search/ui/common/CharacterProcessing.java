package com.github.wingadium.search.ui.common;

public class CharacterProcessing {
    public static String remove_unicode(String str) {
        str = org.apache.commons.lang3.StringUtils.stripAccents(str);
        return str;
    }
}

