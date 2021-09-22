import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {

        int numberOfRows = 6;
        int numberOfColumns = 7;
        int winningNumber = 4;

        Scanner scanner = new Scanner(System.in);

        if(args.length == 3)
        {
            numberOfRows = Integer.parseInt(args[0]);
            numberOfColumns = Integer.parseInt(args[1]);
            winningNumber = Integer.parseInt(args[2]);
        }
        else if(args.length > 0)
        {
            System.out.println("Incorrect number of program arguments");
            System.out.println("Expecting no arguments or three arguments {number or rows, number of columns, winning number}");

            System.exit(-1);
        }

        ConnectN game = new ConnectN(numberOfRows, numberOfColumns, winningNumber);

        boolean keepPlaying = true;

        while(keepPlaying)
        {
            boolean gameOn = true;
            game.resetGame();

            while(gameOn)
            {
                System.out.println(game);
                System.out.println("Player " + game.getPlayerTurn() + "\'s turn");

                // Since -1 is a valid value for wanting to exit, setting to -2
                int columnNumber = -2;

                try
                {
                    columnNumber = Integer.parseInt(scanner.next());
                }
                catch(NumberFormatException nfe)
                {
                    // Empty catch is okay here
                    // columnNumber will not be set and -2 value is handled on following lines
                }

                if(!(columnNumber == -1 || (columnNumber > 0 && columnNumber <= game.getNumberOfColumns())))
                {
                    System.out.println("Invalid input");
                }
                else if(columnNumber == -1)
                {
                    gameOn = false;
                    keepPlaying = false;
                }
                else
                {
                    // Note the column number is converted to an index here
                    // Converts from user friendly input acceptance to necessary array index
                    boolean inserted = game.insertChip(columnNumber-1);

                    if(inserted)
                    {
                        int winner = game.detectWin();

                        if(winner != -1 || game.isGameFull())
                        {
                            gameOn = false;
                            System.out.println(game);

                            if(winner == -1)
                            {
                                System.out.println("Tie game");
                            }
                            else
                            {
                                System.out.println("Player " + winner + " has won");
                            }

                            System.out.println("Enter any number to player again or -1 to exit");

                            int playAgain = -1;

                            try
                            {
                                playAgain = Integer.parseInt(scanner.next());
                            }
                            catch(NumberFormatException nfe)
                            {
                                keepPlaying = false;

                                // Redundantly handled in following logic
                                // Will exit for non-numeric input
                            }

                            if(playAgain == -1)
                            {
                                keepPlaying = false;
                            }
                        }
                        else
                        {
                            game.switchTurn();
                        }
                    }
                    else
                    {
                        System.out.println("Cannot insert a chip in that column");
                    }
                }
            }
        }
    }
}