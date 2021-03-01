package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSControl;
import com.nurverek.firestorm.FSViewConfig;
import com.nurverek.vanguard.VLListType;
import com.nurverek.vanguard.VLTask;
import com.nurverek.vanguard.VLTaskContinous;
import com.nurverek.vanguard.VLVControl;
import com.nurverek.vanguard.VLVCurved;
import com.nurverek.vanguard.VLVDynamicTree;
import com.nurverek.vanguard.VLVManager;
import com.nurverek.vanguard.VLVRunner;
import com.nurverek.vanguard.VLVRunnerEntry;
import com.nurverek.vanguard.VLVTypeManager;
import com.nurverek.vanguard.VLVTypeRunner;
import com.nurverek.vanguard.VLVariable;

public final class CLViewConfig extends VLVDynamicTree<VLVRunner, VLVManager<VLVRunner>>{
    
    public static final int BRANCH_POSITION = 0;
    public static final int BRANCH_PESPECTIVE = 1;
    public static final int BRANCH_LOOKAT = 2;
    public static final int BRANCH_ROTATION_ANGLE = 3;
    
    public static final int NODE_POSITION_X = 0;
    public static final int NODE_POSITION_Y = 1;
    public static final int NODE_POSITION_Z = 2;
    
    public static final int NODE_PERSPECTIVE_FOV = 0;
    public static final int NODE_PERSPECTIVE_ASPECT = 1;
    public static final int NODE_PERSPECTIVE_NEAR = 2;
    public static final int NODE_PERSPECTIVE_FAR = 3;

    public static final int NODE_LOOKAT_X = 0;
    public static final int NODE_LOOKAT_Y = 1;
    public static final int NODE_LOOKAT_Z = 2;

    public static final int NODE_ROTATION_ANGLE = 0;

    private FSViewConfig target;

    protected CLViewConfig(VLVManager registrar, FSViewConfig target){
        super(registrar, 7);
        this.target = target;
    }

    @Override
    protected void createBranches(VLListType<VLVRunner> branches){
        VLVCurved posx = new VLVCurved();
        VLVCurved posy = new VLVCurved();
        VLVCurved posz = new VLVCurved();

        VLVCurved fov = new VLVCurved();
        VLVCurved aspect = new VLVCurved();
        VLVCurved near = new VLVCurved();
        VLVCurved far = new VLVCurved();

        VLVCurved viewx = new VLVCurved();
        VLVCurved viewy = new VLVCurved();
        VLVCurved viewz = new VLVCurved();

        VLVCurved angle = new VLVCurved();

        VLVRunner rpos = new VLVRunner(3, 0);
        VLVRunner rperspective = new VLVRunner(4, 0);
        VLVRunner rview = new VLVRunner(3, 0);
        VLVRunner rrotate = new VLVRunner(1, 0);

        posz.setTask(new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                position(posx.get(), posy.get(), posz.get());
            }
        }));
        fov.setTask(new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                fov(var.get());
            }
        }));
        aspect.setTask(new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                aspect(var.get());
            }
        }));
        near.setTask(new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                near(var.get());
            }
        }));
        far.setTask(new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                far(var.get());
            }
        }));
        viewz.setTask(new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                lookAt(viewx.get(), viewy.get(), viewz.get());
            }
        }));

        rpos.add(new VLVRunnerEntry(posx, 0));
        rpos.add(new VLVRunnerEntry(posy, 0));
        rpos.add(new VLVRunnerEntry(posz, 0));

        rperspective.add(new VLVRunnerEntry(fov, 0));
        rperspective.add(new VLVRunnerEntry(aspect, 0));
        rperspective.add(new VLVRunnerEntry(near, 0));
        rperspective.add(new VLVRunnerEntry(far, 0));

        rview.add(new VLVRunnerEntry(viewx, 0));
        rview.add(new VLVRunnerEntry(viewy, 0));
        rview.add(new VLVRunnerEntry(viewz, 0));

        rrotate.add(new VLVRunnerEntry(angle, 0));

        branches.add(rpos);
        branches.add(rperspective);
        branches.add(rview);
        branches.add(rrotate);
    }

    public void target(FSViewConfig target){
        this.target = target;
    }
    
    public FSViewConfig target(){
        return target;
    }

    public VLVCurved get(int branch, int node){
        return (VLVCurved)root.get(branch).get(node).target;
    }

    public void position(float x, float y, float z){
        target.eyePosition(x, y, z);
        target.lookAtUpdate();
        target.updateViewProjection();
    }

    public void perspective(float fov, float aspect, float near, float far){
        target.perspective(fov, aspect, near, far);
        target.updateViewProjection();
    }

    public void fov(float fov){
        float[] settings = target.perspectiveSettings().provider();

        target.perspective(fov, settings[1], settings[2], settings[3]);
        target.updateViewProjection();
    }

    public void aspect(float aspect){
        float[] settings = target.perspectiveSettings().provider();

        target.perspective(settings[0], aspect, settings[2], settings[3]);
        target.updateViewProjection();
    }

    public void near(float near){
        float[] settings = target.perspectiveSettings().provider();

        target.perspective(settings[0], settings[1], near, settings[3]);
        target.updateViewProjection();
    }

    public void far(float far){
        float[] settings = target.perspectiveSettings().provider();

        target.perspective(settings[0], settings[1], settings[2], far);
        target.updateViewProjection();
    }

    public void lookAt(float viewx, float viewy, float viewz){
        target.lookAt(viewx, viewy, viewz, 0f, 1f, 0f);
        target.updateViewProjection();
    }

    public void animate(int branch, int node, float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, Runnable post){
        activate(branch);

        VLVCurved target = get(branch, node);
        target.setFrom(from);
        target.setTo(to);
        target.initialize(cycles);
        target.setCurve(curve);
        target.setLoop(loop);

        if(post != null){
            target.setTask(new VLTask.TaskGroup<VLVariable>(tasks, 0));
        }

        root.start();
    }

    public void rotate(float fromangle, float toangle, float rotationx, float rotationy, float rotationz, int delay, int cycles, VLVCurved.Curve curve, VLVariable.Loop loop, final Runnable post){
        final float[] settings = FSControl.getViewConfig().viewMatrixSettings().provider().clone();
        angle.setTask(new VLTaskContinous(new VLTask.Task(){

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
            }
        }));
    }
}
