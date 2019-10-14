import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

/*
@Author İnanç Furkan Çakıl
inanc.cakil@gmail.com
https://github.com/inanc & http://inanc.me
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("https://github.com/inanc & http://inanc.me");

        // Create the grid pane
        GridPane gridPane = createPane();
        // Add UI controls to the grid pane
        addUIControls(gridPane);
        // Create a scene with grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();


    }

    private GridPane createPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Multiplication JavaFX Client Application");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add Number1 Label
        Label number1Label = new Label("Number1 : ");
        gridPane.add(number1Label, 0, 1);

        // Add Number1 Text Field
        TextField number1Field = new TextField();
        number1Field.setPrefHeight(40);
        gridPane.add(number1Field, 1, 1);


        // Add Number2 Label
        Label number2Label = new Label("Number2 : ");
        gridPane.add(number2Label, 0, 2);

        // Add Number2 Text Field
        TextField number2Field = new TextField();
        number2Field.setPrefHeight(40);
        gridPane.add(number2Field, 1, 2);

        // Add Result Label
        Label resultLabel = new Label("Result : ");
        gridPane.add(resultLabel, 0, 4);
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));


        // Add Result
        Label result = new Label("");
        result.setPrefHeight(40);
        gridPane.add(result, 1, 4);
        result.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 3, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (number1Field.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter number1");
                    return;
                }
                if (number2Field.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter number2");
                    return;
                }

                try {
                    BigDecimal number1 = new BigDecimal(number1Field.getText().trim());
                    BigDecimal number2 = new BigDecimal(number2Field.getText().trim());
                    MultiplicationDTO multiplicationDTO = getData(number1, number2);

                    if (multiplicationDTO == null) {
                        throw new ConnectException("Connection Fail");

                    }
                    result.setText(multiplicationDTO.getNumber1() + " * " + multiplicationDTO.getNumber2() + " = " + multiplicationDTO.getResult());

                    System.out.println(multiplicationDTO.getNumber1() + " " + multiplicationDTO.getNumber2() + " " + multiplicationDTO.getResult());


                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter number");

                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", e.getMessage());
                }


            }
        });
    }

    private MultiplicationDTO getData(BigDecimal number1, BigDecimal number2) {
        try {
            final String uri = "http://localhost:8080/?number1={number1}&number2={number2}";

            Map<String, BigDecimal> params = new HashMap<>();
            params.put("number1", number1);
            params.put("number2", number2);

            RestTemplate restTemplate = new RestTemplate();
            MultiplicationDTO result = restTemplate.getForObject(uri, MultiplicationDTO.class, params);

            System.out.println(result);
            return result;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }

    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

