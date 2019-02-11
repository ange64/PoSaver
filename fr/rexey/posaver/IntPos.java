package fr.rexey.posaver;


import org.bukkit.Location;

public class IntPos {

    public final int X, Y, Z;
    private static final String sep = " / ";

    public IntPos(Location l) {
        X = (int)l.getX();
        Y = (int)l.getY();
        Z = (int)l.getZ();
    }

    public IntPos(int x ,int y, int z) {
        X = x;
        Y = y;
        Z = z;
    }

    public IntPos(String savedString) {
        String[] values = savedString.split(";");
        X = Integer.valueOf(values[0]);
        Y = Integer.valueOf(values[1]);
        Z = Integer.valueOf(values[2]);
    }

    @Override
    public String toString() {
        return X + ";" + Y + ";" + Z;
    }

    public String displayString() {
        return "[ " + X + "x" + sep + Y + "y" + sep + Z + " ]";
    }

}
