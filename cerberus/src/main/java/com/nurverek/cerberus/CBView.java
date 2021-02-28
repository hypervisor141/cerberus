package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSControl;
import com.nurverek.firestorm.FSR;
import com.nurverek.firestorm.FSViewConfig;
import com.nurverek.vanguard.VLTask;
import com.nurverek.vanguard.VLTaskContinous;
import com.nurverek.vanguard.VLVControl;
import com.nurverek.vanguard.VLVCurved;
import com.nurverek.vanguard.VLVRunner;
import com.nurverek.vanguard.VLVRunnerEntry;
import com.nurverek.vanguard.VLVariable;

public final class CBCamera{

    private static VLVRunner controllerview;
    private static VLVRunner controllerpos;
    private static VLVRunner controllerrotate;
    private static VLVRunner controllerfov;
    private static VLVRunner controlleraspect;
    private static VLVRunner controllernear;
    private static VLVRunner controllerfar;

    public static void initialize(){
        controllerfov = new VLVRunner(1, 5);
        controlleraspect = new VLVRunner(1, 5);
        controllernear = new VLVRunner(1, 5);
        controllerfar = new VLVRunner(1, 5);
        controllerpos = new VLVRunner(4, 5);
        controllerview = new VLVRunner(4, 5);
        controllerrotate = new VLVRunner(1, 5);

        FSR.getControlManager().add(controllerfov);
        FSR.getControlManager().add(controlleraspect);
        FSR.getControlManager().add(controllernear);
        FSR.getControlManager().add(controllerfar);
        FSR.getControlManager().add(controllerpos);
        FSR.getControlManager().add(controllerview);
        FSR.getControlManager().add(controllerrotate);
    }

    public static void perspective(float fov, float aspect, float near, float far){
        FSViewConfig config = FSControl.getViewConfig();
        config.perspective(fov, aspect, near, far);
        config.updateViewProjection();
    }

    public static void fov(float fov){
        FSViewConfig config = FSControl.getViewConfig();
        float[] settings = config.perspectiveSettings().provider();
        config.perspective(fov, settings[1], settings[2], settings[3]);
        config.updateViewProjection();
    }

    public static void aspect(float aspect){
        FSViewConfig config = FSControl.getViewConfig();
        float[] settings = config.perspectiveSettings().provider();
        config.perspective(settings[0], aspect, settings[2], settings[3]);
        config.updateViewProjection();
    }

    public static void near(float near){
        FSViewConfig config = FSControl.getViewConfig();
        float[] settings = config.perspectiveSettings().provider();
        config.perspective(settings[0], settings[1], near, settings[3]);
        config.updateViewProjection();
    }

    public static void far(float far){
        FSViewConfig config = FSControl.getViewConfig();
        float[] settings = config.perspectiveSettings().provider();
        config.perspective(settings[0], settings[1], settings[2], far);
        config.updateViewProjection();
    }

    public static void position(float x, float y, float z){
        FSViewConfig config = FSControl.getViewConfig();
        config.eyePosition(x, y, z);
        config.lookAtUpdate();
        config.updateViewProjection();
    }

    public static void lookAt(float viewx, float viewy, float viewz){
        FSViewConfig config = FSControl.getViewConfig();
        config.lookAt(viewx, viewy, viewz, 0f, 1f, 0f);
        config.updateViewProjection();
    }

    public static void movePosition(float x, float y, float z, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        final float[] orgviewsettings = FSControl.getViewConfig().viewMatrixSettings().provider().clone();

        VLVCurved controlx = new VLVCurved(orgviewsettings[0], x, cycles, VLVariable.LOOP_NONE, curve);
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

    public static void moveFOV(float fov, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(FSControl.getViewConfig().perspectiveSettings().get(0), fov, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

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

    public static void moveAspect(float aspect, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(FSControl.getViewConfig().perspectiveSettings().get(1), aspect, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

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

    public static void moveNear(float near, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(FSControl.getViewConfig().perspectiveSettings().get(2), near, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

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

    public static void moveFar(float far, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        VLVCurved control = new VLVCurved(FSControl.getViewConfig().perspectiveSettings().get(3), far, cycles, VLVariable.LOOP_NONE, curve, new VLTaskContinous(new VLTask.Task(){

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

    public static void moveView(float viewx, float viewy, float viewz, int delay, int cycles, VLVCurved.Curve curve, final Runnable post){
        final float[] orgviewsettings = FSControl.getViewConfig().viewMatrixSettings().provider().clone();

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

    public static void rotate(float fromangle, float toangle, float rotationx, float rotationy, float rotationz, int delay,
                              int cycles, VLVCurved.Curve curve, VLVariable.Loop loop, final Runnable post){

        final float[] settings = FSControl.getViewConfig().viewMatrixSettings().provider().clone();

        VLVCurved angle = new VLVCurved(fromangle, toangle, cycles, loop, curve, new VLTaskContinous(new VLTask.Task(){

            private float[] cache = new float[16];

            @Override
            public void run(VLTask task, VLVariable var){
                FSViewConfig config = FSControl.getViewConfig();
                final float[] pos = config.eyePosition().provider();

                Matrix.setIdentityM(cache, 0);
                Matrix.rotateM(cache, 0, var.get(), rotationx, rotationy, rotationz);
                Matrix.multiplyMV(pos, 0, cache, 0, settings, 0);

                config.eyePositionUpdate();
                config.lookAtUpdate();
                config.updateViewProjection();

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

    private static VLVRunner controllerview;
    private static VLVRunner controllerpos;
    private static VLVRunner controllerrotate;
    private static VLVRunner controllerfov;
    private static VLVRunner controlleraspect;
    private static VLVRunner controllernear;
    private static VLVRunner controllerfar;

    public static void stopView(){
        controllerview.clear();
    }

    public static void stopPosition(){
        controllerpos.clear();
    }

    public static void stopRotation(){
        controllerrotate.clear();
    }

    public static void stopFOV(){
        controllerfov.clear();
    }

    public static void stopAspect(){
        controlleraspect.clear();
    }

    public static void stopNear(){
        controllernear.clear();
    }

    public static void stopFar(){
        controllerfar.clear();
    }

    public static void stop(){
        stopPosition();
        stopView();
        stopRotation();
        stopFOV();
        stopAspect();
        stopNear();
        stopFar();
    }
}
