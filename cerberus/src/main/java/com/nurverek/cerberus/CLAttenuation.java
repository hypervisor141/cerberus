package com.nurverek.cerberus;

import com.nurverek.firestorm.FSAttenuation;

import vanguard.VLFloat;
import vanguard.VLVCurved;
import vanguard.VLVEntry;
import vanguard.VLVManager;
import vanguard.VLVariable;

public class CLAttenuation{

    public static class Radius extends FSAttenuation.Radius{

        private VLVEntry entry;

        public Radius(VLFloat radius){
            super(radius);
        }

        public VLVEntry entry(){
            return entry;
        }

        public void initializeManager(){
            entry = new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(radius));
        }

        public void radius(float from, float to, int delay, int cycles, VLVariable.Loop loop, VLVCurved.Curve curve){
            CLVTools.tune(entry, from, to, delay, cycles, loop, curve);
        }
    }

    public static class Distance extends FSAttenuation.Distance{

        private VLVManager<VLVEntry> manager;

        public Distance(VLFloat constant, VLFloat linear, VLFloat quadratic){
            super(constant, linear, quadratic);
        }

        public VLVManager<VLVEntry> manager(){
            return manager;
        }

        public void initializeManager(){
            manager = new VLVManager<>(3,0);
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(constant)));
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(linear)));
            manager.add(new VLVEntry(new VLVCurved(), 0, new CLMaps.Set(quadratic)));
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
    }
}
