package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
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
	private List<Song> fullSongs;
	private List<Song> songsFromPlaylist;
	private PlaylistService ps;
	private List<User> users;
	private User chosenUser;
	private String newUserName;
	private Song song;
	private Album album;
	private Singer singer;

	@PostConstruct
	public void initialize() throws NamingException{

		// Use JNDI to inject reference to playlist bean
		InitialContext ctx = new InitialContext();
		ps = (PlaylistService) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/PlaylistBean!ch.hevs.playlistservice.PlaylistService");
		//ps.populate();

		/*// Get playlists
		List<Playlist> playlists = ps.getPlaylists();
		playlistNames = new ArrayList<String>();
		for(Playlist p : playlists) {
			playlistNames.add(p.getName());
		}*/

		// Get users
		users = loadUsers();

		newUserName = "John Doe";
	}

	public String showDetails(){
		try{
			Playlist p = ps.getPlaylistByName(chosenPlaylist);
			songsFromPlaylist = p.getSongs();
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

	public String selectUser(User user){
		// Save the user's selection
		chosenUser = user;

		// Set the playlist to the user's playlists
		List<Playlist> playlists = ps.getPlaylistsByUser(chosenUser);

		// Set the names in the list of playlists
		playlistNames = new ArrayList<>();
		for(Playlist p : playlists)
			playlistNames.add(p.getName());

		return "welcomeTest";
	}

	public String songDetails(Song song){
		this.song = song;
		return "songDetails";
	}

	public String albumDetails(Album album){
		this.album = album;
		return "albumDetails";
	}

	public String singerDetails(Singer singer){
		this.singer = singer;
		return "singerDetails";
	}

	public List<User> loadUsers(){
		return ps.getUsers();
	}

	public String addUser(){
		// Create user and log them in immediately
		return selectUser(ps.addUser(newUserName));
	}

	public String createUser(){
		return "createUser";
	}

	public List<Song> getSongsFromPlaylist(){
		return songsFromPlaylist;
	}

	public Song getSong(){
		return song;
	}
	public Album getAlbum(){return album;}

	public Singer getSinger() {
		return singer;
	}

	// Users
	public List<User> getUsers(){
		return users;
	}
	public String getNewUserName(){
		return newUserName;
	}
	public void setNewUserName(String newUserName){
		this.newUserName = newUserName;
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

	public void updateChosenPlaylist(ValueChangeEvent event) {
		this.chosenPlaylist = (String) event.getNewValue();
	}

}
