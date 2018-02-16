/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @FXML private Button task1_btn;
    @FXML private Button task2_btn;
    @FXML private Button task3_btn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void handleTask1(ActionEvent event) {
//        System.out.println("start task 1");
        if (task1 == null) {
            System.out.println("start task 1");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            task1_btn.setText("End Task 1");
        } else {
            task1.end();
            System.out.println("end task 1");
            task1 = null;
            task1_btn.setText("Start Task 1");
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1_btn.setText("Start Task 1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void handleTask2(ActionEvent event) {
//        System.out.println("start task 2");
        if (task2 == null) {
            System.out.println("start task 2");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                if (message.equals("Task2 done.")) {
                    task2 = null;
                    task2_btn.setText("Start Task 2");
                }
                textArea.appendText(message + "\n");
            });
            
            task2.start();
            task2_btn.setText("End Task 2");
        } else {
            task2.end();
            System.out.println("end task 2");
            task2 = null;
            task2_btn.setText("Start Task 2");
        }      
    }
    
    @FXML
    public void handleTask3(ActionEvent event) {
//        System.out.println("start task 3");
        if (task3 == null) {
            System.out.println("start task 3");
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                if (((String)evt.getNewValue()).equals("Task3 done.")) {
                    task3 = null;
                    task3_btn.setText("Start Task 3");
                }
                textArea.appendText((String)evt.getNewValue() + "\n");
//                System.out.println((String)evt.getNewValue());
            });
            
            task3.start();
            task3_btn.setText("End Task 3");
        } else {
            task3.end();
            System.out.println("end task 3");
            task3 = null;
            task3_btn.setText("Start Task 3");
        } 
    } 
}
