import javax.swing.*;

public class Alpha_Beta_Chess {

    //board representation, chose multidimensional array for easy visual representation (opposed to a bitboard)
    static String chessBoard[][] = {
            /*
              p = black pawns
              r = black rook
              n = black knight
              b = black bishop
              q = black queen
              k = black king

              P = white pawn
              R = white rook
              N = white night
              B = white bishop
              Q = white queen
              K = white king
            */

            //can manipulate these values to test possible positions
            {"r","n","b","q","k","b","n","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","N","B","Q","K","B","N","R"}};

    //used for monitoring position of Capitalized King,and monitors lowercase king (white or black respectively)
    //helpful for seeing if my moves puts king in check or not.
    static int kingPositionC, kingPositionL;

    public static void main(String[] args){
       /* JFrame f = new JFrame("Prepare For Awesome Chess!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates program when user clicks red "X" on window
        UserInterface ui = new UserInterface(); //calls UserInterface
        f.add(ui); // adds the UserInterface and adds it to the window
        f.setSize(500, 500); //window size
        f.setVisible(true);*/
        System.out.println(possibleMoves());
    }



    public static String possibleMoves(){
        String list = "";

        //loop through each square and increment until all 64 squares are checked (8 ranks/rows * 8 files/columns = 64 spaces)
        for (int i=0; i<64; i++){
            //goes through [entireBoard][1-8 rows]
            switch (chessBoard[i/8][i%8]){
                //search until finding a Piece, check possible moves for specified piece at position "i" (coordinate on board)
                case"P": list += possibleP(i);
                    break;
                case"R": list += possibleR(i);
                    break;
                case"N": list += possibleN(i);
                    break;
                case"B": list += possibleB(i);
                    break;
                case"Q": list += possibleQ(i);
                    break;
                case"K": list += possibleK(i);
                    break;
            }
        }

        return list; //returns a list of possible moves    // x1,y1, x2, y2, captured_piece
    /*
        //What are possible moves?
        //check for possible moves with each Piece type

            //check to see if a coordinate has a value in it.

              //if coordinate is empty, check to see if it's a legal move for the specific Piece
                     // if legal, then bool possibleMove = true
                    // if possibleMove == true, then move piece to space.

              // if coordinate has a piece in it, check to see if this.Piece can move in that direction
                    //if yes, possible move bool = true
                    //if possibleMoveBool == true, and canMoveThatWay == true, call capturePiece method
                        // move to new position.
     */

    }

    //possible moves for White Pawn
    public static String possibleP(int i){
        String list = "";
        return list;
    }
    //possible moves for White Rook
    public static String possibleR(int i){
        String list = "";
        return list;
    }

    //possible moves for White Knight
    public static String possibleN(int i){
        String list = "";
        return list;
    }

    //possible moves for White Bishop
    public static String possibleB(int i){
        String list = "";
        return list;
    }

    //possible moves for White Queen
    public static String possibleQ(int i){
        String list = "";
        return list;
    }

    //possible moves for White King
    public static String possibleK(int i){
        String list = "", oldPiece;

        //int r = row, int c = column
        int r = i / 8, c = i % 8;

        //King can move in 8 directions, possible positions are 0-9
        for (int j=0; j<9; j++) {

            /*
            0  1  2
            3 "K" 5
            6  7  8
             */

            //0,1,2,3 are different directions, 4 being the space the King is currently in

            if (j != 4) {
                try {
                    //Context: King can't move to spaces with allied pieces.

                    //checking to see if space we're moving to has a piece is that's lowercase (White King can capture black piece)
                    //OR if the space we're moving to is an empty space
                    if (Character.isLowerCase(chessBoard[r - 1 + j / 3][c - 1 + j % 3].charAt(0)) || " ".equals(chessBoard[r - 1 + j / 3][c - 1 + j % 3])) {

                        oldPiece = chessBoard[r - 1 + j / 3][c - 1 + j % 3];

                        //King's previous position empty
                        chessBoard[r][c] = " ";

                        //King's new position has been recorded.
                        chessBoard[r - 1 + j / 3][c - 1 + j % 3] = "K";

                        int kingTemp = kingPositionC;

                        //updates new King's column position
                        kingPositionC = i + (j / 3) * 8 + j % 3 - 9;

                        if (kingSafe()) {
                            list = list + r + c + (r - 1 + j / 3) + (c - 1 + j % 3) + oldPiece;
                        }
                        chessBoard[r][c] = "K";
                        chessBoard[r - 1 + j / 3][c - 1 + j % 3] = oldPiece;
                        kingPositionC = kingTemp;
                    }
                } catch (Exception e) {}
            }
        }
        //need to add castling later.

        return list;
    }

    public static boolean kingSafe(){
        return true;
    }
}


// https://www.youtube.com/watch?v=Fy3A_BsBktU&index=9&list=PLQV5mozTHmaffB0rBsD6m9VN1azgo5wXl
// at 3:47
