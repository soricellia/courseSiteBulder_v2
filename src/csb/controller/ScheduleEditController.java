package csb.controller;

import static csb.CSB_PropertyType.REMOVE_ITEM_MESSAGE;
import csb.data.Assignment;
import csb.data.Course;
import csb.data.CourseDataManager;
import csb.data.Lecture;
import csb.data.ScheduleItem;
import csb.gui.AssignmentDialog;
import csb.gui.CSB_GUI;
import csb.gui.LectureDialog;
import csb.gui.MessageDialog;
import csb.gui.ScheduleItemDialog;
import csb.gui.YesNoCancelDialog;
import java.util.Collections;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class ScheduleEditController {
    ScheduleItemDialog sid;
    LectureDialog ld;
    AssignmentDialog ad;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public ScheduleEditController(Stage initPrimaryStage, Course course, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new ScheduleItemDialog(initPrimaryStage, course, initMessageDialog);
        ad = new AssignmentDialog(initPrimaryStage,course,initMessageDialog);
        ld = new LectureDialog(initPrimaryStage,course,initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddScheduleItemRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showAddScheduleItemDialog(course.getStartingMonday());
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            ScheduleItem si = sid.getScheduleItem();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addScheduleItem(si);
            
            //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleEditScheduleItemRequest(CSB_GUI gui, ScheduleItem itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        sid.showEditScheduleItemDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            ScheduleItem si = sid.getScheduleItem();
            itemToEdit.setDescription(si.getDescription());
            itemToEdit.setDate(si.getDate());
            itemToEdit.setLink(si.getLink());
            
            //update gui
            course.getScheduleItems().set(course.getScheduleItems().indexOf(itemToEdit), si);
            Collections.sort(course.getScheduleItems());
            //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveScheduleItemRequest(CSB_GUI gui, ScheduleItem itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeScheduleItem(itemToRemove);
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
    }
    public void handleAddLectureRequest(CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ld.showAddLectureDialog();
        
        // DID THE USER CONFIRM?
        if (ld.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Lecture lecture = ld.getLecture();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addLecture(lecture);
            
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleEditLectureRequest(CSB_GUI gui, Lecture itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ld.showEditLectureDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (ld.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Lecture lecture = ld.getLecture();
            itemToEdit.setTopic(lecture.getTopic());
            itemToEdit.setSessions(lecture.getSessions());
            
            //update gui
            course.getLectures().set(course.getLectures().indexOf(itemToEdit), lecture);
            
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveLectureRequest(CSB_GUI gui, Lecture lectureToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeLecture(lectureToRemove);
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
    }
        
    public void handleEditAssignmentRequest(CSB_GUI gui, Assignment assignmentToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ad.showEditAssignmentDialog(assignmentToEdit);
        
        // DID THE USER CONFIRM?
        if (ad.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Assignment assignment = ad.getAssignment();
            assignmentToEdit.setName(assignment.getName());
            assignmentToEdit.setDate(assignment.getDate());
            assignmentToEdit.setTopics(assignment.getTopics());
            
            //update gui
            course.getAssignments().set(course.getAssignments().indexOf(assignmentToEdit), assignment);
            Collections.sort(course.getAssignments());
            //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    public void handleAddAssignmentRequest(CSB_GUI gui){
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ad.showAddAssignmentDialog(course.getStartingMonday());
        
        // DID THE USER CONFIRM?
        if (ad.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Assignment assignment = ad.getAssignment();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addAssignment(assignment);
            
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleRemoveAssignmentRequest(CSB_GUI gui, Assignment assignmentToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeAssignment(assignmentToRemove);
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
    }

    public void handleMoveLectureUpRequest(CSB_GUI gui, TableView<Lecture> lectureTable, Course course) {
      
        Lecture lecture = lectureTable.getSelectionModel().getSelectedItem();
        //MAKE SURE THIS IS NOT THE FIRST LECTURE
        int index = course.getLectures().indexOf(lecture);
        if(index>0){
            //SWITCH SELECTED LECTURE WITH THE LECTURE RIGHT ABOVE IT IF IT EXISTS    
            Lecture tempLecture = course.getLectures().get(index-1);
            course.getLectures().set(index-1, lecture);
            course.getLectures().set(index, tempLecture);
        }
    }
    public void handleMoveLectureDownRequest(CSB_GUI gui, TableView<Lecture> lectureTable, Course course) {
      
        Lecture lecture = lectureTable.getSelectionModel().getSelectedItem();
        //MAKE SURE THIS IS NOT THE LAST LECTURE
        int index = course.getLectures().indexOf(lecture);
        if(index < course.getLectures().size()){
            //SWITCH SELECTED LECTURE WITH THE LECTURE RIGHT ABOVE IT IF IT EXISTS    
            Lecture tempLecture = course.getLectures().get(index+1);
            course.getLectures().set(index+1, lecture);
            course.getLectures().set(index, tempLecture);
            
             //COURSE IS NOW DIRTY AND THUS CAN BE SAVED
            gui.getFileController().markAsEdited(gui);
        }
    }
}