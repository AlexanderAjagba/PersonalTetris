public class Grid {
    public static void main(String[] args){
        int rows = 5;
        int columns = 5;
        String[][] grid = new String[rows][columns];
        for(int row_index = 0; row_index < rows; row_index++) {
            for(int column_index = 0; column_index < columns; column_index++){
                grid[row_index][column_index] = "O";
            }
    }
    for (String[] row : grid){
        int count = 1;
        for(String value : row) {
            if(count < 5){
                System.out.print(value);
                count++;
            }else{
                System.out.print(value+"\n");
                
            }
                
        }

    }
       

    }
    
}
