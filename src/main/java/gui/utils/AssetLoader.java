package gui.utils;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for loading and caching game assets
 */
public class AssetLoader {
    
    private static AssetLoader instance;
    private final Map<String, Image> imageCache = new HashMap<>();
    
    private AssetLoader() {
        // Private constructor for singleton
    }
    
    public static AssetLoader getInstance() {
        if (instance == null) {
            instance = new AssetLoader();
        }
        return instance;
    }
    
    /**
     * Load an image from resources
     */
    public Image loadImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }
        
        try {
            var stream = getClass().getResourceAsStream(path);
            if (stream == null) {
                System.err.println("Resource not found: " + path);
                return null;
            }
            Image image = new Image(stream);
            imageCache.put(path, image);
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Load a character portrait
     */
    public Image loadCharacterPortrait(String characterType) {
        String path = "/images/portraits/" + characterType.toLowerCase() + ".png";
        return loadImage(path);
    }
    
    /**
     * Load a location icon
     */
    public Image loadLocationIcon(String locationType) {
        String path = "/images/icons/" + locationType.toLowerCase() + ".png";
        return loadImage(path);
    }
    
    /**
     * Load a food icon
     */
    public Image loadFoodIcon(String foodType) {
        String path = "/images/icons/food/" + foodType.toLowerCase() + ".png";
        return loadImage(path);
    }
    
    /**
     * Clear the cache
     */
    public void clearCache() {
        imageCache.clear();
    }
    
    /**
     * Get cache size
     */
    public int getCacheSize() {
        return imageCache.size();
    }
}
