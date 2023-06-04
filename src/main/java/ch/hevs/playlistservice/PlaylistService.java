package ch.hevs.playlistservice;

import javax.ejb.Local;

import ch.hevs.businessobject.*;

import java.util.List;
import java.util.ArrayList;
import ch.hevs.businessobject.*;

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
	
	ArrayList<Playlist> getPlaylistsByUser(User user);
	void sharePlaylist(Playlist playlist, User newUser);
	
	void populate();
}