package com.github.aelmod.adadm.core;

class Image {
    private String name;
    private byte[] content;

    Image(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    String getName() {
        return name;
    }

    byte[] getContent() {
        return content;
    }
}
