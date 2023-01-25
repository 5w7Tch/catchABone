import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

public class catchABone  extends GraphicsProgram implements catchABoneConstants{
    catchABoneDataBase base;
    int lives = 5;
    GLabel displayLives;
    int level = 1;
    GLabel lvlNum;
    int score = 0;
    GLabel number;
    GImage dog;
    GImage bone;
    GImage start;
    RandomGenerator rg = new RandomGenerator();
    public void init() {
        setSize(350,650);
        base = new catchABoneDataBase();
    }
    public void run() {
        intro();
        waitForClick();
        Start();
        finish();
        waitForClick();
        reset();
        run();
    }
    private void reset(){
        score = 0;
        level = 1;
        lives = 5;
    }
    private void finish(){
        if(score == 100){
            win();
        }
        if(lives == 0){
            lost();
        }
    }
    private void lost(){
        removeAll();
        setBackground(Color.RED);
        GLabel lost = new GLabel("You Lost");
        lost.setFont("Baskerville-40");
        GLabel tryAgain = new GLabel("Click to try again");
        tryAgain.setFont("Baskerville-30");
        add(lost,getWidth()/2.0-lost.getWidth()/2,getHeight()/2.0-5);
        add(tryAgain,getWidth()/2.0-tryAgain.getWidth()/2,getHeight()/2.0+5+tryAgain.getAscent());
        base.addLose(score);
    }
    private void win(){
        base.addWin(score);
    }
    private void intro(){
        removeAll();
        setBackground(Color.ORANGE);
        start = new GImage("introDog.png");
        start.setBounds(0,0, 250,180 );
        add(start,getWidth()/2.0-start.getWidth()/2,getHeight()/2.0-start.getHeight()/2);
        if(base.hasnotPlayed()) {
            loadUp();
        }
        GLabel play = new GLabel("Play");
        play.setFont("Baskerville-30");
        add(play,getWidth()/2.0-play.getWidth()/2,getHeight()-DOG_OFFSET/2.0);
    }
    private void loadUp(){
        int percent = 0;
        GRect rect = new GRect(100,20);
        add(rect, getWidth()/2.0-rect.getWidth()/2,getHeight()-rect.getHeight()-DOG_OFFSET/2.0);
        GLabel percento = new GLabel(percent +"%");
        percento.setFont("Baskerville-10");
        GRect fill = new GRect(percent,19);
        add(fill,rect.getX()+1,rect.getY()+1);
        add(percento,getWidth()/2.0-percento.getWidth()/2,rect.getY()+rect.getHeight()/2.0+percento.getAscent()/2);
        fill.setFilled(true);
        fill.setColor(Color.CYAN);
        while(percent <= 100){
            percento.setLabel(percent +"%");
            pause(20);
            fill.setBounds(rect.getX()+1,rect.getY()+1,percent,19);
            percent++;
        }
        pause(100);
        remove(rect);
        remove(fill);
        remove(percento);
    }
    private void Start(){
        removeAll();
        setBackground(Color.black);
        lvlNum = new GLabel(Integer.toString(level));
        GLabel level = new GLabel("LvL: ");
        level.setFont("Baskerville-15");
        level.setColor(Color.RED);
        lvlNum.setFont("Baskerville-15");
        lvlNum.setColor(Color.GREEN);
        add(level,getWidth()-level.getWidth()-15,5+level.getAscent());
        add(lvlNum, level.getX()+level.getWidth(), level.getY());
        number = new GLabel(score +"/100");
        number.setFont("Baskerville-15");
        number.setColor(Color.GREEN);
        GLabel score = new GLabel("SCORE: ");
        score.setFont("Baskerville-15");
        score.setColor(Color.RED);
        add(score,5,5+score.getAscent());
        add(number, score.getX()+score.getWidth(), score.getY());
        addDog();
        addLives(score.getY());
        boneRain();
    }
    private void addLives(double y){
        GImage heart = new GImage("heart.png");
        heart.setBounds(0,0,90,50);
        add(heart,-15,y+5);
        displayLives = new GLabel(Integer.toString(lives));
        displayLives.setFont("Baskerville-40");
        add(displayLives,30-displayLives.getWidth()/2,y+43);
    }
    private void addDog(){
        dog = new GImage("dog.png");
        dog.setBounds(0,0,140,70);
        add(dog,getWidth()/2.0-dog.getWidth()/2.0, getHeight()-dog.getHeight());
        addMouseListeners();
    }
    private void boneRain(){
        double delay = 30;
        while(lives != 0 || score != 100){
            bone = new GImage("bone.png");
            bone.setBounds(0,0,70,40);
            double X = rg.nextDouble(1.0,9.0);
            if(rg.nextBoolean()){
                X = X * -1;
            }
            double xset = rg.nextDouble(0, getWidth()-bone.getWidth());
            add(bone,xset,0);

            while(bone.getY()+bone.getHeight()/2+3 <= getHeight()){
                if(bone.getX()<=0){
                    X = X*-1;
                }
                if(bone.getX() + bone.getWidth()/2 >= getWidth()){
                    X = X*-1;
                }
                bone.move(X, 5);
                if(area()){
                    remove(bone);
                    bone = null;
                    addScore();
                    if(score == 20){
                        delay = 13;
                    }
                    if(score == 60){
                        delay = 7;
                    }
                    break;
                }
                pause(delay);
            }
            if(bone != null){
                loseLive();
            }
            if(lives == 0){
                break;
            }
            if(score == 100){
                break;
            }
            pause(DELAY);
        }
    }

    private void loseLive(){
        lives--;
        displayLives.setLabel(Integer.toString(lives));
    }
    private void addScore(){
        score++;
        number.setLabel(score +"/100");
        if(score == 20){
            level++;
            remove(dog);
            dog.setImage("dogLvL2.png");
            lvlNum.setLabel(Integer.toString(level));
            dog.setBounds(0,0, 140,70);
            add(dog,getWidth()/2.0-dog.getWidth()/2.0, getHeight()-dog.getHeight());
        }
        if(score == 60){
            level++;
            remove(dog);
            dog.setImage("dogLvL3.png");
            lvlNum.setLabel(Integer.toString(level));
            dog.setBounds(0,0, 160,80);
            add(dog,getWidth()/2.0-dog.getWidth()/2.0, getHeight()-dog.getHeight());
        }
    }
    private boolean area(){
        int count = 0;
        double x = bone.getX();
        double y = bone.getY();

        double crashX = dog.getX()+dog.getWidth()/3.5;
        double crashY = dog.getY();
        if(y >= crashY && y <= crashY+dog.getHeight()-bone.getHeight()/2){
            count++;
        }
        if(x >= crashX-bone.getWidth()/2.0 && x <= crashX + dog.getWidth()/3){
            count++;
        }
        return count == 2;
    }

    public void mouseMoved(MouseEvent e){
        if(!outOfArea(e.getX())) {
            dog.setLocation(e.getX()-dog.getWidth()/2, dog.getY());
        }
    }
    private boolean outOfArea(double x){
        return x >= getWidth()-dog.getWidth()/6 || x <= 0+dog.getWidth()/6;
    }

}

