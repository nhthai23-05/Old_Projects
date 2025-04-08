package hust.soict.dsai.aims.media;

import hust.soict.dsai.aims.exception.PlayerException;

public class Track implements Playable {
    private String title;
    private int length;

    public Track(String title, int length){
        this.title = title;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public boolean equals(Object o){
        if (o instanceof Track) {
            return getTitle().equalsIgnoreCase(((Track) o).getTitle())
                    & getLength() == ((Track) o).getLength();
        }
        return false;
    }

    public String toString(){
        return "Track's title: " + getTitle() + '\n'
         + "Track's length: " + getLength();
    }
    public String play() throws PlayerException {
        if (this.getLength() > 0) {
            String res = "Playing track: " + this.getTitle() + "\n";
            res = res + "Track length: " + this.getLength();
            return res;
        } else {
            System.err.println("abbc");
            throw new PlayerException("ERROR: DVD length is non-positive!");
        }
    }
}