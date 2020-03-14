package it.polimi.ingsw.View;

import com.google.gson.annotations.SerializedName;

public class Tile {
    @SerializedName("line0")
    private String line0;
    @SerializedName("line1")
    private String line1;
    @SerializedName("line2")
    private String line2;
    @SerializedName("line3")
    private String line3;
    @SerializedName("line4")
    private String line4;
    @SerializedName("height")
    private int height;
    @SerializedName("player")
    private boolean player;

    public String getLine(int x) {
        switch (x) {
            case 0:
                return line0;
            case 1:
                return line1;
            case 2:
                return line2;
            case 3:
                return line3;
            default:
                return line4;
        }
    }

    public String getLine0() {
        return line0;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getLine3() {
        return line3;
    }

    public String getLine4() {
        return line4;
    }

    public int getHeight() {
        return height;
    }

    public boolean getPlayer() {
        return player;
    }
}
