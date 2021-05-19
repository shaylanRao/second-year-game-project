package sample.controllers.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.models.audio.SoundManager;
import javafx.fxml.FXML;

public class LeaderboardScreen {

    @FXML
    private Label name_player1;
    @FXML
    private Label laps_player1;
    @FXML
    private Label time_player1;
    @FXML
    private Label name_player2;
    @FXML
    private Label laps_player2;
    @FXML
    private Label time_player2;


    public void setUp(int laps_count, int total_time,
                      int laps_count2, int total_time2)
    {
        System.out.println(" - setting up leaderboard data... ");
        name_player1.setText("Player1");
        laps_player1.setText(String.valueOf(laps_count));
        time_player1.setText(String.valueOf(total_time / 1000.0f));

        name_player2.setText("Player2");
        laps_player2.setText(String.valueOf(laps_count2));
        time_player2.setText(String.valueOf(total_time2 / 1000.0f));
    }

    public void continueClicked(MouseEvent mouseEvent) {

        System.out.println("Leaderboard::CONTINUE clicked!");
        SoundManager.play("button");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/MainScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
