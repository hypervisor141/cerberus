package hypervisor.cerberus;

import android.opengl.Matrix;

import hypervisor.firestorm.engine.FSView;
import hypervisor.firestorm.sync.FSSyncPostMap;
import hypervisor.vanguard.sync.VLSyncMap;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVariable;

@SuppressWarnings("unused")
public class CLView extends FSView{

    public static final int CAT_PERSPECTIVE = 0;
    public static final int CAT_ORTHOGRAPHIC = 1;
    public static final int CAT_MOVE_VIEW_POSITION = 2;
    public static final int CAT_MOVE_VIEW_CENTER = 3;
    public static final int CAT_MOVE_VIEW_UP = 4;
    public static final int CAT_ROTATE_VIEW_POSITION = 5;
    public static final int CAT_ROTATE_VIEW_CENTER = 6;
    public static final int CAT_ROTATE_VIEW_UP = 7;
    public static final int CAT_SCALE_VIEW_POSITION = 8;
    public static final int CAT_SCALE_VIEW_CENTER = 9;
    public static final int CAT_SCALE_VIEW_UP = 10;

    protected VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLView(boolean perspectivemode){
        super(perspectivemode);
    }

    protected CLView(){

    }

    protected CLView(CLView src, long flags){
        copy(src, flags);
    }

    public void buildManager(){
        manager = new VLVManagerDynamic<>(0, 11, 11, 0);

        CLMaps.SelfActivate<VLVManager<VLVEntry>, VLVManagerDynamic<?>> activator = new CLMaps.SelfActivate<>(manager);
        CLMaps.SelfDeactivate<VLVManager<VLVEntry>, VLVManagerDynamic<?>> deactivator = new CLMaps.SelfDeactivate<>(manager);

        VLVManager<VLVEntry> perspective = new VLVManager<>(4, 0, activator, new FSSyncPostMap<>(new MapPerspective(this)), deactivator, null);
        VLVManager<VLVEntry> orthographic = new VLVManager<>(6, 0, activator, new FSSyncPostMap<>(new MapOrthographic(this)), deactivator, null);
        VLVManager<VLVEntry> moveviewpos = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new MapView(this, 0)), deactivator, null);
        VLVManager<VLVEntry> moveviewcenter = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new MapView(this, 3)), deactivator, null);
        VLVManager<VLVEntry> moveviewup = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new MapView(this, 6)), deactivator, null);
        VLVManager<VLVEntry> rotateviewpos = new VLVManager<>(1, 0, activator, new FSSyncPostMap<>(new MapRotateView(this, 0, 0F, 0F, 0F)), deactivator, null);
        VLVManager<VLVEntry> rotateviewcenter = new VLVManager<>(1, 0, activator, new FSSyncPostMap<>(new MapRotateView(this, 3, 0F, 0F, 0F)), deactivator, null);
        VLVManager<VLVEntry> rotateviewup = new VLVManager<>(1, 0, activator, new FSSyncPostMap<>(new MapRotateView(this, 6, 0F, 0F, 0F)), deactivator, null);
        VLVManager<VLVEntry> scaleviewpos = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new MapScaleView(this, 0, 0)), deactivator, null);
        VLVManager<VLVEntry> scaleviewcenter = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new MapScaleView(this, 3, 0)), deactivator, null);
        VLVManager<VLVEntry> scaleviewup = new VLVManager<>(3, 0, activator, new FSSyncPostMap<>(new MapScaleView(this, 6, 0)), deactivator, null);

        perspective.add(new VLVEntry(new VLVCurved(), 0));
        perspective.add(new VLVEntry(new VLVCurved(), 0));
        perspective.add(new VLVEntry(new VLVCurved(), 0));
        perspective.add(new VLVEntry(new VLVCurved(), 0));

        orthographic.add(new VLVEntry(new VLVCurved(), 0));
        orthographic.add(new VLVEntry(new VLVCurved(), 0));
        orthographic.add(new VLVEntry(new VLVCurved(), 0));
        orthographic.add(new VLVEntry(new VLVCurved(), 0));
        orthographic.add(new VLVEntry(new VLVCurved(), 0));
        orthographic.add(new VLVEntry(new VLVCurved(), 0));

        moveviewpos.add(new VLVEntry(new VLVCurved(), 0));
        moveviewpos.add(new VLVEntry(new VLVCurved(), 0));
        moveviewpos.add(new VLVEntry(new VLVCurved(), 0));

        moveviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        moveviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        moveviewcenter.add(new VLVEntry(new VLVCurved(), 0));

        moveviewup.add(new VLVEntry(new VLVCurved(), 0));
        moveviewup.add(new VLVEntry(new VLVCurved(), 0));
        moveviewup.add(new VLVEntry(new VLVCurved(), 0));

        rotateviewpos.add(new VLVEntry(new VLVCurved(), 0));
        rotateviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        rotateviewup.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));

        manager.addEntry(perspective);
        manager.addEntry(orthographic);
        manager.addEntry(moveviewpos);
        manager.addEntry(moveviewcenter);
        manager.addEntry(moveviewup);
        manager.addEntry(rotateviewpos);
        manager.addEntry(rotateviewcenter);
        manager.addEntry(rotateviewup);
        manager.addEntry(scaleviewpos);
        manager.addEntry(scaleviewcenter);
        manager.addEntry(scaleviewup);
    }

    public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
        return manager;
    }

    public void viewPositionX(float value){
        settingsview.array[0] = value;
    }

    public void viewPositionY(float value){
        settingsview.array[1] = value;
    }

    public void viewPositionZ(float value){
        settingsview.array[2] = value;
    }

    public void viewCenterX(float value){
        settingsview.array[3] = value;
    }

    public void viewCenterY(float value){
        settingsview.array[4] = value;
    }

    public void viewCenterZ(float value){
        settingsview.array[5] = value;
    }

    public void viewUpX(float value){
        settingsview.array[6] = value;
    }

    public void viewUpY(float value){
        settingsview.array[7] = value;
    }

    public void viewUpZ(float value){
        settingsview.array[8] = value;
    }

    public void orthographicLeft(float value){
        settingsorthographic.array[0] = value;
    }

    public void orthographicRight(float value){
        settingsorthographic.array[1] = value;
    }

    public void orthographicBottom(float value){
        settingsorthographic.array[2] = value;
    }

    public void orthographicTop(float value){
        settingsorthographic.array[3] = value;
    }

    public void orthographicNear(float value){
        settingsorthographic.array[4] = value;
    }

    public void orthographicFar(float value){
        settingsorthographic.array[5] = value;
    }

    public void perspectiveAspect(float value){
        settingsperspective.array[0] = value;
    }

    public void perspectiveFOV(float value){
        settingsperspective.array[1] = value;
    }

    public void perspectiveNear(float value){
        settingsperspective.array[2] = value;
    }

    public void perspectiveFar(float value){
        settingsperspective.array[3] = value;
    }

    public float viewPositionX(){
        return settingsview.array[0];
    }

    public float viewPositionY(){
        return settingsview.array[1];
    }

    public float viewPositionZ(){
        return settingsview.array[2];
    }

    public float viewCenterX(){
        return settingsview.array[3];
    }

    public float viewCenterY(){
        return settingsview.array[4];
    }

    public float viewCenterZ(){
        return settingsview.array[5];
    }

    public float viewUpX(){
        return settingsview.array[6];
    }

    public float viewUpY(){
        return settingsview.array[7];
    }

    public float viewUpZ(){
        return settingsview.array[8];
    }

    public float orthographicLeft(){
        return settingsorthographic.array[0] ;
    }

    public float orthographicRight(){
        return settingsorthographic.array[1] ;
    }

    public float orthographicBottom(){
        return settingsorthographic.array[2] ;
    }

    public float orthographicTop(){
        return settingsorthographic.array[3] ;
    }

    public float orthographicNear(){
        return settingsorthographic.array[4] ;
    }

    public float orthographicFar(){
        return settingsorthographic.array[5] ;
    }

    public float perspectiveAspect(){
        return settingsperspective.array[0] ;
    }

    public float perspectiveFOV(){
        return settingsperspective.array[1] ;
    }

    public float perspectiveNear(){
        return settingsperspective.array[2] ;
    }

    public float perspectiveFar(){
        return settingsperspective.array[3] ;
    }

    public void viewRotatePosition(float angle, float x, float y, float z){
        CLVTools.rotateView(settingsview.array, 0, angle, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewRotateCenter(float angle, float x, float y, float z){
        CLVTools.rotateView(settingsview.array, 3, angle, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewRotateUp(float angle, float x, float y, float z){
        CLVTools.rotateView(settingsview.array, 6, angle, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewScalePosition(float x, float y, float z){
        CLVTools.scaleView(settingsview.array, 0, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewScaleCenter(float x, float y, float z){
        CLVTools.scaleView(settingsview.array, 3, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewScaleUp(float x, float y, float z){
        CLVTools.scaleView(settingsview.array, 6, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewPositionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, viewPositionY());
        CLVTools.tune(target, 2, viewPositionZ());

        target.start();
        manager.start();
    }

    public void viewPositionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, viewPositionX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, viewPositionZ());

        target.start();
        manager.start();
    }

    public void viewPositionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, viewPositionX());
        CLVTools.tune(target, 1, viewPositionY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewCenterX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, viewCenterZ());

        target.start();
        manager.start();
    }

    public void viewCenterY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, viewCenterZ());

        target.start();
        manager.start();
    }

    public void viewCenterZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewUpX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, viewCenterZ());

        target.start();
        manager.start();
    }

    public void viewUpY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, viewCenterZ());

        target.start();
        manager.start();
    }

    public void viewUpZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewPosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewRotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_VIEW_POSITION);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        MapRotateView map = (MapRotateView)target.syncerOnChange();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        target.start();
        manager.start();
    }

    public void viewRotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_VIEW_CENTER);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        MapRotateView map = (MapRotateView)target.syncerOnChange();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        target.start();
        manager.start();
    }

    public void viewRotateUp(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ROTATE_VIEW_UP);
        CLVTools.tune(target, 0, fromangle, toangle, delay, cycles, loop, curve);

        MapRotateView map = (MapRotateView)target.syncerOnChange();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();

        target.start();
        manager.start();
    }

    public void viewScalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_VIEW_POSITION);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewScaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_VIEW_CENTER);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void viewScaleUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_SCALE_VIEW_UP);

        CLVTools.tune(target, 0, fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromZ, toZ, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void perspectiveFov(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_PERSPECTIVE);
        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void perspectiveAspect(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_PERSPECTIVE);
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void perspectiveNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_PERSPECTIVE);
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void perspectiveFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_PERSPECTIVE);
        CLVTools.tune(target, 3, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void perspective(float fromFov, float toFov, float fromAspect, float toAspect, float fromNear, float toNear,
                            float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){

        VLVManager<VLVEntry> target = manager.getEntry(CAT_PERSPECTIVE);

        CLVTools.tune(target, 0, fromFov, toFov, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromAspect, toAspect, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromNear, toNear, delay, cycles, loop, curve);
        CLVTools.tune(target, 3, fromFar, toFar, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographicLeft(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographicRight(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographicBottom(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographicTop(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        CLVTools.tune(target, 3, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographicNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        CLVTools.tune(target, 4, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographicFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        CLVTools.tune(target, 5, from, to, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    public void orthographic(float fromLeft, float toLeft, float fromRight, float toRight, float fromBottom, float toBottom, float fromTop, float toTop, float fromNear, float toNear,
                             float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_ORTHOGRAPHIC);
        
        CLVTools.tune(target, 0, fromLeft, toLeft, delay, cycles, loop, curve);
        CLVTools.tune(target, 1, fromRight, toRight, delay, cycles, loop, curve);
        CLVTools.tune(target, 2, fromBottom, fromBottom, delay, cycles, loop, curve);
        CLVTools.tune(target, 3, fromTop, toTop, delay, cycles, loop, curve);
        CLVTools.tune(target, 4, fromNear, toNear, delay, cycles, loop, curve);
        CLVTools.tune(target, 5, fromFar, toFar, delay, cycles, loop, curve);

        target.start();
        manager.start();
    }

    @Override
    public void copy(FSView src, long flags){
        super.copy(src, flags);

        CLView target = (CLView)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLView duplicate(long flags){
        return new CLView(this, flags);
    }

    private static class MapView extends VLSyncMap<VLVManager<VLVEntry>, CLView>{

        public int offset;
        
        public MapView(CLView view, int offset){
            super(view);
            this.offset = offset;
        }

        public MapView(MapView src, long flags){
            copy(src, flags);
        }

        protected MapView(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.settingsView(offset, source.get(0).target.get());
            target.settingsView(offset + 1, source.get(1).target.get());
            target.settingsView(offset + 2, source.get(2).target.get());

            target.applyLookAt();
            target.applyViewProjection();
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);
            this.offset = ((MapView)src).offset;
        }

        @Override
        public MapView duplicate(long flags){
            return new MapView(this, flags);
        }
    }

    private static class MapPerspective extends VLSyncMap<VLVManager<VLVEntry>, CLView>{

        public MapPerspective(CLView view){
            super(view);
        }

        public MapPerspective(MapPerspective src, long flags){
            copy(src, flags);
        }

        protected MapPerspective(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.perspective(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(), source.get(3).target.get());
            target.applyViewProjection();
        }

        @Override
        public MapPerspective duplicate(long flags){
            return new MapPerspective(this, flags);
        }
    }

    private static class MapOrthographic extends VLSyncMap<VLVManager<VLVEntry>, CLView>{

        public MapOrthographic(CLView view){
            super(view);
        }

        public MapOrthographic(MapOrthographic src, long flags){
            copy(src, flags);
        }

        protected MapOrthographic(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.orthographic(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get());

            target.applyViewProjection();
        }

        @Override
        public MapOrthographic duplicate(long flags){
            return new MapOrthographic(this, flags);
        }
    }

    private static class MapScaleView extends CLMaps.ScalePoint{

        public CLView view;

        public MapScaleView(CLView view, int targetoffset, int sourceoffset){
            super(view.settingsview, targetoffset, sourceoffset);
        }

        public MapScaleView(MapScaleView src, long flags){
            copy(src, flags);
        }

        protected MapScaleView(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);

            view.applyLookAt();
            view.applyViewProjection();
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            MapScaleView target = (MapScaleView)src;
            this.target = target.target;
        }

        @Override
        public MapScaleView duplicate(long flags){
            return new MapScaleView(this, flags);
        }
    }

    public static class MapRotateView extends VLSyncMap<VLVManager<VLVEntry>, CLView>{

        protected float[] cache;
        protected float[] startstatecache;
        public int offset;
        public float x;
        public float y;
        public float z;

        public MapRotateView(CLView view, int offset, float x, float y, float z){
            super(view);

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;

            cache = new float[16];
            startstatecache = new float[4];
        }

        public MapRotateView(MapRotateView src, long flags){
            copy(src, flags);
        }

        protected MapRotateView(){

        }

        public void tune(){
            float[] settings = target.settingsview.array;

            startstatecache[0] = settings[offset];
            startstatecache[1] = settings[offset + 1];
            startstatecache[2] = settings[offset + 2];
            startstatecache[3] = 1F;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            Matrix.setIdentityM(cache, 0);
            Matrix.rotateM(cache, 0, source.get(0).target.get(), x, y, z);
            Matrix.multiplyMV(cache, 0, cache, 0, startstatecache, 0);

            float[] settings = target.settingsview.array;
            settings[offset] = cache[0];
            settings[offset + 1] = cache[1];
            settings[offset + 2] = cache[2];

            target.applyLookAt();
            target.applyViewProjection();
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            MapRotateView target = (MapRotateView)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                cache = target.cache;
                startstatecache = target.startstatecache;

            }else if(((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE)){
                cache = target.cache.clone();
                startstatecache = target.startstatecache.clone();

            }else{
                Helper.throwMissingDefaultFlags();
            }

            offset = target.offset;
            x = target.x;
            y = target.y;
            z = target.z;
        }

        @Override
        public MapRotateView duplicate(long flags){
            return new MapRotateView(this, flags);
        }
    }
}
