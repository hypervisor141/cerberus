package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSControl;
import com.nurverek.firestorm.FSR;
import com.nurverek.firestorm.FSViewConfig;
import com.nurverek.vanguard.VLTask;
import com.nurverek.vanguard.VLTaskContinous;
import com.nurverek.vanguard.VLVControl;
import com.nurverek.vanguard.VLVCurved;
import com.nurverek.vanguard.VLVManager;
import com.nurverek.vanguard.VLVRunner;
import com.nurverek.vanguard.VLVRunnerEntry;
import com.nurverek.vanguard.VLVariable;

public final class CBView{

    private CBAnimator animator;
    private FSViewConfig target;
    
    public CBView(FSViewConfig target){
        this.target = target;

        animator = new CBAnimator(FSR.getControlManager(), 7, 0);
        animator.add(3, 0);
        animator.register(0, ,,0);
    }

    public void target(FSViewConfig target){
        this.target = target;
    }

    public FSViewConfig target(){
        return target;
    }

    public void movePosition(float x, float y, float z, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        final float[] orgviewsettings = target.viewMatrixSettings().provider().clone();

        VLVCurved controlx = ;
        VLVCurved controly = new VLVCurved(orgviewsettings[1], y, cycles, VLVariable.LOOP_NONE, curve);
        VLVCurved controlz = new VLVCurved(orgviewsettings[2], z, cycles, VLVariable.LOOP_NONE, curve);

        VLVControl update = new VLVControl(cycles, VLVariable.LOOP_NONE, new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                position(controlx.get(), controly.get(), controlz.get());

                if(!var.active()){
                    controllerpos.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controllerpos.add(new VLVRunnerEntry(controlx, delay));
        controllerpos.add(new VLVRunnerEntry(controly, delay));
        controllerpos.add(new VLVRunnerEntry(controlz, delay));
        controllerpos.add(new VLVRunnerEntry(update, delay));
        controllerpos.start();
    }

    public void moveFOV(float fov, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(target.perspectiveSettings().get(0), fov, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                fov(var.get());

                if(!var.active()){
                    controllerfov.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controllerfov.add(new VLVRunnerEntry(control, delay));
        controllerfov.start();
    }

    public void moveAspect(float aspect, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(target.perspectiveSettings().get(1), aspect, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                aspect(var.get());

                if(!var.active()){
                    controlleraspect.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controlleraspect.add(new VLVRunnerEntry(control, delay));
        controlleraspect.start();
    }

    public void moveNear(float near, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(target.perspectiveSettings().get(2), near, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                near(var.get());

                if(!var.active()){
                    controllernear.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controllernear.add(new VLVRunnerEntry(control, delay));
        controllernear.start();
    }

    public void moveFar(float far, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(target.perspectiveSettings().get(3), far, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                far(var.get());

                if(!var.active()){
                    controllerfar.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controllerfar.add(new VLVRunnerEntry(control, delay));
        controllerfar.start();
    }

    public void moveView(float viewx, float viewy, float viewz, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        final float[] orgviewsettings = target.viewMatrixSettings().provider().clone();

        VLVCurved controlviewx = new VLVCurved(orgviewsettings[3], viewx, cycles, VLVariable.LOOP_NONE, curve);
        VLVCurved controlviewy = new VLVCurved(orgviewsettings[4], viewy, cycles, VLVariable.LOOP_NONE, curve);
        VLVCurved controlviewz = new VLVCurved(orgviewsettings[5], viewz, cycles, VLVariable.LOOP_NONE, curve);

        VLVControl update = new VLVControl(cycles, VLVariable.LOOP_NONE, new VLTaskContinous(new VLTask.Task(){

            @Override
            public void run(VLTask task, VLVariable var){
                lookAt(controlviewx.get(), controlviewy.get(), controlviewz.get());

                if(!var.active()){
                    controllerview.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controllerview.add(new VLVRunnerEntry(controlviewx, delay));
        controllerview.add(new VLVRunnerEntry(controlviewy, delay));
        controllerview.add(new VLVRunnerEntry(controlviewz, delay));
        controllerview.add(new VLVRunnerEntry(update, delay));
        controllerview.start();
    }

    public void rotate(float fromangle, float toangle, float rotationx, float rotationy, float rotationz, int delay,
                              int cycles, VLVCurved.Curve curve, VLVariable.Loop loop, final Runnable post){

        final float[] settings = target.viewMatrixSettings().provider().clone();

        VLVCurved angle = new VLVCurved(fromangle, toangle, cycles, loop, curve, new VLTaskContinous(new VLTask.Task(){

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

                if(!var.active()){
                    controllerrotate.clear();

                    if(post != null){
                        post.run();
                    }
                }
            }
        }));

        controllerrotate.add(new VLVRunnerEntry(angle, delay));
        controllerrotate.start();
    }
    
    public void stopView(){
        controllerview.clear();
    }

    public void stopPosition(){
        controllerpos.clear();
    }

    public void stopRotation(){
        controllerrotate.clear();
    }

    public void stopFOV(){
        controllerfov.clear();
    }

    public void stopAspect(){
        controlleraspect.clear();
    }

    public void stopNear(){
        controllernear.clear();
    }

    public void stopFar(){
        controllerfar.clear();
    }

    public void stop(){
        stopPosition();
        stopView();
        stopRotation();
        stopFOV();
        stopAspect();
        stopNear();
        stopFar();
    }
}
