package de.votuu.emojis.customchar;

public enum CharCategory {

    EMOTE(":{0}:"),
    TAGS("{{0}}");

    private String replaceType;

    CharCategory(String replaceType) {
        this.replaceType = replaceType;
    }

    public String replaceType() {
        return replaceType;
    }
}
