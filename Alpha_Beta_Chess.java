import javax.swing.*;
import java.util.*;

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
            // x1, y1, x2, y2, captured piece
            /*
                0   1   2   3   4   5   6   7
               --------------------------------
            0 |" "|" "|" "|" "|" "|" "|" "|" "|  0
            1 |" "|" "|" "|" "|" "|" "|" "|" "|  1
            2 |" "|" "|" "|" "|" "|" "|" "|" "|  2
            3 |" "|" "|" "|" "|" "|" "|" "|" "|  3
            4 |" "|" "|" "|" "|" "|" "|" "|" "|  4
            5 |" "|" "|" "|" "|" "|" "|" "|" "|  5
            6 |" "|" "|" "|" "|" "|" "|" "|" "|  6
            7 |" "|" "|" "|" "|" "|" "|" "|" "|  7
               --------------------------------
                0   1   2   3   4   5   6   7
             */

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
        while(!"K".equals(chessBoard[kingPositionC/8][kingPositionC%8])){kingPositionC++;} //get my King's location
        while(!"k".equals(chessBoard[kingPositionL/8][kingPositionL%8])){kingPositionL++;} //get enemy King's location

       /* JFrame f = new JFrame("Prepare For Awesome Chess!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates program when user clicks red "X" on window
        UserInterface ui = new UserInterface(); //calls UserInterface
        f.add(ui); // adds the UserInterface and adds it to the window
        f.setSize(500, 500); //window size
        f.setVisible(true);*/

        System.out.println(possibleMoves());
        makeMove(("7655 "));
        undoMove(("7655 "));

        //for 8 lines, print out array in string notation
        for (int i=0; i<8; i++){
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }

    public static void makeMove(String move){
        if(move.charAt(4) != 'P'){
            //x1, y1, x2, y2, captured piece
            //0 and 1 are starting, 2 and 3 are destination
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
        }

        else{
            //if pawn promotion
            //column1, column2, captured piece, new piece, P --> pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))] = " ";
            chessBoard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(3));
        }
    }

    public static void undoMove(String move){
        if(move.charAt(4) != 'P'){
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));
        }

        else{
            //undo pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))] = "P";
            chessBoard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(2));
        }
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
        String list = "", oldPiece;
        int r = i/8, c = i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//possible capture
                if (Character.isLowerCase(chessBoard[r - 1][c + j].charAt(0)) && i >= 16) {
                    oldPiece = chessBoard[r - 1][c + j];
                    chessBoard[r][c] = " ";
                    chessBoard[r - 1][c + j] = "P";

                    if (kingSafe()) {
                        list = list + r + c + (r - 1) + (c + j) + oldPiece;
                    }

                    chessBoard[r][c] = "P";
                    chessBoard[r - 1][c + j] = oldPiece;
                }
            } catch (Exception e) {}

            try {//promotion & capture
                if (Character.isLowerCase(chessBoard[r - 1][c + j].charAt(0)) && i < 16) {
                    String[] temp = {"Q", "R", "B", "N"};
                    for (int k = 0; k < 4; k++) {
                        oldPiece = chessBoard[r - 1][c + j];
                        chessBoard[r][c] = " ";
                        chessBoard[r - 1][c + j] = temp[k];
                        if (kingSafe()) {
                            //column1, column2, captured piece, new piece, P --> pawn promotion
                            //don't need r because already know the row (1 to 0), end of board
                            list = list + c + (c + j) + oldPiece + temp[k] + "P";
                        }
                        chessBoard[r][c] = "P";
                        chessBoard[r - 1][c + j] = oldPiece;
                    }
                }

            } catch (Exception e) {}
        }
        try {//move one up
            if (" ".equals(chessBoard[r - 1][c]) && i >= 16) {
                oldPiece = chessBoard[r - 1][c];
                chessBoard[r][c] = " ";
                chessBoard[r - 1][c] = "P";

                if (kingSafe()) {
                    list = list + r + c + (r - 1) + c + oldPiece;
                }

                chessBoard[r][c] = "P";
                chessBoard[r - 1][c] = oldPiece;
            }
        } catch (Exception e){}

        try {//promotion & no capture
            if (" ".equals(chessBoard[r - 1][c]) && i < 16) {
                String[] temp = {"Q", "R", "B", "N"};
                for (int k = 0; k < 4; k++) {
                    oldPiece = chessBoard[r - 1][c];
                    chessBoard[r][c] = " ";
                    chessBoard[r - 1][c] = temp[k];

                    if (kingSafe()) {
                        //column1, column2, captured piece, new piece, P
                        //don't need r because already know the row (1 to 0), end of board
                        list = list + c + oldPiece + temp[k] + "P";
                    }
                    chessBoard[r][c] = "P";
                    chessBoard[r - 1][c] = oldPiece;
                }
            }
        } catch (Exception e){}

        try {//move two up
            // 48 referring to the bottom two rows
            if (" ".equals(chessBoard[r - 1][c]) && " ".equals(chessBoard[r - 2][c]) && i >= 48) {
                oldPiece = chessBoard[r - 2][c];
                chessBoard[r][c] = " ";
                chessBoard[r - 2][c] = "P";

                if (kingSafe()) {
                    list = list + r + c + (r - 2) + c + oldPiece;
                }

                chessBoard[r][c] = "P";
                chessBoard[r - 2][c] = oldPiece;
            }
        } catch (Exception e){}

        return list;
    }
    //possible moves for White Rook (could be similar to Bishop, but taking different approach)
    public static String possibleR(int i){
        String list = "", oldPiece;
        int r = i/8, c = i%8;
        int temp = 1;
        for (int j=-1; j<=1; j+=2) {

            //checks a vertical line (column)
            try{
                while(" ".equals(chessBoard[r][c+temp*j])){
                    //memorize old location
                    oldPiece = chessBoard[r][c+temp*j];

                    //move from current location
                    chessBoard[r][c] = " ";

                    //set old location as Rook
                    chessBoard[r][c+temp*j] = "R";

                    if (kingSafe()) {
                        list = list+ r + c + (r+temp*j) + c + oldPiece;
                    }

                    //move to new location as Rook
                    chessBoard[r][c] = "R";
                    chessBoard[r][c+temp*j] = oldPiece;
                    temp++;
                }

                //if the enemy space is at the new position,
                if(Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))){
                    oldPiece = chessBoard[r][c+temp*j];

                    //move from current location
                    chessBoard[r][c] = " ";

                    //set old location as Rook
                    chessBoard[r + temp * j][c] = "R";

                    if (kingSafe()) {
                        list = list+ r + c + (r+temp*j) + c + oldPiece;
                    }

                    //move to new location as Rook
                    chessBoard[r][c] = "R";
                    chessBoard[r][c+temp*j] = oldPiece;
                }

            } catch(Exception e){}
            //reset temp to 1
            temp = 1;
            //checks a horizontal line
            try {
                while (" ".equals(chessBoard[r + temp * j][c])) {
                    //memorize old location
                    oldPiece = chessBoard[r + temp * j][c];

                    //move from current location
                    chessBoard[r][c] = " ";

                    //set old location as Rook
                    chessBoard[r + temp * j][c] = "R";

                    if (kingSafe()) {
                        list = list + r + c + (r+temp*j) + c + oldPiece;
                    }

                    //move to new location as Rook
                    chessBoard[r][c] = "R";
                    chessBoard[r + temp * j][c] = oldPiece;
                    temp++;
                }

                //if the enemy space is at the new position,
                if (Character.isLowerCase(chessBoard[r + temp * j][c].charAt(0))) {
                    oldPiece = chessBoard[r + temp * j][c];

                    //move from current location
                    chessBoard[r][c] = " ";

                    //set old location as Rook
                    chessBoard[r + temp * j][c] = "R";

                    if (kingSafe()) {
                        list = list + r + c + (r+temp*j) + c + oldPiece;
                    }

                    //move to new location as Rook
                    chessBoard[r][c] = "R";
                    chessBoard[r + temp * j][c] = oldPiece;
                }
            } catch (Exception e){}
            temp = 1;
        }
        return list;
    }
    //possible moves for White Knight
    public static String possibleN(int i){
        String list = "", oldPiece;
        int r = i/8, c = i%8;
        for (int j =-1; j <=1; j+=2){
            for(int k=-1; k<=1; k+=2){
                try{
                    if(Character.isLowerCase(chessBoard[r+j][c+k*2].charAt(0)) || " ".equals(chessBoard[r+j][c+k*2])){
                        oldPiece = chessBoard[r+j][c+k*2];
                        chessBoard[r][c] = " ";
                        if(kingSafe()){
                            list = list + r + c + (r+j) + (c+k*2) + oldPiece;
                        }
                        chessBoard[r][c] = "N";
                        chessBoard[r+j][c+k*2] = oldPiece;
                    }
                } catch(Exception e){}

                try{
                    if(Character.isLowerCase(chessBoard[r+j*2][c+k].charAt(0)) || " ".equals(chessBoard[r+j*2][c+k])){
                        oldPiece = chessBoard[r+j*2][c+k];
                        chessBoard[r][c] = " ";
                        if(kingSafe()){
                            list = list + r + c + (r+j*2) + (c+k) + oldPiece;
                        }
                        chessBoard[r][c] = "N";
                        chessBoard[r+j*2][c+k] = oldPiece;
                    }
                } catch(Exception e){}
            }
        }
        return list;
    }

    //possible moves for White Bishop (similar to Queen)
    public static String possibleB(int i){
        String list = "", oldPiece;
        int r = i/8, c = i%8;
        int temp = 1;

        //allows for horizontal and vertical movement in 4 directions
        // moves along x axis (-1 = left, +1 = right)
        for (int j=-1; j<=1; j+=2){
            //moves along y axis (-1 = down, +1 = up)
            for (int k=-1; k<=1; k+=2){
                try{
                    //while there's an empty space,
                    //[move queen by row + temp (starts at 1) * x direction (j)]
                    //[move queen by column + temp (starts at 1) * y direction (k)]
                    while(" ".equals(chessBoard[r+temp*j][c+temp*k])){

                        //take previous position
                        oldPiece = chessBoard[r+temp*j][c+temp*k];

                        //establish current move's position as a blank space
                        chessBoard[r][c] = " ";

                        //establishes Queen's position where the oldPiece was
                        chessBoard[r+temp*j][c+temp*k] = "B";

                        if (kingSafe()) {
                            list = list+ r + c + (r+temp*j) + (c+temp*k) + oldPiece;
                        }

                        //moves Queen to the previous blank space (made a move to the new location)
                        chessBoard[r][c] = "B";

                        //establishes oldPiece as the previous location the Queen was at
                        oldPiece = chessBoard[r+temp*j][c+temp*k] = oldPiece;

                        //increment temp's value by 1 every time until there's no empty spaces left
                        temp++;
                    }

                    //if chess space is not blank, and is lowercase (black piece)
                    if(Character.isLowerCase(chessBoard[r + temp * j][c + temp * k].charAt(0))){
                        oldPiece = chessBoard[r+temp*j][c+temp*k];
                        chessBoard[r][c] = " ";
                        chessBoard[r+temp*j][c+temp*k] = "B";
                        if (kingSafe()) {
                            list = list + r + c + (r + temp *j) + (c + temp * k) + oldPiece;
                        }

                        chessBoard[r][c] = "B";
                        oldPiece = chessBoard[r+temp*j][c+temp*k] = oldPiece;
                    }
                } catch (Exception e) {}
                //after directions from Queen are checked, temp is set back to 1 (1 space away from the Queen)
                temp = 1;
            }
        }
        return list;
    }

    //possible moves for White Queen
    public static String possibleQ(int i){
        String list = "", oldPiece;
        int r = i/8, c = i%8;
        int temp = 1;

        //allows for horizontal, vertical, and diagonal movement in 8 directions
        // moves along x axis (-1 = left, +1 = right)
        for (int j=-1; j<=1; j++){
            //moves along y axis (-1 = down, +1 = up)
            for (int k=-1; k<=1; k++){
                //eliminates unnecessary check, because no point in checking when 0,0 for Queen
                if(j!=0 || k!=0) {
                    try {
                        //while there's an empty space,
                        //[move queen by row + temp (starts at 1) * x direction (j)]
                        //[move queen by column + temp (starts at 1) * y direction (k)]
                        while (" ".equals(chessBoard[r + temp * j][c + temp * k])) {

                            //take previous position
                            oldPiece = chessBoard[r + temp * j][c + temp * k];

                            //establish current move's position as a blank space
                            chessBoard[r][c] = " ";

                            //establishes Queen's position where the oldPiece was
                            chessBoard[r + temp * j][c + temp * k] = "Q";

                            if (kingSafe()) {
                                list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece;
                            }

                            //moves Queen to the previous blank space (made a move to the new location)
                            chessBoard[r][c] = "Q";

                            //establishes oldPiece as the previous location the Queen was at
                            oldPiece = chessBoard[r + temp * j][c + temp * k] = oldPiece;

                            //increment temp's value by 1 every time until there's no empty spaces left
                            temp++;
                        }

                        //if chess space is not blank, and is lowercase (black piece)
                        if (Character.isLowerCase(chessBoard[r + temp * j][c + temp * k].charAt(0))) {
                            oldPiece = chessBoard[r + temp * j][c + temp * k];
                            chessBoard[r][c] = " ";
                            chessBoard[r + temp * j][c + temp * k] = "Q";

                            if (kingSafe()) {
                                list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece;
                            }

                            chessBoard[r][c] = "Q";
                            oldPiece = chessBoard[r + temp * j][c + temp * k] = oldPiece;
                        }
                    } catch (Exception e) {}
                    //after directions from Queen are checked, temp is set back to 1 (1 space away from the Queen)
                    temp = 1;
                }
            }
        }
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

    //checks to see if my King is safe against specific enemy piece types
    public static boolean kingSafe(){
        //enemy bishop / queen
        //modified from bishop checks
        int temp = 1;
        for (int i=-1; i<=1; i+=2) {
            //moves along y axis (-1 = down, +1 = up)
            for (int j = -1; j <= 1; j += 2) {
                try {
                    //moves in a diagonal path. If empty space, keep going.
                    while (" ".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8 + temp * j])) {
                        temp++;
                    }
                    //if enemy bishop in space
                    if ("b".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8 + temp * j]) ||
                            "q".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8 + temp * j])){
                        return false;
                    }
                } catch (Exception e) {}
                temp = 1;
            }
        }

        //enemy rook / queen
        for (int i=-1; i<=1; i+=2) {
            try {
                while (" ".equals(chessBoard[kingPositionC / 8][kingPositionC % 8 + temp * i])) {
                    temp++;
                }
                if ("r".equals(chessBoard[kingPositionC / 8][kingPositionC % 8 + temp * i]) ||
                        "q".equals(chessBoard[kingPositionC / 8][kingPositionC % 8 + temp * i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp = 1;
            try {
                while (" ".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8])) {
                    temp++;
                }
                if ("r".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8]) ||
                        "q".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp = 1;
        }
        //knight
        for(int i=-1; i<=1; i+=2){
            for(int j=-1; j<=1; j+=2){
                try{
                    //checks if Knight in position 1 row, 2 columns away
                    if("n".equals(chessBoard[kingPositionC/8+i][kingPositionC%8+j*2])){
                        return false;
                    }
                } catch (Exception e){}
                try{
                    //checks if Knight in position 2 rows, 1 column away
                    if ("n".equals(chessBoard[kingPositionC/8+i*2][kingPositionC%8+j])){
                            return false;
                        }
                    } catch (Exception e){}
                }
            }

        //pawn - as long as King is not in top 2 rows (pawns can't move backwards, so don't check for that)
        if(kingPositionC >= 16) {
            try {
                //checks diagonal upwards (left and right) positions
                if ("p".equals(chessBoard[kingPositionC / 8 - 1][kingPositionC % 8 - 1])) {
                    return false;
                }
            } catch (Exception e) {
            }

            try {
                //checks diagonal downwards (left and right) positions
                if ("p".equals(chessBoard[kingPositionC / 8 - 1][kingPositionC % 8 + 1])) {
                    return false;
                }
            } catch (Exception e) {}
        }

        //king - checks if enemy king's attack radius within movement distance
        for(int i=-1; i<=1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(i!=0 || j!=0) {
                    try {
                        //checks if Knight in position 2 rows, 1 column away
                        if ("k".equals(chessBoard[kingPositionC / 8 + i][kingPositionC % 8 + j])) {
                            return false;
                        }
                    } catch (Exception e) {}
                }
            }
        }
        return true;
    }
}


// https://www.youtube.com/watch?v=UZLnDvdeNo8&index=19&list=PLQV5mozTHmaffB0rBsD6m9VN1azgo5wXl
// Alpha-Beta Algorithm (Part 1) - Java Chess Engine Tutorial 18
// Logic Crazy Chess


//Alpha-Beta Pruning / Mini-Max algorithm explanation: http://web.cs.ucla.edu/~rosen/161/notes/alphabeta.html
