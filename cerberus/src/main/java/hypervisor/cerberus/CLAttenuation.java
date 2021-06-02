package hypervisor.cerberus;

import hypervisor.firestorm.program.FSAttenuation;
import hypervisor.vanguard.primitive.VLFloat;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVariable;

public class CLAttenuation{

    public static class Radius extends FSAttenuation.Radius{

        protected VLVEntry entry;

        public Radius(VLFloat radius){
            super(radius);
        }

        public Radius(Radius src, long flags){
            copy(src, flags);
        }

        protected Radius(){}

        public void buildManager(){
            entry = new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(radius));
        }

        public VLVEntry entry(){
            return entry;
        }

        public float radiusValue(){
            return radius.get();
        }

        public void radius(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
            CLVTools.tune(entry, from, to, delay, cycles, loop, curve);
        }

        @Override
        public void copy(FSAttenuation src, long flags){
            super.copy(src, flags);

            CLAttenuation.Radius target = (CLAttenuation.Radius)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                entry = target.entry;

            }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
                entry = target.entry.duplicate(VLCopyable.FLAG_DUPLICATE);

            }else{
                Helper.throwMissingDefaultFlags();
            }
        }

        @Override
        public CLAttenuation.Radius duplicate(long flags){
            return new CLAttenuation.Radius(this, flags);
        }
    }

    public static class Distance extends FSAttenuation.Distance{

        protected VLVManager<VLVEntry> manager;

        public Distance(VLFloat constant, VLFloat linear, VLFloat quadratic){
            super(constant, linear, quadratic);
        }

        public Distance(Distance src, long flags){
            copy(src, flags);
        }

        protected Distance(){}

        public void buildManager(){
            manager = new VLVManager<>(3,0);
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(constant)));
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(linear)));
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(quadratic)));
        }

        public VLVManager<VLVEntry> manager(){
            return manager;
        }

        public float constantValue(){
            return constant.get();
        }

        public float linearValue(){
            return linear.get();
        }

        public float quadraticValue(){
            return quadratic.get();
        }

        public void constant(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
            CLVTools.tune(manager.get(0), from, to, delay, cycles, loop, curve);
        }

        public void linear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
            CLVTools.tune(manager.get(1), from, to, delay, cycles, loop, curve);
        }

        public void quadratic(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
            CLVTools.tune(manager.get(2), from, to, delay, cycles, loop, curve);
        }

        @Override
        public void copy(FSAttenuation src, long flags){
            super.copy(src, flags);

            CLAttenuation.Distance target = (CLAttenuation.Distance)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                manager = target.manager;

            }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
                manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_FORCE_DUPLICATE_ENTRIES);

            }else{
                Helper.throwMissingDefaultFlags();
            }
        }

        @Override
        public CLAttenuation.Distance duplicate(long flags){
            return new CLAttenuation.Distance(this, flags);
        }
    }
}
