
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameMaster {
    final private Board game;
    final private Scanner scanner;



    public static void main(String[] args) {
        
        GameMaster master = new GameMaster();
        // System.out.println("Welcome to a lovely game of checkers! Reminder that if you want you are able to kill an enemy, \nput the enemy's row and column to kill it, not where you think you will land. \nWhite will begin. Make your move");
        // master.play();
        master.alphaBetaVsAlphaBeta(2);

    }

    public GameMaster(){
        game = new Board();
        scanner = new Scanner(System.in); 
    }

    public void alphaBetaVsAlphaBeta(int maxDepth){
        AlphaBeta ab = new AlphaBeta();
        boolean isWhite = true; 

        while(!game.gameEnd(isWhite)){
            game.printBoard();
            
            Move bestMove = ab.bestmove(maxDepth, game, isWhite);
            if(bestMove == null){
                if(isWhite){
                    System.out.println("White has no valid moves, game over!");
                    break;
                } else{
                    System.out.println("Black has no valid moves, game over!");
                    break;
                }
                
            }
            System.out.println("Best Move is, " + Arrays.toString(bestMove.stone.getPosition()) + " to " +  Arrays.toString(bestMove.getMove()));
System.out.println();
            game.movePiece(bestMove);
            if(isWhite){
                System.out.println("White just played");
            } else{
                System.out.println("Black just played");

            }
            isWhite = !isWhite; 

        }

        System.out.println("Done");


    }

    private void play() {
        char currentTeam = 'w';
        while(!checkIfGameEnded('w')&&!checkIfGameEnded('b')){
            if(currentTeam == 'w'){
                playWhite();
            } else{
                playBlack();
            }

            
            currentTeam = currentTeam == 'w' ? 'b':'w'; 
        }

        System.out.println("Game over, " + (currentTeam =='w' ? "Black" : "White") + " has won!");
        scanner.close();
    }   

    public void handleMoves(char team){
        while (true) {
            System.out.println("Choose a starting piece, row and column");
            int x;
            int y; 
            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input, choose a row and column");
                scanner.nextLine();
                continue; 
            }
            

            stone piece = this.game.getStone(x, y);

            if (piece == null) {
                System.out.println("That's empty! Try again.");
                continue;
            }
            if(piece.getTeam() != team){
                System.out.println("That's the wrong team! Try again.");
                continue;
            }

            if(piece.getValidMoves(game.boardList).isEmpty()){
                System.out.println("No Moves Available. Pick again.");
                continue;
            }

            

            System.out.println("Choose a position to move to, row and column:");
            int newX;
            int newY;
            try{
                newX = scanner.nextInt();
                newY = scanner.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Incorrect input, you will restart");
                scanner.nextLine();
                continue; 
            }
            
             
            int[] moveTo = {newX, newY};
            Move theMove = new Move(piece, moveTo);

            if (game.checkIfValid(piece, moveTo)) {
                game.movePiece(theMove);
                break;  // Exit loop after a successful move
            } else {
                System.out.println("Invalid move! Please re-enter the piece position.");
            }
        }
        //add logic to print a w* or a b* if its a king. 

    }

    public void playWhite(){
        System.out.println("Board:");
        this.game.printBoard();
        
        System.out.println("Please choose a 'w' based on row column coordinates. Type the row, press enter, type the column, then press enter");
        handleMoves('w');
    }
    public void playBlack(){
        System.out.println("Board:");
        this.game.printBoard();

        System.out.println("Please choose a 'b' based on row column coordinates. Type the row, press enter, type the column, then press enter");
        handleMoves('b');
    }
    public boolean checkIfGameEnded(char team){
        for(stone[] row: this.game.getBoard()){
            for(stone element: row){
                if(element != null){
                if(element.getTeam() == team){
                    if(!element.getValidMoves(game.boardList).isEmpty()){
                        return false; 
                    }
                }
            }
            }
        }
        return true;
    }
}
