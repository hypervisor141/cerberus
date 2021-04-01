package com.nurverek.cerberus;

import android.opengl.Matrix;

import vanguard.VLArrayFloat;
import vanguard.VLFloat;
import vanguard.VLSyncMap;
import vanguard.VLVEntry;
import vanguard.VLVManager;

public class CLMaps{

    public static class Set extends VLSyncMap<VLVEntry, VLFloat>{

        public Set(VLFloat target){
            super(target);
        }

        @Override
        public void sync(VLVEntry source){
            target.set(source.target.get());
        }
    }

    public static class SetArray extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public int targetoffset;
        public int sourceoffset;
        public int count;

        public SetArray(VLArrayFloat target, int targetoffset, int sourceoffset, int count){
            super(target);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
            this.count = count;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            float[] target = this.target.provider();

            for(int i = 0; i < count; i++){
                target[targetoffset + i] = source.get(sourceoffset + i).target.get();
            }
        }
    }

    public static class RotatePoint extends VLSyncMap<VLVEntry, VLArrayFloat>{

        public float[] cache;
        public float[] startstatecache;
        public int offset;
        public float x;
        public float y;
        public float z;

        public RotatePoint(VLArrayFloat target, int offset, float x, float y, float z){
            super(target);

            cache = new float[16];
            startstatecache = new float[4];

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void tune(){
            startstatecache[0] = target.get(offset);
            startstatecache[1] = target.get(offset + 1);
            startstatecache[2] = target.get(offset + 2);
            startstatecache[3] = 1F;
        }

        @Override
        public void sync(VLVEntry source){
            float[] target = this.target.provider();

            Matrix.setIdentityM(cache, 0);
            Matrix.rotateM(cache, 0, source.target.get(), x, y, z);
            Matrix.multiplyMV(target, offset, cache, 0, startstatecache, 0);
        }
    }

    public static class ScalePoint extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public float[] cache;
        public int targetoffset;
        public int sourceoffset;

        public ScalePoint(VLArrayFloat target, int targetoffset, int sourceoffset){
            super(target);

            cache = new float[16];
            Matrix.setIdentityM(cache, 0);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            float[] target = this.target.provider();

            Matrix.scaleM(cache, 0, source.get(sourceoffset).target.get(), source.get(sourceoffset + 1).target.get(), source.get(sourceoffset + 2).target.get());
            Matrix.multiplyMV(target, targetoffset, cache, 0, target, targetoffset);
        }
    }

    public static class RotateMatrix extends VLSyncMap<VLVEntry, VLArrayFloat>{

        public int offset;
        public float x;
        public float y;
        public float z;

        public RotateMatrix(VLArrayFloat target, int offset, float x, float y, float z){
            super(target);

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void sync(VLVEntry source){
            Matrix.rotateM(target.provider(), offset, source.target.get(), x, y, z);
        }
    }

    public static class ScaleMatrix extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public int targetoffset;
        public int sourceoffset;

        public ScaleMatrix(VLArrayFloat target, int targetoffset, int sourceoffset){
            super(target);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            Matrix.scaleM(target.provider(), targetoffset, source.get(sourceoffset).target.get(), source.get(sourceoffset + 1).target.get(), source.get(sourceoffset + 2).target.get());
        }
    }
}
