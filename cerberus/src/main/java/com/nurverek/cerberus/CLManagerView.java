package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSControl;
import com.nurverek.firestorm.FSView;
import com.nurverek.vanguard.VLListType;
import com.nurverek.vanguard.VLSyncMap;
import com.nurverek.vanguard.VLVCurved;
import com.nurverek.vanguard.VLVEntry;
import com.nurverek.vanguard.VLVManager;
import com.nurverek.vanguard.VLVManagerRootDynamic;
import com.nurverek.vanguard.VLVTypeRunner;
import com.nurverek.vanguard.VLVariable;

public class CLViewConfig{
    
    public static final int MANAGER_POSITION = 0;
    public static final int MANAGER_PESPECTIVE = 1;
    public static final int MANAGER_LOOKAT = 2;

    private FSView target;
    private VLVManagerRootDynamic manager;

    protected CLViewConfig(){
        this.target = target;
        initialize();
    }
    
    public void initialize(FSView target){
        manager = new VLVManagerRootDynamic(4, 0, 4);

        VLVManager view = new VLVManager(9, 0, new MapView(target));
        VLVManager perspective = new VLVManager(4, 0, new MapPerspective(target));
        VLVManager orthographic = new VLVManager(6, 0, new MapOrthographic(target));

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

        VLListType<VLVTypeRunner> entries = manager.entries();
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

    public VLVManagerRootDynamic manager(){
        return manager;
    }

    private void tune(int managerindex, int varindex, float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = ((VLVManager)manager.entries().get(managerindex)).get(varindex);
        entry.delay(delay);
        entry.resetDelayTrackers();

        VLVCurved target = (VLVCurved)entry.target;
        target.setFrom(from);
        target.setTo(to);
        target.setLoop(loop);
        target.setCurve(curve);
        target.initialize(cycles);
    }

    public void positionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_POSITION, 0, from, to, delay, cycles, loop, curve);
    }

    public void positionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_POSITION, 1, from, to, delay, cycles, loop, curve);
    }

    public void positionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_POSITION, 2, from, to, delay, cycles, loop, curve);
    }

    public void centerX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_LOOKAT, 3, from, to, delay, cycles, loop, curve);
    }

    public void centerY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_LOOKAT, 4, from, to, delay, cycles, loop, curve);
    }

    public void centerZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_LOOKAT, 5, from, to, delay, cycles, loop, curve);
    }

    public void upX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_LOOKAT, 6, from, to, delay, cycles, loop, curve);
    }

    public void upY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_LOOKAT, 7, from, to, delay, cycles, loop, curve);
    }

    public void upZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_LOOKAT, 8, from, to, delay, cycles, loop, curve);
    }

    public void fov(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_PESPECTIVE, 0, from, to, delay, cycles, loop, curve);
    }

    public void aspect(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_PESPECTIVE, 1, from, to, delay, cycles, loop, curve);
    }

    public void near(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_PESPECTIVE, 2, from, to, delay, cycles, loop, curve);
    }

    public void far(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        tune(MANAGER_PESPECTIVE, 3, from, to, delay, cycles, loop, curve);
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

    private static class MapView extends VLSyncMap<VLVManager, FSView>{
        
        public MapView(FSView target){
            super(target);
        }

        @Override
        public void sync(VLVManager source){
            target.lookAt(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get(),
                    source.get(6).target.get(), source.get(7).target.get(), source.get(8).target.get());

            target.applyViewProjection();
        }
    }

    private static class MapPerspective extends VLSyncMap<VLVManager, FSView>{

        public MapPerspective(FSView target){
            super(target);
        }

        @Override
        public void sync(VLVManager source){
            target.perspective(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(), source.get(3).target.get());
            target.applyViewProjection();
        }
    }

    private static class MapOrthographic extends VLSyncMap<VLVManager, FSView>{

        public MapOrthographic(FSView target){
            super(target);
        }

        @Override
        public void sync(VLVManager source){
            target.orthographic(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get());

            target.applyViewProjection();
        }
    }
}
