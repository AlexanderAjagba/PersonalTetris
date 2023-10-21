package PersonalTetris.src;
import java.util.Random;

public class PieceController {
    Random random = new Random();
    public char[][][] current_shape; 
    private char unique_piece;
    private int current_rotation = 0;

   
    // recall that the max spaces would be 9 spaces(adjustable depending on grid size[10 X 20])
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

    private char[][][] IShape = {
    {
        {'I','I','I'}
    },
    {
        {'I'},
        {'I'},
        {'I'}
    } 
    };
    private char [][][] LShape = {
        {
            {'O','O','L'},
            {'L','L','L'}
        },
        {
            {'L','O'},
            {'L','O'},
            {'L','L'}
            
        },
        {
            {'L','L','L'},
            {'L','O','O'}
        },
        {
            {'L','L'},
            {'O','L'},
            {'O','L'}

        }
    };
    private char [][][] Sshape = {
        {
            {'O','S','S'},
            {'S','S','O'}
        },
        {
            {'S','O'},
            {'S','S'},
            {'O','S'}
        }
    };

    private char[][][] Zshape = {
        {
            {'Z','Z','O'},
            {'O','Z','Z'}
        },
        {
            {'O','Z'},
            {'Z','Z'},
            {'Z','O'}
        }
    };
    private char[][][] RLShape = { // the other variation like L 
        {
            {'R','O','O'},
            {'R','R','R'}
        },
        {
            {'R','R'},
            {'R','O'},
            {'R','O'}
        },
        {
            {'R','R','R'},
            {'O','O','R'}
        
        },
        {
            {'O','R'},
            {'O','R'},
            {'R','R'}
        }
    };
    
    
    
    public int find_length(){
        int length = 0; // be sure to subtract by 1 
        for(char[][] rotation : this.current_shape){
            length++; 
        }
        return length;  
    }

    public void increaseRotation() {
        int new_rotation =  this.current_rotation+1;
        System.out.println(new_rotation);
        if(this.find_length()-1 < new_rotation){
            this.current_rotation = 0;
        }else{
            this.current_rotation = new_rotation;
        }
    }

    public void decreaseRotation(){
        int new_rotation = this.current_rotation-1;
        if(new_rotation < 0){
            this.current_rotation = this.find_length() - 1;
        }else{
            this.current_rotation = new_rotation;
        }
    }
    public int getrotation(){
        return this.current_rotation;
    }
    public char getUniquepiece(){
        return this.unique_piece;
    }

    public char[][][] getTShape(){
        return TShape;
    }

    public char[][][] getSquareShape(){
        return squareShape; 
    }
    public char[][][] getIShape(){
        return IShape;
    }
    public char[][][] getZShape(){
        return Zshape;
    }
    public char[][][] getLShape(){
        return LShape;
    }
    public char[][][] getSshape(){
        return Sshape;
    }
    public char[][][] getRLShape(){
        return RLShape;
    }


    public void randomize() { // picking between all my shapes 
        int selector = random.nextInt(7);   // make sure to change number after adding all pieces
        switch (selector){
            case 0: 
                this.current_shape = getTShape();
                this.unique_piece = 'T';
                this.current_rotation = 0;
                break;
            case 1: 
                this.current_shape  = getSquareShape();
                this.unique_piece = 'U';
                this.current_rotation = 0;
                break;
            case 2:
                this.current_shape  = getIShape();
                this.unique_piece = 'I';
                this.current_rotation = 0;
                break;
            case 3:
                this.current_shape  = getLShape();
                this.unique_piece = 'L';
                this.current_rotation = 0;
                break;
            case 4:
                this.current_shape  = getSshape();
                this.unique_piece = 'S';
                this.current_rotation = 0;
                break;
            case 5:
                this.current_shape  = getZShape();
                this.unique_piece = 'Z';
                this.current_rotation = 0;
                break;
            case 6:
                this.current_shape  = getRLShape();
                this.unique_piece = 'R';
                this.current_rotation = 0;
                break;
        }
    }
    
  


}
