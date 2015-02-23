/**
 * Created by mike on 2/21/2015.
 */

import java.io.FileWriter;
import org.json.*;

public class JsonLevel {
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
            value = value*2 + 15;
            _level.put(elementName, value);
        }
        catch(JSONException jEx) {

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void modifyTimeLimit() {
        modifyElement("timeLimit");
    }

    public void modifyNoMoves() {
        modifyElement("moveLimit");
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
