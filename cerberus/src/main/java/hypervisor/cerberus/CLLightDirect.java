package hypervisor.cerberus;

import hypervisor.firestorm.program.FSLight;
import hypervisor.firestorm.program.FSLightDirect;
import hypervisor.vanguard.array.VLArrayFloat;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVariable;

public final class CLLightDirect extends FSLightDirect{

    public static final int CAT_POSITION = 0;
    public static final int CAT_CENTER = 1;
    public static final int CAT_ROTATE_POSITION = 2;
    public static final int CAT_ROTATE_CENTER = 3;
    public static final int CAT_SCALE_POSITION = 4;
    public static final int CAT_SCALE_CENTER = 5;

    protected VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLLightDirect(VLArrayFloat position, VLArrayFloat center){
        super(position, center);
    }

    public CLLightDirect(CLLightDirect src, long flags){
        copy(src, flags);
    }

    protected CLLightDirect(){

    }

    public void buildManager(){
        manager = new VLVManagerDynamic<>(0, 6, 6, 0);

        VLVManager<VLVEntry> position = new VLVManager<>(3, 0, new CLMaps.SetArray(position(), 0, 0, 3, manager));
        VLVManager<VLVEntry> center = new VLVManager<>(3, 0, new CLMaps.SetArray(center(), 0, 0, 3, manager));
        VLVManager<VLVEntry> rotatepos = new VLVManager<>(1, 0, new CLMaps.RotatePoint(super.position, 0, 0F, 0F, 0F, manager));
        VLVManager<VLVEntry> rotatecenter = new VLVManager<>(1, 0, new CLMaps.RotatePoint(super.center, 0, 0F, 0F, 0F, manager));
        VLVManager<VLVEntry> scalepos = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.position, 0, 0, manager));
        VLVManager<VLVEntry> scalecenter = new VLVManager<>(3, 0, new CLMaps.ScalePoint(super.center, 0, 0, manager));

        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));
        position.add(new VLVEntry(new VLVCurved(), 0));

        center.add(new VLVEntry(new VLVCurved(), 0));
        center.add(new VLVEntry(new VLVCurved(), 0));
        center.add(new VLVEntry(new VLVCurved(), 0));

        rotatepos.add(new VLVEntry(new VLVCurved(), 0));
        rotatecenter.add(new VLVEntry(new VLVCurved(), 0));

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

    public void positionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        CLVTools.tune(manager.getEntry(CAT_POSITION), 0, from, to, delay, cycles, loop, curve, post);
    }

    public void positionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        CLVTools.tune(manager.getEntry(CAT_POSITION), 1, from, to, delay, cycles, loop, curve, post);
    }

    public void positionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        CLVTools.tune(manager.getEntry(CAT_POSITION), 2, from, to, delay, cycles, loop, curve, post);
    }

    public void centerX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        CLVTools.tune(manager.getEntry(CAT_CENTER), 0, from, to, delay, cycles, loop, curve, post);
    }

    public void centerY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        CLVTools.tune(manager.getEntry(CAT_CENTER), 1, from, to, delay, cycles, loop, curve, post);
    }

    public void centerZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        CLVTools.tune(manager.getEntry(CAT_CENTER), 2, from, to, delay, cycles, loop, curve, post);
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

    public void rotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_POSITION);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve, post);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)target.syncer();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void rotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_CENTER);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve, post);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)target.syncer();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void scalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_POSITION);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, post);
    }

    public void scaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_CENTER);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, post);
    }

    @Override
    public void copy(FSLight src, long flags){
        super.copy(src, flags);

        CLLightDirect target = (CLLightDirect)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_FORCE_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLLightDirect duplicate(long flags){
        return new CLLightDirect(this, flags);
    }
}
