package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Song")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="title", nullable = true)
	private String title;
	@Column(name="length", nullable = true)
	private int length;

	// relations
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_ALBUM")
	private Album album;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_SINGER")
	private Singer singer;
	
    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Playlist> playlists;

	// id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	// title
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	// length
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	// album (From Album)
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;

		List<Song> albumSongs = album.getSongs();
		albumSongs.add(this);
		album.setSongs(albumSongs);
	}
	
	// playlists (From Playlist)
    public List<Playlist> getPlaylists() { 
    	return playlists; 
	}
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	// singer
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
		singer.addSong(this);
	}

	// constructors
	public Song() {
		album = new Album();
		playlists = new ArrayList<>();
	}
	public Song(String title, int length) {
		this.title = title;
		this.length = length;
		this.album = new Album();
		playlists = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		Song s = (Song) obj;
		return this.id == s.getId();
	}
}
