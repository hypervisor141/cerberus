package hypervisor.cerberus;

import hypervisor.firestorm.program.FSAttenuation;
import hypervisor.vanguard.primitive.VLFloat;
import hypervisor.vanguard.variable.VLVCurved;
import hypervisor.vanguard.variable.VLVEntry;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVManagerDynamic;
import hypervisor.vanguard.variable.VLVariable;

@SuppressWarnings("unused")
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
            manager = new VLVManager<>(1, 0, new CLMaps.Set(radius));
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
        }

        public VLVManager<VLVEntry> manager(){
            return manager;
        }

        public float radiusValue(){
            return radius.value;
        }

        public void radius(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            CLVTools.tune(manager, 0, from, to, delay, cycles, loop, curve, post);
            manager.start();
        }

        @Override
        public void copy(FSAttenuation src, long flags){
            super.copy(src, flags);

            CLAttenuation.Radius target = (CLAttenuation.Radius)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                manager = target.manager;

            }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
                manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_DUPLICATE_ENTRIES);

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

            VLVManager<VLVEntry> constant = new VLVManager<>(1,0, new CLMaps.Set(constant()));
            VLVManager<VLVEntry> linear = new VLVManager<>(1,0, new CLMaps.Set(linear()));
            VLVManager<VLVEntry> quadratic = new VLVManager<>(1,0, new CLMaps.Set(quadratic()));

            constant.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
            linear.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));
            quadratic.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Chain<>()));

            manager.addEntry(constant);
            manager.addEntry(linear);
            manager.addEntry(quadratic);
        }

        public VLVManagerDynamic<VLVManager<VLVEntry>> manager(){
            return manager;
        }

        public float constantValue(){
            return constant.value;
        }

        public float linearValue(){
            return linear.value;
        }

        public float quadraticValue(){
            return quadratic.value;
        }

        public void constant(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            VLVManager<VLVEntry> target = manager.getEntry(CAT_CONSTANT);
            CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve, post);

            manager.activateEntry(CAT_CONSTANT);
            target.start();
            manager.start();
        }

        public void linear(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            VLVManager<VLVEntry> target = manager.getEntry(CAT_LINEAR);
            CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve, post);

            manager.activateEntry(CAT_LINEAR);
            target.start();
            manager.start();
        }

        public void quadratic(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve, CLMaps.Chain.Post<VLVEntry> post){
            VLVManager<VLVEntry> target = manager.getEntry(CAT_QUADRATIC);
            CLVTools.tune(target, 0, from, to, delay, cycles, loop, curve, post);

            manager.activateEntry(CAT_QUADRATIC);
            target.start();
            manager.start();
        }

        @Override
        public void copy(FSAttenuation src, long flags){
            super.copy(src, flags);

            CLAttenuation.Distance target = (CLAttenuation.Distance)src;

            if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
                manager = target.manager;

            }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
                manager = target.manager.duplicate(FLAG_CUSTOM | VLVManager.FLAG_DUPLICATE_ENTRIES);

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
