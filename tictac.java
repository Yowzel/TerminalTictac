/* Gia-Huy Gonzalez
   11/26/18
   This program is just a game of tic tac toe
*/
import java.util.*;
public class tictac {
  // Main method that calls run
  public static void main(String[] args){
    run();
  }
  // Run method that combines all the methods
  public static void run(){
    // declaring variables
    boolean playagain = true;
    boolean sameName = true;
    int moves = 0;
    boolean winner = false;
    String secondPlayer = "";
    String turn = "";
    Scanner sc = new Scanner(System.in);
    // description
    System.out.println("Let's play tic tac toe");
    while(playagain == true){
      String firstPlayer = getName("first", sc);
      // repeat if the first name and second name are the same
      while(sameName == true){
        secondPlayer = getName("second", sc);
        if (firstPlayer.equals(secondPlayer)){
          System.out.println("You cannot play against yourself");
        } else {
          sameName = false;
        }
      }
      sameName = true;
      System.out.println("Wait, flipping the coin to see who goes\nfirst");
      int coin = flipCoin();
      System.out.print("Hit enter to see the result\n");
      sc.nextLine();
      String second = "";
      String first = chooseFirstPlayer(coin, firstPlayer, secondPlayer);
      if (first.equals(firstPlayer)){
        second = secondPlayer;
      } else {
        second = firstPlayer;
      }
      System.out.println("The player " + first + " will start the game");
      char[][] board = intializeBoard();
      printBoard(board);
      System.out.println(first + ", you will be \'x\'");
      System.out.println(second + ", you will be \'o\'");
      // while loop that continues until a winner is found or more than 9 moves
      while(winner == false && moves < 9){
        char win = ' ';
        boolean reachable = false;
        turn = first;
        for(int i = 0; i < 9; i++){
          if(moves>=5){
            win = isWinner(board);
            if(win == 'x'){
              System.out.println("Congratulations " + first + ", you are the winner!!!!");
              reachable = true;
            } else if(win == 'o'){
              System.out.println("Congratulations " + second + ", you are the winner!!!!");
              reachable = true;
            } else if(win == ' ' && moves > 8 || moves >8){
              System.out.println("cats game!");
              reachable = true;
            }
          }
          if(reachable == true){
            winner = true;
            break;
          }
          if(turn.equals(first)){
            doUserMove(sc, board, turn, 'x');
            printBoard(board);
            turn = second;
          } else {
            doUserMove(sc, board, turn, 'o');
            printBoard(board);
            turn = first;
          }
          moves++;
          if(moves >= 7){
            win = isWinner(board);
            if(win == 'x'){
              System.out.println("Congratulations " + first + ", you are the winner!!!!");
              reachable = true;
            } else if (win == 'o'){
              System.out.println("Congratulations " + second + ", you are the winner!!!!");
              reachable = true;
            }else if(win == ' ' && moves > 8 || moves >8){
              System.out.println("cats game!");
              reachable = true;
            }
          }
        }
      }
      //Redos the variables so it can play the game again
      playagain = true;
      sameName = true;
      moves = 0;
      winner = false;
      secondPlayer = "";
      turn = "";
      // plays again if user wants to, or stops if they don't
      boolean again = yesTo(sc);
      if(again == false){
        playagain = false;
      }
    }
    secondPlayer = " ";
  }
  // method that checks if a input is a valid name
  public static String getName(String player, Scanner sc){
    boolean valid = false;
    boolean flag = false;
    int spaceCount = 0;
    String name = "";
    // repeats until a valid name is entered
    while (valid == false || spaceCount > 0){
      spaceCount = 0;
      System.out.print("Enter the " + player + " Player's name--> ");;
      name = sc.nextLine();
      for(int i = 0; i < name.length(); i++){
        char c = name.charAt(i);
        if (c == ' '){
          spaceCount++;
        }
        if (!Character.isLetter(c) && c != ' '){
          flag = true;
        }
      }
      if (flag == true) {
        valid = false;
      } else {
        valid = true;
      }
    }
    return name;
  }
  //flipCoin method just returns a number either 0 or 1.
  public static int flipCoin(){
    // declaring random variable
    Random rand = new Random();
    int num = rand.nextInt(2);
    return num;
  }
  // This method takes in parameters and determines the first player.
  public static String chooseFirstPlayer(int num, String one, String two){
    String first = "";
    if (num == 1){
      first = one;
    } else {
      first = two;
    }
    return first;
  }
  //intializes the board
  public static char[][] intializeBoard(){
    char[][] table = new char[4][4];
    return table;
  }
  // This method prints the table
  public static void printBoard(char[][] table){
    System.out.println("-------------");
    for(int i = 0; i < 3; i++){
      System.out.print("|");
      for(int j = 0; j < 3; j++){
        // Does the correct spacing, for the table
        if(Character.isLetter(table[i][j])){
          System.out.print(" " + table[i][j] + " |");
        } else {
          System.out.print(table[i][j] + "   |");
        }
      }
      System.out.println();
      System.out.println("-------------");
    }
  }
  // takes the players move
  public static void doUserMove(Scanner sc, char[][] table, String name, char xo){
    boolean repeat = true;
    int row = 0;
    int column = 0;
    // for loop that repeats until a valid position is entered
    while(repeat == true){
      row = getPos(sc, "row", name);
      column = getPos(sc, "column", name);
      if(Character.isLetter(table[row-1][column-1])){
        System.out.println("Error, this position is already in use, try again");
      } else {
        repeat = false;
      }
    }
    table[row-1][column-1] = xo;
  }
  // This method, takes a parameter, and returns a number
  public static int getPos(Scanner sc, String rowcolum, String name){
    boolean repeat = true;
    int num = 0;
    while(repeat == true){
      System.out.print(name + " enter the " + rowcolum + "--> ");
      while(!sc.hasNextInt()){
        System.out.print(name + " enter the " + rowcolum + "--> ");
        sc.next();
      }
      num = sc.nextInt();
      sc.nextLine();
      // checks if the number is valid, otherwise it repeats
      if(num == 1 || num == 2 || num == 3){
        repeat = false;
      } else {
        continue;
      }
    }
    repeat = true;
    return num;
  }
  // goes through each row, column and diagonal to see if a user has won
  public static char isWinner(char[][]table){
    int xcount=0;
    int ocount=0;
    // checks for horizontal position
    if(table[0][0] == 'x' && table[1][1] == 'x' && table[2][2] == 'x'){
      return 'x';
    }
    if(table[0][0] == 'o' && table[1][1] == 'o' && table[2][2] == 'o'){
      return 'o';
    }
    if(table[0][2] == 'x' && table[1][1] == 'x' && table[2][0] == 'x'){
      return 'x';
    }
    if(table[0][2] == 'o' && table[1][1] == 'o' && table[2][0] == 'o'){
      return 'o';
    }
    // checks for row positions
    for(int i = 0; i < 3; i++){
      for(int j = 0; j <3; j++){
        if(table[i][j] == 'x'){
          xcount++;
        }
        if(table[i][j] == 'o'){
          ocount++;
        }
      }
      if(xcount == 3){
        return 'x';
      }
      if(ocount == 3){
        return 'o';
      }
      xcount = 0;
      ocount = 0;
    }
    // check for column positions
    for(int i = 0; i < 3; i++){
      for(int j = 0; j <3; j++){
        if(table[j][i] == 'x'){
          xcount++;
        }
        if(table[j][i] == 'o'){
          ocount++;
        }
      }
      if(xcount == 3){
        return 'x';
      }
      if(ocount == 3){
        return 'o';
      }
      xcount = 0;
      ocount = 0;
    }
    return ' ';
  }
  // gets users answer to play against
  public static boolean yesTo(Scanner sc){
    boolean repeat = true;
    boolean torf = false;
    String answer ="";
    // repeats till a valid input
    while(repeat == true){
      System.out.print("Want to play again? ");
      answer = sc.nextLine();
      if(answer.equals("y")){
        repeat = false;
        torf = true;
      } else if (answer.equals("n")){
        repeat = false;
        torf = false;
      } else {
        System.out.println("Please answer y or n.");
      }
    }
    // returns true or false
    if(answer == "y"){
      return torf;
    } else{
      return torf;
    }
  }
}
