import java.util.*;//YOU NEED THIS

public class Board {
//properties of board. {you probably have different names}
  int w, h;//width and height of the maze
  boolean win=false;//win conidition.
  int [][]b;
  int [] location, finish;//location is player
  //0 is wall, 1 is path, 2 is player (my choice but for you its true for path i think)
//CONSTRUCTOR, generate from scratch. generate part is last part in code
  public Board(int x, int y){//CONSTRUCTOR AND GENERATOR
    h=x;
    w=y;
    b=new int[x][y];    
    location=new int[]{1,1};//for player, 1,1 for making maze
    //i placed it on the edge so it can eaily be cleared. {idk if youll need this}
    this.generate();//generate METHOD
    finish=new int[]{x-1,y-2};//finish is on lower right on edge
    b[finish[0]][finish[1]]=1;//finish path
  }
//methods that don't need Board class
  char[] randDir(){//shuffle an array!
    int r;
    char []s={'u','d','l','r'};
    char temp;
    for(int j=0;j<(int)((Math.random()*(4)));j++){// a random amount of times
      for(int i=3;i>0;i--){//go through the array right to left
        r=(int)((Math.random()*(i)));//switching randomly elements to the leftr of the inex
        temp=s[i];
        s[i]=s[r];
        s[r]=temp;
      }
    }
    return s;
  }
//stack methods for strings lol
  String addPair(String s,int a,int b){
    //This method is .add for stacks
    s=a+","+b+";"+s;//when you add ints and strings the output is always a string as if the ints were converted to strings
    return s;
  }
  String popPair(String s){
    s=s.substring(s.indexOf(";")+1,s.length());
    return s;
  }
  int[] lastPair(String s){
    //This Method is peek for stacks
    //outputs the last pair of stack to a {0,2} 
    int[]out=new int[2];
    String sub=s.substring(0,s.indexOf(";"));
    out[0]=Integer.parseInt(sub.substring(0,sub.indexOf(",")));
    out[1]=Integer.parseInt(sub.substring(sub.indexOf(",")+1,sub.length()));
    return out;
  }
//these need board class 
  boolean moveIsValid(char d,int distance,int look){
    //w,h,b,location//based on location, determine if move in direction d distance times is possible.
    //not sure if you need int look 
    switch(d){
        case 'u':
          //when you're going up or left, you have to worry about exiting the array 0
          if((location[0]-distance)>=0){
            return b[location[0]-distance][location[1]]!=look;
            //this is location is a coordinate pair sotred in an array. this is the same as: b[x-d][y] where x and y is the location and d is the distance
          }else{
          return false;
          }
        case 'd':
        if((location[0]+distance)<h){
          //when you go down and right you cant pass h or w bc the last index is h-1, r-1
            return b[location[0]+distance][location[1]]!=look;
          }else{
          return false;
          }
        case 'l':
        if((location[1]-distance)>=0){
            return b[location[0]][location[1]-distance]!=look;
          }else{
          return false;
          }
        case 'r':
        if((location[1]+distance)<w){
            return b[location[0]][location[1]+distance]!=look;
          }else{
          return false;
          }
        default:
          return false;//so the compiler won't get angry lol
      }
  }
  void move(char dir,int d){//only runs if moveIsValid
    //location,b
    //dir is direction you go, d is how far you go. 
    //this is needed because you can move one space as a player but have to move two spaces when you generate
    for(int m=0;m<d;m++){//repeat the following d times
      b[location[0]][location[1]]=1;//mark player location as path {not needed in your case}
      switch(dir){//update player's location
        case 'u':
          location[0]-=1;
          break;//breaks in a for loop stop the for loop, even if they are in switches or in if statements
        case 'd':
        location[0]+=1;
          break;
        case 'l':
        location[1]-=1;
          break;
        case 'r':
        location[1]+=1;
          break;
      }      
    }
  }
  public void print(){//you don't need this unless
    String out;
    int [][]maze=this.b;
    maze[this.location[0]][this.location[1]]=2;//place marker at location
    for(int j=0;j<h;j++){//for each row
      out="";
      for(int i=0;i<w;i++){//for each column
        if(maze[j][i]==0){//add a character to out. this could also be a switch statement with 'continue' instead of break. continue in a for loop skips the rest of the code in the for loop and increments
          out+="░░";//wall
        }else if(maze[j][i]==1){//path
          out+="  ";
        }else if(maze[j][i]==2){//player
          out+="❤ ";
        }else{
          out+="asdfg";//for error checking
        }
      }
      System.out.println(out);
    }
  }
  //YOU DONT NEED THIS DONT READ IT VERY LONG
  public void askmove(){
    //asks player for move, keeps asking until valid input
    //keeps asking if move isnt valid
    boolean isvalid = false;
    int count;
    Scanner inp = new Scanner(System.in);
    String s;
    do {
      System.out.println("Enter move [w,a,s,d]:");
      s = inp.nextLine();
      for(int i=0;i<s.length();i++){
        switch(s.charAt(i)){
          case 'w':
            s=s.substring(0,i)+'u'+s.substring(i+1,s.length());
            continue;
          case 'a':
            s=s.substring(0,i)+'l'+s.substring(i+1,s.length());
            continue;
          case 's':
            s=s.substring(0,i)+'d'+s.substring(i+1,s.length());
            continue;
          case 'd':
            s=s.substring(0,i)+'r'+s.substring(i+1,s.length());
            continue;
          case '0': //cheatcode
            b[location[0]][location[1]]=1;
            location[0]=finish[0];
            location[1]=finish[1];
            break;
          default:
            s=s.substring(0,i)+'#'+s.substring(i+1,s.length());
            continue;
          }
      }//translate to garbage and direction
      count = 0;
      for (int i = 0; i < s.length(); i++) {
          if (s.charAt(i) == '#') {//if garbage stop counting
            count = 0;
            break;
          }
          else if(s.charAt(i) == '#'){
            this.win=true;
            clearScreen();
            return;//cheatcode
          }
          else {
            count++;//but count consecutive correct inputs
          }
      }
      if (count !=0) {//if no break aka you counted everything, valid
        isvalid = true;
      } else {
        isvalid = false;
      }
    } while (!(isvalid));
    //valid input
    for(int i=0;i<s.length();i++)if(moveIsValid(s.charAt(i),1,0))this.move(s.charAt(i),1);
    //updates board
    //mark location after move as player '2'
    //see if player wins
    count=0;
    for(int i=0;i<location.length;i++){
      if(location[i]==finish[i]){
        count++;
      }
    }    
    if (count==2){
      this.win=true;
    }
    clearScreen();
  }
  public static void clearScreen() {//you don't need this
    //may not work on everything  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  }
//Generate!
  public void generate(){//random walk algorithm
    String stack="";//loctation history
    stack=addPair(stack,1,1);
    boolean moved=false;
    while(stack.length()>0){//while your stack is fill
      this.location=lastPair(stack);//make location the most recent square
      char []direction=randDir();//randomize which direction we walk in
      moved=false;//reset move to false
      for(char d:direction){//go through all the possible moves
          if(moveIsValid(d,2,1)){//check if two spaces from location id valid in each direction, we are looking for path squares to avoid.
            this.move(d,2);//we move two spaces for generation to ensure that there are walls in the maze
            //add new location to stack
            stack=addPair(stack, this.location[0],this.location[1]);
            moved=true;//you moved!
            break;//exit the for loop
          }
      }
      if(moved==false){//all directions are invalid 
        b[location[0]][location[1]]=1;//set current space to path
        stack=popPair(stack);//remove the square we are on
      }      
    }
    //location will be at 1,1 or your inital location at the end of this.
  }

}