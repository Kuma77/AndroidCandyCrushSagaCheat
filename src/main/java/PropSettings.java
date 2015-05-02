import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mike on 3/8/2015.
 */
public class PropSettings {
    private final String PROPERTIES_FILE = "tweakdata.prop";
    private final String PROP_PATH;

    private Properties propFile = new Properties();

    PropSettings(String filePath) {
        PROP_PATH = filePath + PROPERTIES_FILE;
        InputStream is = null;
        try {
            File f = new File(PROP_PATH);
            is = new FileInputStream( f );
            propFile.load(is);
        } catch (IOException e) {
            System.out.println("Properties file not found!");
        }
    }

    private void modifyValue(String property, int newValue) {
        String strValue = propFile.getProperty(property);

        propFile.setProperty(property, Integer.toString(newValue));
    }

    public void ModifyStoredLives() {
        final String LIVES_MAX_PROP = "lives.base.max";
        final String MYSTERY_INTERVAL = "mystery.quests.interval";
        final String LIVES_INTERVAL = "lives.gain.interval";

        Map<String, Integer> propValue = new HashMap<String, Integer >() {{
            put("send.extra.life.interval.hours", 1);
            put(LIVES_MAX_PROP, 50);
            put(MYSTERY_INTERVAL, 30);
            put(LIVES_INTERVAL, 10);
            put("mystery.quests.interval", 1);
            put("cross.promo.request.throttle.interval", 864000);
        }};

        for(Map.Entry<String, Integer> entry : propValue.entrySet()) {
            modifyValue(entry.getKey(), entry.getValue());
        }
    }

    public void WriteSettings() {
        File f = new File(PROP_PATH);
        try {
            OutputStream os = new FileOutputStream(f);
            propFile.store(os, null);
        } catch (IOException e) {
            System.out.println("Could not write the settings file! ");
        }
    }

    public void Write() {
        File f = new File(PROP_PATH);
        try {
            OutputStream os = new FileOutputStream(f);
            for(Map.Entry<Object, Object> e : propFile.entrySet()) {
                String line = new String();
                line = e.getKey().toString() + " " + e.getValue().toString() + "\n";
                try {
                    os.write(line.getBytes());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
