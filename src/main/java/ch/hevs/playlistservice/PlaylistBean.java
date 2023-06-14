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

		Album nightmareAlbum = new Album("Nightmare", 2010, a7x);

		Song nightmare = new Song("Nightmare", 355);
		nightmare.setSinger(a7x);
		nightmare.setAlbum(nightmareAlbum);

		Song afterlife = new Song("Afterlife", 402);
		afterlife.setSinger(a7x);
		afterlife.setAlbum(nightmareAlbum);

		List<Song> metalSongs = new ArrayList<>();
		metalSongs.add(gameOver);
		metalSongs.add(cosmic);
		Playlist metal = new Playlist("Metal", sergio, metalSongs);


		Singer fleshgodApocalypse = new Singer("Fleshgod Apocalypse");
		Album blueAlbum = new Album("Blue", 2006, fleshgodApocalypse);

		Song blue = new Song("Blue", 168);
		blue.setAlbum(blueAlbum);
		blue.setSinger(fleshgodApocalypse);

		Song no = new Song("No", 420);
		no.setAlbum(blueAlbum);
		no.setSinger(fleshgodApocalypse);


		em.persist(libad);
		em.persist(nightmareAlbum);
		em.persist(blueAlbum);
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
		playlist = em.merge(playlist);
		song = em.merge(song);

		em.persist(playlist);
		playlist.addSong(song);
	}

	@Override
	public void deleteSongFromPlaylist(Song song, Playlist playlist) {
		playlist = em.merge(playlist);
		//song = em.merge(song);

		playlist.deleteSong(song);
		em.persist(playlist);
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

	public User addUser(String username){
		User newUser = new User(username);
		em.persist(newUser);

		return newUser;
	}

	@Override
	public Playlist addPlaylist(String playlistName, User user) {
		List<Song> songs = new ArrayList<>();

		Playlist newPlaylist = new Playlist(playlistName, user, songs);
		em.merge(newPlaylist);

		return newPlaylist;
	}

	@Override
	public void addNewSong(Singer singer, Album album, Song song) {
		// Rechercher le chanteur dans la base de données en fonction du nom
		Query singerQuery = em.createQuery("SELECT s FROM Singer s WHERE s.name = :name");
		singerQuery.setParameter("name", singer.getName());
		List<Singer> existingSingers = singerQuery.getResultList();

		if (!existingSingers.isEmpty()) {
			// Chanteur trouvé, associer à la chanson
			Singer existingSinger = existingSingers.get(0);
			song.setSinger(existingSinger);
		} else {
			// Chanteur non trouvé, le persister
			em.persist(singer);
			song.setSinger(singer);
		}

		// Rechercher l'album dans la base de données en fonction du titre
		Query albumQuery = em.createQuery("SELECT a FROM Album a WHERE a.title = :title");
		albumQuery.setParameter("title", album.getTitle());
		List<Album> existingAlbums = albumQuery.getResultList();

		if (!existingAlbums.isEmpty()) {
			// Album trouvé, associer à la chanson
			Album existingAlbum = existingAlbums.get(0);
			song.setAlbum(existingAlbum);
		} else {
			// Album non trouvé, le persister
			em.persist(album);
			song.setAlbum(album);
		}

		// Persister la chanson
		em.persist(song);
	}

	@Override
	public ArrayList<Playlist> getPlaylistsByUser(User user) {
		em.merge(user);

		Query query = em.createQuery("SELECT p FROM Playlist p WHERE p.owner=:owner");
		query.setParameter("owner", user);

		return (ArrayList<Playlist>) query.getResultList();
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
		Query q = em.createQuery("select p from Playlist p left join fetch p.songs where p.name=:name");
		q.setParameter("name", playlistName);

		// Return the first playlist with the given name TODO make sure every playlist has a unique name
//		return (Playlist) q.getResultList().get(0);

		List<Playlist> resultList = q.getResultList();
		if (!resultList.isEmpty()) {
			// Return the first playlist with the given name
			return resultList.get(0);
		} else {
			// Handle the case when the playlist is not found
			return null; // Or throw an exception, return a default value, etc.
		}
	}

}
