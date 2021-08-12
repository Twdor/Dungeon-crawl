package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    KEY("key"),
    SWORD("sword", 10),
    ARMOUR("armour", 5),
    CLOSED_DOOR("closed door"),
    OPEN_DOOR("open door"),
    TREE("tree"),
    WINDOW("window"),
    FENCE("fence"),
    GATE("gate"),
    PATH("path"),
    ENTRANCE("entrance"),
    GRASS("grass"),
    STAIRS("stairs");

    private final String tileName;

    private int increaseValue;

    CellType(String tileName) { this.tileName = tileName; }

    CellType(String tileName, int increaseValue) { this.tileName = tileName; this.increaseValue = increaseValue; }

    public String getTileName() {
        return tileName;
    }

    public int getIncreaseValue() { return increaseValue; }
}
