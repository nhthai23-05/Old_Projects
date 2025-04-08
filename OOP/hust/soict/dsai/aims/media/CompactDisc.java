package hust.soict.dsai.aims.media;

import hust.soict.dsai.aims.exception.PlayerException;

import java.util.ArrayList;
import java.util.Iterator;

public class CompactDisc extends Disc implements Playable {
    private String artist;
    ArrayList<Track> tracks = new ArrayList<>();

    public String getArtist() {
        return artist;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public String tracksInfo (){
        String infor = "";
        for (Track track : tracks) {
            infor = infor + track.toString();
        }
        return infor;
    }

    public CompactDisc(String title, String category, String director, float cost,
                       int length, String artist, ArrayList<Track> tracks){
        super(title, category, director, length, cost);
        this.artist = artist;
        this.tracks = tracks;
    }

    public boolean checkTrackAvailable(ArrayList<Track> trackArrayList, Track track){
        for (Track check: trackArrayList){
            if (check.getTitle().equalsIgnoreCase(track.getTitle())){
                return true;
            }
        }
        return false;
    }

    public void addTrack(Track track){
        if (checkTrackAvailable(tracks, track)){
            System.out.println("Track has already been added!");
        } else {
            tracks.add(track);
        }
    }

    public void removeTrack(Track track){
        if (checkTrackAvailable(tracks, track)){
            tracks.remove(track);
        } else {
            System.out.println("No track found!");
        }
    }

    public void removeTrack (String title){
        for (Track track: tracks){
            if (track.getTitle().equalsIgnoreCase(title)){
                removeTrack(track);
            }
        }
        System.out.println("No track found!");
    }

    public int getLength(){
        int length = 0;
        for (Track track: tracks){
            length += track.getLength();
        }
        return length;
    }

    public String play() throws PlayerException {
        if(this.getLength() > 0) {
            String res = "";
            for (Track track: tracks){
                res  = res + track.play();
            }
            Iterator<Track> iter = tracks.iterator();
            Track nextTrack;
            while (iter.hasNext()){
                nextTrack = (Track) iter.next();
                try {
                    nextTrack.play();
                }catch (PlayerException e){
                    throw new PlayerException("ERROR: DVD length is non-positive!");
                }
            }
            return res;
        } else {
            throw new PlayerException("ERROR: CD length is non-positive!");
        }
    }

    public String toString(){
        return "CD - " + getTitle() + " - " + getCategory() + " - " + getArtist()
                + " - " + getDirector() + " - "
                + getLength() + " - " + getCost() + '\n' + tracksInfo();
    }
}