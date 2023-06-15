package PersonalTetris.src;

public class Main {
    public static void main(String[] args){
        Grid tetris = new Grid(10,10); // find the right size for all the pieces to fit in to make a row filled
        tetris.change_start_pos(6, 2);
        System.out.println(tetris.get_totalcolumns());
        System.out.println(tetris.get_totalrows());
        tetris.summon_new_tetrimono();
        System.out.println("-------------\n\n");
        tetris.move_right(tetris.active_piece);
    }
}
