
static int[] state = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
void setup(){
  size(600,600);
}

void draw(){
  int widthCount=100;
  int heightCount=100;
  
 
  for(int i=0;i<16;i++){
    fill(50);
    if(state[i]==0){
      fill(200);
    }
    rect(widthCount,heightCount,100,100);
    
    textSize(50);
    fill(0,102,153);
    text(state[i],widthCount+10,heightCount+50);
   
    if(i % 4 == 3){
      widthCount =0;
      heightCount= heightCount+100;
    }
    widthCount = widthCount+100;
  }
  
  
  //move(state);
}

void move(int[] state){
  for(int i =0; i<state.length;i++){
     if(state[i]==0){
         //if(i % 4 != 0){
         //  left(state);
         //}
         //if(i % 4 != 3){
         //  right(state);
         ///}
         if(i>3){
           up(state);
         }
         //if(i<12){
         //  down(state);
         //}
         
     }
  }

}

void up(int[] state){
  for(int i =0;i<state.length;i++){
    if(state[i]==0){
        int temp = state[i];
        state[i] = state[i - 4];
        state[i - 4] = temp;
    }
  }
}
void down(int[] state){
  for(int i =0;i<state.length;i++){
    if(state[i]==0){
        int temp = state[i];
        state[i] = state[i + 4];
        state[i +4] = temp;
    }
  }
}
void right(int[] state){
  for(int i =0;i<state.length;i++){
    if(state[i]==0){
        int temp = state[i];
        state[i] = state[i+1];
        state[i +1] = temp;
    }
  }
}
void left(int[] state){
  for(int i =0;i<state.length;i++){
    if(state[i]==0){
        int temp = state[i];
        state[i] = state[i - 1];
        state[i - 1] = temp;
    }
  }
}

public static class State{
  
  private int[] state;
  public State(int[] state){
      this.state = state;
  }
  
  
}