package de.ztube.yuno.gui.heart;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.ztube.yuno.Yuno;

/**
 * Created by ZTube on 18.07.2016.
 * Yuno
 */

/**The HeartContainer manages the Hearts and displays them in a row*/
public class HeartContainer extends Actor implements Disposable {
    private Array<Heart> hearts;

    public HeartContainer(int heartCount) {

        hearts = new Array<Heart>(heartCount);

        for (int i = 0; i < heartCount; i++) {
            hearts.add(new Heart());
        }
        for (int i = 0; i < hearts.size; i++) {
            hearts.get(i).setPosition(i * hearts.get(i).getWidth(), Yuno.SCREEN_HEIGHT - hearts.get(i).getHeight());
        }
        int width = getContainerWidth();
        for (Heart heart : hearts) {
            heart.setX(heart.getX() + Yuno.SCREEN_WIDTH / 2 - width / 2);
        }

    }

    //Add new Hearts
    public void addHeart(int count) {
        for (int i = 0; i <= count; i++) {
            hearts.add(new Heart(Heart.HeartState.EMPTY));
        }

        for (int i = 0; i < hearts.size; i++) {
            hearts.get(i).setPosition(i * hearts.get(i).getWidth(), Yuno.SCREEN_HEIGHT - hearts.get(i).getHeight());
        }

        int width = getContainerWidth();

        for (Heart heart : hearts) {
            heart.setX(heart.getX() + Yuno.SCREEN_WIDTH / 2 - width / 2);
        }
    }

    //Remove a Heart
    public void removeHeart(int count) {
        for (int i = 0; i <= count; i++) {
            hearts.pop().dispose();
        }

        for (int i = 0; i < hearts.size; i++) {
            hearts.get(i).setPosition(i * hearts.get(i).getWidth(), Yuno.SCREEN_HEIGHT - hearts.get(i).getHeight());
        }

        int width = getContainerWidth();

        for (Heart heart : hearts) {
            heart.setX(heart.getX() + Yuno.SCREEN_WIDTH / 2 - width / 2);
        }
    }

    //Add one Heart
    public void addHeart() {
        addHeart(1);
    }

    //Remove one Heart
    public void removeHeart() {
        removeHeart(1);
    }

    //Damage by int lifes
    public void damage(int lifes) {
        int damage = lifes;
        hearts.reverse();

        for (Heart heart : hearts) {
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
        hearts.reverse();
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

    //Returns the with of the Actor
    public int getContainerWidth() {
        return Math.round(hearts.peek().getX() + hearts.peek().getWidth() - hearts.first().getX());
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
        for (int i = 0; i < hearts.size; i++) {
            lifes += 2;
        }
        return lifes;
    }

    @Override
    public void dispose() {
        for (Heart heart : hearts) {
            heart.dispose();
        }
    }

    //Render the hearts
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        for (Heart heart : hearts) {
            heart.draw(batch);
        }
    }
}

