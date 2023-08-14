package com.locydragon.rli.util.particle;

public class RotationModel {
    public double zita = 0.0;
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public RotationModel(String code) {
        code = code.trim().replace("(", "").replace(")", "");
        this.zita = Double.valueOf(code.split(",")[0]);
        this.x = (int)(double)Double.valueOf(code.split(",")[1]);
        this.y = (int)(double)Double.valueOf(code.split(",")[2]);
        this.z = (int)(double)Double.valueOf(code.split(",")[3]);
    }
}
