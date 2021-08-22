package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    CLOSED_DOOR("closed door"),
    OPEN_DOOR("open door"),
    TREE("tree"),
    WINDOW("window"),
    FENCE("fence"),
    GATE("gate"),
    PATH("path"),
    ENTRANCE("entrance"),
    GRASS("grass"),
    STAIRS("stairs"),
    ENEMY("enemy"),
    FLAG("flag"),
    BARS("bars"),
    BARREL("barrel"),
    CHEST("chest"),
    LIGHT("light");

    private final String tileName;


    CellType(String tileName) { this.tileName = tileName; }


    public String getTileName() {
        return tileName;
    }

}
