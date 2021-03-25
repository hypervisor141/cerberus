package com.nurverek.cerberus;

import android.opengl.GLES32;

import com.nurverek.firestorm.FSBrightness;
import com.nurverek.firestorm.FSConfig;
import com.nurverek.firestorm.FSConfigDynamic;
import com.nurverek.firestorm.FSConfigGroup;
import com.nurverek.firestorm.FSControl;
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

    public void setUBOSegmentsModel(int type){
        settings.modelsegments = type;
    }

    public void setUBOSegmentsPosition(int type){
        settings.positionsegments = type;
    }

    public void setUBOSegmentsColor(int type){
        settings.colorsegments = type;
    }

    public void setUBOSegmentsTexCoords(int type){
        settings.texcoordsegments = type;
    }

    public void setUBOSegmentsNormals(int type){
        settings.normalssegments = type;
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
    public void customize(VLListType<FSMesh> meshes, VLListType<FSShader> shaders, int debug){
        FSShader vshader = new FSShader(GLES32.GL_VERTEX_SHADER);
        FSShader fshader = new FSShader(GLES32.GL_FRAGMENT_SHADER);

        shaders.add(vshader);
        shaders.add(fshader);

        FSConfigGroup predraw = new FSConfigGroup(FSConfig.MODE_ALWAYS,10, 10);
        FSConfigGroup draw = new FSConfigGroup(FSConfig.MODE_ALWAYS,10, 10);
        FSConfigGroup postdraw = new FSConfigGroup(FSConfig.MODE_ALWAYS,10, 10);

        setupconfig = predraw;
        meshconfig = draw;
        postdrawconfig = postdraw;

        if(settings.type_position == TYPE_ATTRIBUTE){
            FSConfig positions = new FSP.AttribPointer(FSConfig.MODE_ALWAYS, FSG.ELEMENT_POSITION, 0);
            FSConfig enableposition = new FSP.AttribEnable(FSConfig.MODE_ALWAYS, positions.location());
            FSConfig disableposition = new FSP.AttribDisable(FSConfig.MODE_ALWAYS, positions.location());

            registerAttributeLocation(vshader, positions);

            predraw.configs.add(enableposition);
            draw.configs.add(positions);
            postdraw.configs.add(disableposition);

            vshader.addAttribute(positions.location(), "vec4", "position");

        }else if(settings.type_position == TYPE_UNIFORM){
            int count = meshes.get(0).instance(0).positions().size() / FSG.UNIT_SIZE_POSITION;
            FSConfig positions = new FSP.Uniform4fve(FSConfig.MODE_ONETIME, 0, FSG.ELEMENT_POSITION, 0, count);

            registerUniformArrayLocation(vshader, count, positions);
            draw.configs.add(positions);
            vshader.addUniform(positions.location(), "vec4", "position");

        }else if(settings.type_position == TYPE_UBO){
            int size = meshes.size();
            int size2 = 0;
            int size3 = 0;
            int max = 0;
            int maxmeshindex = 0;
            int maxinstanceindex = 0;

            FSMesh mesh;

            for(int i = 0; i < size; i++){
                mesh = meshes.get(i);
                size2 = mesh.size();

                for(int i2 = 0; i2 < size2; i2++){
                    size3 = mesh.instance(i2).positions().size();

                    if(max < size3){
                        max = size3;
                        maxmeshindex = i;
                        maxinstanceindex = i2;
                    }
                }
            }

            segmentation needed

            FSConfig positions = new FSP.UniformBlockElement(FSConfig.MODE_ALWAYS, FSG.ELEMENT_POSITION, "POSITIONS", 0);

            draw.configs.add(positions);
            vshader.addUniformBlock("std140", "POSITIONS", "vec4 positions[" + meshes.get(maxmeshindex).instance(maxinstanceindex).vertexSize() + "];");
        }
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

        public int modelsegments;
        public int positionsegments;
        public int colorsegments;
        public int texcoordsegments;
        public int normalssegments;

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

            modelsegments = 1;
            positionsegments = 1;
            colorsegments = 1;
            texcoordsegments = 1;
            normalssegments = 1;
        }
    }
}
