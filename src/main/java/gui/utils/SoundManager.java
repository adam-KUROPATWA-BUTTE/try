package gui.utils;

/**
 * Manager for sound effects and music
 * Currently a placeholder for future audio implementation
 */
public class SoundManager {
    
    private static SoundManager instance;
    private boolean soundEnabled = true;
    private boolean musicEnabled = true;
    private double soundVolume = 0.7;
    private double musicVolume = 0.5;
    
    private SoundManager() {
        // Private constructor for singleton
    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    /**
     * Play a sound effect
     */
    public void playSound(String soundName) {
        if (!soundEnabled) return;
        
        // TODO: Implement actual sound playback
        System.out.println("Playing sound: " + soundName);
    }
    
    /**
     * Play background music
     */
    public void playMusic(String musicName) {
        if (!musicEnabled) return;
        
        // TODO: Implement actual music playback
        System.out.println("Playing music: " + musicName);
    }
    
    /**
     * Stop all sounds
     */
    public void stopAll() {
        // TODO: Implement
    }
    
    // Getters and setters
    
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
    
    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }
    
    public boolean isMusicEnabled() {
        return musicEnabled;
    }
    
    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }
    
    public double getSoundVolume() {
        return soundVolume;
    }
    
    public void setSoundVolume(double soundVolume) {
        this.soundVolume = Math.max(0, Math.min(1, soundVolume));
    }
    
    public double getMusicVolume() {
        return musicVolume;
    }
    
    public void setMusicVolume(double musicVolume) {
        this.musicVolume = Math.max(0, Math.min(1, musicVolume));
    }
}
