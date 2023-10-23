package com.example.libraryservice.model;

public class Views {
    public interface BookView {
        public interface Get {}
        public interface Post {}
        public interface Put {}
    }

    public interface AuthorView {
        public interface Get {}
        public interface Post {}
        public interface Put {}
    }

    public interface LibraryUserView {
        public interface Get {}
        public interface Post {}
        public interface Put {}
    }

    public interface BookViewWithLibraryUserAndAuthor extends BookView.Get, LibraryUserView.Get, AuthorView.Get {}
    public interface AuthorViewWithBook extends AuthorView.Get, BookView.Get {}
    public interface LibraryUserViewWithBook extends LibraryUserView.Get, BookView.Get {}
}
