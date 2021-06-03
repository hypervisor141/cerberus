package hypervisor.cerberus;

import android.opengl.Matrix;

import hypervisor.firestorm.engine.FSView;
import hypervisor.vanguard.sync.VLSyncMap;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVariable;

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

        VLVManager<VLVEntry> perspective = new VLVManager<>(4, 0, new MapPerspective(this));
        VLVManager<VLVEntry> orthographic = new VLVManager<>(6, 0, new MapOrthographic(this));
        VLVManager<VLVEntry> moveviewpos = new VLVManager<>(3, 0, new MapView(this, 0));
        VLVManager<VLVEntry> moveviewcenter = new VLVManager<>(3, 0, new MapView(this, 3));
        VLVManager<VLVEntry> moveviewup = new VLVManager<>(3, 0, new MapView(this, 6));
        VLVManager<VLVEntry> rotateviewpos = new VLVManager<>(1, 0, new MapRotateView(this, 0, 0F, 0F, 0F));
        VLVManager<VLVEntry> rotateviewcenter = new VLVManager<>(1, 0, new MapRotateView(this, 3, 0F, 0F, 0F));
        VLVManager<VLVEntry> rotateviewup = new VLVManager<>(1, 0, new MapRotateView(this, 6, 0F, 0F, 0F));
        VLVManager<VLVEntry> scaleviewpos = new VLVManager<>(3, 0, new MapScaleView(this, 0, 0));
        VLVManager<VLVEntry> scaleviewcenter = new VLVManager<>(3, 0, new MapScaleView(this, 3, 0));
        VLVManager<VLVEntry> scaleviewup = new VLVManager<>(3, 0, new MapScaleView(this, 6, 0));

        perspective.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        perspective.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        perspective.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        perspective.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        orthographic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        orthographic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        orthographic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        orthographic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        orthographic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        orthographic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        moveviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        moveviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        moveviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        moveviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        moveviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        moveviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        moveviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        moveviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        moveviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        rotateviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        rotateviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        rotateviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

        scaleviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

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
        settingsview.provider()[0] = value;
    }

    public void viewPositionY(float value){
        settingsview.provider()[1] = value;
    }

    public void viewPositionZ(float value){
        settingsview.provider()[2] = value;
    }

    public void viewCenterX(float value){
        settingsview.provider()[3] = value;
    }

    public void viewCenterY(float value){
        settingsview.provider()[4] = value;
    }

    public void viewCenterZ(float value){
        settingsview.provider()[5] = value;
    }

    public void viewUpX(float value){
        settingsview.provider()[6] = value;
    }

    public void viewUpY(float value){
        settingsview.provider()[7] = value;
    }

    public void viewUpZ(float value){
        settingsview.provider()[8] = value;
    }

    public void orthographicLeft(float value){
        settingsorthographic.provider()[0] = value;
    }

    public void orthographicRight(float value){
        settingsorthographic.provider()[1] = value;
    }

    public void orthographicBottom(float value){
        settingsorthographic.provider()[2] = value;
    }

    public void orthographicTop(float value){
        settingsorthographic.provider()[3] = value;
    }

    public void orthographicNear(float value){
        settingsorthographic.provider()[4] = value;
    }

    public void orthographicFar(float value){
        settingsorthographic.provider()[5] = value;
    }

    public void perspectiveAspect(float value){
        settingsperspective.provider()[0] = value;
    }

    public void perspectiveFOV(float value){
        settingsperspective.provider()[1] = value;
    }

    public void perspectiveNear(float value){
        settingsperspective.provider()[2] = value;
    }

    public void perspectiveFar(float value){
        settingsperspective.provider()[3] = value;
    }

    public float viewPositionX(){
        return settingsview.provider()[0];
    }

    public float viewPositionY(){
        return settingsview.provider()[1];
    }

    public float viewPositionZ(){
        return settingsview.provider()[2];
    }

    public float viewCenterX(){
        return settingsview.provider()[3];
    }

    public float viewCenterY(){
        return settingsview.provider()[4];
    }

    public float viewCenterZ(){
        return settingsview.provider()[5];
    }

    public float viewUpX(){
        return settingsview.provider()[6];
    }

    public float viewUpY(){
        return settingsview.provider()[7];
    }

    public float viewUpZ(){
        return settingsview.provider()[8];
    }

    public float orthographicLeft(){
        return settingsorthographic.provider()[0] ;
    }

    public float orthographicRight(){
        return settingsorthographic.provider()[1] ;
    }

    public float orthographicBottom(){
        return settingsorthographic.provider()[2] ;
    }

    public float orthographicTop(){
        return settingsorthographic.provider()[3] ;
    }

    public float orthographicNear(){
        return settingsorthographic.provider()[4] ;
    }

    public float orthographicFar(){
        return settingsorthographic.provider()[5] ;
    }

    public float perspectiveAspect(){
        return settingsperspective.provider()[0] ;
    }

    public float perspectiveFOV(){
        return settingsperspective.provider()[1] ;
    }

    public float perspectiveNear(){
        return settingsperspective.provider()[2] ;
    }

    public float perspectiveFar(){
        return settingsperspective.provider()[3] ;
    }

    public void viewPositionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve, post);
        CLVTools.tune(target, 1, viewPositionY());
        CLVTools.tune(target, 2, viewPositionZ());
    }

    public void viewPositionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, viewPositionX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve, post);
        CLVTools.tune(target, 2, viewPositionZ());
    }

    public void viewPositionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(target, 0, viewPositionX());
        CLVTools.tune(target, 1, viewPositionY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve, post);
    }

    public void viewCenterX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve, post);
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, viewCenterZ());
    }

    public void viewCenterY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve, post);
        CLVTools.tune(target, 2, viewCenterZ());
    }

    public void viewCenterZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve, post);
    }

    public void viewUpX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve, post);
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, viewCenterZ());
    }

    public void viewUpY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, from, to, delay, cycles, loop, curve, post);
        CLVTools.tune(target, 2, viewCenterZ());
    }

    public void viewUpZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> target = manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(target, 0, viewCenterX());
        CLVTools.tune(target, 1, viewCenterY());
        CLVTools.tune(target, 2, from, to, delay, cycles, loop, curve, post);
    }

    public void viewPosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_MOVE_VIEW_POSITION);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, post);
    }

    public void viewCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_MOVE_VIEW_CENTER);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, post);
    }

    public void viewUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_MOVE_VIEW_UP);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, post);
    }

    public void viewRotatePosition(float angle, float x, float y, float z){
        CLVTools.rotateView(settingsview.provider(), 0, angle, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewRotateCenter(float angle, float x, float y, float z){
        CLVTools.rotateView(settingsview.provider(), 3, angle, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewRotateUp(float angle, float x, float y, float z){
        CLVTools.rotateView(settingsview.provider(), 6, angle, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewScalePosition(float x, float y, float z){
        CLVTools.scaleView(settingsview.provider(), 0, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewScaleCenter(float x, float y, float z){
        CLVTools.scaleView(settingsview.provider(), 3, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewScaleUp(float x, float y, float z){
        CLVTools.scaleView(settingsview.provider(), 6, x, y, z);

        applyLookAt();
        applyViewProjection();
    }

    public void viewRotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_ROTATE_VIEW_POSITION);
        CLVTools.tune(manager, 0, fromangle, toangle, delay, cycles, loop, curve, post);

        MapRotateView map = (MapRotateView)manager.syncer();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void viewRotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_ROTATE_VIEW_CENTER);
        CLVTools.tune(manager, 0, fromangle, toangle, delay, cycles, loop, curve, post);

        MapRotateView map = (MapRotateView)manager.syncer();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void viewRotateUp(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_ROTATE_VIEW_UP);
        CLVTools.tune(manager, 0, fromangle, toangle, delay, cycles, loop, curve, post);

        MapRotateView map = (MapRotateView)manager.syncer();
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void viewScalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_VIEW_POSITION);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, post);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, null);
    }

    public void viewScaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_VIEW_CENTER);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, post);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, null);
    }

    public void viewScaleUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_SCALE_VIEW_UP);

        CLVTools.tune(manager, 0, fromX, toX, delay, cycles, loop, curve, post);
        CLVTools.tune(manager, 1, fromY, toY, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromZ, toZ, delay, cycles, loop, curve, null);
    }

    public void perspectiveFov(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_PERSPECTIVE), 0, from, to, delay, cycles, loop, curve, post);
    }

    public void perspectiveAspect(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_PERSPECTIVE), 1, from, to, delay, cycles, loop, curve, post);
    }

    public void perspectiveNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_PERSPECTIVE), 2, from, to, delay, cycles, loop, curve, post);
    }

    public void perspectiveFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_PERSPECTIVE), 3, from, to, delay, cycles, loop, curve, post);
    }

    public void perspective(float fromFov, float toFov, float fromAspect, float toAspect, float fromNear, float toNear,
                            float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){

        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_PERSPECTIVE);

        CLVTools.tune(manager, 0, fromFov, toFov, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromAspect, toAspect, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromNear, toNear, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 3, fromFar, toFar, delay, cycles, loop, curve, post);
    }

    public void orthographicLeft(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_ORTHOGRAPHIC), 0, from, to, delay, cycles, loop, curve, post);
    }

    public void orthographicRight(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_ORTHOGRAPHIC), 1, from, to, delay, cycles, loop, curve, post);
    }

    public void orthographicBottom(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_ORTHOGRAPHIC), 2, from, to, delay, cycles, loop, curve, post);
    }

    public void orthographicTop(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_ORTHOGRAPHIC), 3, from, to, delay, cycles, loop, curve, post);
    }

    public void orthographicNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_ORTHOGRAPHIC), 4, from, to, delay, cycles, loop, curve, post);
    }

    public void orthographicFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        CLVTools.tune(manager.getEntry(CAT_ORTHOGRAPHIC), 5, from, to, delay, cycles, loop, curve, post);
    }

    public void orthographic(float fromLeft, float toLeft, float fromRight, float toRight, float fromBottom, float toBottom, float fromTop, float toTop, float fromNear, float toNear,
                             float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
        VLVManager<VLVEntry> manager = this.manager.getEntry(CAT_ORTHOGRAPHIC);
        
        CLVTools.tune(manager, 0, fromLeft, toLeft, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 1, fromRight, toRight, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 2, fromBottom, fromBottom, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 3, fromTop, toTop, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 4, fromNear, toNear, delay, cycles, loop, curve, null);
        CLVTools.tune(manager, 5, fromFar, toFar, delay, cycles, loop, curve, post);
    }

    @Override
    public void copy(FSView src, long flags){
        super.copy(src, flags);

        CLView target = (CLView)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_FORCE_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLView duplicate(long flags){
        return new CLView(this, flags);
    }

    private static class MapView extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public int offset;
        
        public MapView(FSView target, int offset){
            super(target);
            this.offset = offset;
        }

        public MapView(MapView src, long flags){
            copy(src, flags);
        }

        protected MapView(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            float[] settings = target.settingsView().provider();
            settings[offset] = source.get(0).target.get();
            settings[offset + 1] = source.get(1).target.get();
            settings[offset + 2] = source.get(2).target.get();

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

    private static class MapPerspective extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public MapPerspective(FSView target){
            super(target);
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

    private static class MapOrthographic extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public MapOrthographic(FSView target){
            super(target);
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
            this.view = view;
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

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                view = target.view;

            }else if(((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE)){
                view = target.view.duplicate(FLAG_DUPLICATE);

            }else{
                Helper.throwMissingDefaultFlags();
            }
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

        public MapRotateView(CLView target, int offset, float x, float y, float z){
            super(target);

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
            float[] settings = target.settingsview.provider();

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

            float[] settings = target.settingsview.provider();
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
