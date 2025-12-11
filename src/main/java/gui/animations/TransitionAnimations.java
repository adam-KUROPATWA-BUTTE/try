package gui.animations;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Utility class for common transition animations
 */
public class TransitionAnimations {
    
    /**
     * Create a fade in animation
     */
    public static FadeTransition fadeIn(Node node, double duration) {
        FadeTransition ft = new FadeTransition(Duration.seconds(duration), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        return ft;
    }
    
    /**
     * Create a fade out animation
     */
    public static FadeTransition fadeOut(Node node, double duration) {
        FadeTransition ft = new FadeTransition(Duration.seconds(duration), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        return ft;
    }
    
    /**
     * Create a scale animation
     */
    public static ScaleTransition scale(Node node, double fromScale, double toScale, double duration) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(duration), node);
        st.setFromX(fromScale);
        st.setFromY(fromScale);
        st.setToX(toScale);
        st.setToY(toScale);
        return st;
    }
    
    /**
     * Create a pulse animation (scale up and down)
     */
    public static Animation pulse(Node node) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.5), node);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);
        
        ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.5), node);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        SequentialTransition pulse = new SequentialTransition(scaleUp, scaleDown);
        pulse.setCycleCount(Animation.INDEFINITE);
        return pulse;
    }
    
    /**
     * Create a shake animation
     */
    public static Animation shake(Node node) {
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(50), node);
        tt1.setByX(10);
        
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(50), node);
        tt2.setByX(-20);
        
        TranslateTransition tt3 = new TranslateTransition(Duration.millis(50), node);
        tt3.setByX(20);
        
        TranslateTransition tt4 = new TranslateTransition(Duration.millis(50), node);
        tt4.setByX(-20);
        
        TranslateTransition tt5 = new TranslateTransition(Duration.millis(50), node);
        tt5.setByX(10);
        
        return new SequentialTransition(tt1, tt2, tt3, tt4, tt5);
    }
    
    /**
     * Create a glow effect animation
     */
    public static Timeline glow(Node node, Color color) {
        DropShadow glow = new DropShadow();
        glow.setColor(color);
        glow.setRadius(0);
        node.setEffect(glow);
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(glow.radiusProperty(), 0)),
            new KeyFrame(Duration.seconds(0.5), 
                new KeyValue(glow.radiusProperty(), 20)),
            new KeyFrame(Duration.seconds(1.0), 
                new KeyValue(glow.radiusProperty(), 0))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }
    
    /**
     * Create a slide in animation
     */
    public static TranslateTransition slideIn(Node node, double fromX, double fromY, double duration) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), node);
        tt.setFromX(fromX);
        tt.setFromY(fromY);
        tt.setToX(0);
        tt.setToY(0);
        return tt;
    }
    
    /**
     * Apply a bounce animation
     */
    public static Animation bounce(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), node);
        tt.setByY(-30);
        tt.setAutoReverse(true);
        tt.setCycleCount(2);
        return tt;
    }
}
