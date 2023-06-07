package ch.hevs.businessobject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Playlist")
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="name")
	private String name;

	// relations
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_USER")
	private User owner;
	
	@ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name="Song")
	private List<Song> songs;
	
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
	
	// owner (From User)
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	// songs (From Song)
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	public void addSong(Song song) {
		if (this.songs == null){
			songs = new ArrayList<>();
		}
		songs.add(song);
		song.getPlaylists().add(this);
	}
	
	// TODO change this because it's useless
	public List<User> getUsers(){
		return new ArrayList<User>();
	}
	
	// constructors
	public Playlist() {
	}
	public Playlist(String name, User owner, List<Song> songs) {
		this.name = name;
		this.owner = owner;
		this.songs = songs;
	}
}
