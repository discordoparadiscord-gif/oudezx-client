package com.oudezx.feature.mod;

import com.oudezx.feature.Feature;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;

public class Waypoints extends Feature {
    public static class Waypoint {
        public String name;
        public double x, y, z;
        public int color;
        public boolean visible;

        public Waypoint(String name, double x, double y, double z, int color) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
            this.color = color;
            this.visible = true;
        }
    }

    private final List<Waypoint> waypoints = new ArrayList<>();

    public Waypoints() {
        super("Waypoints");
    }

    @Override
    public void update() {
        // Update waypoint rendering
    }

    public void addWaypoint(String name, double x, double y, double z, int color) {
        waypoints.add(new Waypoint(name, x, y, z, color));
    }

    public void removeWaypoint(String name) {
        waypoints.removeIf(w -> w.name.equals(name));
    }

    public List<Waypoint> getWaypoints() {
        return new ArrayList<>(waypoints);
    }
}