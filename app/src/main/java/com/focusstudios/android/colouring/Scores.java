package com.focusstudios.android.colouring;

public class Scores {

    private int id;
    private String score;
    private int gamesPlayed;
    private int music;
    private int sfx;

    public Scores(int id, String score, int gamesPlayed, int music, int sfx) {
        this.id = id;
        this.score = score;
        this.gamesPlayed = gamesPlayed;
        this.music = music;
        this.sfx = sfx;
    }

    public int getId()
    {
        return id;
    }
    public String getScore() {
        return score;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public int getMusic() { return music; }
    public int getSFX() { return sfx; }
}