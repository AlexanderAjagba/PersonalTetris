package PersonalTetris.src;
import java.security.cert.CertPathParameters;
import java.sql.Time;
import java.util.ArrayList;

public class Grid {
    private int x_pos;
    private int y_pos; 
    private int rows;
    private int columns;
    private char[][] grid;
    private static int high_score = 0; // when the user want to try again
    private int current_score = 0;
    private boolean game_status;
    public int summon_x_pos = 3;
    public int summon_y_pos = 3;
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
        rows = 10;
        columns = 10;
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

    public char[][] get_grid(){
        return this.grid;
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
    // Fix-ME: add restriction with 'O' pieces!

    public void cleanslots(PieceController piece){ // very important in cleaning up the grid of active pieces before tertrismono movement
        int piece_rotation = piece.getrotation();
        int current_row = 0;
        for(char[] row : piece.current_shape[piece_rotation]){
            int current_column = 0;
            for(char slot : row){
                if(slot != 'O'){
                    this.grid[current_row+this.y_pos][current_column+this.x_pos] = 'O';
                    current_column++;
                }else{
                    current_column++;
                    continue;
            }
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
                if(slot == 'O'){ // we don't want to replace an existing piece with 'O'
                    current_column++;
                    continue;
                }
                this.grid[current_row+this.y_pos][current_column+this.x_pos] = slot;
                current_column++;
            }
            current_row++; 
        }
    }

    public void summon_new_tetrimono() {// starting from a certain location i am replacing certain blocks
        //bring tetris block to grid
        PieceController current_piece = new PieceController();
        current_piece.randomize();
        change_start_pos(summon_x_pos, summon_y_pos);
        boolean game_over_checker = false;
        while(existing_placement_collision(current_piece)){
            summon_y_pos -= 1;
            if(summon_y_pos < 0){
                game_over_checker = true;
                break;
            }
            change_start_pos(summon_x_pos,summon_y_pos);
        }
        if(!game_over_checker){
            placement(current_piece);
            active_piece = current_piece; // the active piece would gain properities of new piece
            display();
        }else{
            game_over();
        }
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
    /* for move right and move left checkers theres a serious issue that 
     * that due to 'O' pieces in tetrimono shapes, slots_occupied will be 
     * have unintended effects as the code thinks 'O' as an active piece to focus on 
     */

    public boolean move_rightchecker(PieceController tetrimono){ // y-axis dont matter in most situations
        int piece_rotation = tetrimono.getrotation();
        for(char[] row : tetrimono.current_shape[piece_rotation]){
            int total = (this.x_pos + row.length-1)+1;
            if(total > columns-1){
                return false; // border restriction moving right
            }
           
        }
        if(right_collision_detector(tetrimono)){
            return false; // pieces occupied
        }
        return true;
    
    }
    public boolean move_leftchecker(PieceController tetrimono){
        int piece_rotation = tetrimono.getrotation();
        for(char[] row : tetrimono.current_shape[piece_rotation]){ 
            int total = this.x_pos-1;
            if(total < 0){
                return false; // border restriction
                }
           
    }
    if(left_collision_detector(tetrimono)){ // pieces are occupied
        return false;
        }
    return true;

}

    public void move_left(PieceController tetrimono){
        if(move_leftchecker(tetrimono)){
            this.cleanslots(tetrimono);
            this.change_start_pos(x_pos-1, y_pos);
            this.placement(tetrimono);
            this.display();
        }else{
            this.display(); 
        }
    }

    public void rotate_left(PieceController tetrimono){
        this.cleanslots(tetrimono);
        tetrimono.decreaseRotation();
        while(existing_placement_collision(tetrimono)){
          //rotation is blocked
            tetrimono.decreaseRotation();
        }
        this.placement(tetrimono);
        display();
    }

    public void rotate_right(PieceController tetrimono){
        this.cleanslots(tetrimono);
        tetrimono.increaseRotation();
        while(existing_placement_collision(tetrimono)){
            //rotation is blocked
            display();
            tetrimono.increaseRotation();
        }
        //if no problems occur
        display();
        System.out.println();
        this.placement(tetrimono);
        display();
    }

    public Boolean move_downChecker(PieceController tetrimono){ // move down or change 'active piece' when not allowed
        int piece_rotation = tetrimono.getrotation();
        int row_number = 0;
        for(char[] row : tetrimono.current_shape[piece_rotation]){
            int total = (this.y_pos + row_number)+1;
            if(total > this.rows-1){
                return false;
            }
            row_number++; 
            
        }if(down_collision_detector(tetrimono)){
            return false;
        }return true;
        
    }


    public void move_down(PieceController tetrimono){
        if(move_downChecker(tetrimono)){
            this.cleanslots(tetrimono);
            this.change_start_pos(x_pos, y_pos+1);
            this.placement(tetrimono);
            this.display();
        }else{
            // this is when the active piece changes
            ArrayList<Integer> markers = removal_marker(this);
            remove_pieces(markers);
            freefall_checker(markers);
            score_increase(markers);
            this.summon_new_tetrimono();
        }

    }
    public boolean left_collision_detector(PieceController tetrimono){ // return true or false if theres pieces blocking next left movement
        int piece_rotation = tetrimono.getrotation(); 
        int row_number = 0; 
        for(char[] row : tetrimono.current_shape[piece_rotation]){
            char unique_piece = tetrimono.getUniquepiece();
            char starting_position = row[0];
            if(starting_position == 'O'){
                if(this.grid[row_number+this.y_pos][this.x_pos] != starting_position){
                    //no empty space
                    return true;
                }
                else{
                    //blocked piece
                    row_number++;
                    continue;
                }
            }
            if(starting_position == unique_piece){
                if(this.grid[(row_number+this.y_pos)][this.x_pos-1] != 'O'){
                    return true;
                }else{
                    row_number++;
                    continue;
                }

            }

            }
            return false;
        }
        

    public boolean right_collision_detector(PieceController tetrimono){ // return  true or false if theres pieces blocking next right movement
        int piece_rotation = tetrimono.getrotation(); 
        int row_number = 0; 
        for(char[] row : tetrimono.current_shape[piece_rotation]){
            char unique_piece = tetrimono.getUniquepiece();
            char final_postiton = row[row.length-1];
            if(final_postiton == 'O'){
                if(this.grid[row_number+this.y_pos][(this.x_pos+row.length-1)] != final_postiton){
                    // no empty space exists
                    return true;
                }
                else{
                    //active piece change is blocked
                    row_number++;
                    continue;
                }
            }
            if(final_postiton == unique_piece){
                if(this.grid[(row_number+this.y_pos)][(this.x_pos+row.length-1)+1] != 'O'){
                    return true;
                }else{
                    row_number++;
                    continue;
                }

            }

            }
            return false;
    } 
    public boolean down_collision_detector(PieceController tetrimono){ // return true or false if theres pieces blocking next down movement to maybe initiate next active piece
        int piece_rotation = tetrimono.getrotation();
        char[][] current_shape = tetrimono.current_shape[piece_rotation];
        int last_row = current_shape.length-1;
        int piece_counter = 0; 
        for(char piece : current_shape[last_row]){
            if(piece == 'O'){
                if(this.grid[this.y_pos+last_row][this.x_pos+piece_counter] != 'O'){
                    return true;
                }else{
                    piece_counter++;
                    continue;
                }
            }
            if(piece == tetrimono.getUniquepiece()){
                if(this.grid[(this.y_pos+last_row)+1][this.x_pos+piece_counter] != 'O'){
                    return true;
                }else{
                    piece_counter++;
                    continue;
                }
            }
        }
        return false;
    }

    public boolean existing_placement_collision(PieceController tetrimono){ // for many uses(summoning and rotation) if pieces occupy the 
        //assuming I havent actually place pieces yet and clean active slots(if needed)
        int piece_rotation = tetrimono.getrotation();
        int row_number = 0;
        for(char[] row : tetrimono.current_shape[piece_rotation]){
            int y_total = (this.y_pos + row_number);
            if((y_total > rows-1 || y_total < 0)){
                return true;
            }
            int column_number = 0;
            for(char slot : row){
                int x_total = (this.x_pos + column_number);
                if(x_total > columns-1 || x_total < 0){
                    return true;
                }
                if(this.grid[y_total][x_total] != 'O' && slot != 'O'){
                    return true;
                }
                else{
                    column_number++;
                    continue;
                }
            }
            row_number++;
        }
        return false;
    }
    // removal marker will return a list(s) of rows to be converted to 'O'
    // make sure to clear active pieces before running this
    public ArrayList<Integer> removal_marker(Grid current_layout){ 
        ArrayList<Integer> row_remove = new ArrayList<Integer>();
        int current_row = 0;
        for(char[] row : current_layout.grid){
            ArrayList<Character> current_row_list = new ArrayList<Character>();
            for(char piece : row){
               current_row_list.add(piece);
            }
            if(!current_row_list.contains('O')){
                row_remove.add(current_row);
                current_row++;
            }else{
                current_row++;
            }
        }
        return row_remove; // make to sure to check if it's empty 
    }

    public void remove_pieces(ArrayList<Integer> markers){ //
        if(!markers.isEmpty()){
            for(int marker_index = 0; marker_index < markers.size(); marker_index++){
                for(int column_index = 0; column_index < columns; column_index++){
                    this.grid[markers.get(marker_index)][column_index] = 'O';
                

                }
            }
        }
    }


    public void game_activation(){
        game_status = true; 
    }
    
    /*for reference I'm gonna use 2018 final round world championship final round
     * I want all pieces not 'O' above the first removal marker to fall
     * down X amount(extactly the size of marker).  
      */

    public void shift_piece_down(){ // shift pieces X amount of times depending on loaction 

    }

    public void freefall_checker(ArrayList<Integer> markers){ // temporarily clear active and check for free fall
      if(!markers.isEmpty()){
        int firstindexremoved = markers.get(0);
        int deletion_amount = markers.size();
        for(int row_checker = firstindexremoved-1;row_checker > 0; row_checker--){
            for(int column_index = 0; column_index < columns; column_index++){
                if(this.grid[row_checker][column_index] != 'O'){
                    char current_piece = this.grid[row_checker][column_index];
                    this.grid[row_checker+deletion_amount][column_index] = current_piece;
                    this.grid[row_checker][column_index] = 'O';
                    
                }
            }
        }
        score_increase(markers);
    }
    }
    public void score_comparison(){
        if(high_score < current_score){
            high_score = current_score;
        }
    }
    

    public void score_increase(ArrayList<Integer> markers){
        int lines_filled = markers.size();
        int score_given = 40 * lines_filled;
        current_score += score_given;
    }
    
    public void game_over() { // this method should be changed when converted to javafx
        System.out.println("looks like a game over(dont press anything bud...it's just a test chill)");
    }
    
};
    
