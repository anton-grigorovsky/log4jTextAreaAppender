package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Антон on 03.06.2018.
 */
public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static TextArea textArea;

    public static void main(String[] args) {
        launch(args);
        TextAreaAppender.addTextArea(textArea);
    }

    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        textArea = new TextArea();
        textArea.setEditable(false);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        VBox pane = new VBox();
        Scene scene = new Scene(pane);
        Button button = new Button("Start");
        button.setOnAction(e -> logging());
        pane.getChildren().addAll(textArea, button);
        stage.setScene(scene);
        stage.show();

        initialize();


    }

    public void initialize() throws Exception {
        TextAreaAppender.addTextArea(textArea);
    }


    private void logging()
    {
        for(int i = 0; i < 10; i++)
        {
            log.warn("Logging message - " + i);
        }
    }
}
