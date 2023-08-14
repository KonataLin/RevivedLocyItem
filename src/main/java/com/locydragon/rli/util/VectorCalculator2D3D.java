package com.locydragon.rli.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class VectorCalculator2D3D {
    public static Vector[] calculateBasisVector(Location eyeLocation, Vector eyeVector, boolean Using3D) {
        if (!Using3D) {
            Vector y = new Vector(eyeVector.getX(), 0, eyeVector.getZ()).normalize();
            Vector x = new Vector(y.getZ(), 0, -y.getX()).normalize();
            Vector z = new Vector(0, 1, 0);
            return new Vector[] {x, y, z};
        } else {
            Vector y = eyeVector.clone().normalize();
            /**
            double z_3Point = (-(y.getX() * y.getX() + y.getZ() * y.getZ())) / (y.getX() * y.getY());
            double z_2Point = (-(y.getY() * z_3Point + y.getX())) / y.getZ();
            Vector z = new Vector(1, z_3Point, z_2Point).normalize();
            Bukkit.getLogger().info(y.angle(z) + "");
             **/
            Vector z = new Vector();

            double rotX = eyeLocation.getYaw();
            double rotY = eyeLocation.getPitch() - 90;

            z.setY(-Math.sin(Math.toRadians(rotY)));

            double xz = Math.cos(Math.toRadians(rotY));

            z.setX(-xz * Math.sin(Math.toRadians(rotX)));
            z.setZ(xz * Math.cos(Math.toRadians(rotX)));

            Vector x = getCrossProduct(y, z).normalize();
            return new Vector[]{x, y, z};
        }
    }

    public static Vector getCrossProduct(Vector a, Vector o)
    {
        double x = a.getY() * o.getZ() - o.getY() * a.getZ();
        double y = a.getZ() * o.getX() - o.getZ() * a.getX();
        double z = a.getX() * o.getY() - o.getX() * a.getY();
        return new Vector(x, y, z);
    }

    public static double[] rotateOnSphericalCoordinateSystem
            (double x, double y, double z, double zita, double phi) {
        double r = Math.sqrt(x * x + y * y + z * z);
        double prePhi_ = Math.toDegrees(Math.atan(y / x));
        double preZita_ = Math.toDegrees(Math.acos(z / r));
        double newPhi_ = Math.toRadians(prePhi_ + phi);
        double newZita_ = Math.toRadians(preZita_ + zita);
        double newX = r * Math.sin(newZita_) * Math.cos(newPhi_);
        double newY = r * Math.sin(newZita_) * Math.sin(newPhi_);
        double newZ = r * Math.cos(newZita_);
        return new double[] {newX, newY, newZ};
    }

    public static double[] rotation3D(double[] points, double angle, int x, int y, int z)
    {
            double x_old = points[0];
            double y_old = points[1];
            double z_old = points[2];
            double[] initial = {1,0,0,0};
            double[] total = new double[4];
            double[] local = new double[4];
            local[0] = Math.cos(0.5 * angle);
            local[1] = x * Math.sin(0.5 * angle);
            local[2] = y * Math.sin(0.5 * angle);
            local[3] = z * Math.sin(0.5 * angle);
            total[0] = local[0] * initial[0] - local[1] * initial[1] - local[2] * initial[2] - local[3] * initial[3];
            total[1] = local[0] * initial[1] + local[1] * initial[0] + local[2] * initial[3] - local[3] * initial[2];
            total[2] = local[0] * initial[2] - local[1] * initial[3] + local[2] * initial[0] + local[3] * initial[1];
            total[3] = local[0] * initial[3] + local[1] * initial[2] - local[2] * initial[1] + local[3] * initial[0];

            float x_new = (float)((1 - 2 * total[2] * total[2] - 2 * total[3] * total[3]) * x_old
                    + (2 * total[1] * total[2] - 2 * total[0] * total[3]) * y_old
                    + (2 * total[1] * total[3] + 2 * total[0] * total[2]) * z_old);
            float y_new = (float) ((2 * total[1] * total[2] + 2 * total[0] * total[3]) * x_old
                    + (1 - 2 * total[1] * total[1] - 2 * total[3] * total[3]) * y_old
                    + (2 * total[2] * total[3] + 2 * total[0] * total[1]) * z_old);
            float z_new = (float) ((2 * total[1] * total[3] - 2 * total[0] * total[2]) * x_old
                    + (2 * total[2] * total[3] - 2 * total[0] * total[1]) * y_old
                    + (1 - 2 * total[1] * total[1] - 2 * total[2] * total[2]) * z_old);
            return new double[] {x_new, y_new, z_new};
    }
}
