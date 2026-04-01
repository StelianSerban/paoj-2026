package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs = new Song[0];

    public String getName()
    {
        return name;
    }

    public Playlist(String name)
    {
        this.name = name;
    }
    void addSong(Song song)
    {
        Song[] newSongs = new Song[songs.length + 1];
        System.arraycopy(songs, 0, newSongs, 0, songs.length);
        newSongs[songs.length] = song;
        songs = newSongs;
    }

    void printSortedByTitle()
    {
        Song[] copy = new Song[songs.length];
        System.arraycopy(songs, 0, copy, 0, songs.length);
        Arrays.sort(copy);
        for(Song s : copy)
        {
            System.out.println(s.toString());
        }
    }

    void printSortedByDuration()
    {
        Song[] copy = new Song[songs.length];
        System.arraycopy(songs, 0, copy, 0, songs.length);
        Arrays.sort(copy, new SongDurationComparator());
        for(Song s : copy)
        {
            System.out.println(s.toString());
        }
    }

    int getTotalDuration()
    {
        int n = 0;
        for(Song s : songs)
        {
            n += s.durationSeconds();
        }
        return n;
    }
}
