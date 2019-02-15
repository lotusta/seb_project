package lt.testpro.app;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

/**
 * IBAN check
 *
 */
@SuppressWarnings("restriction")
public class App extends Application
{
	
    public void start(Stage primaryStage) {
    	Utility utils = new Utility();
    	
        Button btn = new Button(Const.ButtonWorkTypeIBAN);
        TextField textFieldGetIBAN = new TextField();
        Label textFielsetResult = new Label();
        Label labelWorkType = new Label(Const.LabelWorkTypeIBAN);
        CheckBox chBxchooseWorkType = new CheckBox("IBAN check from file");
        Button btnChoose = new Button(Const.ButtonChooseFile);
        FileChooser fileChooser = new FileChooser();
        btnChoose.setVisible(false);
        
        textFieldGetIBAN.setPrefWidth(250);
        chBxchooseWorkType.setOnAction(e->{
        	textFieldGetIBAN.setText("");
        	textFielsetResult.setText("");
        	if (chBxchooseWorkType.isSelected()) {
        		labelWorkType.setText(Const.LabelWorkTypeFile);
        		btn.setText(Const.ButtonWorkTypeFile);
        		btnChoose.setVisible(true);
        	} else {
        		labelWorkType.setText(Const.LabelWorkTypeIBAN);
        		btn.setText(Const.ButtonWorkTypeIBAN);
        		btnChoose.setVisible(false);
        	}

        });
        btn.setOnAction(e -> {
            String text = textFieldGetIBAN.getText();
            if (text.isEmpty()) {
            	textFielsetResult.setText(Const.resultNoPath);
            } else {
            	if (chBxchooseWorkType.isSelected()) {
            		if (utils.readFromFile(text)) {
            			textFielsetResult.setText(Const.resultAnswerFileIsCreated); 
            		} else {
            			textFielsetResult.setText(Const.resultFileNotExist);            			
            		}
            	} else {
            		
            		if(utils.isIBANCorrect(text)) {
                    	textFielsetResult.setText(Const.resultIsPositive);
            		} else {
                    	textFielsetResult.setText(Const.resultIsNegative);
            		}
            	}

            }
        });
        btnChoose.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null)
            	textFieldGetIBAN.setText(selectedFile.getAbsolutePath());
        });
        

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.add(chBxchooseWorkType, 1, 1);
        root.add(btn, 1, 3);
        root.add(btnChoose, 2, 3);
        root.add(textFieldGetIBAN, 2, 2);
        root.add(labelWorkType, 1, 2);
        root.add(textFielsetResult, 1, 4);

        Scene scene = new Scene(root, 450, 300);

        primaryStage.setTitle(Const.windowTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main( String[] args )
    {
     	launch(args);
    }
}
