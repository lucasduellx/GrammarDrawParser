import gpdraw.*;
import java.util.ArrayDeque;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Color;


public class App {
    static String init,vars, consts = "+-[]Ff";
    static HelperRule[] rules;
    static double angle, length = 5;
    static int repeats;
    static boolean testDraw = true; 
    static SketchPad canvas;
    static DrawingTool pen;


    public static void main(String[] args) throws Exception {
        File file = new File("gramatica.txt");
        FileReader read = new FileReader(file); 
        BufferedReader reader = new BufferedReader(read);
        String[] gramatic = reader.readLine().split(";");
        reader.close();
        vars = gramatic[0];
        init = gramatic[1];
        rules = parseRuleInput(gramatic[2]);
        angle = Double.parseDouble(gramatic[3]);
        repeats = Integer.parseInt(gramatic[4]);
        canvas = new SketchPad(1000,1000);
        pen = new DrawingTool(canvas);
        initDraw(-100, 0, 90, 2, new Color(0,0,255));
        saveSVG();
    }

    public static void saveSVG() throws IOException{
        SVGGraphics2D g2 = new SVGGraphics2D(1000, 1000);
        pen.getPadPanel().printAll(g2);
        File f = new File("img/gramaticimg.svg");
        SVGUtils.writeToSVG(f, g2.getSVGElement());
    }

    public static void initDraw(int x, int y, double dir, int width, Color color){
        pen.up();
        pen.move(x,y);
        pen.setDirection(dir);
        pen.down();
        pen.setColor(color);
        pen.setWidth(width);
        draw(init, repeats);
    }

    public static void draw(String str, int repeats){
        ArrayDeque<Double> stack = new ArrayDeque<Double>();

        for(int i=0; i<str.length(); ++i){
            char c = str.charAt(i);

            if(consts.indexOf(c) != -1){
                if(c == '['){ 
                    stack.push(length);
                    stack.push(pen.getXPos());
                    stack.push(pen.getYPos());
                    stack.push(pen.getDirection());
                    stack.push((double)pen.getWidth());
                } else if(c == ']'){ 
                    double width = stack.pop();
                    double dir = stack.pop();
                    double y = stack.pop();
                    double x = stack.pop();
                    length = stack.pop();
                    pen.up();
                    pen.move(x,y);
                    pen.setDirection(dir);
                    pen.setWidth((int)width);
                    pen.down();
                } else {
                    doConstAction(c);
                }
            } else if(vars.indexOf(c) != -1){
                if(repeats == 0 && testDraw)
                {
                    pen.forward(length);
                } else 
                {
                    String rule = getRule(c);
                    draw(rule,repeats-1);
                }
            } else {
                System.exit(0);
            }
        }
    }

    private static void doConstAction(char cons){
        switch(cons){
            case '+': 
            pen.turnRight(angle); break;
            case '-':
            pen.turnLeft(angle); break;
            case 'F':
            pen.forward(length); break;
            case 'f':
            pen.up(); pen.forward(length); pen.down(); break;
        }
    }

    private static String getRule(char var){
        int varIndex = vars.indexOf(var);
        if(varIndex == -1){
            System.exit(0);
            return null;
        } else {
            return rules[varIndex].getRule();
        }
    }

    private static HelperRule[] parseRuleInput(String input){
        HelperRule[] result = new HelperRule[vars.length()];
        String[] splitInput = input.split(", *");

        for(int i=0; i<splitInput.length; ++i){
            String rule = splitInput[i];

            rule = rule.replace(" ","");
            if(!rule.matches("[A-Za-z]=.*")){
                System.exit(0);
            }

            char var = rule.charAt(0);
            int varIndex = vars.indexOf(var);
            if(varIndex == -1){
                System.exit(0);
            } else {
                if(result[varIndex] == null) result[varIndex] = new HelperRule(); 
                result[varIndex].addRule(rule.substring(2));
            }
        }
        return result;
    }
}
