package dev.me.bombies.dynamiccore.utils.plugin;

import lombok.Getter;

import java.util.Objects;

public class Coordinates {
    @Getter
    private double decimalX, decimalY, decimalZ;

    @Getter
    private int x, y, z;

    public Coordinates(double x, double y, double z) {
        decimalX = x;
        decimalY = y;
        decimalZ = z;
    }

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public Coordinates addX(int inc) {
        this.x += inc;
        return this;
    }

    public Coordinates addY(int inc) {
        this.y += inc;
        return this;
    }

    public Coordinates addZ(int inc) {
        this.z += inc;
        return this;
    }
}
