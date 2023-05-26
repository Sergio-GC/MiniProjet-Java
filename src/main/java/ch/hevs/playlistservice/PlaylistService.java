package ch.hevs.playlistservice;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PlaylistService {
	List<Singer> getSingers();
	List<Album> getAlbums();
	List<Song> getSongs();
	List<Playlist> getPlaylists();
	List<User> getUsers();
	
	void addSongToPlaylist(Song song, Playlist playlist);
	Playlist createPlaylist(String playlistName, List<Song> songs);
	void deletePlaylist(Playlist playlist);
	
	List<Playlist> getPlaylistsByUser(User user);
	void sharePlaylist(Playlist playlist, User newUser);
}