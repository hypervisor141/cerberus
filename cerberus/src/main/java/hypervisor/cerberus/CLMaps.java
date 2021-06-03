package hypervisor.cerberus;

import android.opengl.Matrix;

import hypervisor.vanguard.array.VLArrayFloat;
import hypervisor.vanguard.primitive.VLFloat;
import hypervisor.vanguard.sync.VLSyncMap;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVTypeManager;

public class CLMaps{

    public static abstract class TaskedMap<SOURCE extends VLVTypeManager<?>, TARGET> extends VLSyncMap<SOURCE, TARGET>{

        public Runnable post;
        public VLVManagerDynamic<SOURCE> host;

        public TaskedMap(TARGET target, VLVManagerDynamic<SOURCE> host){
            super(target);
            this.host = host;
        }

        protected TaskedMap(){

        }

        @Override
        public void sync(SOURCE source){
            if(source.done()){
                if(host != null){
                    host.deactivateEntry(source);
                }
                if(post != null){
                    post.run();
                }
            }
        }
    }

    public static class Set extends TaskedMap<VLVManager<VLVEntry>, VLFloat>{

        public Set(VLFloat target, VLVManagerDynamic<VLVManager<VLVEntry>> host){
            super(target, host);
        }

        protected Set(Set src, long flags){}

        protected Set(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);
            target.set(source.get(0).target.get());
        }

        @Override
        public Set duplicate(long flags){
            return new Set(this, flags);
        }
    }

    public static class SetArray extends TaskedMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public int targetoffset;
        public int sourceoffset;
        public int count;

        public SetArray(VLArrayFloat target, int targetoffset, int sourceoffset, int count, VLVManagerDynamic<VLVManager<VLVEntry>> host){
            super(target, host);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
            this.count = count;
        }

        protected SetArray(SetArray src, long flags){
            copy(src, flags);
        }

        protected SetArray(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);
            float[] target = this.target.provider();

            for(int i = 0; i < count; i++){
                target[targetoffset + i] = source.get(sourceoffset + i).target.get();
            }
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            SetArray target = (SetArray)src;
            targetoffset = target.targetoffset;
            sourceoffset = target.sourceoffset;
            count = target.count;
        }

        @Override
        public SetArray duplicate(long flags){
            return new SetArray(this, flags);
        }
    }

    public static class RotatePoint extends TaskedMap<VLVManager<VLVEntry>, VLArrayFloat>{

        protected float[] cache;
        protected float[] startstatecache;

        public int offset;
        public float x;
        public float y;
        public float z;

        public RotatePoint(VLArrayFloat target, int offset, float x, float y, float z, VLVManagerDynamic<VLVManager<VLVEntry>> host){
            super(target, host);

            cache = new float[16];
            startstatecache = new float[4];

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        protected RotatePoint(RotatePoint src, long flags){
            copy(src, flags);
        }

        protected RotatePoint(){

        }

        public void tune(){
            float[] target = this.target.provider();

            startstatecache[0] = target[offset];
            startstatecache[1] = target[offset + 1];
            startstatecache[2] = target[offset + 2];
            startstatecache[3] = 1F;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);

            float[] target = this.target.provider();

            Matrix.setIdentityM(cache, 0);
            Matrix.rotateM(cache, 0, source.get(0).target.get(), x, y, z);
            Matrix.multiplyMV(target, offset, cache, 0, startstatecache, 0);
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            RotatePoint target = (RotatePoint)src;

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
        public RotatePoint duplicate(long flags){
            return new RotatePoint(this, flags);
        }
    }

    public static class ScalePoint extends TaskedMap<VLVManager<VLVEntry>, VLArrayFloat>{

        protected float[] cache;

        public int targetoffset;
        public int sourceoffset;

        public ScalePoint(VLArrayFloat target, int targetoffset, int sourceoffset, VLVManagerDynamic<VLVManager<VLVEntry>> host){
            super(target, host);

            cache = new float[16];
            Matrix.setIdentityM(cache, 0);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
        }

        protected ScalePoint(ScalePoint src, long flags){
            copy(src, flags);
        }

        protected ScalePoint(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);

            float[] target = this.target.provider();

            Matrix.scaleM(cache, 0, source.get(sourceoffset).target.get(), source.get(sourceoffset + 1).target.get(), source.get(sourceoffset + 2).target.get());
            Matrix.multiplyMV(target, targetoffset, cache, 0, target, targetoffset);
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            ScalePoint target = (ScalePoint)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                cache = target.cache;

            }else if(((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE)){
                cache = target.cache.clone();

            }else{
                Helper.throwMissingDefaultFlags();
            }

            targetoffset = target.targetoffset;
            sourceoffset = target.sourceoffset;
        }

        @Override
        public ScalePoint duplicate(long flags){
            return new ScalePoint(this, flags);
        }
    }

    public static class RotateMatrix extends TaskedMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public int offset;
        public float x;
        public float y;
        public float z;

        public RotateMatrix(VLArrayFloat target, int offset, float x, float y, float z, VLVManagerDynamic<VLVManager<VLVEntry>> host){
            super(target, host);

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        protected RotateMatrix(RotateMatrix src, long flags){
            copy(src, flags);
        }

        protected RotateMatrix(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);
            Matrix.rotateM(target.provider(), offset, source.get(0).target.get(), x, y, z);
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            RotateMatrix target = (RotateMatrix)src;
            offset = target.offset;
            x = target.x;
            y = target.y;
            z = target.z;
        }

        @Override
        public RotateMatrix duplicate(long flags){
            return new RotateMatrix(this, flags);
        }
    }

    public static class ScaleMatrix extends TaskedMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public int targetoffset;
        public int sourceoffset;

        public ScaleMatrix(VLArrayFloat target, int targetoffset, int sourceoffset, VLVManagerDynamic<VLVManager<VLVEntry>> host){
            super(target, host);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
        }

        protected ScaleMatrix(ScaleMatrix src, long flags){
            copy(src, flags);
        }

        protected ScaleMatrix(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);
            Matrix.scaleM(target.provider(), targetoffset, source.get(sourceoffset).target.get(), source.get(sourceoffset + 1).target.get(), source.get(sourceoffset + 2).target.get());
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            ScaleMatrix target = (ScaleMatrix)src;

            targetoffset = target.targetoffset;
            sourceoffset = target.sourceoffset;
        }

        @Override
        public ScaleMatrix duplicate(long flags){
            return new ScaleMatrix(this, flags);
        }
    }
}
