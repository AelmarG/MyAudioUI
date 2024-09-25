// Aelmar Gewarges 501185723

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	//I've now succeeded in doing exactly what was stated above WOOHOO YIPPEEE <3

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>();
		playlists   = new ArrayList<Playlist>();
		
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) throws ContentAlreadyDownloadedException
	{
		
		if(content.getType() == Song.TYPENAME && !songs.contains(content))
		{
			songs.add((Song) content);
			
		}
		
		else if(content.getType() == AudioBook.TYPENAME && !audiobooks.contains(content))
		{
			audiobooks.add((AudioBook) content);
			
		}
		
		else
		{
			throw new ContentAlreadyDownloadedException(content.getType() + " " + content.getTitle() + " already downloaded");
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print(index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i<audiobooks.size(); i++)
		{
			int index = i+1;
			System.out.print(index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i<playlists.size(); i++)		//goes thru every playlist
		{
			int index = i+1;
			System.out.print(index + ". ");						//prints the index of the current playlist
			System.out.print(playlists.get(i).getTitle());		//prints the playlist title
			System.out.println();
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names
		
		ArrayList<String> artists = new ArrayList<String>();  //makes new artist list
		
		for(int i = 0; i<songs.size(); i++)		//goes through each song downloaded
		{
			if (!artists.contains(songs.get(i).getArtist()))	//checks to see if the artist of the current song isn't in the list
			{
				artists.add(songs.get(i).getArtist());			//if not, they are added
			}
		}
		
		for(int i = 0; i < artists.size(); i++)		//goes thru every artist
		{
			int index = i+1;
			System.out.print("" + index + ". ");	//prints the index of the current artist
			System.out.print(artists.get(i));		//prints the artist
			System.out.println();
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)	throws SongNotFoundException
	{
		if (this.songs.size() == 0 || index < 1 || index > songs.size())		//checks if library is empty or if given index outside of song lists index
		{
			throw new SongNotFoundException("Song not found");
		}
		
		for (int i = 0; i<playlists.size(); i++) // goes through each playlist
		{

			if (playlists.get(i).getContent().contains(this.songs.get(index-1))) // sees if song in library is in playlist
			{
				playlists.get(i).getContent().remove(this.songs.get(index-1)); // if so, removes song from playlist
			}
				
		}
		
		if (this.songs.contains(this.songs.get(index-1)))
		{
			this.songs.remove(index-1); // removes the song from the library
			
		}
		
		
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(this.songs, new SongYearComparator());		// sorts the songs by year using the SongYearComparator class
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			return a.getYear()-b.getYear();			//subtracts the years of the 2 songs & returns an int (positive means a > b)
		}
		
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
		Collections.sort(this.songs, new SongLengthComparator());	// sorts the songs by length using the SongLengthComparator class
	}
	
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		
		public int compare(Song a, Song b)
		{
			return a.getLength()-b.getLength();		//subtracts the lengths of the 2 songs & returns an int (positive means a > b)
		}
		
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(this.songs);		//uses the sorting method of the Songs class
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws SongNotFoundException
	{
		if (index < 1 || index > songs.size())		//checks if the index given is outside the range of the songs downloaded
		{
			 throw new SongNotFoundException("Song not found");
		
		}
		songs.get(index-1).play();		//plays the song if it is in the songs list
		
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)	throws AudioBookNotFoundException, ChapterNotFoundException
	{
		if (index < 1 || index > audiobooks.size())		//checks if index given is in the range of the audiobooks list
		{
			throw new AudioBookNotFoundException("Audiobook not found");
		}
		if (chapter < 1 || chapter > audiobooks.get(index-1).getChapterTitles().size())		//checks if chapter is in the book
		{
			throw new ChapterNotFoundException("Chapter not found");
		}
		
		audiobooks.get(index-1).selectChapter(chapter);		// selects the specified chapter of the specified book
		audiobooks.get(index-1).play();						// plays the chapter of the book
		
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) throws AudioBookNotFoundException
	{
		if (index < 1 || index > audiobooks.size())		//checks if index given is in the range of the audiobooks list
		{
			throw new AudioBookNotFoundException("Audiobook not found");
		}
		audiobooks.get(index-1).printTOC();		//prints the TOC of the book indicated
		
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)	throws PlaylistAlreadyExistsException
	{
		Playlist newPL = new Playlist(title);		// makes an instance of the playlist
		
		if (!this.playlists.contains(newPL))		// if the playlist doesn't exist in the playlist it runs the code below
		{
			this.playlists.add(newPL);				// adds the new playlist in the arraylist of playlists
			
		}
		
		else
		{
			throw new PlaylistAlreadyExistsException("Playlist already exists");
		}
		
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws PlaylistNotFoundException
	{
		Playlist PL = new Playlist(title);		// makes an instance of the playlist
		
		if(!this.playlists.contains(PL))	//checks if playlist exists
		{
			throw new PlaylistNotFoundException("Playlist not found");
		}

		for (int i = 0; i<this.playlists.size(); i++)
		{
			if (this.playlists.get(i).getTitle().equals(title))
			{
				this.playlists.get(i).printContents();
				
			}
			
		}
	
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws PlaylistNotFoundException
	{
		
		Playlist PL = new Playlist(playlistTitle);		// makes an instance of the playlist
		
		if(!this.playlists.contains(PL))	//checks if playlist exists
		{
			throw new PlaylistNotFoundException("Playlist not found");
		}
		
		for (int i=0; i<this.playlists.size(); i++)
		{
			if (this.playlists.get(i).getTitle().equals(playlistTitle))
			{
				this.playlists.get(i).playAll();
				
			}
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws PlaylistNotFoundException, AudioContentNotFoundException
	{
		
		Playlist PL = new Playlist(playlistTitle);		// makes an instance of the playlist
		
		if(!this.playlists.contains(PL))	//checks if playlist exists
		{
			throw new PlaylistNotFoundException("Playlist not found");
		}
		
		
		for (int i = 0; i<this.playlists.size(); i++)
		{
			
			if (this.playlists.get(i).getTitle().equals(playlistTitle))
			{
				
				if (indexInPL >= 1 && indexInPL <= this.playlists.get(i).getContent().size())
				{
					this.playlists.get(i).play(indexInPL);	
				}
				
				else
				{
					throw new AudioContentNotFoundException("Content not found");
				}
			}
		}
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws PlaylistNotFoundException, TypeNotFoundException, SongNotFoundException, AudioBookNotFoundException
	{
		
		Playlist PL = new Playlist(playlistTitle);		// makes an instance of the playlist
		
		if(!this.playlists.contains(PL))	//checks if playlist exists
		{
			throw new PlaylistNotFoundException("Playlist not found");
		}
		
		if (!(type.equalsIgnoreCase("song") || type.equalsIgnoreCase("audiobook"))) 		//checks to see if given type is song or audiobook
		{
			throw new TypeNotFoundException("Type not found");
		}
		
		
		for (int i = 0; i<this.playlists.size(); i++)
		{
			
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				
				switch(type.toLowerCase())
				{
				
				case "song":
					
					if (index < 1 || index > songs.size())	//checks if given index is valid
					{
						throw new SongNotFoundException("Song not found");
					}
					
					this.playlists.get(i).addContent(songs.get(index-1));
					break;
					
				case "audiobook":
					
					if (index < 1 || index > audiobooks.size())		//checks if given index is valid
					{
						throw new AudioBookNotFoundException("Audiobook not found");
					}
					
					this.playlists.get(i).addContent(audiobooks.get(index-1));
					break;
				}		
			}
		}
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws  AudioContentNotFoundException, PlaylistNotFoundException
	{
		
		Playlist PL = new Playlist(title);		// makes an instance of the playlist
		
		if(!this.playlists.contains(PL))	//checks if playlist exists
		{
			throw new PlaylistNotFoundException("Playlist not found");
		}
		
		for (int i = 0; i<this.playlists.size(); i++)
		{
			if(this.playlists.get(i).getTitle().equals(title))
			{
				if (index >= 1 && index <= this.playlists.get(i).getContent().size())
				{
					this.playlists.get(i).deleteContent(index);
					
				}
				
				else
				{
					throw new AudioContentNotFoundException("Content not found");
				}
				
			}	
		}
	}
	
	public void titleSearch(String title)
	{
		
	}
	
}

//All created exception classes
class AudioContentNotFoundException extends RuntimeException
{
	public AudioContentNotFoundException() {}
	
	public AudioContentNotFoundException(String message)
	{
		super(message);
	}
}

class PlaylistNotFoundException extends RuntimeException
{
	public PlaylistNotFoundException() {}
	
	public PlaylistNotFoundException(String message)
	{
		super(message);
	}
}

class PlaylistAlreadyExistsException extends RuntimeException
{
	public PlaylistAlreadyExistsException() {}
	
	public PlaylistAlreadyExistsException(String message)
	{
		super(message);
	}
}

class ChapterNotFoundException extends RuntimeException
{
	public ChapterNotFoundException() {}
	
	public ChapterNotFoundException(String message)
	{
		super(message);
	}
}

class SongNotFoundException extends RuntimeException
{
	public SongNotFoundException() {}
	
	public SongNotFoundException(String message)
	{
		super(message);
	}
}

class AudioBookNotFoundException extends RuntimeException
{
	public AudioBookNotFoundException() {}
	
	public AudioBookNotFoundException(String message)
	{
		super(message);
	}
}

class TypeNotFoundException extends RuntimeException
{
	public TypeNotFoundException() {}
	
	public TypeNotFoundException(String message)
	{
		super(message);
	}
}

class ContentAlreadyDownloadedException extends RuntimeException
{
	public ContentAlreadyDownloadedException() {}
	
	public ContentAlreadyDownloadedException(String message)
	{
		super(message);
	}
}