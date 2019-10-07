package com.ld45.game.audio;

import com.badlogic.gdx.audio.Music;
import com.ld45.game.assets.Assets;

public enum MusicType {
    Background("audio/bg.ogg")
    ;

    MusicType(String path) {
        this.MUSIC = Assets.getInstance().getMusic(path);
    }

    public final Music MUSIC;

    public static void loop(MusicType music) {
        music.MUSIC.play();
        music.MUSIC.setLooping(true);
    }

}