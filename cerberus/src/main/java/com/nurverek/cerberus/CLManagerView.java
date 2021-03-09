package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSControl;
import com.nurverek.firestorm.FSView;

import vanguard.VLListType;
import vanguard.VLSyncMap;
import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVManager;
import vanguard.VLVManagerDynamic;
import vanguard.VLVManagerRoot;
import vanguard.VLVManagerRootDynamic;
import vanguard.VLVTypeRunner;
import vanguard.VLVariable;

public class CLManagerView{

    public static final int CAT_VIEW = 0;
    public static final int CAT_PERSPECTIVE = 1;
    public static final int CAT_ORTHOGRAPHIC = 2;

    private FSView target;
    private VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLManagerView(FSView target){
        initialize(target);
    }

    public void initialize(FSView target){
        this.target = target;

        manager = new VLVManagerDynamic<>(3, 0, 3, new MapManagerCheck(manager));

        VLVManager<VLVEntry> view = new VLVManager<>(9, 0, new MapView(target));
        VLVManager<VLVEntry> perspective = new VLVManager<>(4, 0, new MapPerspective(target));
        VLVManager<VLVEntry> orthographic = new VLVManager<>(6, 0, new MapOrthographic(target));

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

        VLListType<VLVManager<VLVEntry>> entries = manager.entries();
        entries.add(view);
        entries.add(perspective);
        entries.add(orthographic);
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
        tune(CAT_PERSPECTIVE,0, from, to, delay, cycles, loop, curve);
    }

    public void orthographicRight(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,1, from, to, delay, cycles, loop, curve);
    }

    public void orthographicBottom(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,2, from, to, delay, cycles, loop, curve);
    }

    public void orthographicTop(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,3, from, to, delay, cycles, loop, curve);
    }

    public void orthographicNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,4, from, to, delay, cycles, loop, curve);
    }

    public void orthographicFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        tune(CAT_PERSPECTIVE,5, from, to, delay, cycles, loop, curve);
    }

    public void orthographic(float fromLeft, float toLeft, float fromRight, float toRight, float fromBottom, float toBottom, float fromTop, float toTop, float fromNear, float toNear,
                             float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){

        tune(CAT_PERSPECTIVE,0, fromLeft, toLeft, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,1, fromRight, toRight, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,2, fromBottom, fromBottom, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,3, fromTop, toTop, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,4, fromNear, toNear, delay, cycles, loop, curve);
        tune(CAT_PERSPECTIVE,5, fromFar, toFar, delay, cycles, loop, curve);
    }

    public void rotate(float from, float to, float rotationx, float rotationy, float rotationz, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        final float[] settings = FSControl.getViewConfig().viewMatrixSettings().provider().clone();

        tune(MANAGER_ROTATION, NODE_ROTATION_ANGLE, from, to, delay, cycles, loop, curve, new VLTaskContinous(new VLTask.Task(){

            private float[] cache = new float[16];

            @Override
            public void run(VLTask task, VLVariable var){
                final float[] pos = target.eyePosition().provider();

                Matrix.setIdentityM(cache, 0);
                Matrix.rotateM(cache, 0, var.get(), rotationx, rotationy, rotationz);
                Matrix.multiplyMV(pos, 0, cache, 0, settings, 0);

                target.eyePositionUpdate();
                target.lookAtUpdate();
                target.updateViewProjection();

                if(!var.active() && post != null){
                    post.run();
                }
            }
        }));
    }

    private static class MapManagerCheck extends VLSyncMap<VLVManager<VLVManager<VLVEntry>>, VLVManagerDynamic<VLVManager<VLVEntry>>>{

        public MapManagerCheck(VLVManagerDynamic<VLVManager<VLVEntry>> target){
            super(target);
        }

        @Override
        public void sync(VLVManager<VLVManager<VLVEntry>> source){
            if(target.done()){
                target.get().nullify();
            }
        }
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
