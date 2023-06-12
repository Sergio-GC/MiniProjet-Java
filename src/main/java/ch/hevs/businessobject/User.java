package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="name")
	private String name;
	
	// relations
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name = "playlists")
	private List<Playlist> playlists;
	
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

	
	// playlists (From Playlist)
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	// constructors
	public User() {
		playlists = new ArrayList<Playlist>();
	}
	public User(String name) {
		this.name = name;
		this.playlists = new ArrayList<Playlist>();
	}

	public void addPlaylist(Playlist playlist){
		playlists.add(playlist);
		playlist.setOwner(this);
	}
}
