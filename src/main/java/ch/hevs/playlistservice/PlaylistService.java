package ch.hevs.playlistservice;

import ch.hevs.businessobject.*;

import javax.ejb.Local;
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;

@Local
public interface PlaylistService {
	ArrayList<Singer> getSingers();
	ArrayList<Album> getAlbums();
	ArrayList<Song> getSongs();
	ArrayList<Playlist> getPlaylists();
	ArrayList<User> getUsers();
	
	void addSongToPlaylist(Song song, Playlist playlist);
	Playlist createPlaylist(int userId, String playlistName, List<Song> songs);
	void deletePlaylist(Playlist playlist);
	void deleteSongFromPlaylist(Song song, Playlist playlist);
	
	ArrayList<Playlist> getPlaylistsByUser(User user);
	void sharePlaylist(Playlist playlist, User newUser);
	User addUser(String username);
	Playlist addPlaylist(String playlistName, User user);
	
	void populate();

	Playlist getPlaylistByName(String playlistName);
}