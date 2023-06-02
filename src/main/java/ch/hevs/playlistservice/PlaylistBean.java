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
		Song nr = new Song("Norwegian Reggaeton", 69, new Album());
		Song db = new Song("Drown me in Blood", 420, new Album());
		Song bs = new Song("Bones", 36, new Album());
		
		em.persist(nr);
		em.persist(db);
		em.persist(bs);
		
		Album bst = new Album("Bullshit album", 1969);
		List<Song> bullshitSongs = new ArrayList<Song>();
		bullshitSongs.add(nr);
		bullshitSongs.add(db);
		bst.setSongs(bullshitSongs);
		
		em.persist(bst);
		
		nr.setAlbum(bst);
		db.setAlbum(bst);
		
		Album dzn = new Album("DeezN", 1420);
		bullshitSongs = new ArrayList<Song>();
		bullshitSongs.add(bs);
		dzn.setSongs(bullshitSongs);
		
		em.persist(dzn);		
		bs.setAlbum(dzn);
		
		Singer singer1 = new Singer("Pre Malone");
		em.persist(singer1);
		
		Singer singer2 = new Singer("During Malone (?)");
		em.persist(singer2);
		
		User user1 = new User("Julienne");
		em.persist(user1);
		
		User user2 = new User("Sergio");
		em.persist(user2);
		
		Playlist fire = new Playlist("My playlist is, literally, fire :3", user1);
		Playlist ice = new Playlist("My playlist is not so literally fire :(", user2);
		
		em.persist(ice);
		em.persist(fire);
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
		Playlist playlist = new Playlist(playlistName, user);
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
			List<Integer> users = playlist.getUsers();
			for(int i : users) {
				Query q = em.createQuery("from Users u where u.id=:id");
				q.setParameter("id", i);
				User u = (User) q.getSingleResult();
				em.persist(u);
				
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
		return user.getPlaylists();
	}

	@Override
	public void sharePlaylist(Playlist playlist, User newUser) {
		// TODO Auto-generated method stub
		long id = newUser.getId();
		em.persist(playlist);
		List<Long> usersWithRights = playlist.getUsers();
		usersWithRights.add(id);
		
		
	}

}
