/**
 * Created by mike on 2/21/2015.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;

public class JsonLevel {
    private static final String TIMER = new String("moveLimit");
    private static final String MOVES = new String("timeLimit");
    private static final String EXPLOSION = new String("pepperCandyExplosionTurns");

    private JSONObject _level;

    public JsonLevel(String jsonString) {
        try {
            _level = new JSONObject(jsonString);
        }
        catch (Exception ex) {
        }
    }

    private void modifyElement(String elementName) {
        try {
            int value = _level.getInt(elementName);
            System.out.println(elementName + ": " + value);
            value = value*3 + 15;
            _level.put(elementName, value);
        }
        catch(JSONException jEx) {
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void modifyTimeLimit() {
        modifyElement(TIMER);
    }

    public void modifyNoMoves() {
        modifyElement(MOVES);
    }

    public void modifyExplosionTimer() {
        modifyElement(EXPLOSION);
    }

    public void writeJSON(String filePath) {
        try {
            FileWriter fwrite = new FileWriter(filePath);
            _level.write(fwrite);
            fwrite.flush();
            fwrite.close();
        }
        catch(JSONException jEx) {
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
