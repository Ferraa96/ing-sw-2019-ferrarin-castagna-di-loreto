package it.polimi.ingsw.view;

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

    public void setIdentifier(char id) {
        String line = line4;
        line = line.substring(0, 12);
        line4 = line + id;
    }
}
