/**
 * Created by Dana on 05-Mar-16.
 */
/////////// fix press numeric number after result
/////////// fix C button for operators
/////////// make better design

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.geometry.*;

public class FX8 extends Application{

    ArrayList<Button> buttons = new ArrayList<>();
    String number = "";
    double number1 = 0;
    TextField text = new TextField();
    Button lastPressed = new Button();
    Button op = new Button();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Calculator");

        //TextField
        text.setAlignment(Pos.BASELINE_RIGHT);
        text.setText("0");

        ArrayList<Button> buttons = createButtons();

        //Layout is created with createLayout method

        //Handling Events
        buttons.get(0).setOnAction(e -> handleNumericButton(text, buttons.get(0)));
        buttons.get(1).setOnAction(e -> handleNumericButton(text, buttons.get(1)));
        buttons.get(2).setOnAction(e -> handleNumericButton(text, buttons.get(2)));
        buttons.get(3).setOnAction(e -> handleNumericButton(text, buttons.get(3)));
        buttons.get(4).setOnAction(e -> handleNumericButton(text, buttons.get(4)));
        buttons.get(5).setOnAction(e -> handleNumericButton(text, buttons.get(5)));
        buttons.get(6).setOnAction(e -> handleNumericButton(text, buttons.get(6)));
        buttons.get(7).setOnAction(e -> handleNumericButton(text, buttons.get(7)));
        buttons.get(8).setOnAction(e -> handleNumericButton(text, buttons.get(8)));
        buttons.get(9).setOnAction(e -> handleNumericButton(text, buttons.get(9)));

        buttons.get(10).setOnAction(e -> handleOperatorsButton(text, buttons.get(10)));
        buttons.get(11).setOnAction(e -> handleOperatorsButton(text, buttons.get(11)));
        buttons.get(12).setOnAction(e -> handleOperatorsButton(text, buttons.get(12)));
        buttons.get(13).setOnAction(e -> handleOperatorsButton(text, buttons.get(13)));

        buttons.get(14).setOnAction(e -> handleAllClearButton(text));
        buttons.get(15).setOnAction(e -> handleEqualsButton());

        buttons.get(16).setOnAction(e -> handleMinus(text));
        buttons.get(17).setOnAction(e -> handlePoint(text));

        buttons.get(18).setOnAction(e -> handleClear(text));

        //Scene + Stage
        Scene scene = new Scene(createLayout(text), 250, 300);
        scene.getStylesheets().add("buttons.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox createLayout(TextField text){
        //Layout - VBox with TextField, GridPane and HBox
        GridPane layout = new GridPane();
        layout.setHgap(20);
        layout.setVgap(20);

        layout.add(buttons.get(0), 1, 5); //digits from 0 to 9
        layout.add(buttons.get(1), 1, 4);
        layout.add(buttons.get(2), 2, 4);
        layout.add(buttons.get(3), 3, 4);
        layout.add(buttons.get(4), 1, 3);
        layout.add(buttons.get(5), 2, 3);
        layout.add(buttons.get(6), 3, 3);
        layout.add(buttons.get(7), 1, 2);
        layout.add(buttons.get(8), 2, 2);
        layout.add(buttons.get(9), 3, 2);

        layout.add(buttons.get(10), 4, 5); //add
        layout.add(buttons.get(11), 4, 4); //substract
        layout.add(buttons.get(12), 4, 3); //multiply
        layout.add(buttons.get(13), 4, 2); //divide

        layout.add(buttons.get(16), 2, 5); //plus-minus
        layout.add(buttons.get(15), 3, 5); //equals
        layout.setAlignment(Pos.CENTER);

        HBox last = new HBox(25);
        last.getChildren().addAll(buttons.get(17), buttons.get(18), buttons.get(14)); //point, clearAll, clear
        last.setAlignment(Pos.CENTER);

        final VBox big = new VBox(5);
        big.setAlignment(Pos.CENTER);
        big.getChildren().setAll(text, layout, last);
        return big;
    }

    public ArrayList<Button> createButtons(){
        //Buttons - store in ArrayList 10 buttons for digits, 4 buttons for operations, 2 buttons: clear, equals
        for (int i = 0; i<10; i++){
            Button button = new Button();
            button.setText(Integer.toString(i));
            buttons.add(button);
        }

        Button add = new Button("+");
        Button substract = new Button("-");
        Button multiply = new Button("*");
        Button divide = new Button("/");
        buttons.add(add);
        buttons.add(substract);
        buttons.add(multiply);
        buttons.add(divide);

        Button allClear = new Button("AC");
        Button equals = new Button("=");
        buttons.add(allClear);
        buttons.add(equals);

        Button minus = new Button("+-");
        Button point = new Button(".");
        buttons.add(minus);
        buttons.add(point);

        Button clear = new Button("C");
        buttons.add(clear);

        return buttons;
    }

    public void handleNumericButton(TextField text, Button button){
        number = number + button.getText();
        if (number.startsWith("0") && number.length() != 1)
            number = number.substring(1);
        text.setText(number);
        lastPressed = button;
    }

    public void handleOperatorsButton(TextField text, Button button){
        try {
            if(number.equalsIgnoreCase("")) {
                op = button;
                operate(op);
            }
            else {
                text.setText(button.getText());
                op = button;
                number1 = Double.parseDouble(number);
                number = "";
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Enter number first");
            alert.showAndWait();
        }
    }

    public void handleEqualsButton(){
        operate(op);
    }

    public void handleAllClearButton(TextField text){
        number1 = 0;
        number = "";
        text.setText("0");
    }

    public void operate(Button button){
        double result = 0;
        if (button.equals(buttons.get(10))) {
            result = number1 + Double.parseDouble(number);
            double finalValue = Math.round( result * 100.0 ) / 100.0;
            if(Double.toString(finalValue).endsWith(".0"))
                text.setText(Double.toString(finalValue).replace(".0", ""));
            else
                text.setText(Double.toString(finalValue));
        }
        if (button.equals(buttons.get(11))) {
            result = number1 - Double.parseDouble(number);
            double finalValue = Math.round( result * 100.0 ) / 100.0;
            if(Double.toString(finalValue).endsWith(".0"))
                text.setText(Double.toString(finalValue).replace(".0", ""));
            else
                text.setText(Double.toString(finalValue));
        }
        if (button.equals(buttons.get(12))) {
            result = number1 * Double.parseDouble(number);
            double finalValue = Math.round( result * 100.0 ) / 100.0;
            if(Double.toString(finalValue).endsWith(".0"))
                text.setText(Double.toString(finalValue).replace(".0", ""));
            else
                text.setText(Double.toString(finalValue));
        }
        if (button.equals(buttons.get(13))) {
            try {
                result = number1 / Double.parseDouble(number);
                double finalValue = Math.round( result * 100.0 ) / 100.0;
                if(Double.toString(finalValue).endsWith(".0"))
                    text.setText(Double.toString(finalValue).replace(".0", ""));
                else
                    text.setText(Double.toString(finalValue));
            } catch (ArithmeticException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("You cannot divide by 0!");
                alert.showAndWait();
            }
        }
        number = Double.toString(result);
    }

    public void handleMinus(TextField text) {
        if (number.equalsIgnoreCase("")) {
            text.setText("-");
            number = "-";
        } else {
            double newNo = -(Double.parseDouble(number));
            number = Double.toString(newNo);
            if (number.endsWith(".0"))
                text.setText(number.replace(".0", ""));
            else
                text.setText(number);
        }
    }

    public void handlePoint(TextField text){
        number = number + "." ;
        text.setText(number);
        lastPressed = buttons.get(17);
    }

    public void handleClear(TextField text){
        for (int i = 0; i < 10; i++) {                   //lastPressed is numeric button
            if (lastPressed == buttons.get(i)){
                number = number.substring(0, number.length()-1);
                text.setText(number);
            }
        }
       /* for (int i = 10; i < 14; i++) {
            if (lastPressed == buttons.get(i)) {       //lastPressed is operator
                text.setText(number);
                op = lastPressed;
            }
        }*/
        if (lastPressed == buttons.get(17)) {          //lastPressed is point
            number = number.substring(0, number.length()-1);
            text.setText(number);
        }
    }
}
