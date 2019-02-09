package com.mygdx.game.UIs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.Player;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

//the actual gameui that is the same throughout
public class GameUI extends InputAdapter {
    private Stage stage;
    private Label livesLabel;
    private Label moneyLabel;
    private Label levelLabel;
    private Table entityTable;
    private Label info;
    private Actor entity;
    private Label name;
    private Label health;
    private Table gameTable;
    private Window pane;
    private BattleScreen screen;
    private TowerUI openTowerUI;
    private ArrayList<TowerUI> towerUIs;


    private Player player;


    public GameUI(Player player, Skin style, EntityGroup entityGroup, BattleScreen screen) {
        this.player = player;
        gameTable = new Table();
        levelLabel = new Label("Level:", style);
        moneyLabel = new Label("Money:", style);
        livesLabel = new Label("Lives:", style);

        gameTable.setDebug(true);
        towerUIs = new ArrayList<>();
        gameTable.add(levelLabel).width(100).center();
        gameTable.row();
        gameTable.add(moneyLabel).width(100).center();
        gameTable.row();
        gameTable.add(livesLabel).width(100).center();
        gameTable.setPosition(75, screenH - 50);

        health = new Label("", style);
        name = new Label("", style);
        entityTable = new Table(style);
        entityTable.setDebug(true);
        entityTable.setPosition(200, 0);
        entityTable.left().bottom().add(name).center();
        entityTable.add(health);
        //entityTable.add(info);

        pane = new Window("", style);
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new Texture("skullButtonUp.png"))
                , new TextureRegionDrawable(new Texture("skullButtonDown.png")));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //make this static
                entityGroup.getSpawner().setSpawning(true);
            }
        });
        pane.add(playButton);
        pane.setPosition(screenW, 0);
        stage = new Stage();
        stage.addActor(entityTable);
        stage.addActor(gameTable);
        stage.addActor(pane);
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        levelLabel.setText(String.format("Level: %d", player.getLevel()));
        moneyLabel.setText(String.format("Money: %d", player.getMoney()));
        livesLabel.setText(String.format("Lives: %d", player.getLives()));

    }

    public ArrayList<TowerUI> getTowerUIs() {
        return towerUIs;
    }

    public void updateInfo() {
        if (entity.getClass() == Fighter.class) {
            Fighter f = (Fighter) entity;
            health.setText(String.format("HP: %f", f.getHealth()));
            name.setText(String.format("NAME:%s", f.data.name));
            if (!f.isAlive()) {
                entity = null;
            }


        } else if (entity.getClass() == Tower.class) {
            Tower t = (Tower) entity;

        }
    }

    public void changeInfo(Actor actor) {
        entity = actor;

    }
    public void draw() {
        if (entity != null) {
            updateInfo();

        }



        stage.draw();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //loops through all the objects and check if the x,y is on them
        if (openTowerUI != null) {
            openTowerUI.getUI().remove();
            openTowerUI = null;

        }



        for (TowerUI t : towerUIs) {


            if (t.getRect().contains(screenX, Utilities.convertMouseY(screenY))) {


                openTowerUI = t;

                stage.addActor(openTowerUI.getUI());


            }
        }


        return false;
    }
}
