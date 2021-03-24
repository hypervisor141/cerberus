package com.nurverek.cerberus;

import com.nurverek.firestorm.FSBrightness;
import com.nurverek.firestorm.FSG;
import com.nurverek.firestorm.FSGamma;
import com.nurverek.firestorm.FSLight;
import com.nurverek.firestorm.FSLightDirect;
import com.nurverek.firestorm.FSLightPoint;
import com.nurverek.firestorm.FSLightSpot;
import com.nurverek.firestorm.FSMesh;
import com.nurverek.firestorm.FSP;
import com.nurverek.firestorm.FSShader;
import com.nurverek.firestorm.FSShadow;
import com.nurverek.firestorm.FSShadowDirect;
import com.nurverek.firestorm.FSShadowPoint;
import com.nurverek.firestorm.FSTexture;

import vanguard.VLListType;

public class CLPBasic extends FSP{

    public static final int TYPE_DISABLED = 35235;
    public static final int TYPE_ATTRIBUTE = 35236;
    public static final int TYPE_UNIFORM = 35237;
    public static final int TYPE_UBO = 35238;

    private Settings settings;

    public CLPBasic(int meshcapacity, int debugmode){
        super(10, meshcapacity, debugmode);
        settings = new Settings();
    }

    public void setTypeModel(int type){
        settings.type_model = type;
    }

    public void setTypePosition(int type){
        settings.type_position = type;
    }

    public void setTypeColor(int type){
        settings.type_color = type;
    }

    public void setTypeTexCoords(int type){
        settings.type_texcoords = type;
    }

    public void setTypeNormals(int type){
        settings.type_normals = type;
    }

    public void setTexture(boolean enabled){
        settings.texture = enabled;
    }

    public void setBrightness(FSBrightness brightness){
        settings.brightness = brightness;
    }

    public void setGamma(FSGamma gamma){
        settings.gamma = gamma;
    }

    public void addLightDirect(FSLightDirect light){
        settings.lights_direct.add(light);
        settings.lighting = true;
    }

    public void addLightPoint(FSLightPoint light){
        settings.lights_point.add(light);
        settings.lighting = true;
    }

    public void addLightSpot(FSLightSpot light){
        settings.lights_spot.add(light);
        settings.lighting = true;
    }

    public void addLightDirectShadow(FSShadowDirect shadow){
        settings.shadow_direct.add(shadow);

        settings.lighting = true;
        settings.shadows = true;
    }

    public void addLightPointShadow(FSShadowPoint shadow){
        settings.shadow_point.add(shadow);

        settings.lighting = true;
        settings.shadows = true;
    }

    @Override
    public void customize(VLListType<FSShader> shaders, int debug){
        
    }

    @Override
    public void destroy(){
        super.destroy();

        settings = null;
    }
    
    private static final class Settings{
        
        public int type_model;
        public int type_position;
        public int type_color;
        public int type_texcoords;
        public int type_normals;

        public FSBrightness brightness;
        public FSGamma gamma;

        public VLListType<FSLightDirect> lights_direct;
        public VLListType<FSLightPoint> lights_point;
        public VLListType<FSLightSpot> lights_spot;

        public VLListType<FSShadowDirect> shadow_direct;
        public VLListType<FSShadowPoint> shadow_point;

        public boolean texture;
        public boolean lighting;
        public boolean shadows;

        private Settings(){
            type_model = TYPE_DISABLED;
            type_position = TYPE_DISABLED;
            type_color = TYPE_DISABLED;
            type_texcoords = TYPE_DISABLED;
            type_normals = TYPE_DISABLED;

            lights_direct = new VLListType<>(10, 10);
            lights_point = new VLListType<>(10, 10);
            lights_spot = new VLListType<>(10, 10);

            shadow_direct = new VLListType<>(10, 10);
            shadow_point = new VLListType<>(10, 10);

            shadows = false;
            lighting = false;
        }
    }
}
