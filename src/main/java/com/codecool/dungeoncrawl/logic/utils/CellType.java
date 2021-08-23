package com.codecool.dungeoncrawl.logic.utils;

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

    public boolean isWalkable(){
        switch (this) {
            case FLOOR:
            case OPEN_DOOR:
            case STAIRS:
                return true;
//            case EMPTY:
//            case WALL:
//            case CLOSED_DOOR:
//            case LIGHT:
//            case CHEST:
//                return false;
        }
        return false;
    }

}
