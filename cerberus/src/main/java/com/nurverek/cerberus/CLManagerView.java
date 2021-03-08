package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSControl;
import com.nurverek.firestorm.FSView;
import com.nurverek.vanguard.VLListType;
import com.nurverek.vanguard.VLSyncMap;
import com.nurverek.vanguard.VLVCurved;
import com.nurverek.vanguard.VLVEntry;
import com.nurverek.vanguard.VLVManager;
import com.nurverek.vanguard.VLVManagerRoot;
import com.nurverek.vanguard.VLVManagerRootDynamic;
import com.nurverek.vanguard.VLVTypeRunner;
import com.nurverek.vanguard.VLVariable;

import vanguard.VLListType;
import vanguard.VLSyncMap;
import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVManager;
import vanguard.VLVManagerRoot;
import vanguard.VLVManagerRootDynamic;
import vanguard.VLVTypeRunner;

public class CLManagerView{

    public static final int CAT_VIEW = 0;
    public static final int CAT_PERSPECTIVE = 1;
    public static final int CAT_ORTHO = 2;

    public static final int VAR_VIEW_X = 0;
    public static final int VAR_VIEW_Y = 1;
    public static final int VAR_VIEW_Z = 2;
    public static final int VAR_CENTER_X = 3;
    public static final int VAR_CENTER_Y = 4;
    public static final int VAR_CENTER_Z = 5;
    public static final int VAR_UP_X = 6;
    public static final int VAR_UP_Y = 7;
    public static final int VAR_UP_Z = 8;

    public static final int VAR_PERS_FOV = 0;
    public static final int VAR_PERS_ASPECT = 1;
    public static final int VAR_PERS_NEAR = 2;
    public static final int VAR_PERS_FAR = 3;

    public static final int VAR_ORTHO_LEFT = 0;
    public static final int VAR_ORTHO_RIGHT = 1;
    public static final int VAR_ORTHO_BOTTOM = 2;
    public static final int VAR_ORTHO_TOP = 3;
    public static final int VAR_ORTHO_NEAR = 4;
    public static final int VAR_ORTHO_FAR = 5;

    private FSView target;
    private VLVManagerRootDynamic manager;

    public CLManagerView(FSView target){
        initialize(target);
    }

    public void initialize(FSView target){
        this.target = target;

        manager = new VLVManagerRootDynamic(3, 0, 3, new MapManagerCheck(manager));

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

    public void start(){
        manager.start();
    }

    public void activate(int category){
        manager.activate(category);
    }

    public void deactivate(int category){
        manager.deactivate(category);
    }

    public void tune(int category, int variable, float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager manager = (VLVManager)this.manager.entries().get(category);
        VLVEntry entry = manager.get(variable);
        entry.delay(delay);
        entry.resetDelayTrackers();

        VLVCurved target = (VLVCurved)entry.target;
        target.setFrom(from);
        target.setTo(to);
        target.setLoop(loop);
        target.setCurve(curve);
        target.initialize(cycles);
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

    private static class MapManagerCheck extends VLSyncMap<VLVManagerRoot, VLVManagerRootDynamic>{

        public MapManagerCheck(VLVManagerRootDynamic target){
            super(target);
        }

        @Override
        public void sync(VLVManagerRoot source){
            if(target.done()){
                target.get().nullify();
            }
        }
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
