package com.rcc.web;

import java.util.ArrayList;
import java.util.List;

public class Flash implements java.io.Serializable {
    private List messages;
    private List errors;
    private List warnings;

    public Flash() {
        this.messages = new ArrayList();
        this.errors = new ArrayList();
        this.warnings = new ArrayList();
    }

    public Flash(Flash f) {
        this.messages = new ArrayList(f.messages);
        this.errors = new ArrayList(f.errors);
        this.warnings = new ArrayList(f.warnings);
    }

    public void clear() {
        this.messages.clear();
        this.errors.clear();
        this.warnings.clear();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public void addWarning(String warning) {
        this.warnings.add(warning);
    }

    public List getMessages() {
        return this.messages;
    }

    public List getErrors() {
        return this.errors;
    }

    public List getWarnings() {
        return this.warnings;
    }

    public boolean hasMessages() {
        return !this.messages.isEmpty();
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public boolean hasWarnings() {
        return !this.warnings.isEmpty();
    }

    public boolean hasAny() {
        return this.hasMessages() || this.hasErrors() || this.hasWarnings();
    }
}
