package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Singer")
public class Singer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="name", nullable = true)
	private String name;
	
	// relations
	@OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//@JoinColumn(name = "FK_SINGER")
	private List<Song> songs;
	
	@OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//@JoinColumn(name = "FK_SINGER")
	@Column(nullable = true)
	private List<Album> albums;

	// id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	// name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// songs
	public List<Song> getSongs(){
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	// albums
	public List<Album> getAlbums(){
		return albums;
	}
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	// constructors
	public Singer() {
		songs = new ArrayList<>();
		albums = new ArrayList<>();
	}
	public Singer(String name) {
		this.name = name;
		songs = new ArrayList<>();
		albums = new ArrayList<>();
	}

	public void addSong(Song song){
		songs.add(song);
	}

	public void addAlbum(Album album){
		albums.add(album);
		album.setSinger(this);
	}
}
