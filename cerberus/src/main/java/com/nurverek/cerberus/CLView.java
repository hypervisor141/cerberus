package com.nurverek.cerberus;

import android.opengl.Matrix;

import com.nurverek.firestorm.FSView;

import vanguard.VLListType;
import vanguard.VLSyncMap;
import vanguard.VLSyncType;
import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVManager;
import vanguard.VLVManagerDynamic;
import vanguard.VLVariable;

public class CLView extends FSView{

    private static final float[] CACHE = new float[16];

    public static final int CAT_VIEW = 0;
    public static final int CAT_PERSPECTIVE = 1;
    public static final int CAT_ORTHOGRAPHIC = 2;
    public static final int CAT_ROTATE_VIEW_POSITION = 3;
    public static final int CAT_ROTATE_VIEW_CENTER = 4;
    public static final int CAT_ROTATE_VIEW_UP = 5;
    public static final int CAT_SCALE_VIEW_POSITION = 6;
    public static final int CAT_SCALE_VIEW_CENTER = 7;
    public static final int CAT_SCALE_VIEW_UP = 8;

    private VLVManagerDynamic<VLVManager<VLVEntry>> manager;

    public CLView(boolean perspectivemode){
        super(perspectivemode);
    }

    protected CLView(){

    }

    protected CLView(CLView src, long flags){
        copy(src, flags);
    }

    public void initializeManager(){
        manager = new VLVManagerDynamic<>(3, 3, 9);

        VLVManager<VLVEntry> view = new VLVManager<>(9, 0, new MapView(this));
        VLVManager<VLVEntry> perspective = new VLVManager<>(4, 0, new MapPerspective(this));
        VLVManager<VLVEntry> orthographic = new VLVManager<>(6, 0, new MapOrthographic(this));
        VLVManager<VLVEntry> rotateviewpos = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> rotateviewcenter = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> rotateviewup = new VLVManager<>(1, 0);
        VLVManager<VLVEntry> scaleviewpos = new VLVManager<>(3, 0, new MapScaleView(this, 0, 0));
        VLVManager<VLVEntry> scaleviewcenter = new VLVManager<>(3, 0, new MapScaleView(this, 3, 0));
        VLVManager<VLVEntry> scaleviewup = new VLVManager<>(3, 0, new MapScaleView(this, 6, 0));

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

        rotateviewpos.add(new VLVEntry(new VLVCurved(), 0, new MapRotateView(this, 0, 0F, 0F, 0F)));
        rotateviewcenter.add(new VLVEntry(new VLVCurved(), 0, new MapRotateView(this, 3, 0F, 0F, 0F)));
        rotateviewup.add(new VLVEntry(new VLVCurved(), 0, new MapRotateView(this, 6, 0F, 0F, 0F)));

        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewpos.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewcenter.add(new VLVEntry(new VLVCurved(), 0));

        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));
        scaleviewup.add(new VLVEntry(new VLVCurved(), 0));

        VLListType<VLVManager<VLVEntry>> entries = manager.entries();

        entries.add(view);
        entries.add(perspective);
        entries.add(orthographic);
        entries.add(rotateviewpos);
        entries.add(rotateviewcenter);
        entries.add(rotateviewup);
        entries.add(scaleviewpos);
        entries.add(scaleviewcenter);
        entries.add(scaleviewup);
    }

    public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
        return manager;
    }

    public void viewPositionX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(0), from, to, delay, cycles, loop, curve);
    }

    public void viewPositionY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(1), from, to, delay, cycles, loop, curve);
    }

    public void viewPositionZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(2), from, to, delay, cycles, loop, curve);
    }

    public void viewCenterX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(3), from, to, delay, cycles, loop, curve);
    }

    public void viewCenterY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(4), from, to, delay, cycles, loop, curve);
    }

    public void viewCenterZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(5), from, to, delay, cycles, loop, curve);
    }

    public void viewUpX(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(6), from, to, delay, cycles, loop, curve);
    }

    public void viewUpY(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(7), from, to, delay, cycles, loop, curve);
    }

    public void viewUpZ(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_VIEW).get(8), from, to, delay, cycles, loop, curve);
    }

    public void viewPosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_VIEW);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_VIEW);

        CLVTools.tune(manager.get(3), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(4), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(5), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_VIEW);

        CLVTools.tune(manager.get(6), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(7), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(8), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewRotatePosition(float angle, float x, float y, float z){
        rotateView(0, angle, x, y, z);
    }

    public void viewRotateCenter(float angle, float x, float y, float z){
        rotateView(3, angle, x, y, z);
    }

    public void viewRotateUp(float angle, float x, float y, float z){
        rotateView(6, angle, x, y, z);
    }

    public void viewScalePosition(float x, float y, float z){
        scaleView(0, x, y, z);
    }

    public void viewScaleCenter(float x, float y, float z){
        scaleView(3, x, y, z);
    }

    public void viewScaleUp(float x, float y, float z){
        scaleView(6, x, y, z);
    }

    private void rotateView(int matoffset, float angle, float x, float y, float z){
        float[] viewmat = matrixView().provider();

        Matrix.setIdentityM(CACHE, 0);
        Matrix.rotateM(CACHE, 0, angle, x, y, z);
        Matrix.multiplyMV(viewmat, matoffset, CACHE, 0, viewmat, matoffset);
    }

    private void scaleView(int matoffset, float x, float y, float z){
        float[] viewmat = matrixView().provider();

        Matrix.setIdentityM(CACHE, 0);
        Matrix.scaleM(CACHE, 0, x, y, z);
        Matrix.multiplyMV(viewmat, matoffset, CACHE, 0, viewmat, matoffset);
    }

    public void viewRotatePosition(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = this.manager.entries().get(CAT_ROTATE_VIEW_POSITION).get(0);

        CLVTools.tune(entry, fromangle, toangle, delay, cycles, loop, curve);

        MapRotateView map = (MapRotateView)entry.syncer;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void viewRotateCenter(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = this.manager.entries().get(CAT_ROTATE_VIEW_CENTER).get(0);

        CLVTools.tune(entry, fromangle, toangle, delay, cycles, loop, curve);

        MapRotateView map = (MapRotateView)entry.syncer;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void viewRotateUp(float fromangle, float toangle, float x, float y, float z, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVEntry entry = this.manager.entries().get(CAT_ROTATE_VIEW_UP).get(0);

        CLVTools.tune(entry, fromangle, toangle, delay, cycles, loop, curve);

        MapRotateView map = (MapRotateView)entry.syncer;
        map.x = x;
        map.y = y;
        map.z = z;
        map.tune();
    }

    public void viewScalePosition(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_SCALE_VIEW_POSITION);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewScaleCenter(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_SCALE_VIEW_CENTER);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void viewScaleUp(float fromX, float toX, float fromY, float toY, float fromZ, float toZ, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_SCALE_VIEW_UP);

        CLVTools.tune(manager.get(0), fromX, toX, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromY, toY, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromZ, toZ, delay, cycles, loop, curve);
    }

    public void perspectiveFov(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_PERSPECTIVE).get(0), from, to, delay, cycles, loop, curve);
    }

    public void perspectiveAspect(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_PERSPECTIVE).get(1), from, to, delay, cycles, loop, curve);
    }

    public void perspectiveNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_PERSPECTIVE).get(2), from, to, delay, cycles, loop, curve);
    }

    public void perspectiveFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_PERSPECTIVE).get(3), from, to, delay, cycles, loop, curve);
    }

    public void perspective(float fromFov, float toFov, float fromAspect, float toAspect, float fromNear, float toNear,
                            float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){

        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_PERSPECTIVE);

        CLVTools.tune(manager.get(0), fromFov, toFov, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromAspect, toAspect, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromNear, toNear, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(3), fromFar, toFar, delay, cycles, loop, curve);
    }

    public void orthographicLeft(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_ORTHOGRAPHIC).get(0), from, to, delay, cycles, loop, curve);
    }

    public void orthographicRight(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_ORTHOGRAPHIC).get(1), from, to, delay, cycles, loop, curve);
    }

    public void orthographicBottom(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_ORTHOGRAPHIC).get(2), from, to, delay, cycles, loop, curve);
    }

    public void orthographicTop(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_ORTHOGRAPHIC).get(3), from, to, delay, cycles, loop, curve);
    }

    public void orthographicNear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_ORTHOGRAPHIC).get(4), from, to, delay, cycles, loop, curve);
    }

    public void orthographicFar(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        CLVTools.tune(manager.entries().get(CAT_ORTHOGRAPHIC).get(5), from, to, delay, cycles, loop, curve);
    }

    public void orthographic(float fromLeft, float toLeft, float fromRight, float toRight, float fromBottom, float toBottom, float fromTop, float toTop, float fromNear, float toNear,
                             float fromFar, float toFar, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
        VLVManager<VLVEntry> manager = this.manager.entries().get(CAT_ORTHOGRAPHIC);
        
        CLVTools.tune(manager.get(0), fromLeft, toLeft, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(1), fromRight, toRight, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(2), fromBottom, fromBottom, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(3), fromTop, toTop, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(4), fromNear, toNear, delay, cycles, loop, curve);
        CLVTools.tune(manager.get(5), fromFar, toFar, delay, cycles, loop, curve);
    }

    @Override
    public void copy(FSView src, long flags){
        super.copy(src, flags);

        CLView target = (CLView)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            manager = target.manager;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_FORCE_DUPLICATE_ENTRIES);

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public CLView duplicate(long flags){
        return new CLView(this, flags);
    }

    private static class MapView extends VLSyncMap<VLVManager<VLVEntry>, FSView>{
        
        public MapView(FSView target){
            super(target);
        }

        public MapView(MapView src, long flags){
            copy(src, flags);
        }

        protected MapView(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.lookAt(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get(),
                    source.get(6).target.get(), source.get(7).target.get(), source.get(8).target.get());

            target.applyViewProjection();
        }

        @Override
        public MapView duplicate(long flags){
            return new MapView(this, flags);
        }
    }

    private static class MapPerspective extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public MapPerspective(FSView target){
            super(target);
        }

        public MapPerspective(MapPerspective src, long flags){
            copy(src, flags);
        }

        protected MapPerspective(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.perspective(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(), source.get(3).target.get());
            target.applyViewProjection();
        }

        @Override
        public MapPerspective duplicate(long flags){
            return new MapPerspective(this, flags);
        }
    }

    private static class MapOrthographic extends VLSyncMap<VLVManager<VLVEntry>, FSView>{

        public MapOrthographic(FSView target){
            super(target);
        }

        public MapOrthographic(MapOrthographic src, long flags){
            copy(src, flags);
        }

        protected MapOrthographic(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            target.orthographic(source.get(0).target.get(), source.get(1).target.get(), source.get(2).target.get(),
                    source.get(3).target.get(), source.get(4).target.get(), source.get(5).target.get());

            target.applyViewProjection();
        }

        @Override
        public MapOrthographic duplicate(long flags){
            return new MapOrthographic(this, flags);
        }
    }

    private static class MapScaleView extends CLMaps.ScalePoint{

        private CLView view;

        public MapScaleView(CLView view, int targetoffset, int sourceoffset){
            super(view.settingsview, targetoffset, sourceoffset);
            this.view = view;
        }

        public MapScaleView(MapScaleView src, long flags){
            copy(src, flags);
        }

        protected MapScaleView(){

        }

        @Override
        public void sync(VLVManager<VLVEntry> source){
            super.sync(source);

            view.applyLookAt();
            view.applyViewProjection();
        }

        @Override
        public void copy(VLSyncType<VLVManager<VLVEntry>> src, long flags){
            super.copy(src, flags);

            MapScaleView target = (MapScaleView)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                view = target.view;

            }else if(((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE)){
                view = target.view.duplicate(FLAG_DUPLICATE);

            }else{
                Helper.throwMissingDefaultFlags();
            }
        }

        @Override
        public MapScaleView duplicate(long flags){
            return new MapScaleView(this, flags);
        }
    }

    public static class MapRotateView extends VLSyncMap<VLVEntry, CLView>{

        protected float[] cache;
        protected float[] startstatecache;

        public int offset;
        public float x;
        public float y;
        public float z;

        public MapRotateView(CLView target, int offset, float x, float y, float z){
            super(target);

            this.offset = offset;
            this.x = x;
            this.y = y;
            this.z = z;

            cache = new float[16];
            startstatecache = new float[4];
        }

        public MapRotateView(MapRotateView src, long flags){
            copy(src, flags);
        }

        protected MapRotateView(){

        }

        public void tune(){
            float[] settings = target.settingsview.provider();

            startstatecache[0] = settings[offset];
            startstatecache[1] = settings[offset + 1];
            startstatecache[2] = settings[offset + 2];
            startstatecache[3] = 1F;
        }

        @Override
        public void sync(VLVEntry source){
            Matrix.setIdentityM(cache, 0);
            Matrix.rotateM(cache, 0, source.target.get(), x, y, z);
            Matrix.multiplyMV(cache, 0, cache, 0, startstatecache, 0);

            float[] settings = target.settingsview.provider();
            settings[offset] = cache[0];
            settings[offset + 1] = cache[1];
            settings[offset + 2] = cache[2];

            target.applyLookAt();
            target.applyViewProjection();
        }

        @Override
        public void copy(VLSyncType<VLVEntry> src, long flags){
            super.copy(src, flags);

            MapRotateView target = (MapRotateView)src;

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
        public MapRotateView duplicate(long flags){
            return new MapRotateView(this, flags);
        }
    }
}
