package PersonalTetris.src;

public class Grid {
    private int x_pos;
    private int y_pos; 
    private int rows;
    private int columns;
    private char[][] grid;
    private boolean game_status;
    PieceController active_piece;

    public Grid(int assign_rows, int assign_columns) { /*int. a grid object with custom rows and columns. 
                                                        I need to find the best rows and columns to fit all pieces effectively  */ 
        rows = assign_rows;
        columns = assign_columns;
        grid = new char[rows][columns];
        x_pos = 0;
        y_pos = 0; 
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
    
    public int get_totalrows(){
        return rows;
    }

    public int get_totalcolumns(){
        return columns;
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
    // important for x and y movement
    public void change_start_pos(int new_x_pos,int new_y_pos){ // sets x pos and y pos to the new positions
        if(new_x_pos < 0){
            this.x_pos = 0;
        }if(new_x_pos > this.get_totalcolumns()){
            this.x_pos  = this.get_totalcolumns();
        }
        if(new_y_pos < 0) { 
            this.y_pos = 0;
        }
        if(new_y_pos > this.get_totalrows()){
            this.y_pos = this.get_totalrows();
            
        }
        else {
            this.x_pos = new_x_pos;
            this.y_pos = new_y_pos;
        }
        }

    public int getX_pos() { 
        return this.x_pos;
    }

    public int getY_pos() {
        return this.y_pos;
    }
    public void cleanslots(PieceController piece){ // very important in cleaning up the grid of active pieces before tertrismono movement
        int piece_rotation = piece.getrotation();
        int current_row = 0;
        for(char[] row : piece.current_shape[piece_rotation]){
            int current_column = 0;
            for(char slot : row){
                this.grid[current_row+this.y_pos][current_column+this.x_pos] = 'O';
                current_column++;
            }
            current_row++; 
        }
    }
    public void placement(PieceController piece) { 
        int piece_rotation = piece.getrotation();
        int current_row = 0;
        for(char[] row : piece.current_shape[piece_rotation]){
            int current_column = 0;
            for(char slot : row){
                this.grid[current_row+this.y_pos][current_column+this.x_pos] = slot;
                current_column++;
            }
            current_row++; 
        }
    }

    /* int piece_rotation = current_piece.getrotation();
        int current_row = 0;
        for(char[] row : current_piece.current_shape[piece_rotation]){
            int current_column = 0;
            for(char slot : row){
                this.grid[current_row+this.y_pos][current_column+this.x_pos] = slot;
                current_column++;
            }
            current_row++; 
        }  TO BE DELETED*/

    public void summon_new_tetrimono() {// starting from a certain location i am replacing certain blocks
        //bring tetris block to grid
        PieceController current_piece = new PieceController();
        current_piece.randomize();
        placement(current_piece);
        active_piece = current_piece; // the active piece would gain properities of new piece
        display();
    }

    public void move_right(PieceController tetrimono){ // moving the active piece 1 unit to the right
        if(move_rightchecker(tetrimono)){
            cleanslots(tetrimono);
            change_start_pos(x_pos+1, y_pos);
            placement(tetrimono);
            display();
        }else{
            display(); 
        }
    }

    public boolean move_rightchecker(PieceController tetrimono){ // y-axis dont matter in most situations
        boolean border_restricted = false;
        boolean slots_occupied = false;
        int piece_rotation = tetrimono.getrotation();
        int current_row = 0;
        for(char[] row : tetrimono.current_shape[piece_rotation]){
            int column_index = 0;
            for(char slot : row){
                int total =column_index+x_pos+1;
                if(total > columns-1){
                    border_restricted = true;
                    break;
                }
                column_index++;      
            }
            if(this.grid[current_row][row.length] != 'O') {
                slots_occupied = true;
                break;
            }
        }
        if(border_restricted == true || slots_occupied == true){
            return false;
        }else{
            return true;
        }
    }
    
    public void game_activation(){
        game_status = true; 
    }
    
    public void game_start(){
        // it will have movement implemented with all 
    }



    };
    

