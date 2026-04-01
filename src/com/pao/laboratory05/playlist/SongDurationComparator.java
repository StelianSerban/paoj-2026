package com.pao.laboratory05.playlist;

import java.util.Comparator;

public class SongDurationComparator implements Comparator<Song> {

    @Override
    public int compare(Song song, Song t1) {
        return t1.durationSeconds() - song.durationSeconds();
    }
}
