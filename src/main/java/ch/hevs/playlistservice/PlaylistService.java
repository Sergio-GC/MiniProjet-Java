package ch.hevs.playlistservice;

import javax.ejb.Local;

import ch.hevs.businessobject.*;

import java.util.List;
import ch.hevs.businessobject.*;

@Local
public interface PlaylistService {
	List<Singer> getSingers();
	List<Album> getAlbums();
	List<Song> getSongs();
	List<Playlist> getPlaylists();
	List<User> getUsers();
	
	void addSongToPlaylist(Song song, Playlist playlist);
	Playlist createPlaylist(int userId, String playlistName, List<Song> songs);
	void deletePlaylist(Playlist playlist);
	
	List<Playlist> getPlaylistsByUser(User user);
	void sharePlaylist(Playlist playlist, User newUser);
}