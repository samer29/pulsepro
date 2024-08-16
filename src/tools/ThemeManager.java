package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javafx.scene.layout.StackPane;

public class ThemeManager {

    private static final String THEME_KEY = "theme";
    private static ThemeManager instance;
    private final Preferences prefs;
    private final List<StackPane> panes = new ArrayList<>();

    private ThemeManager() {
        prefs = Preferences.userNodeForPackage(ThemeManager.class);
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public String getTheme() {
        return prefs.get(THEME_KEY, "light"); // Default to light theme if none is set
    }

    public void applyTheme(StackPane rootStackPane) {
        String theme = prefs.get(THEME_KEY, "light"); // Default to light theme
        if (theme.equals("dark")) {
            setDarkTheme(rootStackPane);
        } else {
            setLightTheme(rootStackPane);
        }
    }

    public void setDarkTheme(StackPane rootStackPane) {
        rootStackPane.getStylesheets().remove(getClass().getResource("/css/pulseProtheme.css").toExternalForm());
        rootStackPane.getStylesheets().add(getClass().getResource("/css/pulseProthemeDARK.css").toExternalForm());
        prefs.put(THEME_KEY, "dark");
        System.out.println(rootStackPane.getScene().getStylesheets());
    }

    public void setLightTheme(StackPane rootStackPane) {
        rootStackPane.getStylesheets().remove(getClass().getResource("/css/pulseProthemeDARK.css").toExternalForm());
        rootStackPane.getStylesheets().add(getClass().getResource("/css/pulseProtheme.css").toExternalForm());
        prefs.put(THEME_KEY, "light");
    }

    public void toggleTheme(StackPane rootStackPane, boolean isDarkMode) {
        if (isDarkMode) {
            setDarkTheme(rootStackPane);
        } else {
            setLightTheme(rootStackPane);
        }
        applyThemeToAll();
    }

    public void registerPane(StackPane pane) {
        panes.add(pane);
        applyTheme(pane);
    }

    public void applyThemeToAll() {
        for (StackPane pane : panes) {
            applyTheme(pane);
        }
    }
}
