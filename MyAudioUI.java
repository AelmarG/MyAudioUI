// Aelmar Gewarges 501185723

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary

			AudioContentStore store = new AudioContentStore();
			
			// Create my music mylibrary
			Library mylibrary = new Library();
	
			Scanner scanner = new Scanner(System.in);
			System.out.print(">");
	
			// Process keyboard actions
		
			while (scanner.hasNextLine())
			{
				try
				{
					String action = scanner.nextLine();
		
					if (action == null || action.equals("")) 
					{
						System.out.print("\n>");
						continue;
					}
					else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
						return;
					
					else if (action.equalsIgnoreCase("STORE"))	// List all songs
					{
						store.listAll(); 
					}
					else if (action.equalsIgnoreCase("SONGS"))	// List all items
					{
						mylibrary.listAllSongs(); 
					}
					else if (action.equalsIgnoreCase("BOOKS"))	// List all books
					{
						mylibrary.listAllAudioBooks(); 
					}
					else if (action.equalsIgnoreCase("ARTISTS"))	// List all artists
					{
						mylibrary.listAllArtists(); 
					}
					else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
					{
						mylibrary.listAllPlaylists(); 
					}
					// Download audiocontent (song/audiobook/podcast) from the store 
					// Specify the index of the content
					else if (action.equalsIgnoreCase("DOWNLOAD")) 
					{
						int indexStart = 0;
						int indexEnd = 0;
						
						System.out.print("From Store Content #: ");
						if (scanner.hasNextInt())
						{
							indexStart = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
							
							System.out.print("To Store Content #: ");
							if (scanner.hasNextInt())
							{
								indexEnd = scanner.nextInt();
								scanner.nextLine();
							}
						}
						
						while (indexStart <= indexEnd)
						{
							//tries to download the songs within the range
							try
							{
							AudioContent content = store.getContent(indexStart);
							mylibrary.download(content);
							System.out.println(content.getType() + " " + content.getTitle() + " added to library");
							}
							
							//catches any songs already downloaded and prints a message explaining this
							catch (ContentAlreadyDownloadedException e)
							{
								System.out.println(e.getMessage());
							}							
							
							//goes to next index
							indexStart++;
						}
											
					}
					
					else if (action.equalsIgnoreCase("DOWNLOADA"))
					{
						String name = "";
						
						System.out.print("Artist Name: ");
						if (scanner.hasNextLine())
						{
							name = scanner.nextLine();
							
							//goes through each piece of content of the given artist
							for (int i = 0; i < store.getSpecificArtist(name).size(); i++)
							{
								//tries to download the current content in the list of contents of the given artist
								try
								{
									AudioContent content = store.getContent(store.getSpecificArtist(name).get(i));
									mylibrary.download(content);
									System.out.println(content.getType() + " " + content.getTitle() + " added to library");
								}

								//catches any content already downloaded
								catch(ContentAlreadyDownloadedException e)
								{
									System.out.println(e.getMessage());
								}
							}
							
						}
					}
					
					else if (action.equalsIgnoreCase("DOWNLOADG"))
					{
						String genre = "";
						
						System.out.print("Genre [POP, ROCK, JAZZ, RAP]: ");
						if (scanner.hasNext())
						{
							genre = scanner.next();
							
							//goes through each song in the given genre
							for (int i = 0; i < store.getSpecificGenre(genre).size(); i++)
							{
								//tries to download the current song in the list of songs in the given genre
								try
								{
									AudioContent content = store.getContent(store.getSpecificGenre(genre).get(i));
									mylibrary.download(content);
									System.out.println(content.getType() + " " + content.getTitle() + " added to library");
								}

								//catches any songs already downloaded
								catch(ContentAlreadyDownloadedException e)
								{
									System.out.println(e.getMessage());
								}
							}
							
						}
					}
					
					
					// Get the *library* index (index of a song based on the songs list)
					// of a song from the keyboard and play the song 
					else if (action.equalsIgnoreCase("PLAYSONG")) 
					{
						int song = 0;
						
						System.out.print("Song Number: ");
						if (scanner.hasNextInt())
						{
							song = scanner.nextInt();			//sets the song to the given index
							scanner.nextLine();
							
							
							mylibrary.playSong(song);
							
							
						}
						
					}
					// Print the table of contents (TOC) of an audiobook that
					// has been downloaded to the library. Get the desired book index
					// from the keyboard - the index is based on the list of books in the library
					else if (action.equalsIgnoreCase("BOOKTOC")) 
					{
						int book = 0;
						
						System.out.print("Book Number: ");
						
						if (scanner.hasNextInt())
						{
							book = scanner.nextInt(); 	//sets the book to the given index
							scanner.hasNextLine();
							
							
							mylibrary.printAudioBookTOC(book);
							
							
						}
						
					// Print error message if the book doesn't exist in the library
					}
					// Similar to playsong above except for audio book
					// In addition to the book index, read the chapter 
					// number from the keyboard - see class Library
					else if (action.equalsIgnoreCase("PLAYBOOK")) 
					{
						int book = 0;
						int chapter = 0;
						
						System.out.print("Book Number:");
						
						if (scanner.hasNextInt())
						{
							book = scanner.nextInt();	//sets the book to the first given number
							scanner.hasNextLine();
							
							System.out.print("Chapter Number:");
							
							if (scanner.hasNextInt())
							{
								chapter = scanner.nextInt();	//sets the chapter to the next given number
								scanner.hasNextLine();
								
								
								mylibrary.playAudioBook(book, chapter);
								
								
							}
						}
						
						
					}
		
					// Specify a playlist title (string) 
					// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PLAYALLPL")) 
					{
						String title = "";
						
						System.out.print("Playlist Title: ");
						
						if (scanner.hasNext())
						{
							title = scanner.next();
							scanner.hasNextLine();
											
							mylibrary.playPlaylist(title);
							
						}
					}
					// Specify a playlist title (string) 
					// Read the index of a song/audiobook/podcast in the playist from the keyboard 
					// Play all the audio content 
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PLAYPL")) 
					{
						String title = "";
						int index = 0;
						
						System.out.print("Playlist Title: ");
						
						if (scanner.hasNext())
						{
							title = scanner.next();
							
							System.out.print("Index: ");
							
							if (scanner.hasNextInt())
							{
								index = scanner.nextInt();
								
								mylibrary.playPlaylist(title, index);
								
							}
						}
					}
					// Delete a song from the list of songs in mylibrary and any play lists it belongs to
					// Read a song index from the keyboard
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("DELSONG")) 
					{
							int index = 0;
							
							System.out.print("Library Index: ");
							
							if (scanner.hasNextInt())
							{
								index = scanner.nextInt();			//sets index to number input
								scanner.hasNextLine();
								
								mylibrary.deleteSong(index);
								
							}
					}
					// Read a title string from the keyboard and make a playlist
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("MAKEPL")) 
					{
						String playlist = "";
						
						System.out.print("Playlist Title: ");
						
						if (scanner.hasNext())
						{
							playlist = scanner.next();			//sets title to what is typed
							scanner.hasNextLine();
												
							mylibrary.makePlaylist(playlist);
							
						}
					}
					// Print the content information (songs, audiobooks, podcasts) in the playlist
					// Read a playlist title string from the keyboard
				  // see class Library for the method to call
					else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
					{
						String playlist = "";		
						
						System.out.print("Playlist Title: ");
						
						if (scanner.hasNext())
						{
							playlist = scanner.next();		//sets playlist to what is input
							scanner.hasNextLine();
											
							mylibrary.printPlaylist(playlist);
							
						}
					}
					// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
					// Read the playlist title, the type of content ("song" "audiobook" "podcast")
					// and the index of the content (based on song list, audiobook list etc) from the keyboard
				  // see class Library for the method to call
					else if (action.equalsIgnoreCase("ADDTOPL")) 
					{
						String playlist = "";
						String type = "";
						int index = 0;
						
						System.out.print("Playlist: ");
						
						if (scanner.hasNext())
						{
							playlist = scanner.next();	//sets playlist to name input
							scanner.hasNextLine();
							
							System.out.print("Audio Type [SONG, AUDIOBOOK]: ");
							
							if (scanner.hasNext())
							{
								type = scanner.next();	//sets the audio content type to type input
								scanner.hasNextLine();
								
								System.out.print("Index: ");
								
								if (scanner.hasNext())
								{
									index = scanner.nextInt();	//sets index to number input
									scanner.hasNextLine();
																
									mylibrary.addContentToPlaylist(type, index, playlist);
									
								}
							}
						}
					}
					// Delete content from play list based on index from the playlist
					// Read the playlist title string and the playlist index
				  // see class Library for the method to call
					else if (action.equalsIgnoreCase("DELFROMPL")) 
					{
						String playlist = "";
						int index = 0;
						
						System.out.print("Playlist Title: ");
						
						if (scanner.hasNext())
						{
							playlist = scanner.next();		//sets playlist to name input
							
							System.out.print("Index: ");
							
							if (scanner.hasNextInt())
							{
								index = scanner.nextInt();		//sets index to number input
								
								mylibrary.delContentFromPlaylist(index, playlist);
								
							}
						}
					}
					
					else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
					{
						mylibrary.sortSongsByYear();
					}
					else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
					{
						mylibrary.sortSongsByName();
					}
					else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
					{
						mylibrary.sortSongsByLength();
					}
					
					else if (action.equalsIgnoreCase("SEARCH"))
					{
						String title = "";
						
						System.out.print("Title: ");
						
						if (scanner.hasNextLine())
						{
							title = scanner.nextLine();		//sets title to string input
							
							store.searchContent(store.getTitleMap(), title);
						}
					}
				
					else if (action.equalsIgnoreCase("SEARCHA"))
					{
						String name = "";

						System.out.print("Artist: ");

						if (scanner.hasNextLine())
						{
							name = scanner.nextLine();

							store.searchArtist(store.getArtistMap(), name);
						}
					}
					
					else if (action.equalsIgnoreCase("SEARCHG"))
					{
						String name = "";

						System.out.print("Genre [POP, ROCK, JAZZ, RAP]: ");

						if (scanner.hasNextLine())
						{
							name = scanner.nextLine();

							store.searchGenre(store.getGenreMap(), name);
						}
					}
				}

				catch (ContentAlreadyDownloadedException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (PlaylistNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (PlaylistAlreadyExistsException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (ChapterNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (SongNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (AudioBookNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch (TypeNotFoundException e)
				{
					System.out.println(e.getMessage());
				}

				catch (TitleNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
			
				catch(ArtistNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
				catch(GenreNotFoundException e)
				{
					System.out.println(e.getMessage());
				}
				
					
				System.out.print("\n>");
			}
		
	}
}