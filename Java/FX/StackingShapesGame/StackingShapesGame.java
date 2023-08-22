import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.*;
import java.util.Random;
import javafx.animation.*;
import javafx.concurrent.*;

import java.util.*;

class Point {
    public int x, y; // Coordinates of the point
    public int type; // Type of the point

    public Point(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}

class BoardLine {
    public boolean cleared;
    public int y;
    public int pointSize; // Size of points %
    public List<Point> points;

    public BoardLine(int y) {
        this.cleared = true;
        this.y = y;
        this.points = new ArrayList<Point>();
        this.pointSize = 100;
    }
}

class Shape {
    /**
     * Contains the type of shape (an integer from 1 to 7)
     * 
     *  * 1 -- L 
     * 2 -- l 
     * 3 -- T 
     * 4 -- S 
     * 5 -- Z 
     * 6 -- J 
     * 7 -- O
     */
    
    int [][][] rotate_mod;
    Point[] points;
    int points_count = 0;
    int rotation_pos = 0;

    public Shape() {
        this.rotation_pos = 0;

//        createPoints();        
    }

    public void moveDown() {
        for (int i = 0; i < points_count; i++) {
            points[i].y++;
        }
    }

    public void moveRight() {
        for (int i = 0; i < points_count; i++) {
            points[i].x++;
        }
    }

    public void moveLeft() {
        for (int i = 0; i < points_count; i++) {
            points[i].x--;
        }
    }

    public int getRotatedX(int point) {
        if (rotate_mod == null) return points[point].x;
        if ((points_count == 0) || (point < 0) || (point >= points_count)) return 0;
        return points[point].x + rotate_mod[rotation_pos][0][point];
    }

    public int getRotatedY(int point) {
        if (rotate_mod == null) return points[point].y;
        if ((points_count == 0) || (point < 0) || (point >= points_count)) return 0;
        return points[point].y + rotate_mod[rotation_pos][1][point];
    }

    public void rotate() {
        if (rotate_mod == null) return;
        for(int i = 0; i < points_count; i++) { 
            points[i].x += rotate_mod[rotation_pos][0][i];
            points[i].y += rotate_mod[rotation_pos][1][i];
        }
        if(rotation_pos < 3) rotation_pos++; else rotation_pos = 0;
    }
}

class Shape_L extends Shape {

    public Shape_L() {
        super();
        
        rotate_mod = new int [][][]   {{{1, 0, -1, 0},{-1, 0, 1, 2}}, 
                                                            {{1, 0, -1, -2},{1, 0, -1, 0}},
                                                            {{-1, 0, 1, 0},{1, 0, -1, -2}},
                                                            {{-1, 0, 1, 2},{-1, 0, 1, 0}}};

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(3, 1, 1);
        points[1] = new Point(4, 1, 1);
        points[2] = new Point(5, 1, 1);
        points[3] = new Point(5, 0, 1);
    }
}

class Shape_I extends Shape {

    public Shape_I() {
        super();
        
        rotate_mod = new int [][][]   {{{2, 1, 0, -1},{-1, 0, 1, 2}}, 
                                                            {{1, 0, -1, -2},{2, 1, 0, -1}},
                                                            {{-2, -1, 0, 1},{1, 0, -1, -2}},
                                                            {{-1, 0, 1, 2},{-2, -1, 0, 1}}};

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(3, 0, 2);
        points[1] = new Point(4, 0, 2);
        points[2] = new Point(5, 0, 2);
        points[3] = new Point(6, 0, 2);
    }
}

class Shape_T extends Shape {

    public Shape_T() {
        super();
        
        rotate_mod = new int [][][]   {{{1, 0, -1, -1},{-1, 0, 1, -1}}, 
                                                            {{1, 0, -1, 1},{1, 0, -1, -1}},
                                                            {{-1, 0, 1, 1},{1, 0, -1, 1}},
                                                            {{-1, 0, 1, -1},{-1, 0, 1, 1}}};

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(3, 0, 3);
        points[1] = new Point(4, 0, 3);
        points[2] = new Point(5, 0, 3);
        points[3] = new Point(4, 1, 3);
    }
}

class Shape_S extends Shape {

    public Shape_S() {
        super();
        
        rotate_mod = new int [][][]   {{{1, 0, 1, 0},{-1, 0, 1, 2}}, 
                                      {{1, 0, -1, -2},{1, 0, 1, 0}},
                                      {{-1, 0, -1, 0},{1, 0, -1, -2}},
                                      {{-1, 0, 1, 2},{-1, 0, -1, 0}}};

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(3, 1, 4);
        points[1] = new Point(4, 1, 4);
        points[2] = new Point(4, 0, 4);
        points[3] = new Point(5, 0, 4);
    }
}

class Shape_Z extends Shape {

    public Shape_Z() {
        super();
        
        rotate_mod = new int [][][]   {{{2, 1, 0, -1},{0, 1, 0, 1}}, 
                                       {{0, -1, 0, -1},{2, 1, 0, -1}},
                                       {{-2, -1, 0, 1},{0, -1, 0, -1}},
                                       {{0, 1, 0, 1},{-2, -1, 0, 1}}};

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(3, 0, 5);
        points[1] = new Point(4, 0, 5);
        points[2] = new Point(4, 1, 5);
        points[3] = new Point(5, 1, 5);
    }
}

class Shape_J extends Shape {

    public Shape_J() {
        super();
        
        rotate_mod = new int [][][]   {{{2, 1, 0, -1},{0, -1, 0, 1}}, 
                                       {{0, 1, 0, -1},{2, 1, 0, -1}},
                                       {{-2, -1, 0, 1},{0, 1, 0, -1}},
                                       {{0, -1, 0, 1},{-2, -1, 0, 1}}};

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(3, 0, 6);
        points[1] = new Point(3, 1, 6);
        points[2] = new Point(4, 1, 6);
        points[3] = new Point(5, 1, 6);
    }
}

class Shape_O extends Shape {

    public Shape_O() {
        super();

        points_count = 4;
        points = new Point[points_count];
        
        points[0] = new Point(4, 1, 7);
        points[1] = new Point(4, 0, 7);
        points[2] = new Point(5, 1, 7);
        points[3] = new Point(5, 0, 7);
    }
}

class GameBoard {
    private WritableImage fonImage;
    private WritableImage fieldImage;
    private PixelWriter fonWriter;
    private PixelWriter fieldWriter;
    
    private Random rand;
    private Shape shape;

    private List<BoardLine> lines;

    private int pointWidth = 30;
    private int width = 10;
    private int height = 22;
    
    private int pointShadeWidth;
    private boolean shapeCreated = false;
    private boolean shapeNeedCreated = true;
    
    public GameBoard(int w, int h, int pw, StackPane pane){
        this.width = w;
        this.height = h;
        this.pointWidth = pw;
        this.pointShadeWidth = (pw * 20) / 100; // 20 %
        
        rand = new Random();
         
        lines = new ArrayList<BoardLine>();

        fonImage = new WritableImage(pointWidth * width, pointWidth * height);
        fieldImage = new WritableImage(pointWidth * width, pointWidth * height);
        fonWriter = fonImage.getPixelWriter();
        fieldWriter = fieldImage.getPixelWriter();
        
        pane.getChildren().add(new ImageView(fonImage));
        pane.getChildren().add(new ImageView(fieldImage));
        
        drawFon();
        drawField();
    }
    
    public void drawFon(){
        for(int y = 0; y < height; y++) { 
            for(int x = 0; x < width; x++) { 
                for(int point_y = 0; point_y < pointWidth; point_y++) { 
                    for(int point_x = 0; point_x < pointWidth; point_x++) { 
                        if ((point_x < 1) || (point_x > pointWidth - 2) || (point_y < 1) || (point_y > pointWidth - 2)) {
                            fonWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), Color.BLACK);
                        }else{
                            if ((point_x > 4) && (point_x < 25) && (point_y > 4) && (point_y < 25)) {
                                fonWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), Color.rgb(80, 80, 80));
                            }else{
                                if ((pointWidth - point_x) > (point_y)) {
                                    fonWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), Color.rgb(70, 70, 70));
                                }else{
                                    fonWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), Color.rgb(60, 60, 60));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawLine(BoardLine line){
        int x;
        int y;

        int shadeWidth = (pointShadeWidth * line.pointSize) / 100;

        int mainX1 = shadeWidth;
        int mainX2 = pointWidth - shadeWidth;
        int mainY1 = shadeWidth;
        int mainY2 = pointWidth - shadeWidth;
        // pointSize 100% clearX1 = 0
        // pointSize 0% clearX1 = pointWidth/2

        int pw = (pointWidth * line.pointSize) / 100;
        int cw = (pointWidth - pw) / 2;
        
        int clearX1 = 1 + cw;
        int clearX2 = pointWidth - cw - 1;
        int clearY1 = 1 + cw;
        int clearY2 = pointWidth - cw - 1;
        
        Color mainColor = Color.rgb(0, 120, 120);
        Color topShadeColor = Color.rgb(0, 120, 120);
        Color bottomShadeColor = Color.rgb(0, 120, 120);

        line.cleared = false;
        for (Point point : line.points) {
            x = point.x;
            y = point.y;
            switch (point.type) {
                case 1 : 
                    mainColor = Color.rgb(0, 130, 130); 
                    topShadeColor = Color.rgb(0, 120, 120); 
                    bottomShadeColor = Color.rgb(0, 100, 100); 
                    break;
                case 2 : 
                    mainColor = Color.rgb(0, 180, 0); 
                    topShadeColor = Color.rgb(0, 170, 0); 
                    bottomShadeColor = Color.rgb(0, 150, 0); 
                    break;
                case 3 : 
                    mainColor = Color.rgb(180, 180, 0); 
                    topShadeColor = Color.rgb(170, 170, 0); 
                    bottomShadeColor = Color.rgb(150, 150, 0); 
                    break;
                case 4 : 
                    mainColor = Color.rgb(30, 30, 200); 
                    topShadeColor = Color.rgb(30, 30, 190); 
                    bottomShadeColor = Color.rgb(30, 30, 170); 
                    break;
                case 5 : 
                    mainColor = Color.rgb(180, 0, 180); 
                    topShadeColor = Color.rgb(170, 0, 170); 
                    bottomShadeColor = Color.rgb(150, 0, 150); 
                    break;
                case 6 : 
                    mainColor = Color.rgb(250, 125, 0); 
                    topShadeColor = Color.rgb(240, 115, 0); 
                    bottomShadeColor = Color.rgb(220, 95, 0); 
                    break;
                case 7 : 
                    mainColor = Color.rgb(180, 0, 0); 
                    topShadeColor = Color.rgb(170, 0, 0); 
                    bottomShadeColor = Color.rgb(150, 0, 0); 
                    break;
            }
            for(int point_y = clearY1; point_y < clearY2; point_y++) { 
                for(int point_x = clearX1; point_x < clearX2; point_x++) { 
                    if ((point_x >= mainX1) && (point_x < mainX2) && (point_y >= mainY1) && (point_y < mainY2)) {
                        fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), mainColor);
                    }else{
                        if ((pointWidth - point_x) > (point_y)) {
                            fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), topShadeColor);
                        }else{
                            fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), bottomShadeColor);
                        }
                    }
                }
            }
        }
    }

    public void drawField(){
//        int x;
//        int y;
//        Color mainColor = Color.rgb(0, 120, 120);
//        Color topShadeColor = Color.rgb(0, 120, 120);
//        Color bottomShadeColor = Color.rgb(0, 120, 120);

        for (BoardLine line : lines) {
            drawLine(line);
        }
    }

/*
    public void drawField(){
        int x;
        int y;
        Color mainColor = Color.rgb(0, 120, 120);
        Color topShadeColor = Color.rgb(0, 120, 120);
        Color bottomShadeColor = Color.rgb(0, 120, 120);

        for (BoardLine line : lines) {
            line.cleared = false;
            for (Point point : line.points) {
                x = point.x;
                y = point.y;
                switch (point.type) {
                    case 1 : 
                        mainColor = Color.rgb(0, 130, 130); 
                        topShadeColor = Color.rgb(0, 120, 120); 
                        bottomShadeColor = Color.rgb(0, 100, 100); 
                        break;
                    case 2 : 
                        mainColor = Color.rgb(0, 180, 0); 
                        topShadeColor = Color.rgb(0, 170, 0); 
                        bottomShadeColor = Color.rgb(0, 150, 0); 
                        break;
                    case 3 : 
                        mainColor = Color.rgb(180, 180, 0); 
                        topShadeColor = Color.rgb(170, 170, 0); 
                        bottomShadeColor = Color.rgb(150, 150, 0); 
                        break;
                    case 7 : 
                        mainColor = Color.rgb(180, 0, 0); 
                        topShadeColor = Color.rgb(170, 0, 0); 
                        bottomShadeColor = Color.rgb(150, 0, 0); 
                        break;
                }
                for(int point_y = 1; point_y < pointWidth - 1; point_y++) { 
                    for(int point_x = 1; point_x < pointWidth - 1; point_x++) { 
                        if ((point_x > 4) && (point_x < 25) && (point_y > 4) && (point_y < 25)) {
                            fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), mainColor);
                        }else{
                            if ((pointWidth - point_x) > (point_y)) {
                                fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), topShadeColor);
                            }else{
                                fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), bottomShadeColor);
                            }
                        }
                    }
                }
            }
        }
    }
*/
    
    public void createShape(){
        if (!shapeNeedCreated) return;
        int type = rand.nextInt(7);
        switch (type){
            case 0: shape = new Shape_L(); break;
            case 1: shape = new Shape_I(); break;
            case 2: shape = new Shape_T(); break;
            case 3: shape = new Shape_S(); break;
            case 4: shape = new Shape_Z(); break;
            case 5: shape = new Shape_J(); break;
            case 6: shape = new Shape_O(); break;
        }
        shapeCreated = true;
        shapeNeedCreated = false;
        drawShape();
    }

    public void drawShape(){
        int x;
        int y;

        int mainX1 = pointShadeWidth;
        int mainX2 = pointWidth - pointShadeWidth;
        int mainY1 = pointShadeWidth;
        int mainY2 = pointWidth - pointShadeWidth;
        
        int clearX1 = 1;
        int clearX2 = pointWidth - 1;
        int clearY1 = 1;
        int clearY2 = pointWidth - 1;

        Color mainColor = Color.rgb(0, 120, 120);
        Color topShadeColor = Color.rgb(0, 120, 120);
        Color bottomShadeColor = Color.rgb(0, 100, 100);
        
        for(int count = 0; count < shape.points_count; count++) { 
            x = shape.points[count].x;
            y = shape.points[count].y;
            switch (shape.points[count].type) {
                case 1 : 
                    mainColor = Color.rgb(0, 130, 130); 
                    topShadeColor = Color.rgb(0, 120, 120); 
                    bottomShadeColor = Color.rgb(0, 100, 100); 
                    break;
                case 2 : 
                    mainColor = Color.rgb(0, 180, 0); 
                    topShadeColor = Color.rgb(0, 170, 0); 
                    bottomShadeColor = Color.rgb(0, 150, 0); 
                    break;
                case 3 : 
                    mainColor = Color.rgb(180, 180, 0); 
                    topShadeColor = Color.rgb(170, 170, 0); 
                    bottomShadeColor = Color.rgb(150, 150, 0); 
                    break;
                case 4 : 
                    mainColor = Color.rgb(30, 30, 200); 
                    topShadeColor = Color.rgb(30, 30, 190); 
                    bottomShadeColor = Color.rgb(30, 30, 170); 
                    break;
                case 5 : 
                    mainColor = Color.rgb(180, 0, 180); 
                    topShadeColor = Color.rgb(170, 0, 170); 
                    bottomShadeColor = Color.rgb(150, 0, 150); 
                    break;
                case 6 : 
                    mainColor = Color.rgb(250, 125, 0); 
                    topShadeColor = Color.rgb(240, 115, 0); 
                    bottomShadeColor = Color.rgb(220, 95, 0); 
                    break;
                case 7 : 
                    mainColor = Color.rgb(180, 0, 0); 
                    topShadeColor = Color.rgb(170, 0, 0); 
                    bottomShadeColor = Color.rgb(150, 0, 0); 
                    break;
            }
            for(int point_y = clearY1; point_y < clearY2; point_y++) { 
                for(int point_x = clearX1; point_x < clearX2; point_x++) { 
                    if ((point_x >= mainX1) && (point_x < mainX2) && (point_y >= mainY1) && (point_y < mainY2)) {
                        fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), mainColor);
                    }else{
                        if ((pointWidth - point_x) > (point_y)) {
                            fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), topShadeColor);
                        }else{
                            fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), bottomShadeColor);
                        }
                    }
                }
            }
        }
    }

    public void clearShape(){
        int x;
        int y;
        
        for(int count = 0; count < shape.points_count; count++) { 
            x = shape.points[count].x;
            y = shape.points[count].y;
            for(int point_y = 1; point_y < pointWidth - 1; point_y++) { 
                for(int point_x = 1; point_x < pointWidth - 1; point_x++) { 
                    fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), Color.TRANSPARENT);
                }
            }
        }
    }

    public void shapeMoveRight(){
        int x;
        if (!shapeCreated) return;
        for (int i = 0; i < shape.points_count; i++) {
            x = shape.points[i].x + 1;
            if (x >= width) return;
            for (BoardLine line : lines) {
                if (shape.points[i].y == line.y) {
                    for (Point point : line.points) {
                        if (x == point.x) return;
                    }
                }
            }
        }

        clearShape();
        shape.moveRight();
        drawShape();
    }

    public void shapeMoveLeft(){
        int x;
        if (!shapeCreated) return;
        for (int i = 0; i < shape.points_count; i++) {
            if (shape.points[i].x == 0) return;
            x = shape.points[i].x - 1;
            for (BoardLine line : lines) {
                if (shape.points[i].y == line.y) {
                    for (Point point : line.points) {
                        if (x == point.x) return;
                    }
                }
            }
        }
        clearShape();
        shape.moveLeft();
        drawShape();
    }

    public void shapeMoveDown(){
        int y;
        if (!shapeCreated) return;
        for (int i = 0; i < shape.points_count; i++) {
            y = shape.points[i].y + 1;
            if (y >= height){
                shapeToBoard();
                return;
            }
            for (BoardLine line : lines) {
                if (y == line.y) {
                    for (Point point : line.points) {
                        if (shape.points[i].x == point.x) {
                            shapeToBoard();
                            return;
                        }
                    }
                }
            }
        }
        clearShape();
        shape.moveDown();
        drawShape();
    }
    
    public void shapeRotate(){

        int x;
        int y;

        if (!shapeCreated) return;
        for (int i = 0; i < shape.points_count; i++) {
            x = shape.getRotatedX(i);// + shape.rotate_mod[shape.rotation_pos][0][i];
            y = shape.getRotatedY(i);// + shape.rotate_mod[shape.rotation_pos][1][i];
            if ((x < 0) || (x >= width) || (y < 0) || (y >= height)) return;
            for (BoardLine line : lines) {
                if (y == line.y) {
                    for (Point point : line.points) {
                        if (x == point.x) return;
                    }
                }
            }
        }

        clearShape();
        shape.rotate();
        drawShape();
    }

    public void clearLine(BoardLine line){
        int x;
        int y;
        
        if (line.cleared) return;
        
        for (Point point : line.points) {
            x = point.x;
            y = point.y;
            for(int point_y = 1; point_y < pointWidth - 1; point_y++) { 
                for(int point_x = 1; point_x < pointWidth - 1; point_x++) { 
                    fieldWriter.setColor(point_x + (x * pointWidth), point_y + (y * pointWidth), Color.TRANSPARENT);
                }
            }
        }
    }

    public void shapeToBoard(){
        int fullLines = 0; 
        boolean lineExist = false;
        
        for (int i = 0; i < shape.points_count; i++) {
            lineExist = false;
            for (BoardLine line : lines) {
                if (line.y == shape.points[i].y) {
                    lineExist = true;
                    line.points.add(shape.points[i]);
                    line.cleared = false;
                    break;
                }
            }
            if (!lineExist) {
                BoardLine line = new BoardLine(shape.points[i].y);
                line.points.add(shape.points[i]);
                line.cleared = false;
                lines.add(line);
            }
        }
        
        for (BoardLine line : lines) {
            if (line.points.size() == this.width) fullLines++;
        }
        
        if (fullLines == 0) shapeNeedCreated = true;

        /*
        Iterator itr = lines.iterator();
        while (itr.hasNext()) {
            BoardLine line = (BoardLine)itr.next();
            if (line.points.size() == this.width) {
                line_y = line.y;
                clearLine(line);
                itr.remove();
                for (BoardLine line_j : lines) {
                    if (line_j.y < line_y) {
                        clearLine(line_j);
                        line_j.y++;
                        for (Point point : line_j.points) {
                            point.y++;
                        }
                    }
                }
            }        
        }
        drawField();
*/        
        shapeCreated = false;
    }
    
    public void resizeFullLines(){
        int line_y;

        Iterator itr = lines.iterator();
        while (itr.hasNext()) {
            BoardLine line = (BoardLine)itr.next();
            if (line.points.size() == this.width) {
                if (line.pointSize <= 0) {
                    line_y = line.y;
//                    clearLine(line);
                    itr.remove();
                    for (BoardLine line_j : lines) {
                        if (line_j.y < line_y) {
                            clearLine(line_j);
                            line_j.y++;
                            for (Point point : line_j.points) {
                                point.y++;
                            }
                        }
                    }
                    drawField();
                    shapeNeedCreated = true;
                }else{
                    clearLine(line);
                    line.pointSize -= 5;
                    drawLine(line);
                }
            }        
        }
    }

}

public class StackingShapesGame extends Application {

    boolean work_on;
    long shapeTimePoint;
    long boardTimePoint;

    private Group cellGroup;
    private StackPane stackPane;
    private BorderPane borderPane, pauseCenter, gameOverCenter;
    private HBox rootHBox;
    private Scene scene;
    
    GameBoard gameBoard;
    
    Ball ball;
        
    public static void main(String[] args) {
        launch(args);
    }

    // планировка
    public void createScene() {
    
        // Seperate layouts from the top to the bottom
        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
//        borderPane.getStyleClass().add("background");
//        borderPane.setTop(vboxTop);
//        borderPane.setBottom(vboxBottom);

        // Grid setup
//        tetrisGrid = new GridPane();
//        tetrisGrid.getStyleClass().add("grid");
//        tetrisGrid.getStyleClass().add("background");

//        for (int i = 0; i < Board.WIDTH; i++) {
//        for (int i = 0; i < 10; i++) {
//            tetrisGrid.getColumnConstraints().add(new ColumnConstraints(POINT_WIDTH));
//        }

//        for (int i = 0; i < Board.HEIGHT; i++) {
//        for (int i = 0; i < 20; i++) {
//            tetrisGrid.getRowConstraints().add(new RowConstraints(POINT_WIDTH));
//        }
        

/*      
*/
//        ImageView fonView = new ImageView(fonImage); 
//        ImageView fieldView = new ImageView(fieldImage); 
        
//        writer = fonImage.getPixelWriter();
        // Allows to overlays layouts for pausing the game
        stackPane = new StackPane();
//        stackPane.getChildren().add(fieldView);
//        stackPane = new StackPane(tetrisGrid);
//        stackPane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
 //       stackPane.getChildren().add(tetrisGrid);

        rootHBox = new HBox(borderPane, stackPane);
//        rootHBox.getStyleClass().add("background");
        rootHBox.setPadding(new Insets(5.0));
        rootHBox.setSpacing(25.0);
        
        gameBoard = new GameBoard(10, 20, 30, stackPane);
        
        scene = new Scene(rootHBox);
    }

    @Override
    public void start(Stage primaryStage) {
        
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
 
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });

//        Circle circle = new Circle(50, Color.web("white", 0.55));
//        Circle circle = new Circle(50, 50, 50, Color.GREEN);
//        ball = new Ball(50, 50, 50, Color.GREEN); 
//        circle.setCenterX(0);
//        circle.setCenterY(0);
//        circle.setStrokeType(StrokeType.OUTSIDE);
//        circle.setStroke(Color.web("white", 0.16));
//        circle.setStrokeWidth(4);
   
//        StackPane mainPane = new StackPane();
//        Group mainGroup = new Group();

//        mainPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//        scene = new TetrisScene(mainGroup, 800, 600, Color.BLACK);
        
        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        stackPane = new StackPane();

        rootHBox = new HBox(borderPane, stackPane);
        rootHBox.setPadding(new Insets(5.0));
        rootHBox.setSpacing(25.0);
        
        gameBoard = new GameBoard(10, 20, 30, stackPane);
        
        scene = new Scene(rootHBox);
        
        scene.setOnKeyPressed((ke) -> {
            if (ke.getCode().equals(KeyCode.LEFT) || ke.getCode().equals(KeyCode.A)) {
                gameBoard.shapeMoveLeft();
//                    ball.moveLeft();
            } else if (ke.getCode().equals(KeyCode.DOWN) || ke.getCode().equals(KeyCode.S)) {
                gameBoard.shapeMoveDown();
//                    ball.moveDown();
            } else if (ke.getCode().equals(KeyCode.RIGHT) || ke.getCode().equals(KeyCode.D)) {
                gameBoard.shapeMoveRight();
//                    ball.moveRight();
            } else if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.W)) {
                gameBoard.shapeRotate();
//                    ball.moveUp();
            } else if (ke.getCode().equals(KeyCode.ESCAPE) || ke.getCode().equals(KeyCode.Q)) {
                work_on = false;
            }
        });

        primaryStage.setScene(scene);

//        mainGroup.getChildren().add(ball);
        primaryStage.setTitle("Игра тетрис");
        primaryStage.show();
        
        work_on = true;
//        timePoint = System.nanoTime();

//        new Thread(drawTask).start();
        
        boardTimePoint = System.nanoTime();
        shapeTimePoint = System.nanoTime();
        
        new AnimationTimer(){
            @Override
            public void handle (long now){
                gameBoard.createShape();
                if ((now - boardTimePoint) > 10000000){
                    boardTimePoint = now;
                    gameBoard.resizeFullLines();
                }

                if ((now - shapeTimePoint) > 1000000000){
                    shapeTimePoint = now;
                    gameBoard.shapeMoveDown();
                }
                 
            }
        }.start();
        
/*
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(4000));
            }
 
            protected void interpolate(double frac) {
                ball.move();
            }
 
        };
        animation.play();
        */
/*        
        Transition transitionX =
            new TranslateTransition(Duration.millis(40000), ball);
        transitionX.setFromX(50);
        transitionX.setToX(400);
        transitionX.setCycleCount(1);
        transitionX.setAutoReverse(true);
        transitionX.play();
        */
/*
        TranslateTransition translateTransitionX =
            new TranslateTransition(Duration.millis(40000), ball);
        translateTransitionX.setFromX(50);
        translateTransitionX.setToX(400);
        translateTransitionX.setCycleCount(1);
        translateTransitionX.setAutoReverse(true);

        TranslateTransition translateTransitionY =
            new TranslateTransition(Duration.millis(40000), ball);
        translateTransitionY.setFromY(50);
        translateTransitionY.setToY(400);
        translateTransitionY.setCycleCount(1);
        translateTransitionY.setAutoReverse(true);
        
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransitionX,
                translateTransitionY
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
*/
/*                
        Timeline timeline = new Timeline();
        
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, // set start position at 0
                new KeyValue(ball.translateXProperty(), 0),
                new KeyValue(ball.translateYProperty(), 0)
            ),
            new KeyFrame(new Duration(5000), // set end position at 40s
                new KeyValue(ball.translateXProperty(), 400),
                new KeyValue(ball.translateYProperty(), 400)
            )
        );
        // play 40s of animation
        timeline.setCycleCount(12);
        timeline.setAutoReverse(true);
        timeline.play();
*/        
    }  
    
    public void stop() {
        work_on = false;
        System.out.println("Close app.");
    }
    //    public static void main(String []args) {
 
//        EventQueue.invokeLater(() -> {
//            MainFrame frame = new MainFrame() ;
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//        });
//   }
}
