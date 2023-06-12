package ch.hevs.managedbeans;

import ch.hevs.businessobject.Playlist;
import ch.hevs.businessobject.Song;
import ch.hevs.playlistservice.PlaylistService;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class testBean {
	private String chosenPlaylist;
	private List<String> playlistNames;
	private List<String> playlistSongs;
	private List<Song> fullSongs;
	private PlaylistService ps;

	@PostConstruct
	public void initialize() throws NamingException{

		// Use JNDI to inject reference to playlist bean
		InitialContext ctx = new InitialContext();
		ps = (PlaylistService) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/PlaylistBean!ch.hevs.playlistservice.PlaylistService");
		//ps.populate();

		// Get playlists
		List<Playlist> playlists = ps.getPlaylists();
		playlistNames = new ArrayList<String>();
		for(Playlist p : playlists) {
			playlistNames.add(p.getName());
		}
	}

	public String showDetails(){
		try{
			Playlist p = ps.getPlaylistByName(chosenPlaylist);

			List<Song> songs = p.getSongs();
			playlistSongs = new ArrayList<>();

			for(Song s : songs){
				playlistSongs.add(s.getTitle() + " - " + s.getSinger().getName());
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return "playlistDetails";
	}

	public String addSongs(){
		try{
			fullSongs = ps.getSongs();
			List<Integer> songsIndexes = new ArrayList<>();

			List<Song> playlistSongs = ps.getPlaylistByName(chosenPlaylist).getSongs();
			for(int i = 0; i < fullSongs.size(); i++){
				for(int j = 0; j < playlistSongs.size(); j++){
					if (fullSongs.get(i).equals(playlistSongs.get(j))) {
						songsIndexes.add(i);
						break;
					}
				}
			}

			for(int i = 0; i < songsIndexes.size(); i++){
				fullSongs.remove(songsIndexes.get(i) -i);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "addSongs";
	}

	public void addSongToPlaylist(Song song){
		if(chosenPlaylist != null){
			Playlist p = ps.getPlaylistByName(chosenPlaylist);
			ps.addSongToPlaylist(song, p);
		}
	}

	// Songs
	public List<Song> getFullSongs(){
		return fullSongs;
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

	// Songs
	public List<String> getPlaylistSongs(){
		return playlistSongs;
	}

	public void updateChosenPlaylist(ValueChangeEvent event) {
		this.chosenPlaylist = (String) event.getNewValue();
	}

}
