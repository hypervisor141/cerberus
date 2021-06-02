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

public class CLLightSpot extends FSLightSpot{

    public static final int CAT_POSITION = 0;
    public static final int CAT_CENTER = 1;
    public static final int CAT_CUTOFFS = 2;
    public static final int CAT_ROTATE_POSITION = 3;
    public static final int CAT_ROTATE_CENTER = 4;
    public static final int CAT_SCALE_POSITION = 5;
    public static final int CAT_SCALE_CENTER = 6;

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
        manager = new VLVManagerDynamic<>(7, 0, 7, 0);

        VLVManager<VLVEntry> position = new VLVManager<>(3, 0, new CLMaps.SetArray(position(), 0, 0, 3));
        VLVManager<VLVEntry> center = new VLVManager<>(3, 0, new CLMaps.SetArray(center(), 0, 0, 3));
        VLVManager<VLVEntry> cutoffs = new VLVManager<>(2, 0);
        VLVManager<VLVEntry> rotatepos = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> rotatecenter = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> scalepos = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.position, 0, 0));
        VLVManager<VLVEntry> scalecenter = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.center, 0, 0));

        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));

        center.add(new VLVEntry(new VLVCurved(), 0));
        center.add(new VLVEntry(new VLVCurved(), 0));
        center.add(new VLVEntry(new VLVCurved(), 0));

        cutoffs.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(cutoff)));
        cutoffs.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(outercutoff)));

        rotatepos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.RotatePoint(super.position, 0, 0F, 0F, 0F)));
        rotatecenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.RotatePoint(super.center, 0, 0F, 0F, 0F)));

        scalepos.add(new VLVEntry(new VLVCurved(), 0));
        scalepos.add(new VLVEntry(new VLVCurved(), 0));
        scalepos.add(new VLVEntry(new VLVCurved(), 0));

        scalecenter.add(new VLVEntry(new VLVCurved(), 0));
        scalecenter.add(new VLVEntry(new VLVCurved(), 0));
        scalecenter.add(new VLVEntry(new VLVCurved(), 0));

        manager.addEntry(position);
        manager.addEntry(center);
        manager.addEntry(rotatepos);
        manager.addEntry(rotatecenter);
        manager.addEntry(scalepos);
        manager.addEntry(scalecenter);
        manager.addEntry(cutoffs);
    }

    public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
        return manager;
    }

    public void positionX(float value){
        position.provider()[0] = value;
    }

    public void positionY(float value){
        position.provider()[1] = value;
    }

    public void positionZ(float value){
        position.provider()[2] = value;
    }

    public void centerX(float value){
        center.provider()[0] = value;
    }

    public void centerY(float value){
        center.provider()[1] = value;
    }

    public void centerZ(float value){
        center.provider()[2] = value;
    }

    public void cutOff(float value){
        cutoff.set(value);
    }

    public void outerCutOff(float value){
        outercutoff.set(value);
    }

    public float positionX(){
        return position.provider()[0];
    }

    public float positionY(){
        return position.provider()[1];
    }

    public float positionZ(){
        return position.provider()[2];
    }

    public float centerX(){
        return center.provider()[0];
    }

    public float centerY(){
        return center.provider()[1];
    }

    public float centerZ(){
        return center.provider()[2];
    }

    public float cutOffValue(){
        return cutoff.get();
    }

    public float outerCutOffValue(){
        return outercutoff.get();
    }

    public void positionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_POSITION).get(0), from, to, delay, cycles, loop, curve);
    }

    public void positionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_POSITION).get(1), from, to, delay, cycles, loop, curve);
    }

    public void positionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_POSITION).get(2), from, to, delay, cycles, loop, curve);
    }

    public void centerX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_CENTER).get(0), from, to, delay, cycles, loop, curve);
    }

    public void centerY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_CENTER).get(1), from, to, delay, cycles, loop, curve);
    }

    public void centerZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_CENTER).get(2), from, to, delay, cycles, loop, curve);
    }

    public void cutOff(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_CUTOFFS).get(0), from, to, delay, cycles, loop, curve);
    }

    public void outerCutOff(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.getEntry(CAT_CUTOFFS).get(1), from, to, delay, cycles, loop, curve);
    }

    public void rotatePosition(float angle, float x, float y, float z){
        CLVTools.rotateView(position.provider(), 0, angle, x, y, z);
    }

    public void rotateCenter(float angle, float x, float y, float z){
        CLVTools.rotateView(center.provider(), 0, angle, x, y, z);
    }

    public void scalePosition(float x, float y, float z){
        CLVTools.scaleView(position.provider(), 0, x, y, z);
    }

    public void scaleCenter(float x, float y, float z){
        CLVTools.scaleView(center.provider(), 0, x, y, z);
    }

    public void rotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = manager.getEntry(CAT_ROTATE_POSITION).get(0);

        CLVTools.tune(entry, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)entry.syncer;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void rotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = manager.getEntry(CAT_ROTATE_CENTER).get(0);

        CLVTools.tune(entry, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)entry.syncer;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void scalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_POSITION);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void scaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_CENTER);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }

    @Override
    public void copy(FSLight src, long flags){
        super.copy(src, flags);

        CLLightSpot target = (CLLightSpot)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_FORCE_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLLightSpot duplicate(long flags){
        return new CLLightSpot(this, flags);
    }
}
