import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acmx.export.javax.swing.JButton;
import acmx.export.javax.swing.JComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class catchABone  extends GraphicsProgram implements catchABoneConstants{
    GLabel play;
    catchABoneDataBase base;
    int lives = 20;
    GLabel displayLives;
    int level = 1;
    GLabel lvlNum;
    int score = 0;
    GLabel number;
    GImage dog;
    GImage bone;
    GImage bone1;
    GImage bone2;
    GImage start;
    RandomGenerator rg = new RandomGenerator();
    public void init() {
        setSize(350,650);
        base = new catchABoneDataBase();

    }
    public void run(){
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
        if(score == WINNING_SCORE){
            win();
        }
        if(lives == 0){
            lost();
        }
    }
    private void lost(){
        removeAll();
        setBackground(Color.BLACK);

        GLabel lost = new GLabel("You Lost");
        lost.setFont("ALK Katerina-40");
        GLabel tryAgain = new GLabel("Click to go to main menu");
        tryAgain.setFont("ALK Katerina-27");
        lost.setColor(Color.RED);
        tryAgain.setColor(Color.RED);
        add(lost,getWidth()/2.0-lost.getWidth()/2,getHeight()/2.0-5);
        add(tryAgain,getWidth()/2.0-tryAgain.getWidth()/2,getHeight()/2.0+5+tryAgain.getAscent());
        GImage house = new GImage("house.png");
        GImage deadDog = new GImage("sadDog1.png");
        house.setBounds(0,0,130,110);
        deadDog.setBounds(0,0,150,130);
        GImage Bone = new GImage("bone.png");
        Bone.setBounds(0,0,120,60);
        add(deadDog,lost.getX()+35,lost.getY()-lost.getAscent()-deadDog.getHeight()+55);
        add(house, -20,tryAgain.getY()-house.getHeight()-tryAgain.getAscent()+17);
        add(Bone,lost.getX()+lost.getWidth()+10,tryAgain.getY()-50);
        base.addLose(score);

    }
    private void win(){
        removeAll();
        setBackground(Color.PINK);
        GImage win = new GImage("winBackground.png");
        win.setBounds(0,0,getWidth(),getHeight());
        add(win);
        GLabel text = new GLabel("Victory");
        GLabel text1 = new GLabel("Click to go to main menu");
        text1.setFont("ALK Katerina-26");
        text.setFont("ALK Katerina-40");
        add(text,getWidth()/2.0-text.getWidth()/2,getHeight()/2.0-5);
        add(text1,getWidth()/2.0-text1.getWidth()/2,getHeight()/2.0+5+text1.getAscent());
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
        if(!base.hasnotPlayed()){
            //importHistory();
        }
        play = new GLabel("Play");
        play.setFont("Baskerville-30");
        add(play,getWidth()/2.0-play.getWidth()/2,getHeight()-DOG_OFFSET/2.0);
    }
//    private void importHistory(){
//        history.removeAll();
//        for (int i = 0; i < base.getHistory().size(); i++) {
//            history.addItem("null");
//        }
//    }
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
            pause(DELAY);
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
        number = new GLabel(score +"/" + WINNING_SCORE);
        number.setFont("Baskerville-15");
        number.setColor(Color.GREEN);
        GLabel score = new GLabel("SCORE: ");
        score.setFont("Baskerville-15");
        score.setColor(Color.RED);
        add(score,5,5+score.getAscent());
        add(number, score.getX()+score.getWidth(), score.getY());
        addGrass();
        addDog();
        addLives(score.getY());
        boneRain();
    }
    private void addGrass(){
        GImage grass = new GImage("grassV2.png");
        grass.setBounds(0,0,getWidth()+40,50);
        add(grass,-20,getHeight()-grass.getHeight()+5);
    }
    private void addLives(double y){
        GImage heart = new GImage("heart.png");
        heart.setBounds(0,0,90,50);
        add(heart,-15,y+5);
        displayLives = new GLabel(Integer.toString(lives));
        displayLives.setFont("Baskerville-30");
        add(displayLives,30-displayLives.getWidth()/2,y+37);
    }
    private void addDog(){
        dog = new GImage("dog.png");
        dog.setBounds(0,0,140,70);
        add(dog,getWidth()/2.0-dog.getWidth()/2.0, getHeight()-dog.getHeight());
        addMouseListeners();
    }
    private void boneRain(){
        double delay = 30;
        while(lives != 0 || score != WINNING_SCORE){
            if(level ==1){
                lvl1(delay);
            }
            if(level == 2){
                delay = 13;
                lvl2(delay);
            }
            if(level == 3){
                delay = 7;
                lvl3(delay);
            }
            if(lives <= 0){
                break;
            }
            if(score == WINNING_SCORE){
                break;
            }
        }
    }

    private void lvl1(double delay){
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
                break;
            }
            pause(delay);
        }
        if(bone != null){
            loseLive();
        }

        pause(DELAY);
    }
    private void lvl2(double delay){
        bone = new GImage("bone.png");
        bone1 = new GImage("bone.png");
        bone.setBounds(0,0,70,40);
        bone1.setBounds(0,0,70,40);
        double X = rg.nextDouble(1.0,9.0);
        double X1 = rg.nextDouble(1.0,9.0);
        if(rg.nextBoolean()){
            X = X * -1;
        }
        if(rg.nextBoolean()){
            X1 = X1 * -1;
        }
        double xset = rg.nextDouble(0, getWidth()-bone.getWidth());
        double xset1 = rg.nextDouble(0, getWidth()-bone.getWidth());
        add(bone,xset,0);
        add(bone1,xset1,0);
        double height = bone.getY()+bone.getHeight()/2+3;
        while(height <= getHeight()){
            if(bone != null) {
                if (bone.getX() <= 0) {
                    X = X * -1;
                }
                if (bone.getX() + bone.getWidth() / 2 >= getWidth()) {
                    X = X * -1;
                }
            }
            if(bone1 != null) {
                if (bone1.getX() <= 0) {
                    X1 = X1 * -1;
                }
                if (bone1.getX() + bone1.getWidth() / 2 >= getWidth()) {
                    X1 = X1 * -1;
                }
            }
            if(bone != null) {
                bone.move(X, 5);
            }
            if(bone1 != null) {
                bone1.move(X1, 5);
            }
            if(bone != null){
                if(area()) {
                    remove(bone);
                    bone = null;
                    addScore();
                }
            }
            if(bone1 != null) {
                if(area1()) {
                    remove(bone1);
                    bone1 = null;
                    addScore();
                }
            }
            if(bone != null || bone1 != null){
                if(bone != null) {
                    height = bone.getY() + bone.getHeight() / 2 + 3;
                }
                if(bone1 != null) {
                    height = bone1.getY() + bone1.getHeight() / 2 + 3;
                }
            }
            else{
                break;
            }
            pause(delay);
        }
        if(bone != null){
            loseLive();
        }
        if(bone1 != null){
            loseLive();
        }

        pause(DELAY);
    }
    private void lvl3(double delay){
        bone = new GImage("bone.png");
        bone1 = new GImage("bone.png");
        bone2 = new GImage("bone.png");
        bone.setBounds(0,0,70,40);
        bone1.setBounds(0,0,70,40);
        bone2.setBounds(0,0,70,40);
        double X = rg.nextDouble(1.0,9.0);
        double X1 = rg.nextDouble(1.0,9.0);
        double X2 = rg.nextDouble(1.0,9.0);
        if(rg.nextBoolean()){
            X = X * -1;
        }
        if(rg.nextBoolean()){
            X1 = X1 * -1;
        }
        if(rg.nextBoolean()){
            X2 = X2 * -1;
        }
        double xset = rg.nextDouble(0, getWidth()-bone.getWidth());
        double xset1 = rg.nextDouble(0, getWidth()-bone.getWidth());
        double xset2 = rg.nextDouble(0, getWidth()-bone.getWidth());
        add(bone,xset,0);
        add(bone1,xset1,0);
        add(bone2,xset2,0);
        double height = bone.getY()+bone.getHeight()/2+3;
        while(height <= getHeight()){
            if(bone != null) {
                if (bone.getX() <= 0) {
                    X = X * -1;
                }
                if (bone.getX() + bone.getWidth() / 2 >= getWidth()) {
                    X = X * -1;
                }
            }
            if(bone1 != null) {
                if (bone1.getX() <= 0) {
                    X1 = X1 * -1;
                }
                if (bone1.getX() + bone1.getWidth() / 2 >= getWidth()) {
                    X1 = X1 * -1;
                }
            }
            if(bone2 != null) {
                if (bone2.getX() <= 0) {
                    X2 = X2 * -1;
                }
                if (bone2.getX() + bone2.getWidth() / 2 >= getWidth()) {
                    X2 = X2 * -1;
                }
            }
            if(bone != null) {
                bone.move(X, 5);
            }
            if(bone1 != null) {
                bone1.move(X1, 5);
            }
            if(bone2 != null) {
                bone2.move(X2, 5);
            }
            if(bone != null){
                if(area()) {
                    remove(bone);
                    bone = null;
                    addScore();
                }
            }
            if(bone1 != null) {
                if(area1()) {
                    remove(bone1);
                    bone1 = null;
                    addScore();
                }
            }
            if(bone2 != null) {
                if(area2()) {
                    remove(bone2);
                    bone2 = null;
                    addScore();
                }
            }
            if(bone != null || bone1 != null || bone2 != null){
                if(bone != null) {
                    height = bone.getY() + bone.getHeight() / 2 + 3;
                }
                if(bone1 != null) {
                    height = bone1.getY() + bone1.getHeight() / 2 + 3;
                }
                if(bone2 != null) {
                    height = bone2.getY() + bone2.getHeight() / 2 + 3;
                }
            }
            else{
                break;
            }
            pause(delay);
        }
        if(bone != null){
            loseLive();
        }
        if(bone1 != null){
            loseLive();
        }
        if(bone2 != null){
            loseLive();
        }
        pause(DELAY);
    }
    private void loseLive(){
        lives--;
        displayLives.setLabel(Integer.toString(lives));
        displayLives.setLocation(30-displayLives.getWidth()/2,57);
    }
    private void addScore(){
        score++;
        number.setLabel(score + "/" + WINNING_SCORE);
        if(score == LvL2_SCORE){
            level++;
            remove(dog);
            dog.setImage("dogLvL2.png");
            lvlNum.setLabel(Integer.toString(level));
            dog.setBounds(0,0, 140,70);
            add(dog,getWidth()/2.0-dog.getWidth()/2.0, getHeight()-dog.getHeight());
        }
        if(score == LvL3_SCORE){
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
    private boolean area1(){
        int count = 0;
        double x = bone1.getX();
        double y = bone1.getY();

        double crashX = dog.getX()+dog.getWidth()/3.5;
        double crashY = dog.getY();
        if(y >= crashY && y <= crashY+dog.getHeight()-bone1.getHeight()/2){
            count++;
        }
        if(x >= crashX-bone1.getWidth()/2.0 && x <= crashX + dog.getWidth()/3){
            count++;
        }
        return count == 2;
    }
    private boolean area2(){
        int count = 0;
        double x = bone2.getX();
        double y = bone2.getY();

        double crashX = dog.getX()+dog.getWidth()/3.5;
        double crashY = dog.getY();
        if(y >= crashY && y <= crashY+dog.getHeight()-bone2.getHeight()/2){
            count++;
        }
        if(x >= crashX-bone2.getWidth()/2.0 && x <= crashX + dog.getWidth()/3){
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

    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GPoint point = new GPoint(e.getX(),e.getY());
        if(playZone(point)){

        }
    }

    private boolean playZone(GPoint point){
        int count  = 0;
        if(point.getY() >= play.getY() - play.getAscent() && point.getY() <= play.getY()){
            count ++;
        }
        if(point.getX() >= play.getX() && point.getX() <= play.getX()+play.getWidth()){
            count++;
        }
        return count == 2;
    }
}

