package PersonalTetris.src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class Visual extends Application {
    interface mimic_layout{
        void mimic(Grid target_grid);
        void refresh_playarea();
        void change_grid(Grid changed_from,Grid changed_to);
        void game_over_check(Grid target_grid);
        Node getNodeFromGridPane(GridPane gridPane, int col, int row); //  supporter method for refresh_playerarea()

    }

    public void start(Stage primaryStage){ 
        Grid current_grid;
        Grid main_tetris = new Grid(15,10); 
        current_grid = main_tetris;    
        GridPane grid_layer = new GridPane();
        grid_layer.setPadding(new Insets(25,25,25,25));
        Scene scene = new Scene(grid_layer,600,500);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Mystery Tetris by Alexander Ajagba");
        VBox vertical_section = new VBox(15);
        grid_layer.add(vertical_section,20,0,10,10);
        Label current_score = new Label("Score: "+ (main_tetris.get_currentscore()));
        vertical_section.getChildren().add(current_score);
        VBox bottom_vertical_section = new VBox();
        grid_layer.add(bottom_vertical_section, 0, 18,10,1);
        Label movement_help = new Label("Movement: down arrow - down | left/right arrow - left/right");
        Label rotate_help = new Label("Q/E - Left/Right rotation");
        bottom_vertical_section.getChildren().add(movement_help);
        bottom_vertical_section.getChildren().add(rotate_help);




        mimic_layout tetrisFX = new mimic_layout() {
            public void mimic(Grid target_grid){
                char[][] target_grid_layout = target_grid.get_grid();
                for(int row_index = 0; row_index < target_grid.get_totalrows(); row_index++){
                    for(int column_index = 0; column_index < target_grid.get_totalcolumns(); column_index++){
                        Rectangle shape = new Rectangle(25,25);
                        switch(target_grid_layout[row_index][column_index]){
                            case 'O':
                                shape.setFill(Color.BLACK);
                                shape.setStroke(Color.BLACK);
                                grid_layer.add(shape,column_index,row_index); 
                                break;
                            case 'U':
                                shape.setFill(Color.GOLD);
                                shape.setStroke(Color.GOLD);
                                grid_layer.add(shape,column_index,row_index); 
                                break;
                            case 'T':
                                shape.setFill(Color.BLUEVIOLET);
                                shape.setStroke(Color.BLUEVIOLET);
                                grid_layer.add(shape,column_index, row_index); 
                                break;
                            case 'I':
                                shape.setFill(Color.DARKTURQUOISE);
                                shape.setStroke(Color.DARKTURQUOISE);
                                grid_layer.add(shape, column_index, row_index);
                                break;
                            case 'L':
                                shape.setFill(Color.DARKORANGE);
                                shape.setStroke(Color.DARKORANGE);
                                grid_layer.add(shape,column_index,row_index);
                                break;
                            case 'R':
                                shape.setFill(Color.FIREBRICK);
                                shape.setStroke(Color.FIREBRICK);
                                grid_layer.add(shape,column_index,row_index); 
                                break;
                            case 'S':
                                shape.setFill(Color.GREEN);
                                shape.setStroke(Color.GREEN);
                                grid_layer.add(shape, column_index, row_index); 
                                break;
                            case 'Z':
                                shape.setFill(Color.GREENYELLOW);
                                shape.setStroke(Color.GREENYELLOW);
                                grid_layer.add(shape,column_index, row_index); 
                                break;
                        }  
                        
                    }
                }
            }

            public void refresh_playarea(){ // target row and column needs to be adjusted if 
                
                int targetRow = 15; // Row index up to which nodes should be removed
                int targetColumn = 10; // Column index up to which nodes should be removed

        // Remove nodes up to the specified row and column
                for (int row = 0; row <= targetRow; row++) {
                    for (int col = 0; col <= targetColumn; col++) {
                        Node node = getNodeFromGridPane(grid_layer, col, row);
                        if (node != null) {
                            grid_layer.getChildren().remove(node);
                        }
                    }
                }
            }
            public void change_grid(Grid g1,Grid g2){
                g1 = g2;
            }

            public void game_over_check(Grid target_tetris){
                if(!main_tetris.game_status){  
                    System.out.println("Visual should be done now");
                    Label game_over_text = new Label("Game Over!");
                    game_over_text.setTextFill(Color.RED);
                    game_over_text.setMinHeight(50);
                    game_over_text.setMinWidth(40);
                    vertical_section.getChildren().add(game_over_text);

        
                }
            }
            public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
                for (Node node : gridPane.getChildren()) {
                    if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                        return node;
                    }
                }
                return null;
            }
        };


        
        current_grid.create_grid();
        System.out.println("-------------\n\n");
        current_grid.summon_new_tetrimono();
        System.out.println("-------------\n\n");
        tetrisFX.mimic(current_grid);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() { //key input for playarea
            public void handle(KeyEvent key){
            if(current_grid.game_status){
                switch(key.getCode()){
                    case DOWN:
                        current_grid.move_down(current_grid.active_piece);
                        System.out.println("-------------\n\n");
                        tetrisFX.refresh_playarea();
                        current_score.setText("Score: "+String.valueOf(current_grid.get_currentscore()));
                        current_grid.score_comparison();
                        tetrisFX.game_over_check(current_grid);
                        tetrisFX.mimic(current_grid);
                        break;
                    case RIGHT:
                        main_tetris.move_right(current_grid.active_piece);
                        System.out.println("-------------\n\n");
                        tetrisFX.refresh_playarea();
                        tetrisFX.mimic(current_grid);
                        break;
                    case LEFT:
                        main_tetris.move_left(current_grid.active_piece);
                        System.out.println("-------------\n\n");
                        tetrisFX.refresh_playarea();
                        tetrisFX.mimic(current_grid);
                        break;
                    case E:
                        main_tetris.rotate_right(current_grid.active_piece);
                        System.out.println("-------------\n\n");
                        tetrisFX.refresh_playarea();
                        tetrisFX.mimic(current_grid);
                        break;
                    case Q:
                        main_tetris.rotate_left(current_grid.active_piece);
                        System.out.println("-------------\n\n");
                        tetrisFX.refresh_playarea();
                        tetrisFX.mimic(current_grid);
                        break;
                    case C:
                        tetrisFX.refresh_playarea();
                        break;
                    case T:
                        System.out.println(grid_layer.getChildren().size());
                        break;
                }
            }
        }
        });
 
    }
    public static void main(String[] args) {
        launch(args);
    }
}
