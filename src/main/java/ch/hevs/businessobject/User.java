package ch.hevs.businessobject;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="name")
	private String name;
	
	// relations
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)//@JoinColumn(name = "FK_USER")
	private ArrayList<Playlist> playlists;
	
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
	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}
	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	// constructors
	public User() {
	}
	public User(String name) {
		this.name = name;
	}
}
