/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Samer
 */
import java.util.prefs.Preferences;

public class LocalStorage {

    private Preferences prefs;

    public LocalStorage() {
        // Initialize Preferences
        prefs = Preferences.userNodeForPackage(LocalStorage.class);
    }

    // Save a value
    public void saveData(String key, String value) {
        prefs.put(key, value);
    }

    // Retrieve a value
    public String getData(String key, String defaultValue) {
        return prefs.get(key, defaultValue);
    }

    // Remove a value
    public void removeData(String key) {
        prefs.remove(key);
    }
}
