package com.nurverek.cerberus;

import com.nurverek.firestorm.FSViewConfig;
import com.nurverek.vanguard.VLSyncable;
import com.nurverek.vanguard.VLVTypeVariable;

public class CLSyncDefinitions{

    public static class LookAt extends VLSyncable.Definition<VLVTypeVariable, FSViewConfig>{

        public LookAt(FSViewConfig target){
            super(target);
        }

        @Override
        protected void sync(VLVTypeVariable src, FSViewConfig target){

        }
    }
}
