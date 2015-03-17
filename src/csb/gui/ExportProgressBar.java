/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csb.gui;

import csb.data.Course;
import csb.file.CourseSiteExporter;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tony
 */
public class ExportProgressBar extends Stage{
    private ProgressBar progressBar;
    private Button completeButton;
    private Button closeButton;
    private Label taskOutput;
    private GridPane gridPane;
    CourseSiteExporter exporter;
    Course course;
    private boolean isComplete;
    
    //TEXT WE WILL NEED
    private final String DEFAULT_EXPORT_MESSAGE = "Exporting...";
    private final String HOME_EXPORT_MESSAGE = "Exporting Home Page";
    private final String SYLLABUS_EXPORT_MESSAGE = "Export Syllabus Complete";
    private final String SCHEDULE_EXPORT_MESSAGE = "Export Schedule Complete";
    private final String HOMEWORKS_EXPORT_MESSAGE = "Export Homeworks Complete";
    private final String PROJECT_EXPORT_MESSAGE = "Export Project Complete";
    private final String COMPLETE_TEXT = "Complete";
    private final String CANCEL_TEXT = "Cancel";
    
    /**
     *
     * @param primaryStage
     * @param closeMessageDialog
     * @param course
     */
    public ExportProgressBar(Stage primaryStage,Task task) {
        this.isComplete = false;
       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(15,20, 20, 20));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        //INITIALIZE STUFF
        taskOutput = new Label(DEFAULT_EXPORT_MESSAGE);
        taskOutput.textProperty().bind(task.messageProperty());
        taskOutput.setScaleX(1.2);
        taskOutput.setScaleY(1.5);
        taskOutput.setPadding(new Insets(15,15,25,15));
        
        progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.progressProperty().addListener(e->{
            if(progressBar.progressProperty().doubleValue() == 1)
                completeButton.setDisable(false);
        });
        progressBar.setPrefSize(150, 40);
        
        ProgressIndicator pi = new ProgressIndicator();
        pi.setProgress(0);
        pi.progressProperty().bind(task.progressProperty());
        pi.setPrefSize(40,40);
        
        completeButton = new Button(COMPLETE_TEXT);
        completeButton.setDisable(true);
        completeButton.setOnAction(e->{
           isComplete = true;
           ExportProgressBar.this.close();
        });
        closeButton = new Button(CANCEL_TEXT);
        closeButton.setOnAction(e->{
            ExportProgressBar.this.close();
        });
        
        gridPane.add(taskOutput, 0, 3);
        gridPane.add(progressBar, 2, 3);
        gridPane.add(pi, 3, 3);
        gridPane.add(completeButton, 0, 4);
        gridPane.add(closeButton, 2, 4);
        
         // MAKE IT LOOK NICE
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
       
        Scene exportScene = new Scene(gridPane);
        this.setWidth(550);
        this.setHeight(250);
        
        this.setScene(exportScene);
        
        
    }
    public ProgressBar getProgressBar(){
        return progressBar;
    }
    public boolean isComplete(){
        return isComplete;
    }
    public void enableCompletButton(){
         completeButton.setDisable(false);
    }
}
