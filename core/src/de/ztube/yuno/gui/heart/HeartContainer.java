package de.ztube.yuno.gui.heart;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Stack;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The HeartContainer manages the Hearts and displays them in a row*/
public class HeartContainer extends HorizontalGroup{

    private Stack<Heart> hearts;

    private Heart.HeartStyle style;

    public HeartContainer(int heartCount, Skin skin){
        this(heartCount, skin.get(Heart.HeartStyle.class));
    }

    public HeartContainer(int heartCount, Skin skin, String styleName){
        this(heartCount, skin.get(styleName, Heart.HeartStyle.class));
    }

    public HeartContainer(int heartCount, Heart.HeartStyle style){
        this.style = style;
        hearts = new Stack<Heart>();
        for(int i = 0; i < heartCount; i++){
            Heart heart = new Heart(style);
            hearts.push(heart);
            addActor(heart);
        }

    }

    public void addHeart(int count) {
        for (int i = 0; i <= count; i++) {
            Heart heart = new Heart(Heart.HeartState.EMPTY, style);
            hearts.push(heart);
            addActor(heart);
        }
    }

    public void removeHeart(int count) {
        for (int i = 0; i <= count; i++) {
            removeActor(hearts.pop());
        }
    }

    //Add one heart
    public void addHeart() {
        addHeart(1);
    }

    //Remove one heart
    public void removeHeart() {
        removeHeart(1);
    }

    //Damage by int lifes
    public void damage(int lifes) {
        int damage = lifes;

        for (int i = hearts.size() - 1; i >= 0; i--) {
            Heart heart = hearts.get(i);
            if (damage >= 2) {
                if (heart.getState() == Heart.HeartState.FULL) {
                    heart.setState(Heart.HeartState.EMPTY);
                    damage -= 2;
                } else if (heart.getState() == Heart.HeartState.HALF) {
                    heart.setState(Heart.HeartState.EMPTY);
                    damage -= 1;
                }
            }
            if (damage == 1) {
                if (heart.getState() == Heart.HeartState.FULL) {
                    heart.setState(Heart.HeartState.HALF);
                    damage -= 1;
                } else if (heart.getState() == Heart.HeartState.HALF) {
                    heart.setState(Heart.HeartState.EMPTY);
                    damage -= 1;
                }
            }
            if (damage == 0) {
                //do nothing
            }
        }
    }

    //Heal lifes
    public void heal(int lifes) {
        int heal = lifes;

        for (Heart heart : hearts) {
            if (heal >= 2) {
                if (heart.getState() == Heart.HeartState.EMPTY) {
                    heart.setState(Heart.HeartState.FULL);
                    heal -= 2;
                } else if (heart.getState() == Heart.HeartState.HALF) {
                    heart.setState(Heart.HeartState.FULL);
                    heal -= 1;
                }
            }
            if (heal == 1) {
                if (heart.getState() == Heart.HeartState.EMPTY) {
                    heart.setState(Heart.HeartState.HALF);
                    heal -= 1;
                } else if (heart.getState() == Heart.HeartState.HALF) {
                    heart.setState(Heart.HeartState.FULL);
                    heal -= 1;
                }
            }
            if (heal == 0) {
                //do nothing
            }
        }
    }

    //Recover from all damage
    public void fullHeal() {
        heal(getTotalLifes() - getLifes());
    }

    //Kill
    public void kill() {
        damage(getLifes());
    }

    //Returns the current lifes
    public int getLifes() {
        int lifes = 0;
        for (Heart heart : hearts) {
            if (heart.getState() == Heart.HeartState.FULL) {
                lifes += 2;
            } else if (heart.getState() == Heart.HeartState.HALF) {
                lifes += 1;
            }
        }
        return lifes;
    }

    //Returns the total lifes
    public int getTotalLifes() {
        int lifes = 0;
        for (int i = 0; i < hearts.size(); i++) {
            lifes += 2;
        }
        return lifes;
    }
}

