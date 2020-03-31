public class Main {
  public static void main(String[] args) {
    System.out.println("Loading your maze.");
     Board maze=new Board(21,21);//adjust your size here, must be odd
  //this is top level code. you won't be coding this by the looks of it.
    System.out.println("Maze loaded.");
    maze.print();
    int moves=0;
     while(maze.win==false){//this is the game running
       maze.askmove();
       maze.print();
       System.out.println("You made "+(++moves)+" moves.");
    }    
    System.out.println("You win!");
  }
}