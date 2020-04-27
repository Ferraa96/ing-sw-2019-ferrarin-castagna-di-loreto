package it.polimi.ingsw.view;

import com.google.gson.annotations.SerializedName;

public class Tile {
    @SerializedName("line0")
    private final String line0;
    @SerializedName("line1")
    private final String line1;
    @SerializedName("line2")
    private final String line2;
    @SerializedName("line3")
    private final String line3;
    @SerializedName("line4")
    private String line4;
    private int height = 0;

    public Tile(Tile otherTile) {
        this.line0 = otherTile.getLine(0);
        this.line1 = otherTile.getLine(1);
        this.line2 = otherTile.getLine(2);
        this.line3 = otherTile.getLine(3);
        this.line4 = otherTile.getLine(4);
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

    public void setIdentifier(char id) {
        String line = line4;
        line = line.substring(0, 12);
        line4 = line + id;
    }
}
