package ch.hevs.managedbeans;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.playlistservice.PlaylistService;
import ch.hevs.businessobject.*;

public class testBean {
	private String chosenPlaylist;
	private List<String> playlistNames;
	private PlaylistService ps;
	
	@PostConstruct
	public void initialize() throws NamingException{
		
		// Use JNDI to inject reference to playlist bean
		InitialContext ctx = new InitialContext();
		ps = (PlaylistService) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/PlaylistBean!ch.hevs.playlistservice.PlaylistService");
		ps.populate();
		
		// Get playlists
		List<Playlist> playlists = ps.getPlaylists();
		playlistNames = new ArrayList<String>();
		for(Playlist p : playlists) {
			playlistNames.add(p.getName());
		}
	}

	// Chosen Playlist
	public String getChosenPlaylist(){
		return chosenPlaylist;
	}
	public void setChosenPlaylist(final String chosenPlaylist){
		this.chosenPlaylist = chosenPlaylist;
	}
	
	// PlaylistNames
	public List<String> getPlaylistNames(){
		return playlistNames;
	}
	
	public void updateChosenPlaylist(ValueChangeEvent event) {
		this.chosenPlaylist = (String) event.getNewValue();
	}
	
}
