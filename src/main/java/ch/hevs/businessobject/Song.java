package ch.hevs.businessobject;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Song")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="title")
	private String title;
	@Column(name="length")
	private int length;

	// relations
	@ManyToOne
	@JoinColumn(name = "FK_ALBUM")
	private Album album;
	
    @ManyToMany
    @JoinTable(name="Playlist")
    private Set<Playlist> playlists;

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
	}
	
	// playlists (From Playlist)
    public Set<Playlist> getPlaylists() { 
    	return playlists; 
	}
	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}

	// constructors
	public Song() {
	}
	public Song(String title, int length, Album album) {
		this.title = title;
		this.length = length;
		this.album = album;
	}
}
