int cols = 200;
int rows = 200;
int w, h; 
Spot[][]grid = new Spot[cols][rows];
ArrayList<Spot> openSet = new ArrayList<Spot>();
ArrayList<Spot> closedSet = new ArrayList<Spot>();
Spot start;
Spot end;
ArrayList<Spot> path;


float heuristic(Spot a, Spot b) {
  //float dist = dist(a.getI(), a.getJ(), b.getI(), b.getJ());
  float dist = abs(a.getI()-b.getI())+abs(a.getJ()-b.getJ());
  return dist;
}

void setup() {
  size(900, 900);

  w = width/cols;
  h = height/rows;

  // creating the list of spot objects
  for (int i =0; i<cols; i++) {
    for (int j =0; j<rows; j++) {
      grid[i][j] = new Spot(i, j);
      float rand = random(0,5);
      if(rand < 2){
        grid[i][j].makeWall();
      }
      
    }
  }

  for (int i =0; i<cols; i++) {
    for (int j =0; j<rows; j++) {
      grid[i][j].addNeighbours(grid);
    }
  }


  start = grid[0][0];
  end = grid[cols-1][rows-1];
  end.unWall();
  start.unWall();
  openSet.add(start);
}

Spot current;

void draw() {
  background(0);
  if (openSet.size() >0) {
    // finding the winner, so which one has the lowest f score
    int winner = 0;
    for (int i =0; i<openSet.size(); i++) {
      if (openSet.get(i).getF() < openSet.get(winner).getF()) {
        winner = i;
      }
    }

    current = openSet.get(winner);
    if (current.equals(end)) {
      // winner

      print("Winner");
      noLoop();
    }

    closedSet.add(current);
    openSet.remove(current);

    // retrieve the neighbours
    ArrayList<Spot> neighbours = current.getNeighbours();

    for (int i=0; i< neighbours.size(); i++) {

      Spot neighbour = neighbours.get(i);
      if (!closedSet.contains(neighbour) && !neighbour.getWall()) {
        //print("Doesn't contain neighbour");
        float tempG = current.getG()+1;

        if (openSet.contains(neighbour)) {
          if (tempG < neighbour.getG()) {
            neighbour.setG(tempG);
          }
        } else {
          neighbour.setG(tempG);
          openSet.add(neighbour);
        }

        neighbour.setH(heuristic(neighbour, end));
        neighbour.setF(neighbour.getG()+neighbour.getH());
        neighbour.setPrevious(current);
      }
    }
  } else {
   noLoop();
   print("No solution");
    // no solution
  }

  for (int i =0; i< cols; i++) {
    for (int j=0; j<rows; j++) {
      if(!grid[i][j].getWall()){
      grid[i][j].show(color(255));
      }else{
         grid[i][j].show(color(0));
      }
    }
  }

  for (int i=0; i< closedSet.size(); i++) {
    closedSet.get(i).show(color(255, 0, 0));
  }
  for (int i=0; i< openSet.size(); i++) {
    openSet.get(i).show(color(0, 255, 0));
  }


  path = new ArrayList<Spot>();
  Spot temp = current;
  path.add(temp);
  while (temp.getPrevious() != null) {
    path.add(temp.getPrevious());
    temp = temp.getPrevious();
  }

  for (int i=0; i < path.size(); i++) {
    path.get(i).show(color(0, 0, 255));
  }
}


public class Spot {
  private float f, g;
  private float heuristic;
  private int i; 
  private int j; 
  private Spot previous;
  private ArrayList<Spot> neighbours;
  private boolean wall;
  public Spot(int i, int j) {
    //this.f = 0;
    //this.g = 0;
    //this.h = 0;
    wall = false;
    this.i = i; 
    this.j = j;
    this.f = 0;
    this.heuristic =0;
    this.g = 0;
    this.previous = null;
    this.neighbours = new ArrayList<Spot>();
  }

  public boolean getWall(){
    return this.wall;
  }
  public void makeWall(){
    this.wall = true;
  }
  public void unWall(){
    this.wall = false;
  }
  public void show(color col) {
    fill(col); 
    noStroke(); 
    rect(i*w, j*h, w-1, h-1);
  }

  public void addNeighbours(Spot[][]grid) {

    if (this.i < cols-1) {      
      this.neighbours.add(grid[i+1][j]);
    }
    if (this.i>0) {
      this.neighbours.add(grid[i-1][j]);
    }
    if (this.j <rows-1) {
      this.neighbours.add(grid[i][j+1]);
    }
    if (this.j>0) {
      this.neighbours.add(grid[i][j-1]);
    }

    // diagonal moves
    if(this.i >0 && this.j>0){
       this.neighbours.add(grid[i-1][j-1]);
    }

    if(this.i < cols-1 && this.j < rows -1){
      this.neighbours.add(grid[i+1][j+1]);
    }
    
   
    if(this.i < cols -1 && this.j >0){
      this.neighbours.add(grid[i+1][j-1]);
    }
    if(this.i >0 && this.j < rows -1){
      this.neighbours.add(grid[i-1][j+1]);
    }
  
    
    
  }

  public void setH(float heur) {
    this.heuristic = heur;
  }
  public void setG(float newG) {
    this.g = newG;
  }
  public float getH() {
    return this.heuristic;
  }
  public float getG() {
    return this.g;
  }
  public int getI() {
    return this.i;
  }
  public int getJ() {
    return this.j;
  }
  public ArrayList<Spot> getNeighbours() {
    return this.neighbours;
  }
  public float getF() {
    return this.f;
  }
  public void setF(float newF) {
    this.f = newF;
  }
  public void setPrevious(Spot s) {
    this.previous = s;
  }
  public Spot getPrevious() {
    return this.previous;
  }
}