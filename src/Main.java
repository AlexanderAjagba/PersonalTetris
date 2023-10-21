package PersonalTetris.src;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Grid tetris = new Grid(10,10); // find the right size for all the pieces to fit in to make a row filled
        tetris.change_start_pos(6, 2);
        System.out.println(tetris.get_totalcolumns());
        System.out.println(tetris.get_totalrows());
        tetris.summon_new_tetrimono();
        System.out.println("-------------\n\n");
        tetris.rotate_right(tetris.active_piece);
        while(true){
            Scanner input = new Scanner(System.in);
            System.out.println("movement: a, s, and d\nrotation:q or r");
            String response = input.next();
            if(response.equals("d")){
                tetris.move_right(tetris.active_piece);
            }if(response.equals("a")){
                tetris.move_left(tetris.active_piece);
            }
            if(response.equals("s")){
                tetris.move_down(tetris.active_piece);
            }
            if((response.equals("r"))){
                tetris.rotate_right(tetris.active_piece);
            }if(response.equals("q")){
                tetris.rotate_left(tetris.active_piece);
            }
        }
    }
}
