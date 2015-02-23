/**
 * Created by mike on 2/21/2015.
 */
public class ModifyLevelString {
    private static final String TIMER = new String("\"moveLimit\":");
    private static final String MOVES = new String("\"timeLimit\":");

    private String _levelData = new String();

    public ModifyLevelString(String levelData) {
        _levelData = levelData;
    }

    private void increaseNo(String attribute) {
        int attributePos = _levelData.indexOf(attribute);
        String attributeStr = _levelData.substring(attributePos, moveToLastDigit(attributePos));
        int attributeVal = Integer.parseInt(attributeStr);
        attributeVal = attributeVal + 100;
    }

    public void increaseTimer() {
        increaseNo(TIMER);
    }

    public void increaseMoves() {
        int movesPosition = _levelData.indexOf(MOVES);
        String noMoves = _levelData.substring(movesPosition, moveToLastDigit(movesPosition));
    }

    private int moveToLastDigit(int index) {
        int lastDigitIndex = index;
        boolean isDigitPresent = false;

        do {
            String character = _levelData.substring(lastDigitIndex, lastDigitIndex+1);
            try {
                int iChar = Integer.parseInt(character);
                lastDigitIndex++;
                isDigitPresent = true;
            }
            catch (Exception ex) {
                isDigitPresent = false;
                break;
            }
        } while (isDigitPresent);

        return lastDigitIndex;
    }
}
