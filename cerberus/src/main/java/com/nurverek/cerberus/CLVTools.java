package com.nurverek.cerberus;

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
}
