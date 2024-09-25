// Aelmar Gewarges 501185723

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents;
		private Map<String, Integer> titleMap = new HashMap<String, Integer>();
		private Map<String, ArrayList<Integer>> artistMap = new HashMap<String, ArrayList<Integer>>();
		private Map<String, ArrayList<Integer>> genreMap = new HashMap<String, ArrayList<Integer>>();
		
		public AudioContentStore()
		{
			
			//tries to make the store and catches any potential errors
			try
			{
				storeMaker();
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
			titleMap = makeTitleMap();
			artistMap = makeArtistMap();
			genreMap = makeGenreMap();
		}
		
		private ArrayList<AudioContent> storeMaker() throws IOException
		{
			contents = new ArrayList<AudioContent>();
			
			  // Create some songs audiobooks and podcasts and to store
				FileReader reader = new FileReader("./src/store.txt");
				Scanner scanner = new Scanner(reader);
				
				
				//grab data for content 
				while (scanner.hasNextLine())
				{
					switch(scanner.nextLine())
					{
					//if current content is song
					case "SONG":
						System.out.println("LOADING SONG");

						//all needed variables for the song constructor
						String id = scanner.nextLine();
						String title = scanner.nextLine();
						int year = Integer.parseInt(scanner.nextLine());
						int length = Integer.parseInt(scanner.nextLine());
						String artist = scanner.nextLine();
						String composer = scanner.nextLine();
						Song.Genre genre = Song.Genre.valueOf(scanner.nextLine());
						int lyricCount = Integer.parseInt(scanner.nextLine());
						String lyrics = "";
						
						for (int i = 0; i < lyricCount; i++)	//goes thru each lyric in the count
						{
							lyrics += scanner.nextLine() + "\n";	//adds the current line to the lyric string
						}
						
						contents.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, genre, lyrics));	//makes the song and adds it to the contents list
						break;
						
					// if current content is audiobook	
					case "AUDIOBOOK":
						System.out.println("LOADING AUDIOBOOK");

						//all needed variables for the audiobook constructor
						id = scanner.nextLine();
						title = scanner.nextLine();
						year = Integer.parseInt(scanner.nextLine());
						length = Integer.parseInt(scanner.nextLine());
						String author = scanner.nextLine();
						String narrator = scanner.nextLine();
						int chapterCount = Integer.parseInt(scanner.nextLine());
						
						//arraylists for the books chapter titles and contents
						ArrayList<String> chapTitles = new ArrayList<String>();
						ArrayList<String> chapContent = new ArrayList<String>();
						
						
						for (int chapter = 0; chapter < chapterCount; chapter++)	//goes thru each chapter of the current book
						{
							String currentTitle = scanner.nextLine();
							chapTitles.add(currentTitle);	//adds the title into the chapter titles arraylist
						}
						
						for (int chapter = 0; chapter < chapterCount; chapter++)	//goes thru each chaoter of the current book
						{
							String chapterContent = "";
							int ChapterLineCount = Integer.parseInt(scanner.nextLine());	//sets the line count to the next integer

							for (int content = 0; content < ChapterLineCount; content++)	//goes thru each line in the line count
							{
								chapterContent += scanner.nextLine() + "\n";	//adds it to the string of the chapter's content
							}
							 
							chapContent.add(chapterContent);	//adds the current chapter's content into the arraylist
						}
						
						contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapTitles, chapContent));	//makes the book and adds it to the content list
						break;
					}
					
				}
				scanner.close();
				reader.close();
				return contents;
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		//makes the map that maps a contents title to its content
		public Map<String, Integer> makeTitleMap()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				if(!titleMap.containsKey(contents.get(i).getTitle()))	//checks to see if map doesnt contain the current audio content
				{
					titleMap.put(contents.get(i).getTitle(), i);		//if not already in, the content gets added
				}
			}
			
			return titleMap;
		}
		
		public Map<String, ArrayList<Integer>> makeArtistMap()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				switch(contents.get(i).getType())
				{
					case("SONG"):
						Song song = (Song) contents.get(i);
						
						if(!artistMap.containsKey(song.getArtist()))	//if current artist is not in the map
						{
							artistMap.put(song.getArtist(), new ArrayList<Integer>());	//adds the artist to the map with a new empty list
							
							ArrayList<Integer> list = artistMap.get(song.getArtist());	//takes the empty list and adds the current index +1 to it (the first instance of the artist)
							list.add(i+1);
							
							artistMap.put(song.getArtist(), list);	//puts the list with the current index back into the map
						}
						
						else	//if the map contains the current artist
						{
							ArrayList<Integer> list = artistMap.get(song.getArtist());	//takes the list mapped to the artist
							list.add(i+1);	//adds the current index +1 to it
							
							artistMap.put(song.getArtist(), list);	//puts the updated list into the map
						}
						
					case("AUDIOBOOK"):
						if(contents.get(i).getType().equalsIgnoreCase("audiobook"))		//line had to be made for code to function
						{
							AudioBook book = (AudioBook) contents.get(i);
							if(!artistMap.containsKey(book.getAuthor()))	//if current author is not in the map
							{
								artistMap.put(book.getAuthor(), new ArrayList<Integer>());	//adds the artist to the map with a new empty list
								
								ArrayList<Integer> list = artistMap.get(book.getAuthor());	//takes the empty list and adds the current index +1 to it
								list.add(i+1);
								
								artistMap.put(book.getAuthor(), list);	//puts the list with the current index back into the map
							}
						
							else	//if the map contains the current author
							{
								ArrayList<Integer> list = artistMap.get(book.getAuthor());	//takes the list mapped to the author and adds the current index +1 to it
								list.add(i+1);		
						
								artistMap.put(book.getAuthor(), list);	//puts the updated list into the map
							}
						}
				}
			}
			
			return artistMap;
		}
		
		public Map<String, ArrayList<Integer>> makeGenreMap()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				switch(contents.get(i).getType().toLowerCase())
				{
					case("song"):
						Song song = (Song) contents.get(i);
					
						if(!genreMap.containsKey(song.getGenre().toString())) 	// if the current genre is not in the map
						{
							genreMap.put(song.getGenre().toString(), new ArrayList<Integer>());		//adds the genre to the map with an empty list
							
							ArrayList<Integer> list = genreMap.get(song.getGenre().toString());		// takes the empty list and adds the current index +1 to it
							list.add(i+1);	
							
							genreMap.put(song.getGenre().toString(), list);		//puts the updated list back into the map
							
						}
						
						else
						{
							ArrayList<Integer> list = genreMap.get(song.getGenre().toString());		//takes the list and adds the current index +1 to it
							list.add(i+1);
							
							genreMap.put(song.getGenre().toString(), list);		//puts the updated list back into the map
						}
					
					case("audiobook"):	//when the content is an audiobook it breaks as they do not have genres
						break;
				}
			}
			
			return genreMap;
		}
		
		//this searches for a content title
		public void searchContent(Map<String, Integer> titleMap, String title) throws TitleNotFoundException
		{
			Set<String> titleKeys = titleMap.keySet();
	
			if(!titleMap.containsKey(title))		//checks if the given title exists in the map
			{
				throw new TitleNotFoundException(title + " not found");	//if not, throws exception
			}
	
			for (String content : titleKeys)		//goes through each piece of content
			{
	
				if(content.equals(title))			//if the current content matches the given title
				{
					System.out.print(titleMap.get(title)+1 + ". ");		//prints the index of the content
					getContent(titleMap.get(title)+1).printInfo();		//prints the info of the content
					System.out.println();
				}
			}
		}

		//this searches for content of an artist
		public void searchArtist(Map<String, ArrayList<Integer>> artistMap, String name) throws ArtistNotFoundException
		{
	
			Set<String> artistKeys = artistMap.keySet();	//makes a set of the keys
	
			if(!artistMap.containsKey(name))	//checks if the given name exists in the map
			{
				throw new ArtistNotFoundException(name + " not found");		//if not, throws an exeption
			}
	
			for (String artist : artistKeys)	//goes through each artist in the map
			{
				if (artist.equals(name))		//if the current artist is the given artist
				{
					for (int i = 0; i < artistMap.get(name).size(); i++)	//goes through each index of the artist
					{
						System.out.print(artistMap.get(name).get(i) + ". ");		//prints out the index of the current content
						getContent(artistMap.get(name).get(i)).printInfo();			//prints out the info of the current content
						System.out.println();
					}
				}
			}
		}
	
		//this searches for songs of a genre
		public void searchGenre(Map<String, ArrayList<Integer>> genreMap, String name) throws GenreNotFoundException
		{
	
			Set<String> genreKeys = genreMap.keySet();	//makes a set of the keys
	
			if(!genreMap.containsKey(name))		//checks if the given name exists in the map
			{
				throw new GenreNotFoundException(name + " not found");		//if not, throws an exeption
			}
	
			for (String genre : genreKeys)	//goes through each artist in the map
			{
				if (genre.equals(name))		//if the current artist is the given artist
				{
					for (int i = 0; i < genreMap.get(name).size(); i++)		//goes through each index of the genre
					{
						System.out.print(genreMap.get(name).get(i) + ". ");		//prints out the index of the current content
						getContent(genreMap.get(name).get(i)).printInfo();		//prints out the info of the current content
						System.out.println();
					}
				}
			}
		}

		//returns the titleMap for use
		public Map<String, Integer> getTitleMap()
		{
			return this.titleMap;
		}
				
		//returns the artistMap for use
		public Map<String, ArrayList<Integer>> getArtistMap()
		{
			return this.artistMap;
		}

		//returns the genreMap for use
		public Map<String, ArrayList<Integer>> getGenreMap()
		{
			return this.genreMap;
		}

		//returns the arrayList of a given artist
		public ArrayList<Integer> getSpecificArtist(String name) throws ArtistNotFoundException
		{
			Set<String> artistKeys = this.artistMap.keySet();
			ArrayList<Integer> artistContent = new ArrayList<Integer>();

			if (!artistKeys.contains(name))
			{
				throw new ArtistNotFoundException(name + " not found");
			}

			for (String artist : artistKeys)
			{
				if (artist.equals(name))
				{
					artistContent = this.artistMap.get(name);
				}
			}

			return artistContent;
		}

		//returns the arrayList of a given genre
		public ArrayList<Integer> getSpecificGenre(String name) throws GenreNotFoundException
		{
			Set<String> genreKeys = this.genreMap.keySet();
			ArrayList<Integer> genreContent = new ArrayList<Integer>();

			if (!genreKeys.contains(name))
			{
				throw new GenreNotFoundException(name + " not found");
			}

			for (String genre : genreKeys)
			{
				if (genre.equals(name))
				{
					genreContent = this.genreMap.get(name);
				}
			}

			return genreContent;
		}
}

class TitleNotFoundException extends RuntimeException
{
	public TitleNotFoundException() {}
	
	public TitleNotFoundException(String message)
	{
		super(message);
	}
}
class ArtistNotFoundException extends RuntimeException
{
	public ArtistNotFoundException() {}
	
	public ArtistNotFoundException(String message)
	{
		super(message);
	}
}

class GenreNotFoundException extends RuntimeException
{
	public GenreNotFoundException() {}
	
	public GenreNotFoundException(String message)
	{
		super(message);
	}
}