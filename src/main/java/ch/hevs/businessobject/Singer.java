package ch.hevs.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Singer")
public class Singer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="name", nullable = true)
	private String name;
	
	// relations
	@OneToMany(mappedBy = "singer", cascade = CascadeType.ALL)//@JoinColumn(name = "FK_SINGER")
	private List<Song> songs;
	
	@OneToMany(mappedBy = "singer", cascade = CascadeType.ALL)//@JoinColumn(name = "FK_SINGER")
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
	}
	public Singer(String name) {
		this.name = name;
	}
}
