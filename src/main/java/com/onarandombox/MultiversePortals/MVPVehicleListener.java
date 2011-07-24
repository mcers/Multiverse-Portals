package com.onarandombox.MultiversePortals;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import com.onarandombox.utils.Destination;
import com.onarandombox.utils.InvalidDestination;

public class MVPVehicleListener extends VehicleListener {
    private MultiversePortals plugin;

    public MVPVehicleListener(MultiversePortals plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle().getPassenger() instanceof Player) {
            System.out.print("Player inside!");
            Vehicle v = event.getVehicle();
            Player p = (Player) v.getPassenger(); // Grab Player

            // Teleport the Player
            teleportVehicle(v, p);
        }
    }

    private boolean teleportVehicle(Vehicle v, Player p) {

        MVPortal portal = this.plugin.getPortalManager().isPortal(p, v.getLocation());
        // If the portal is not null
        // AND if we did not show debug info, do the stuff
        // The debug is meant to toggle.
        if (portal != null) {
            System.out.print("Found a portal!");
            // TODO: Money
            Destination d = portal.getDestination();

            Location l = d.getLocation();
            Vector vec = p.getVelocity();
            System.out.print("Vector: " + vec.toString());
            System.out.print("Fall Distance: " + p.getFallDistance());
            p.setFallDistance(0);

            if (d instanceof InvalidDestination) {
                System.out.print("Invalid dest!");
                return false;
            }
            v.teleport(l);
            return true;
        }
        return false;
    }
}