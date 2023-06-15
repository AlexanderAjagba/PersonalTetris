package PersonalTetris.src;
import java.util.Random;

public class PieceController {
    Random random = new Random();
    private int current_rotation;
    public char[][][] current_shape; 
   
    // recall that the max spaces would be 9 spaces(adjustable depending on grid size[10 X 20])
    char[][][] blocks = {{// unneeded data structure and will be deleted soon
        // square block piece
        { 'O','U','U','O'
        },
        {
            'O','U','U','O'
        },
        
    },
    {// I block piece
        {
            'O','I','O'
        },
        {
            'O','I','O'
        },
        {
            'O','I','O'
        }
    }
};

    private char[][][] squareShape =   
    {{ {'U','U'},
    {'U', 'U'},                  
    }};
   // randomizer

    
    private char[][][] TShape = { {// no rotations
            {'O', 'T', 'O'},
            {'T', 'T', 'T'}
        },
        {// first rotation 
            {'T', 'O'},
            {'T', 'T'},
            {'T', 'O'}
        },
        {//second rotation 
            {'T', 'T', 'T'},
            {'O', 'T', 'O'}
        },
        { // third and final rotation
            {'O', 'T'},
            {'T', 'T'},
            {'O', 'T'}
    }};

    public int find_length(){
        int length = 0; // be sure to subtract by 1 
        for(char[][] rotation : this.current_shape){
            length++; 
        }
        return length;  
    }

    public void increaseRotation() {
        int new_rotation =  this.current_rotation++;
        if(this.find_length()-1 < new_rotation){
            this.current_rotation = 0;
        }else{
            this.current_rotation = new_rotation;
        }
    }

    public void decreaseRotation(){
        int new_rotation = this.current_rotation--;
        if(new_rotation < 0){
            this.current_rotation = this.find_length() - 1;
        }else{
            this.current_rotation = new_rotation;
        }
    }
    public int getrotation(){
        return this.current_rotation;
    }

    public char[][][] getTShape(){
        return TShape;
    }

    public char[][][] getSquareShape(){
        return squareShape; 
    }


    public void randomize() { // picking between all my shapes 
        int selector = random.nextInt(2);   // make sure to change number after adding all pieces
        switch (selector){
            case 0: 
                this.current_shape = getTShape();
                break;
            case 1: 
                this.current_shape  = getSquareShape();
                break;
        }
    }
    
  


}
