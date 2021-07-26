package hypervisor.cerberus;

import android.opengl.Matrix;

import hypervisor.vanguard.array.VLArrayFloat;
import hypervisor.vanguard.primitive.VLFloat;
import hypervisor.vanguard.sync.VLSyncMap;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVTypeRunner;

@SuppressWarnings("unused")
public class CLMaps{

    public static class SelfDeactivate<SOURCE extends VLVTypeRunner, TARGET extends VLVManagerDynamic<?>> extends VLSyncMap<SOURCE, TARGET>{

        public SelfDeactivate(TARGET host){
            super(host);
        }

        public SelfDeactivate(SelfDeactivate<SOURCE, TARGET> src, long flags){
            copy(src, flags);
        }

        protected SelfDeactivate(){

        }

        @Override
        public void sync(SOURCE source){
            target.deactivateEntry(target.indexOfEntry(source));
        }

        @Override
        public SelfDeactivate<SOURCE, TARGET> duplicate(long flags){
            return new SelfDeactivate<>(this, flags);
        }
    }

    public static class SelfRemove<SOURCE extends VLVTypeRunner, TARGET extends VLVManager<?>> extends VLSyncMap<SOURCE, TARGET>{

        public SelfRemove(TARGET host){
            super(host);
        }

        public SelfRemove(SelfRemove<SOURCE, TARGET> src, long flags){
            copy(src, flags);
        }

        protected SelfRemove(){

        }

        @Override
        public void sync(SOURCE source){
            target.get().remove(source);
        }

        @Override
        public SelfRemove<SOURCE, TARGET> duplicate(long flags){
            return new SelfRemove<>(this, flags);
        }
    }

    public static class Set extends VLSyncMap<VLVManager<VLVEntry>, VLFloat>{

        public Set(VLFloat target){
            super(target);
        }

        protected Set(Set src, long flags){
            copy(src, flags);
        }

        protected Set(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.value = source.get(0).target.get();
        }

        @Override
        public Set duplicate(long flags){
            return new Set(this, flags);
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

        protected SetArray(SetArray src, long flags){
            copy(src, flags);
        }

        protected SetArray(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            float[] target = this.target.array;

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

    public static class RotatePoint extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

        protected float[] cache;
        protected float[] startstatecache;

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

        protected RotatePoint(RotatePoint src, long flags){
            copy(src, flags);
        }

        protected RotatePoint(){

        }

        public void tune(){
            float[] target = this.target.array;

            startstatecache[0] = target[offset];
            startstatecache[1] = target[offset + 1];
            startstatecache[2] = target[offset + 2];
            startstatecache[3] = 1F;
        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            float[] target = this.target.array;

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

    public static class ScalePoint extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

        protected float[] cache;

        public int targetoffset;
        public int sourceoffset;

        public ScalePoint(VLArrayFloat target, int targetoffset, int sourceoffset){
            super(target);

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
            float[] target = this.target.array;

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

    public static class RotateMatrix extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

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

        protected RotateMatrix(RotateMatrix src, long flags){
            copy(src, flags);
        }

        protected RotateMatrix(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            Matrix.rotateM(target.array, offset, source.get(0).target.get(), x, y, z);
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

    public static class ScaleMatrix extends VLSyncMap<VLVManager<VLVEntry>, VLArrayFloat>{

        public int targetoffset;
        public int sourceoffset;

        public ScaleMatrix(VLArrayFloat target, int targetoffset, int sourceoffset){
            super(target);

            this.targetoffset = targetoffset;
            this.sourceoffset = sourceoffset;
        }

        protected ScaleMatrix(ScaleMatrix src, long flags){
            copy(src, flags);
        }

        protected ScaleMatrix(){}

        @Override
        public void sync(VLVManager<VLVEntry> source){
            Matrix.scaleM(target.array, targetoffset, source.get(sourceoffset).target.get(), source.get(sourceoffset + 1).target.get(), source.get(sourceoffset + 2).target.get());
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
