package me.eduardo.humanresources.domain;

import org.springframework.lang.Nullable;

public class ExceptionMessage {

    @Nullable
    private String text;

    public ExceptionMessage(@Nullable String text) {
        this.text = text;
    }

    public ExceptionMessage() {
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

}
