package com.ld45.game.audio;

import com.badlogic.gdx.audio.Sound;
import com.ld45.game.assets.Assets;

public enum SfxType {
    Death_0("audio/die_0.wav"), Ice_0("audio/ice_0.ogg"), Ghost("audio/ghost.mp3"),
    Bell("audio/bell.wav"), Win("audio/win.ogg")
    ;

    SfxType(String path) {
        this.SOUND = Assets.getInstance().getSound(path);
    }

    public final Sound SOUND;

    public static void playSound(SfxType sound) {
        sound.SOUND.play(0.8f);
    }

    public static void playSound(SfxType sound, float volume) {
        sound.SOUND.play(volume);
    }

}