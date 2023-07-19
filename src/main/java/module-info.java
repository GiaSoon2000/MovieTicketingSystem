module com.example.movieticketingsystem {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.movieticketingsystem to javafx.fxml;
    exports com.example.movieticketingsystem;
}