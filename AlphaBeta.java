import java.util.List;
public class AlphaBeta{


    public Move bestmove(int maxDepth, Board board, boolean isWhite){
        //instead of -infinity to just use int
        int bestMoveScore = Integer.MIN_VALUE; 
        Move bestMove = null; 

        for(Move move: board.getAllValidMoves(isWhite)){
            int curr = alphaBeta(board, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, isWhite);
            if (curr > bestMoveScore){
                bestMoveScore = curr; 
                bestMove = move; 
            }
        }
         return bestMove; 
    }

    public int alphaBeta(Board board, int depth, int alpha, int beta, boolean isWhite){
        if(depth == 0 || board.gameEnd(isWhite)){
            return evaluate(board);
        } 
        Board copyOfBoard;
        List<Move> moves = board.getAllValidMoves(isWhite);
        int best;
        if(isWhite){
            best = Integer.MIN_VALUE; 
        } else best = Integer.MAX_VALUE;

        for(Move move: moves){
            copyOfBoard = board.copy(); 
            copyOfBoard.movePiece(move);
            int curr = alphaBeta(copyOfBoard, depth-1, alpha, beta, !isWhite);  

            if(isWhite){
                
                if(curr>best){
                    best = curr;
                }

                if(best > alpha){
                    alpha = best;
                }

            } else{

                if(curr<best){
                    best = curr;
                }

                if(best < beta){
                    beta = best;
                }
            }
            if(alpha >= beta){
                break;
            }
        }
        return best; 

    }
    

    public int evaluate(Board board){
        int score = 0; 
        for(stone[] row: board.getBoard()){
            for(stone s: row){
                if (s == null) continue;

                if(s.getTeam()== 'w'){
                    if(s.kingStatus()){
                        score += 3;
                    } else{
                        score +=1;
                    }

                    score += (s.getValidMoves(board.boardList).size())*0.5; 
                } else{
                    if(s.kingStatus()){
                        score-=3;
                    } else{
                        score-=1; 
                    }

                    score -= (s.getValidMoves(board.boardList).size())*0.5; 

                }
            }
        } 

        return score; 

    }



}