package ch.hevs.playlistservice;

import ch.hevs.businessobject.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PlaylistBean implements PlaylistService {

	@PersistenceContext(name = "PlaylistPU", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlaylistPU");

	public void populate() {
		User sergio = new User("Sergio");

		Singer a7x = new Singer("Avenged Sevenfold");
		Album libad = new Album("Life Is But A Dream", 2023, a7x);

		Song gameOver = new Song("Game Over", 450);
		gameOver.setSinger(a7x);
		gameOver.setAlbum(libad);

		Song cosmic = new Song("Cosmic", 742);
		cosmic.setSinger(a7x);
		cosmic.setAlbum(libad);

		List<Song> metalSongs = new ArrayList<>();
		metalSongs.add(gameOver);
		metalSongs.add(cosmic);
		Playlist metal = new Playlist("Metal", sergio, metalSongs);


		em.persist(libad);
		em.persist(metal);

	}

	@Override
	public ArrayList<Singer> getSingers() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT s FROM Singer s");
		return (ArrayList<Singer>) query.getResultList();
	}

	@Override
	public ArrayList<Album> getAlbums() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT a FROM Album a");
		return (ArrayList<Album>) query.getResultList();
	}

	@Override
	public ArrayList<Song> getSongs() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT s FROM Song s");
		return (ArrayList<Song>) query.getResultList();
	}

	@Override
	public ArrayList<Playlist> getPlaylists() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT p FROM Playlist p");
		return (ArrayList<Playlist>) query.getResultList();
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT u FROM User u");
		return (ArrayList<User>) query.getResultList();
	}

	@Override
	public void addSongToPlaylist(Song song, Playlist playlist) {
		// TODO Auto-generated method stub
		em.persist(playlist);
		playlist.addSong(song);
	}

	@Override
	public Playlist createPlaylist(int userId, String playlistName, List<Song> songs) {
		// TODO Auto-generated method stub
		Query userQuery = em.createQuery("select u from User u where u.id=:id");
		userQuery.setParameter("id", userId);
		User user = (User) userQuery.getSingleResult();
		em.persist(user);

//		List<Playlist> userPlaylists = user.getPlaylists();
//		Playlist playlist = new Playlist(playlistName, user, songs); // TODO finish
//		em.persist(playlist);

		// Add songs to the new playlist
//		for(Song song : songs) {
//			playlist.addSong(song);
//		}
//
//		userPlaylists.add(playlist);
//		user.setPlaylists(userPlaylists);
		return new Playlist();
	}

	@Override
	public void deletePlaylist(Playlist playlist) {
		// TODO Auto-generated method stub
		//Get the user
		User user = playlist.getOwner();

		if(user == null) {
			// TODO throw an exception
		}
		em.persist(user);

		// Get the user's playlists
//		List<Playlist> playlists = user.getPlaylists();
//
//		if(playlists.contains(playlist)) {
//			// Remove playlist from users with rights
//			List<User> users = playlist.getUsers();
//			for(User u : users) {
//				Query q = em.createQuery("select u from User u where u.id=:id");
//				q.setParameter("id", u.getId());
//				User usr = (User) q.getSingleResult();
//				em.persist(usr);
//
//				// Remove and update list of playlists
//				List<Playlist> ps = u.getPlaylists();
//				ps.remove(playlist);
//				u.setPlaylists(ps);
//			}
//
//			// Remove playlist from owner's list and update the list
//			playlists.remove(playlist);
//			user.setPlaylists(playlists);
//		} else {
//			//TODO throw an exception
//		}
	}

	@Override
	public ArrayList<Playlist> getPlaylistsByUser(User user) {
		// TODO Auto-generated method stub
//		return (ArrayList<Playlist>) user.getPlaylists();
		return new ArrayList<Playlist>();
	}

	@Override
	public void sharePlaylist(Playlist playlist, User newUser) {
		// TODO Auto-generated method stub
		long id = newUser.getId();
		em.persist(playlist);
		List<User> usersWithRights = playlist.getUsers();
		usersWithRights.add(newUser);
	}

	public Playlist getPlaylistByName(String playlistName){
		Query q = em.createQuery("select p from Playlist p join fetch p.songs where p.name=:name");
		q.setParameter("name", playlistName);

		// Return the first playlist with the given name TODO make sure every playlist has a unique name
		return (Playlist) q.getResultList().get(0);
	}

}
