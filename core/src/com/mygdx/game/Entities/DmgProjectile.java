package com.mygdx.game.Entities;

import com.mygdx.game.EntityUtilities.ProjectileData;

import java.awt.*;

public class DmgProjectile extends Projectile{
    public DmgProjectile(ProjectileData data, Point start, Point end, Tower t){
        super(data,start,end,t);
    }
    public void damage(Fighter fighter) {
        System.out.println("DmgProjectile");
        fighter.damage((int) data.damage);
        this.remove();
    }
    @Override
    public void act(float delta){
        setPosition((float) (getX() + data.speed * Math.cos(getRotation())), (float) (getY() + data.speed * Math.sin(getRotation())));
        super.act(delta);
    }
}
