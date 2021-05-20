package com.nurverek.cerberus;

import android.opengl.Matrix;
import android.util.Log;

import java.util.Arrays;

import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVariable;

public final class CLVTools{

    private CLVTools(){}

    static void tune(VLVEntry entry, float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        entry.delay(delay);
        entry.resetDelayTrackers();

        VLVCurved target = (VLVCurved)entry.target;
        target.setFrom(from);
        target.setTo(to);
        target.setLoop(loop);
        target.setCurve(curve);
        target.initialize(cycles);
    }

    static void rotateView(float[] target, int offset, float angle, float x, float y, float z){
        float[] CACHE4 = new float[4];
        float[] CACHE16 = new float[16];

        for(int i = offset, i2 = 0; i < 3; i++, i2++){
            CACHE4[i2] = target[i];
        }

        CACHE4[3] = 1F;

        Matrix.setIdentityM(CACHE16, 0);
        Matrix.rotateM(CACHE16, 0, angle, x, y, z);
        Matrix.multiplyMV(CACHE4, 0, CACHE16, 0, CACHE4, 0);

        for(int i = offset, i2 = 0; i < 3; i++, i2++){
            target[i] = CACHE4[i2];
        }
    }

    static void scaleView(float[] target, int offset, float x, float y, float z){
        float[] CACHE4 = new float[4];
        float[] CACHE16 = new float[16];

        for(int i = offset, i2 = 0; i < 3; i++, i2++){
            CACHE4[i2] = target[i];
        }

        CACHE4[3] = 1F;

        Matrix.setIdentityM(CACHE16, 0);
        Matrix.scaleM(CACHE16, 0, x, y, z);
        Matrix.multiplyMV(CACHE4, 0, CACHE16, 0, CACHE4, 0);

        for(int i = offset, i2 = 0; i < 3; i++, i2++){
            target[i] = CACHE4[i2];
        }
    }
}
