package hypervisor.cerberus;

import hypervisor.firestorm.program.FSAttenuation;
import hypervisor.vanguard.primitive.VLFloat;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVariable;

public class CLAttenuation{

    public static class Radius extends FSAttenuation.Radius{

        protected VLVManager<VLVEntry> manager;

        public Radius(VLFloat radius){
            super(radius);
        }

        public Radius(Radius src, long flags){
            copy(src, flags);
        }

        protected Radius(){}

        public void buildManager(){
            manager = new VLVManager<>(1, 0, new CLMaps.Set(radius, null));
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        }

        public VLVManager<VLVEntry> manager(){
            return manager;
        }

        public float radiusValue(){
            return radius.get();
        }

        public void radius(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            CLVTools.tune(manager, 0, from, to, delay, cycles, loop, curve, post);
        }

        @Override
        public void copy(FSAttenuation src, long flags){
            super.copy(src, flags);

            CLAttenuation.Radius target = (CLAttenuation.Radius)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                manager = target.manager;

            }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
                manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_FORCE_DUPLICATE_ENTRIES);

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

        public static final int CAT_CONSTANT = 0;
        public static final int CAT_LINEAR = 1;
        public static final int CAT_QUADRATIC = 2;

        protected VLVManagerDynamic<VLVManager<VLVEntry>> manager;

        public Distance(VLFloat constant, VLFloat linear, VLFloat quadratic){
            super(constant, linear, quadratic);
        }

        public Distance(Distance src, long flags){
            copy(src, flags);
        }

        protected Distance(){}

        public void buildManager(){
            manager = new VLVManagerDynamic<>(0, 3, 3, 0);

            VLVManager<VLVEntry> constant = new VLVManager<>(1,0, new CLMaps.Set(constant(), manager));
            VLVManager<VLVEntry> linear = new VLVManager<>(1,0, new CLMaps.Set(linear(), manager));
            VLVManager<VLVEntry> quadratic = new VLVManager<>(1,0, new CLMaps.Set(quadratic(), manager));

            constant.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
            linear.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
            quadratic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        }

        public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
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

        public void constant(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            CLVTools.tune(manager.getEntry(CAT_CONSTANT), 0, from, to, delay, cycles, loop, curve, post);
        }

        public void linear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            CLVTools.tune(manager.getEntry(CAT_LINEAR), 0, from, to, delay, cycles, loop, curve, post);
        }

        public void quadratic(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            CLVTools.tune(manager.getEntry(CAT_QUADRATIC), 0, from, to, delay, cycles, loop, curve, post);
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
