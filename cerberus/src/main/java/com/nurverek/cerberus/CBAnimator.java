package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.vanguard.VLArray;
import com.nurverek.vanguard.VLArrayFloat;
import com.nurverek.vanguard.VLFloat;
import com.nurverek.vanguard.VLList;
import com.nurverek.vanguard.VLTask;
import com.nurverek.vanguard.VLTaskContinous;
import com.nurverek.vanguard.VLTaskDone;
import com.nurverek.vanguard.VLVConnection;
import com.nurverek.vanguard.VLVControl;
import com.nurverek.vanguard.VLVCurved;
import com.nurverek.vanguard.VLVLinear;
import com.nurverek.vanguard.VLVManager;
import com.nurverek.vanguard.VLVRunner;
import com.nurverek.vanguard.VLVRunnerEntry;
import com.nurverek.vanguard.VLVariable;

public class CBAnimator{

    protected VLVManager root;

    public CBAnimator(VLVManager parent, int capacity, int resizer){
        root = new VLVManager(capacity, resizer);
        parent.add(root);
    }

    public VLVManager root(){
        return root;
    }

    public void add(int capacity, int resizer){
        root.add(new VLVRunner(capacity, resizer));
    }

    public void start(){
        root.start();
    }

    public void pause(){
        root.pause();
    }

    public void reset(){
        root.reset();
    }

    public VLVRunner get(int index){
        return (VLVRunner)root.get(index);
    }

    public void register(int index, Modifier mod, VLVariable var, int delay){
        var.setTask(new VLTaskContinous<VLVariable>(new VLTask.Task<VLVariable>(){

            @Override
            public void run(VLTask<VLVariable> task, VLVariable var){
                mod.modify(var);

                if(!var.active()){
                    mod.done(var);
                }
            }
        }));

        root.get(index).add(new VLVRunnerEntry(var, delay));
    }

    public interface Modifier<TYPE>{

        void modify(VLVariable var);
        void done(VLVariable var);
    }
}
