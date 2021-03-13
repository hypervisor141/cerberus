package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSView;

import vanguard.VLListType;
import vanguard.VLSyncMap;
import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVManager;
import vanguard.VLVManagerDynamic;
import vanguard.VLVariable;

public class CLView{

    public static final int CAT_VIEW = 0;
    public static final int CAT_PERSPECTIVE = 1;
    public static final int CAT_ORTHOGRAPHIC = 2;
    public static final int CAT_ROTATE_VIEW_POSITION = 3;
    public static final int CAT_ROTATE_VIEW_CENTER = 4;
    public static final int CAT_ROTATE_VIEW_UP = 5;
    public static final int CAT_SCALE_VIEW_POSITION = 6;
    public static final int CAT_SCALE_VIEW_CENTER = 7;
    public static final int CAT_SCALE_VIEW_UP = 8;

    private FSView target;
    private VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLView(FSView target){
        initialize(target);
    }

    public void initialize(FSView target){
        this.target = target;

        float[] viewsettings = target.settingsView().provider();

        manager = new VLVManagerDynamic<>(3, 0, 3);

        MapView mapview = new MapView(target);
        MapPerspective mappers = new MapPerspective(target);
        MapOrthographic maportho = new MapOrthographic(target);

        VLVManager<VLVEntry> view = new VLVManager<>(9, 0, mapview);
        VLVManager<VLVEntry> perspective = new VLVManager<>(4, 0, mappers);
        VLVManager<VLVEntry> orthographic = new VLVManager<>(6, 0, maportho);
        VLVManager<VLVEntry> rotateviewpos = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> rotateviewcenter = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> rotateviewup = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> scaleviewpos = new VLVManager<>(3, 0, new MapScale(viewsettings, 0));
        VLVManager<VLVEntry> scaleviewcenter = new VLVManager<>(3, 0, new MapScale(viewsettings, 3));
        VLVManager<VLVEntry> scaleviewup = new VLVManager<>(3, 0, new MapScale(viewsettings, 6));

        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));
        view.add(new VLVEntry(new VLVCurved(), 0));

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

        rotateviewpos.add(new VLVEntry(new VLVCurved(), new MapRotate(viewsettings, 0, 0F, 0F, 0F), 0));
        rotateviewcenter.add(new VLVEntry(new VLVCurved(), new MapRotate(viewsettings, 3, 0F, 0F, 0F), 0));
        rotateviewup.add(new VLVEntry(new VLVCurved(), new MapRotate(viewsettings, 6, 0F, 0F, 0F), 0));

        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));

        VLListType<VLVManager<VLVEntry>> entries = manager.entries();

        entries.add(view);
        entries.add(perspective);
        entries.add(orthographic);
        entries.add(rotateviewpos);
        entries.add(rotateviewcenter);
        entries.add(rotateviewup);
        entries.add(scaleviewpos);
        entries.add(scaleviewcenter);
        entries.add(scaleviewup);
    }
    
    public void target(FSView target){
        this.target = target;
    }

    public FSView target(){
        return target;
    }

    public VLVManager<VLVManager<VLVEntry>> manager(){
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

    private void tune(int category, int variable, float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = manager.get().get(category).get(variable);
        entry.delay(delay);
        entry.resetDelayTrackers();

        VLVCurved target = (VLVCurved)entry.target;
        target.setFrom(from);
        target.setTo(to);
        target.setLoop(loop);
        target.setCurve(curve);
        target.initialize(cycles);
    }

    public void viewPositionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,0, from, to, delay, cycles, loop, curve);
    }

    public void viewPositionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,1, from, to, delay, cycles, loop, curve);
    }

    public void viewPositionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,2, from, to, delay, cycles, loop, curve);
    }

    public void viewCenterX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,3, from, to, delay, cycles, loop, curve);
    }

    public void viewCenterY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,4, from, to, delay, cycles, loop, curve);
    }

    public void viewCenterZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,5, from, to, delay, cycles, loop, curve);
    }

    public void viewUpX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,6, from, to, delay, cycles, loop, curve);
    }

    public void viewUpY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,7, from, to, delay, cycles, loop, curve);
    }

    public void viewUpZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,8, from, to, delay, cycles, loop, curve);
    }

    public void viewPosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,0, fromX, toX, delay, cycles, loop, curve);
        tune(CAT_VIEW,1, fromY, toY, delay, cycles, loop, curve);
        tune(CAT_VIEW,2, fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,3, fromX, toX, delay, cycles, loop, curve);
        tune(CAT_VIEW,4, fromY, toY, delay, cycles, loop, curve);
        tune(CAT_VIEW,5, fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_VIEW,6, fromX, toX, delay, cycles, loop, curve);
        tune(CAT_VIEW,7, fromY, toY, delay, cycles, loop, curve);
        tune(CAT_VIEW,8, fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewRotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ROTATE_VIEW_POSITION, 0, fromangle, toangle, delay, cycles, loop, curve);

        MapRotate map = (MapRotate)manager.get(CAT_ROTATE_VIEW_POSITION).get(0).syncer;
        map.x = x;
        map.y = y;
        map.z = z;
    }

    public void viewRotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ROTATE_VIEW_CENTER, 0, fromangle, toangle, delay, cycles, loop, curve);

        MapRotate map = (MapRotate)manager.get(CAT_ROTATE_VIEW_CENTER).get(0).syncer;
        map.x = x;
        map.y = y;
        map.z = z;
    }

    public void viewRotateUp(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ROTATE_VIEW_UP, 0, fromangle, toangle, delay, cycles, loop, curve);

        MapRotate map = (MapRotate)manager.get(CAT_ROTATE_VIEW_UP).get(0).syncer;
        map.x = x;
        map.y = y;
        map.z = z;
    }

    public void viewScalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_SCALE_VIEW_POSITION, 0, fromX, toX, delay, cycles, loop, curve);
        tune(CAT_SCALE_VIEW_POSITION, 1, fromY, toY, delay, cycles, loop, curve);
        tune(CAT_SCALE_VIEW_POSITION, 2, fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewScaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_SCALE_VIEW_CENTER, 0, fromX, toX, delay, cycles, loop, curve);
        tune(CAT_SCALE_VIEW_CENTER, 1, fromY, toY, delay, cycles, loop, curve);
        tune(CAT_SCALE_VIEW_CENTER, 2, fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewScaleUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_SCALE_VIEW_UP, 0, fromX, toX, delay, cycles, loop, curve);
        tune(CAT_SCALE_VIEW_UP, 1, fromY, toY, delay, cycles, loop, curve);
        tune(CAT_SCALE_VIEW_UP, 2, fromZ, toZ, delay, cycles, loop, curve);
    }

    public void perspectiveFov(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,0, from, to, delay, cycles, loop, curve);
    }

    public void perspectiveAspect(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,1, from, to, delay, cycles, loop, curve);
    }

    public void perspectiveNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,2, from, to, delay, cycles, loop, curve);
    }

    public void perspectiveFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,3, from, to, delay, cycles, loop, curve);
    }

    public void perspective(float fromFov, float toFov, float fromAspect, float toAspect, float fromNear, float toNear,
                            float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){

        tune(CAT_PERSPECTIVE,0, fromFov, toFov, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,1, fromAspect, toAspect, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,2, fromNear, toNear, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,3, fromFar, toFar, delay, cycles, loop, curve);
    }

    public void orthographicLeft(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ORTHOGRAPHIC,0, from, to, delay, cycles, loop, curve);
    }

    public void orthographicRight(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ORTHOGRAPHIC,1, from, to, delay, cycles, loop, curve);
    }

    public void orthographicBottom(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ORTHOGRAPHIC,2, from, to, delay, cycles, loop, curve);
    }

    public void orthographicTop(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ORTHOGRAPHIC,3, from, to, delay, cycles, loop, curve);
    }

    public void orthographicNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ORTHOGRAPHIC,4, from, to, delay, cycles, loop, curve);
    }

    public void orthographicFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_ORTHOGRAPHIC,5, from, to, delay, cycles, loop, curve);
    }

    public void orthographic(float fromLeft, float toLeft, float fromRight, float toRight, float fromBottom, float toBottom, float fromTop, float toTop, float fromNear, float toNear,
                             float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){

        tune(CAT_ORTHOGRAPHIC,0, fromLeft, toLeft, delay, cycles, loop, curve);
        tune(CAT_ORTHOGRAPHIC,1, fromRight, toRight, delay, cycles, loop, curve);
        tune(CAT_ORTHOGRAPHIC,2, fromBottom, fromBottom, delay, cycles, loop, curve);
        tune(CAT_ORTHOGRAPHIC,3, fromTop, toTop, delay, cycles, loop, curve);
        tune(CAT_ORTHOGRAPHIC,4, fromNear, toNear, delay, cycles, loop, curve);
        tune(CAT_ORTHOGRAPHIC,5, fromFar, toFar, delay, cycles, loop, curve);
    }

    private static class MapView extends VLSyncMap<VLVManager<VLVEntry>, FSView>{
        
        public MapView(FSView target){
            super(target);
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.lookAt(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get(),
                    source.get(6).target.get(), source.get(7).target.get(), source.get(8).target.get());

            target.applyViewProjection();
        }
    }

    private static class MapRotate extends VLSyncMap<VLVEntry, float[]>{

        public float[] cache;
        public int offset;
        public float x;
        public float y;
        public float z;

        public MapRotate(float[] target, int offset, float x, float y, float z){
            super(target);

            cache = new float[16];

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void sync(VLVEntry source){
            Matrix.rotateM(cache, 0, source.target.get(), x, y, z);
            Matrix.multiplyMV(target, offset, cache, 0, target, offset);
        }
    }

    private static class MapScale extends VLSyncMap<VLVManager<VLVEntry>, float[]>{

        public float[] cache;
        public int offset;

        public MapScale(float[] target, int offset){
            super(target);

            cache = new float[16];
            this.offset = offset;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            Matrix.scaleM(cache, 0, source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get());
            Matrix.multiplyMV(target, offset, cache, 0, target, offset);
        }
    }

    private static class MapPerspective extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public MapPerspective(FSView target){
            super(target);
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.perspective(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(), source.get(3).target.get());
            target.applyViewProjection();
        }
    }

    private static class MapOrthographic extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public MapOrthographic(FSView target){
            super(target);
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.orthographic(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get());

            target.applyViewProjection();
        }
    }
}
