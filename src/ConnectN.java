public class ConnectN {

    private static int DEFAULT_COLUMNS = 7;
    private static int DEFAULT_ROWS = 6;
    private static int DEFAULT_WINNING_NUMBER = 4;

    private int[][] gameState;

    private int lastColumn;
    private int lastRow;
    private int numberOfColumns;
    private int numberOfRows;
    private int playerTurn;
    private int winningNumber;

    private int totalTurnsMade;
    private int totalPossibleTurns;

    ConnectN() {

        this.numberOfColumns = DEFAULT_COLUMNS;
        this.numberOfRows = DEFAULT_ROWS;
        this.winningNumber = DEFAULT_WINNING_NUMBER;

        resetGame();
    }

    public ConnectN(int numberOfRows, int numberOfColumns, int winningNumber) {

        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.winningNumber = winningNumber;

        resetGame();
    }

    public int[][] getGameState() {
        return gameState;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    boolean insertChip(int columnNumber) {

        // Looking for an empty space from bottom row to insert chip
        for (int i = numberOfRows - 1; i >= 0; i--) {

            if (gameState[i][columnNumber] == 0) {

                gameState[i][columnNumber] = playerTurn;
                lastColumn = columnNumber;
                lastRow = i;
                totalTurnsMade++;
                return true;
            }
        }
        return false;
    }

    int detectWin() {

        int result = 0;

        // Don't need to check for win if the number of total turns made by the players is less than winning number.
        if (totalTurnsMade < winningNumber) {
            result = -1;
        }

        result = detectWinColumns();

        if (result == playerTurn) {
            return result;
        }

        result = detectWinRows();

        if (result == playerTurn) {
            return result;
        }

        result = detectWinDiagonals();

        if (result == playerTurn) {
            return result;
        }

        return result;
    }

    private int detectWinRows() {

        int playersChipInRow = 0; // Keeps count of chips in the current row

        for (int i = 0; i < numberOfColumns; i++) {

            int chip = gameState[lastRow][i];

            if (chip == playerTurn) {
                playersChipInRow++; // Increases the count if an adjacent same chip found

                if (playersChipInRow == winningNumber) {
                    return playerTurn; // No need to check anymore if same adjacent chips are found
                }
            } else {

                playersChipInRow = 0; // Resets the count if adjacent chip is not the same
            }
        }

        return -1;
    }


    private int detectWinColumns() {

        int playersChipInColumn = 0; // Keeps count of chips in the current column

        for (int i = 0; i < numberOfRows; i++) {

            int chip = gameState[i][lastColumn];

            if (chip == playerTurn) {
                playersChipInColumn++; // Increases the count if an adjacent same chip found

                if (playersChipInColumn == winningNumber) {
                    return playerTurn; // No need to check anymore if same adjacent chips are found
                }
            } else {

                playersChipInColumn = 0; // Resets the count if adjacent chip is not the same
            }
        }

        if (playersChipInColumn >= winningNumber) {
            return playerTurn;
        }

        return -1;
    }

    private int detectWinDiagonals() {

        if (checkLeftToRightDiagonal(lastRow, lastColumn) >= winningNumber ||
                checkRightToLeftDiagonal(lastRow, lastColumn) >= winningNumber) {

            return playerTurn;
        }
        return -1;
    }

    // Method to check left to right diagonal from the point player has chosen
    private int checkLeftToRightDiagonal(int pivotRow, int pivotColumn) {

        int sameChipToLeftUp = 0;
        int sameChipToRightDown = 0;

        // Checking left-upwards
        if (pivotRow > 0 && pivotColumn > 0) {

            int currentRow = pivotRow - 1;
            int currentColumn = pivotColumn - 1;

            while ((currentRow >= 0 && currentColumn >= 0) && (gameState[currentRow][currentColumn] == playerTurn)) {
                sameChipToLeftUp++;
                currentRow--;
                currentColumn--;
            }
        }

        // Checking right-downwards
        if (pivotRow < numberOfRows - 1 && pivotColumn < numberOfColumns - 1) {

            int currentRow = pivotRow + 1;
            int currentColumn = pivotColumn + 1;

            while ((currentRow < numberOfRows && currentColumn < numberOfColumns) &&
                    (gameState[currentRow][currentColumn] == playerTurn)) {
                sameChipToRightDown++;
                currentRow++;
                currentColumn++;
            }
        }

        return sameChipToLeftUp + sameChipToRightDown + 1; // 1 is pivot positions count
    }

    // Method to check right to left diagonal starting from the point player has chosen
    private int checkRightToLeftDiagonal(int pivotRow, int pivotColumn) {

        int sameChipToRightUp = 0;
        int sameChipToLeftDown = 0;

        // Checking right-upwards
        if (pivotRow > 0 && pivotColumn < numberOfColumns - 1) {

            int currentRow = pivotRow - 1;
            int currentColumn = pivotColumn + 1;

            while ((currentRow >= 0 && currentColumn < numberOfColumns) &&
                    (gameState[currentRow][currentColumn] == playerTurn)) {

                sameChipToRightUp++;
                currentRow--;
                currentColumn++;
            }
        }

        // Checking left-downwards
        if (pivotRow < numberOfRows - 1 && pivotColumn > 0) {

            int currentRow = pivotRow + 1;
            int currentColumn = pivotColumn - 1;

            while ((currentRow < numberOfRows && currentColumn >= 0) &&
                    (gameState[currentRow][currentColumn] == playerTurn)) {

                sameChipToLeftDown++;
                currentRow++;
                currentColumn--;
            }
        }

        return sameChipToRightUp + sameChipToLeftDown + 1; // 1 is pivot positions count
    }

    boolean isGameFull() {

        /*
        The total number of turns made by players in a specific point is less than
        the number total possible turns means there are still empty spaces left.
         */
        return totalTurnsMade == totalPossibleTurns;
    }

    void switchTurn() {

        // Switching current players turn
        if (playerTurn == 1) {
            playerTurn = 2;
        } else {
            playerTurn = 1;
        }
    }

    public void resetGame() {

        this.gameState = new int[numberOfRows][numberOfColumns];

        this.playerTurn = 1;
        this.lastRow = -1;
        this.lastColumn = -1;

        this.totalTurnsMade = 0;
        this.totalPossibleTurns = numberOfRows * numberOfColumns;
    }


    public String toString() {

        String output = "";

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                output += gameState[i][j] + " ";
            }
            output += "\n";
        }

        return output;
    }
}