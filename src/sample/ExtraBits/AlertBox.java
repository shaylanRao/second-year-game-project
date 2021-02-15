package sample.ExtraBits;

import javafx.fxml.FXMLLoader;
import java.net.URL;

public class AlertBox {

    public static void displayPrompt() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("sample/ExtraBits/alertBox.fxml"));

    }
}
