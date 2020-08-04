/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezfemapp.main;

import ezfemapp.blockProject.BlockProject;
import ezfemapp.gui.ezfem.ModelingScreen;
import ezfemapp.gui.mdcomponents.MyConfirmationPanel;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.stage.Stage;


/**
 *
 * @author GermanSR
 */
public abstract class BasicApp {
    
    public static final int PLATFORM_DESKTOP=0;
    public static final int PLATFORM_MOBILE=1;  
    public static int platform;

    public static final String APP_NAME = "ezFEM";
    public static final String APP_VERSION = " beta 0.1";
    
    String projectDirectory ="";
    String projectPath ="";
    
    BlockProject blocks;
    GUImanager gui;

    public BasicApp(){
        //INITIALIZE WITH A DEFAULT PROJECT
        blocks = new BlockProject("NewProject");
        blocks.createDefaultProject();
    }
    
    public void initializeGUI(Stage primaryStage,boolean maximized){
        //START THE GUI MANAGER
        gui = new GUImanager(this,primaryStage,maximized);
    }

            
    public void setProjectPath(String path){
        this.projectPath = path;
    }
    public void setProjectDirectory(String directory){
        this.projectDirectory = directory;
    }
    
    public void setProject(BlockProject blocks){
        this.blocks = blocks;
        blocks.initializeReferenceProperties();
        blocks.referenceParentToProperties();
        blocks.updateReferenceProperties();
        blocks.generateBlockMatrix();
        getGUImanager().reLoadGUI();
    }
    
    public void newDefaultProject(){
        MyConfirmationPanel confirmPanel = new MyConfirmationPanel();
        confirmPanel.setMessage("Delete everything to create\na new default project?");
        confirmPanel.show(gui);
        setProjectDirectory("");
        setProjectPath("");
        confirmPanel.getPopUp().setOnHidden((event)->{
            if(confirmPanel.getResult()){
                BlockProject newProject = new BlockProject("NewProject");
                newProject.createDefaultProject();
                setProject(newProject); 
            }
        });
    }
    
    
    public GUImanager getGUImanager(){
        return gui;
    }

    public String getProjectDirectory(){
        return projectDirectory;
    }
    
    public String getProjectPath(){
        return projectPath;
    }
    
    public BlockProject getBlocks(){
        return blocks;
    }
    
    
    public abstract boolean openProjectFromLocalFile();
    public abstract boolean saveProjectToLocalFile();
    public abstract boolean saveAs();
    public abstract boolean saveProjectToCurrentPath();
    
}
