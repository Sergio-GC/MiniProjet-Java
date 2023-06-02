package ch.hevs.playlistservice;

import java.util.List;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import ch.hevs.businessobject.*;

@Stateless
public class PlaylistBean implements PlaylistService {
	
	@PersistenceContext(name = "PlaylistPU", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public void populate() {
		// JAI AUCUNE FOUTUE IDEE DE CE QUE JE DOIS FAIRE POUR CETTE MERDE DE BORDEL (?) --> SON OF A BITCH
		
		// Singer
		Singer preMalone = new Singer("Pre Malone");
		Singer duringMalone = new Singer("During Malone");
		
		// Songs
		Song norwegianReggaeton = new Song("Norwegian Reggaeton", 69, new Album());
		Song drownMeInBlood = new Song("Drown me in Blood", 420, new Album());
		Song bones = new Song("Bones", 36, new Album());
		
		// Songs lists for albums
		List<Song> bullshitSongs = new ArrayList<Song>();
		List<Song> deeznSongs = new ArrayList<Song>();
		bullshitSongs.add(norwegianReggaeton);
		bullshitSongs.add(drownMeInBlood);
		deeznSongs.add(bones);
		
		// Albums
		Album bullshitAlbum = new Album("Bullshit", 1969, preMalone, bullshitSongs);
		Album deezn = new Album("Deez Nuts!", 1420, duringMalone, deeznSongs);
		
		// Assign albums to the created songs
		norwegianReggaeton.setAlbum(bullshitAlbum);
		drownMeInBlood.setAlbum(bullshitAlbum);
		bones.setAlbum(deezn);
		
		// Assign singers to the created songs
		norwegianReggaeton.setSinger(preMalone);
		drownMeInBlood.setSinger(preMalone);
		bones.setSinger(duringMalone);
		
		// Assign the created songs to the given singer
		preMalone.setSongs(bullshitSongs);
		duringMalone.setSongs(deeznSongs);
		
		// Assign albums to the singers
		List<Album> preMaloneAlbums = new ArrayList<Album>();
		preMaloneAlbums.add(bullshitAlbum);
		List<Album> duringMaloneAlbums = new ArrayList<Album>();
		duringMaloneAlbums.add(deezn);
		
		preMalone.setAlbums(preMaloneAlbums);
		duringMalone.setAlbums(duringMaloneAlbums);
		
		
		// Users
		User julie = new User("Julienne");
		User grosTas = new User("Sergio");
		
		// Playlists
		List<Song> fireSongs = new ArrayList<Song>();
		fireSongs.add(norwegianReggaeton);
		fireSongs.add(bones);
		Playlist fire = new Playlist("Fire", julie, fireSongs);
		
		List<Song> iceSongs = new ArrayList<Song>();
		iceSongs.add(drownMeInBlood);
		iceSongs.add(norwegianReggaeton);
		Playlist ice = new Playlist("Ice Age (?)", grosTas, iceSongs);
		
		List<Song> shittySongs = new ArrayList<Song>();
		shittySongs.add(norwegianReggaeton);
		shittySongs.add(drownMeInBlood);
		shittySongs.add(bones);
		Playlist shit = new Playlist("Shit", grosTas, shittySongs);
		
		List<Playlist> norwegianPlaylists = new ArrayList<Playlist>();
		norwegianPlaylists.add(fire);
		norwegianPlaylists.add(ice);
		norwegianPlaylists.add(shit);
		norwegianReggaeton.setPlaylists(norwegianPlaylists);
		
		List<Playlist> bonesPlaylists = new ArrayList<Playlist>();
		bonesPlaylists.add(fire);
		bonesPlaylists.add(shit);
		bones.setPlaylists(bonesPlaylists);
		
		List<Playlist> drownMeInBloodPlaylists = new ArrayList<Playlist>();
		drownMeInBloodPlaylists.add(ice);
		drownMeInBloodPlaylists.add(shit);
		drownMeInBlood.setPlaylists(drownMeInBloodPlaylists);
		
		List<Playlist> juliePlaylists = new ArrayList<Playlist>();
		juliePlaylists.add(fire);
		julie.setPlaylists(juliePlaylists);
		
		List<Playlist> grosTasPlaylists = new ArrayList<Playlist>();
		grosTasPlaylists.add(ice);
		grosTasPlaylists.add(ice);
		grosTas.setPlaylists(grosTasPlaylists);
		
		
		// Persist all this shit		
		em.persist(julie);
		em.persist(grosTas);
		
		em.persist(norwegianReggaeton);
		em.persist(drownMeInBlood);
		em.persist(bones);
		
		em.persist(preMalone);
		em.persist(duringMalone);
		
		em.persist(fire);
		em.persist(ice);
		em.persist(shit);
		
		em.persist(bullshitAlbum);
		em.persist(deezn);
	}

	@Override
	public ArrayList<Singer> getSingers() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Singers s");
		return (ArrayList<Singer>) query.getResultList();
	}

	@Override
	public ArrayList<Album> getAlbums() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Albums a");
		return (ArrayList<Album>) query.getResultList();
	}

	@Override
	public ArrayList<Song> getSongs() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Songs s");
		return (ArrayList<Song>) query.getResultList();
	}

	@Override
	public ArrayList<Playlist> getPlaylists() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Playlists p");
		return (ArrayList<Playlist>) query.getResultList();
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Users u");
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
		Query userQuery = em.createQuery("select * from Users u where u.id=:id");
		userQuery.setParameter("id", userId);
		User user = (User) userQuery.getSingleResult();
		em.persist(user);
		
		List<Playlist> userPlaylists = user.getPlaylists();
		Playlist playlist = new Playlist(playlistName, user, songs); // TODO finish
		em.persist(playlist);
		
		// Add songs to the new playlist
		for(Song song : songs) {
			playlist.addSong(song);
		}
		
		userPlaylists.add(playlist);
		user.setPlaylists(userPlaylists);
		return playlist;
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
		List<Playlist> playlists = user.getPlaylists();
		
		if(playlists.contains(playlist)) {
			// Remove playlist from users with rights
			List<User> users = playlist.getUsers();
			for(User u : users) {
				Query q = em.createQuery("from Users u where u.id=:id");
				q.setParameter("id", u.getId());
				User usr = (User) q.getSingleResult();
				em.persist(usr);
				
				// Remove and update list of playlists
				List<Playlist> ps = u.getPlaylists();
				ps.remove(playlist);
				u.setPlaylists(ps);
			}
			
			// Remove playlist from owner's list and update the list
			playlists.remove(playlist);
			user.setPlaylists(playlists);
		} else {
			//TODO throw an exception
		}
	}

	@Override
	public ArrayList<Playlist> getPlaylistsByUser(User user) {
		// TODO Auto-generated method stub
		return (ArrayList<Playlist>) user.getPlaylists();
	}

	@Override
	public void sharePlaylist(Playlist playlist, User newUser) {
		// TODO Auto-generated method stub
		long id = newUser.getId();
		em.persist(playlist);
		List<User> usersWithRights = playlist.getUsers();
		usersWithRights.add(newUser);	
	}

}
