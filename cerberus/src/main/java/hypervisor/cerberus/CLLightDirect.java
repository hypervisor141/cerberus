package hypervisor.cerberus;

import hypervisor.firestorm.program.FSLight;
import hypervisor.firestorm.program.FSLightDirect;
import hypervisor.firestorm.sync.FSSyncPostMap;
import hypervisor.vanguard.array.VLArrayFloat;
import hypervisor.vanguard.sync.VLSyncMap;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVTypeManager;
import hypervisor.vanguard.variable.VLVTypeRunner;
import hypervisor.vanguard.variable.VLVariable;

@SuppressWarnings("unused")
public class CLLightDirect extends FSLightDirect{

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
        manager = new VLVManagerDynamic<>(0, 6, 6, 0, new SyncDirection(this));

        CLMaps.SelfActivate<VLVManager<VLVEntry>, VLVManagerDynamic<?>> activator = new CLMaps.SelfActivate<>(manager);
        CLMaps.SelfDeactivate<VLVManager<VLVEntry>, VLVManagerDynamic<?>> deactivator = new CLMaps.SelfDeactivate<>(manager);
        
        VLVManager<VLVEntry> position = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new CLMaps.SetArray(super.position, 0, 0, 3)), deactivator, null);
        VLVManager<VLVEntry> center = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new CLMaps.SetArray(super.center, 0, 0, 3)), deactivator, null);
        VLVManager<VLVEntry> rotatepos = new VLVManager<>(1, 0, activator, new FSSyncPostMap<>(new CLMaps.RotatePoint(super.position, 0, 0F, 0F, 0F)), deactivator, null);
        VLVManager<VLVEntry> rotatecenter = new VLVManager<>(1, 0, activator, new FSSyncPostMap<>(new CLMaps.RotatePoint(super.center, 0, 0F, 0F, 0F)), deactivator, null);
        VLVManager<VLVEntry> scalepos = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new CLMaps.ScalePoint(super.position, 0, 0)), deactivator, null);
        VLVManager<VLVEntry> scalecenter = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new CLMaps.ScalePoint(super.center, 0, 0)), deactivator, null);

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

        target.start();
        manager.start();
    }

    public void positionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, positionX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, positionZ());

        target.start();
        manager.start();
    }

    public void positionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, positionX());
        CLVTools.tune(target, 1, positionY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void centerX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, centerY());
        CLVTools.tune(target, 2, centerZ());

        target.start();
        manager.start();
    }

    public void centerY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, centerX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, centerZ());

        target.start();
        manager.start();
    }

    public void centerZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, centerX());
        CLVTools.tune(target, 1, centerY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void position(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_POSITION);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void center(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_CENTER);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void rotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_POSITION);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)((FSSyncPostMap<?>)target.syncerOnChange()).task.target;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        target.start();
        manager.start();
    }

    public void rotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_CENTER);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        CLMaps.RotatePoint map = (CLMaps.RotatePoint)((FSSyncPostMap<?>)target.syncerOnChange()).task.target;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        target.start();
        manager.start();
    }

    public void scalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_POSITION);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        manager.start();
        target.start();
    }

    public void scaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_CENTER);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        manager.start();
        target.start();
    }

    @Override
    public void copy(FSLight src, long flags){
        super.copy(src, flags);

        CLLightDirect target = (CLLightDirect)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLLightDirect duplicate(long flags){
        return new CLLightDirect(this, flags);
    }

    private static final class SyncDirection extends VLSyncMap<VLVManager<VLVManager<VLVEntry>>, CLLightDirect>{

        public SyncDirection(CLLightDirect target){
            super(target);
        }

        public SyncDirection(SyncDirection src, long flags){
            copy(src, flags);
        }

        protected SyncDirection(){

        }

        @Override
        public void sync(VLVManager<VLVManager<VLVEntry>> source){
            target.updateDirection();
        }

        @Override
        public SyncDirection duplicate(long flags){
            return new SyncDirection(this, flags);
        }
    }
}
