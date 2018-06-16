package com.app.books.booksapp;

public class RowItem {
    private String author;
    private String book_name;
    private String youtube_link;
    
    public RowItem(String author, String book_name, String youtube_link) {
        this.author = author;
        this.book_name = book_name;
        this.youtube_link = youtube_link;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getYoutube_link() {
		return youtube_link;
	}

	public void setYoutube_link(String youtube_link) {
		this.youtube_link = youtube_link;
	}
  
}