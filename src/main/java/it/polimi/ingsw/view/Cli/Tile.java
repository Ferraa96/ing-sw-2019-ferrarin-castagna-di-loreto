package it.polimi.ingsw.view.Cli;

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
    private int height;
    private final String origLine0;
    private final String origLine1;
    private final String origLine2;
    private final String origLine3;
    private final String origLine4;
    private String playerColor = "";

    public Tile(Tile otherTile) {
        this.line0 = origLine0 = otherTile.getLine(0);
        this.line1 = origLine1 = otherTile.getLine(1);
        this.line2 = origLine2 = otherTile.getLine(2);
        this.line3 = origLine3 = otherTile.getLine(3);
        this.line4 = origLine4 = otherTile.getLine(4);
        this.height = otherTile.getHeight();
    }

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

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setIdentifier(String color) {
        line0 = color + origLine0;
        line1 = color + playerColor + origLine1;
        line2 = color + playerColor + origLine2;
        line3 = color + playerColor + origLine3;
        line4 = color + origLine4;
    }

    public void setPlayerInfo(String playerColor) {
        this.playerColor = playerColor;
        line1 = playerColor + line1;
        line2 = playerColor + line2;
        line3 = playerColor + line3;
    }
}
