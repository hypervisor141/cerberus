package com.nurverek.cerberus;

import com.nurverek.firestorm.FSAttenuation;
import com.nurverek.firestorm.FSLightPoint;

import vanguard.VLArrayFloat;
import vanguard.VLListType;
import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVManager;
import vanguard.VLVManagerDynamic;
import vanguard.VLVariable;

public class CLLightPoint extends FSLightPoint{

    public static final int CAT_POSITION = 0;
    public static final int CAT_ROTATE_POSITION = 1;
    public static final int CAT_SCALE_POSITION = 2;

    private VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLLightPoint(FSAttenuation attenuation, VLArrayFloat position){
        super(attenuation, position);
    }

    public void initializeManager(){
        manager = new VLVManagerDynamic<>(3, 0, 3);

        VLVManager<VLVEntry> position = new VLVManager<>(3, 0, new CLMaps.SetArray(position(), 0, 0, 3));
        VLVManager<VLVEntry> rotatepos = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> scalepos = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.position, 0, 0));

        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));

        rotatepos.add(new VLVEntry(new VLVCurved(), new CLMaps.RotatePoint(super.position, 0, 0F, 0F, 0F), 0));

        scalepos.add(new VLVEntry(new VLVCurved(), 0));
        scalepos.add(new VLVEntry(new VLVCurved(), 0));
        scalepos.add(new VLVEntry(new VLVCurved(), 0));

        VLListType<VLVManager<VLVEntry>> entries = manager.entries();

        entries.add(position);
        entries.add(rotatepos);
        entries.add(scalepos);
    }

    public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
        return manager;
    }

    public void start(){
        manager.start();
    }

    public void activate(int category){
        manager.activate(category);
    }

    public void deactivate(int category){
        manager.deactivate(category);
    }

    public void positionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_POSITION).get(0), from, to, delay, cycles, loop, curve);
    }

    public void positionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_POSITION).get(1), from, to, delay, cycles, loop, curve);
    }

    public void positionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_POSITION).get(2), from, to, delay, cycles, loop, curve);
    }

    public void rotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = manager.entries().get(CAT_ROTATE_POSITION).get(0);

        CLVTools.tune(entry, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)entry.syncer;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void scalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_SCALE_POSITION);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }
}
