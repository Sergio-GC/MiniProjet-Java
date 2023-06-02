package ch.hevs.playlistservice;

import java.util.List;

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

	@Override
	public List<Singer> getSingers() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Singers s");
		return (List<Singer>) query.getResultList();
	}

	@Override
	public List<Album> getAlbums() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Albums a");
		return (List<Album>) query.getResultList();
	}

	@Override
	public List<Song> getSongs() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Songs s");
		return (List<Song>) query.getResultList();
	}

	@Override
	public List<Playlist> getPlaylists() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Playlists p");
		return (List<Playlist>) query.getResultList();
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select * from Users u");
		return (List<User>) query.getResultList();
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
	public List<Playlist> getPlaylistsByUser(User user) {
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
