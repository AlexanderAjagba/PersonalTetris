package PersonalTetris.src;

public class Grid {
    private int rows;
    private int columns;
    private char[][] grid;

    public Grid(int assign_rows, int assign_columns) {
        rows = assign_rows;
        columns = assign_columns;
        grid = new char[rows][columns];
        create_grid();

    }
    public Grid(){
        rows = 5;
        columns = 5;
        grid = new char[rows][columns];
        create_grid();
    }

    public void create_grid(){
        for(int row_index = 0; row_index < rows; row_index++) {
            for(int column_index = 0; column_index < columns; column_index++){
                this.grid[row_index][column_index] = 'O';
            }

        }
    }

    public void display(){
        for (char[] row : grid){ // this is to print out the grid layout
            int count = 1;
            for(char value : row) {
                if(count < rows){
                    System.out.print(value);
                    count++;
                }else{
                    System.out.print(value+"\n");
                    
                }
                    
            }
        }
    }

        char[][][] blocks = {{// these are the inital shape positions and each code     block 
            // square block piece
            { 'U','U'
            },
            {
                'U','U'
            },
            
        }
    
    };
       

    }
    

