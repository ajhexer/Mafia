package datamodel;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextInputControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RTLChange implements ChangeListener<String> {
    private TextInputControl t;
    public RTLChange(TextInputControl t){
        this.t = t;
    }
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if(!t1.isEmpty()){
            if(isRTL(String.valueOf(t1.charAt(0)))){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        t.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    }
                });

            }else{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        t.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                    }
                });
            }
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    t.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                }
            });
        }
    }

    private boolean isRTL(String s){
        Pattern RTL_CHARACTERS =
                Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]");
        Matcher matcher = RTL_CHARACTERS.matcher(s);
        if(matcher.find()){
            return true;  // it's RTL
        }
        return false;
    }
}
