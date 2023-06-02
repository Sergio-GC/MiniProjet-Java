package ch.hevs.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Album")
public class Album {

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		private long id;
		@Column(name="title")
		private String title;
		@Column(name="year")
		private int year;
		
		// relations
		@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)//@JoinColumn(name = "FK_ALBUM")
		private List<Song> songs;

		@ManyToOne
		@JoinColumn(name = "FK_SINGER")
		private Singer singer;
		
		// id
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		// title
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		// year
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		
		// song (From Song)
		public List<Song> getSongs() {
			return songs;
		}
		public void setSongs(List<Song> songs) {
			this.songs = songs;
		}
		
		// singer (From Singer)
		public Singer getSinger() {
			return singer;
		}
		public void setSinger(Singer singer) {
			this.singer = singer;
		}
		
		// constructors
		public Album() {
		}
		public Album(String title, int year, Singer singer, List<Song> songs) {
			this.title = title;
			this.year = year;
			this.singer = singer;
			this.songs = songs;
		}
}
