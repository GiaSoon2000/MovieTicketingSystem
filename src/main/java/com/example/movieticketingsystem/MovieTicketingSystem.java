package com.example.movieticketingsystem;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.application.Application;

import java.util.Optional;

public class MovieTicketingSystem  extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Ticketing System");

        // Create the GUI components
        Label movieLabel = new Label("Movie Name");
        TextField movieTextField = new TextField();

        Label experienceLabel = new Label("Experience");
        ComboBox<String> experienceComboBox = new ComboBox<>();
        experienceComboBox.getItems().addAll(
                "Beanie", "Classic", "Deluxe", "Family-Friendly", "Flexound", "IMAX", "Indulge", "Infinity", "Junior", "Onyx"
        );

        Label showtimeLabel = new Label("Showtime");
        ToggleGroup showtimeToggleGroup = new ToggleGroup();
        RadioButton radioButton11am = new RadioButton("11:00 AM");
        RadioButton radioButton1_30pm = new RadioButton("01:30 PM");
        RadioButton radioButton4pm = new RadioButton("4:00 PM");
        RadioButton radioButton6_30pm = new RadioButton("06:30 PM");
        RadioButton radioButton9pm = new RadioButton("09:00 PM");
        radioButton11am.setToggleGroup(showtimeToggleGroup);
        radioButton1_30pm.setToggleGroup(showtimeToggleGroup);
        radioButton4pm.setToggleGroup(showtimeToggleGroup);
        radioButton6_30pm.setToggleGroup(showtimeToggleGroup);
        radioButton9pm.setToggleGroup(showtimeToggleGroup);

        TextField seatTextField = new TextField();
        Label seatPromptLabel = new Label("Enter seats (e.g., F6, F7)");

        Label popcornLabel = new Label("Food & Beverages");
        ToggleGroup popcornToggleGroup = new ToggleGroup();

        RadioButton radioButtonPopcorn1 = new RadioButton("RM 19.90");
        ImageView popcornImage1 = new ImageView("popcorn1.png");
        popcornImage1.setFitWidth(300);
        popcornImage1.setFitHeight(200);
        Label popcornName1 = new Label("Royal Popcorn Combo – Member Special");
        radioButtonPopcorn1.setGraphic(new VBox(popcornImage1, popcornName1));
        radioButtonPopcorn1.setContentDisplay(ContentDisplay.TOP);

        RadioButton radioButtonPopcorn2 = new RadioButton("RM 17.90");
        ImageView popcornImage2 = new ImageView("popcorn2.png");
        popcornImage2.setFitWidth(300);
        popcornImage2.setFitHeight(200);
        Label popcornName2 = new Label("Royal Popcorn");
        radioButtonPopcorn2.setGraphic(new VBox(popcornImage2, popcornName2));
        radioButtonPopcorn2.setContentDisplay(ContentDisplay.TOP);

        RadioButton radioButtonPopcorn3 = new RadioButton("RM 21.90");
        ImageView popcornImage3 = new ImageView("popcorn3.png");
        popcornImage3.setFitWidth(300);
        popcornImage3.setFitHeight(200);
        Label popcornName3 = new Label("Royal Popcorn Combo");
        radioButtonPopcorn3.setGraphic(new VBox(popcornImage3, popcornName3));
        radioButtonPopcorn3.setContentDisplay(ContentDisplay.TOP);

        radioButtonPopcorn1.setToggleGroup(popcornToggleGroup);
        radioButtonPopcorn2.setToggleGroup(popcornToggleGroup);
        radioButtonPopcorn3.setToggleGroup(popcornToggleGroup);

        Button submitButton = new Button("Submit");

        // Set up the grid layout for the components
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER_LEFT);

        // Add components to the grid pane
        gridPane.add(movieLabel, 0, 0);
        gridPane.add(movieTextField, 1, 0);
        gridPane.add(experienceLabel, 0, 1);
        gridPane.add(experienceComboBox, 1, 1);
        gridPane.add(showtimeLabel, 0, 4);
        gridPane.add(radioButton11am, 1, 2);
        gridPane.add(radioButton1_30pm, 1, 3);
        gridPane.add(radioButton4pm, 1, 4);
        gridPane.add(radioButton6_30pm, 1, 5);
        gridPane.add(radioButton9pm, 1, 6);
        gridPane.add(seatPromptLabel, 0, 7);
        gridPane.add(seatTextField, 1, 7);
        gridPane.add(popcornLabel, 0, 8);

        HBox popcornRadioButtons = new HBox(10);
        popcornRadioButtons.getChildren().addAll(radioButtonPopcorn1, radioButtonPopcorn2, radioButtonPopcorn3);
        gridPane.add(popcornRadioButtons, 1, 8);

        // Add the submit button to the grid pane with right alignment
        gridPane.add(submitButton, 1, 11);
        GridPane.setHalignment(submitButton, HPos.RIGHT);

        // Set the preferred width for the grid pane
        gridPane.setPrefWidth(1000);

        // Set up the event handling for the submit button
        submitButton.setOnAction(event -> {
            try {
                String movieName = movieTextField.getText();
                String experience = experienceComboBox.getValue();
                String showtime;

                RadioButton selectedShowtime = (RadioButton) showtimeToggleGroup.getSelectedToggle();
                if (selectedShowtime != null) {
                    showtime = selectedShowtime.getText();
                }else {
                    throw new Exception("Please select a showtime.");
                }

                String seatInput = seatTextField.getText();
                // Validate the seat format
                if (!isValidSeatFormat(seatInput)) {
                    throw new Exception("Invalid seat format. Please enter seats in the format 'F6, F7'.");
                }

                String popcornChoice = "";

                RadioButton selectedPopcorn = (RadioButton) popcornToggleGroup.getSelectedToggle();
                double popcornPrice = 0;
                if (selectedPopcorn != null) {
                    popcornChoice = selectedPopcorn.getText();

                    // Calculate the popcorn price based on the popcorn choice
                    switch (popcornChoice) {
                        case "RM 19.90" -> popcornPrice = 17.91;
                        case "RM 17.90" -> popcornPrice = 17.90;
                        case "RM 21.90" -> popcornPrice = 21.90;
                    }
                }

                // Perform validation
                if (movieName.isEmpty() || experience == null || showtime.isEmpty() || seatInput.isEmpty() || popcornChoice.isEmpty()) {
                    throw new Exception("Please fill in all the fields.");
                }

                String[] seats = seatInput.split(",");
                int numTickets = seats.length;

                // Calculate the ticket price based on the experience selected
                double ticketPrice = switch (experience) {
                    case "Beanie" -> 19.90;
                    case "Classic", "Junior" -> 15.90;
                    case "Deluxe", "Family-Friendly" -> 23.90;
                    case "Flexound", "IMAX" -> 25.90;
                    case "Indulge", "Infinity" -> 120.00;
                    case "Onyx" -> 89.90;
                    default -> 0.0;
                };

                // Adjusted price string for display in the confirmation
                String adjustedPrice = "";
                if (popcornChoice.equals("RM 19.90")) {
                    adjustedPrice = " (after 10% discount) RM " + popcornPrice;
                }

                // Calculate the total amount
                double totalAmount = ticketPrice * numTickets + popcornPrice;

                // Display the result in a dialog or alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("CGS Cinemas");
                alert.setHeaderText("Ticket Confirmation");
                alert.setContentText("Movie: " + movieName + "\n" +
                        "Experience: " + experience + "\n" +
                        "Showtime: " + showtime + "\n" +
                        "Selected Seats: " + seatInput + "\n" +
                        "Number of Tickets: " + numTickets + "\n" +
                        "Popcorn Choice: " + popcornChoice + adjustedPrice + "\n" +
                        "Total Amount: RM " + String.format("%.2f", totalAmount));

                // Add a custom "Cancel" button
                ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().add(cancelButtonType);

                // Get the result of the alert (wait for user response)
                Optional<ButtonType> result = alert.showAndWait();

                // Check if the "OK" button was clicked (to confirm the ticket booking)
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    showBookingSuccessfulMessage();
                } else {
                    // Handle the "Cancel" button click
                    // Reset the form here
                    movieTextField.setText("");
                    experienceComboBox.getSelectionModel().clearSelection();
                    showtimeToggleGroup.selectToggle(null);
                    seatTextField.setText("");
                    popcornToggleGroup.selectToggle(null);
                }
            } catch (Exception e) {
                // Handle the exception here
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        });

        // Create a scene with the grid pane
        Scene scene = new Scene(gridPane, 1150, 600);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to validate the seat format
    private boolean isValidSeatFormat(String seatInput) {
        String seatPattern = "^[A-Z]\\d+$";
        String[] seats = seatInput.split(",");
        for (String seat : seats) {
            if (!seat.trim().matches(seatPattern)) {
                return false;
            }
        }
        return true;
    }

    private void showBookingSuccessfulMessage() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Booking Successful");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Your booking has been successful!");
        successAlert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}