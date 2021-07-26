package hypervisor.cerberus;

import hypervisor.firestorm.program.FSLight;
import hypervisor.firestorm.program.FSLightSpot;
import hypervisor.vanguard.array.VLArrayFloat;
import hypervisor.vanguard.primitive.VLFloat;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVariable;

@SuppressWarnings("unused")
public class CLLightSpot extends FSLightSpot{

    public static final int CAT_POSITION = 0;
    public static final int CAT_CENTER = 1;
    public static final int CAT_CUTOFF = 2;
    public static final int CAT_OUTERCUTOFF = 3;
    public static final int CAT_ROTATE_POSITION = 4;
    public static final int CAT_ROTATE_CENTER = 5;
    public static final int CAT_SCALE_POSITION = 6;
    public static final int CAT_SCALE_CENTER = 7;

    protected VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLLightSpot(VLArrayFloat position, VLArrayFloat center, VLFloat cutoff, VLFloat outtercutoff){
        super(position, center, cutoff, outtercutoff);
    }

    public CLLightSpot(CLLightSpot src, long flags){
        copy(src, flags);
    }

    protected CLLightSpot(){

    }

    public void buildManager(){
        manager = new VLVManagerDynamic<>(0, 7, 7, 0);

        VLVManager<VLVEntry> position = new VLVManager<>(3, 0, new CLMaps.SetArray(position(), 0, 0, 3));
        VLVManager<VLVEntry> center = new VLVManager<>(3, 0, new CLMaps.SetArray(center(), 0, 0, 3));
        VLVManager<VLVEntry> cutoff = new VLVManager<>(1, 0, new CLMaps.Set(cutOff()));
        VLVManager<VLVEntry> outercutoff = new VLVManager<>(1, 0, new CLMaps.Set(outerCutOff()));
        VLVManager<VLVEntry> rotatepos = new VLVManager<>(1, 0, new CLMaps.RotatePoint(super.position, 0, 0F, 0F, 0F));
        VLVManager<VLVEntry> rotatecenter = new VLVManager<>(1, 0, new CLMaps.RotatePoint(super.center, 0, 0F, 0F, 0F));
        VLVManager<VLVEntry> scalepos = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.position, 0, 0));
        VLVManager<VLVEntry> scalecenter = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.center, 0, 0));

        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));

        center.add(new VLVEntry(new VLVCurved(), 0));
        center.add(new VLVEntry(new VLVCurved(), 0));
        center.add(new VLVEntry(new VLVCurved(), 0));

        cutoff.add(new VLVEntry(new VLVCurved(), 0));
        outercutoff.add(new VLVEntry(new VLVCurved(), 0));

        rotatepos.add(new VLVEntry(new VLVCurved(), 0));
        rotatecenter.add(new VLVEntry(new VLVCurved(), 0));

        scalepos.add(new VLVEntry(new VLVCurved(), 0));
        scalepos.add(new VLVEntry(new VLVCurved(), 0));
        scalepos.add(new VLVEntry(new VLVCurved(), 0));

        scalecenter.add(new VLVEntry(new VLVCurved(), 0));
        scalecenter.add(new VLVEntry(new VLVCurved(), 0));
        scalecenter.add(new VLVEntry(new VLVCurved(), 0));

        manager.addEntry(position);
        manager.addEntry(cutoff);
        manager.addEntry(outercutoff);
        manager.addEntry(center);
        manager.addEntry(rotatepos);
        manager.addEntry(rotatecenter);
        manager.addEntry(scalepos);
        manager.addEntry(scalecenter);
    }

    public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
        return manager;
    }

    public void positionX(float value){
        position.array[0] = value;
    }

    public void positionY(float value){
        position.array[1] = value;
    }

    public void positionZ(float value){
        position.array[2] = value;
    }

    public void centerX(float value){
        center.array[0] = value;
    }

    public void centerY(float value){
        center.array[1] = value;
    }

    public void centerZ(float value){
        center.array[2] = value;
    }

    public void cutOff(float value){
        cutoff.value = value;
    }

    public void outerCutOff(float value){
        outercutoff.value = value;
    }

    public float positionX(){
        return position.array[0];
    }

    public float positionY(){
        return position.array[1];
    }

    public float positionZ(){
        return position.array[2];
    }

    public float centerX(){
        return center.array[0];
    }

    public float centerY(){
        return center.array[1];
    }

    public float centerZ(){
        return center.array[2];
    }

    public float cutOffValue(){
        return cutoff.value;
    }

    public float outerCutOffValue(){
        return outercutoff.value;
    }

    public void rotatePosition(float angle, float x, float y, float z){
        CLVTools.rotateView(position.array, 0, angle, x, y, z);
    }

    public void rotateCenter(float angle, float x, float y, float z){
        CLVTools.rotateView(center.array, 0, angle, x, y, z);
    }

    public void scalePosition(float x, float y, float z){
        CLVTools.scaleView(position.array, 0, x, y, z);
    }

    public void scaleCenter(float x, float y, float z){
        CLVTools.scaleView(center.array, 0, x, y, z);
    }

    public void positionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, positionY());
        CLVTools.tune(target, 2, positionZ());

        manager.activateEntry(CAT_POSITION);
    }

    public void positionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, positionX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, positionZ());

        manager.activateEntry(CAT_POSITION);
    }

    public void positionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, positionX());
        CLVTools.tune(target, 1, positionY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        manager.activateEntry(CAT_POSITION);
    }

    public void centerX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, centerY());
        CLVTools.tune(target, 2, centerZ());

        manager.activateEntry(CAT_CENTER);
    }

    public void centerY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, centerX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, centerZ());

        manager.activateEntry(CAT_CENTER);
    }

    public void centerZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, centerX());
        CLVTools.tune(target, 1, centerY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        manager.activateEntry(CAT_CENTER);
    }

    public void cutOff(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_CUTOFF), 0, from, to, delay, cycles, loop, curve);
        manager.activateEntry(CAT_CUTOFF);
    }

    public void outerCutOff(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_OUTERCUTOFF), 0, from, to, delay, cycles, loop, curve);
        manager.activateEntry(CAT_OUTERCUTOFF);
    }

    public void position(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        manager.activateEntry(CAT_POSITION);
    }

    public void center(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        manager.activateEntry(CAT_CENTER);
    }

    public void rotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_POSITION);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)target.syncerOnChange();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        manager.activateEntry(CAT_ROTATE_POSITION);
        manager.start();
        target.start();
    }

    public void rotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_CENTER);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)target.syncerOnChange();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        manager.activateEntry(CAT_ROTATE_CENTER);
        manager.start();
        target.start();
    }

    public void scalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_POSITION);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        manager.activateEntry(CAT_SCALE_POSITION);
        manager.start();
        target.start();
    }

    public void scaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_CENTER);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        manager.activateEntry(CAT_SCALE_CENTER);
        manager.start();
        target.start();
    }

    @Override
    public void copy(FSLight src, long flags){
        super.copy(src, flags);

        CLLightSpot target = (CLLightSpot)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLLightSpot duplicate(long flags){
        return new CLLightSpot(this, flags);
    }
}

