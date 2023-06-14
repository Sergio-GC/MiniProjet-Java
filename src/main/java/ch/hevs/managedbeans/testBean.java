package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.hevs.playlistservice.PlaylistService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
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
	private String newPlaylistName;
	private String newSongTitle;
	private String newSongLength;
	private String newSingerName;
	private String newAlbumTitle;
	private String newAlbumYear;

	@PostConstruct
	public void initialize() throws NamingException{

		// Use JNDI to inject reference to playlist bean
		InitialContext ctx = new InitialContext();
		ps = (PlaylistService) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/PlaylistBean!ch.hevs.playlistservice.PlaylistService");
		//ps.populate();

		// Get users
		users = loadUsers();

		newUserName = "John Doe";
		newPlaylistName = "My new playlist";

		newSongTitle = "CUFF IT";
		newSongLength = "225";
		newSingerName = "Beyonce";
		newAlbumTitle = "RENAISSANCE";
		newAlbumYear = "2022";
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

	public String addSongToPlaylist(Song song){
		if(chosenPlaylist != null){
			Playlist p = ps.getPlaylistByName(chosenPlaylist);
			ps.addSongToPlaylist(song, p);
		}

		// Reload the list of available songs and rediret the user
		addSongs();
		return "addSongs";
	}

	public String deleteSong(Song song) {
		if (chosenPlaylist != null) {
			Playlist p = ps.getPlaylistByName(chosenPlaylist);
			ps.deleteSongFromPlaylist(song, p);
		}

		return showDetails();
	}

	public String selectUser(User user){
		// Save the user's selection
		chosenUser = user;

		loadPlaylistNames();

		return "welcomeTest";
	}

	public void loadPlaylistNames(){
		List<Playlist> playlists = ps.getPlaylistsByUser(chosenUser);

		playlistNames = new ArrayList<>();
		for(Playlist p : playlists)
			playlistNames.add(p.getName());
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

	public String createPlaylist(){
		return "createPlaylist";
	}

	public String addNewSongs(){
		return "addNewSongs";
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

	// Create New Playlist
	public String addPlaylist() {
		// Create the new playlist
		ps.addPlaylist(newPlaylistName, chosenUser);
		// Reaload the playlists to be shown at the home page
		loadPlaylistNames();

		// Select the new playlist and return to home page
		chosenPlaylist = newPlaylistName;
		return "welcomeTest";
	}
	public String getNewPlaylistName(){
		return newPlaylistName;
	}
	public void setNewPlaylistName(String newPlaylistName) {
		this.newPlaylistName = newPlaylistName;
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

	public String getNewSongTitle() {
		return newSongTitle;
	}

	public void setNewSongTitle(String newSongTitle) {
		this.newSongTitle = newSongTitle;
	}

	public String getNewSongLength() {
		return newSongLength;
	}

	public void setNewSongLength(String newSongLength) {
		this.newSongLength = newSongLength;
	}

	public String getNewSingerName() {
		return newSingerName;
	}

	public void setNewSingerName(String newSingerName) {
		this.newSingerName = newSingerName;
	}

	public String getNewAlbumTitle() {
		return newAlbumTitle;
	}

	public void setNewAlbumTitle(String newAlbumTitle) {
		this.newAlbumTitle = newAlbumTitle;
	}

	public String getNewAlbumYear() {
		return newAlbumYear;
	}

	public void setNewAlbumYear(String newAlbumYear) {
		this.newAlbumYear = newAlbumYear;
	}

	public void addNewSong() {
		try {
			Singer singer = new Singer(newSingerName);
			Album album = new Album(newAlbumTitle, Integer.parseInt(newAlbumYear), singer);
			Song song = new Song(newSongTitle, Integer.parseInt(newSongLength));
			song.setSinger(singer);
			song.setAlbum(album);

			ps.addNewSong(singer, album, song);
			// Ajoutez les objets song, album et singer à votre logique métier appropriée

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New song added successfully!", null));
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid number format for year or length!", null));
		}
	}

	public void validateSongLength(FacesContext context, UIComponent component, Object value) {
		String lengthStr = (String) value;

		try {
			Integer length = Integer.parseInt(lengthStr);

			// Vérifier si la longueur de la chanson est valide (par exemple, supérieure à zéro)
			if (length <= 0) {
				FacesMessage message = new FacesMessage("Invalid song length");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		} catch (NumberFormatException e) {
			FacesMessage message = new FacesMessage("Invalid song length format");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	public void validateAlbumYear(FacesContext context, UIComponent component, Object value) {
		String yearStr = (String) value;

		try {
			Integer year = Integer.parseInt(yearStr);

			// Vérifier si l'année de l'album est valide (par exemple, supérieure à 1900)
			if (year < 1900) {
				FacesMessage message = new FacesMessage("Invalid album year");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		} catch (NumberFormatException e) {
			FacesMessage message = new FacesMessage("Invalid album year format");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}


}
