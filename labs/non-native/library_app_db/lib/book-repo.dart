import 'book.dart';

class BookRepo{
  List<Book> books;
  BookRepo({this.books});

  List<Book> getBooks(){
    return books;
}
  bool addBook(Book book) {
    print(findBook(book));
    if(findBook(book) == -1){
      books.add(book);
      return true;
    }
    else
      return false;

  }
  bool deleteBook(Book book){
    if(books.contains(book)){
      books.remove(book);
      return true;
    }
    else{
      return false;
    }

  }
  bool editBook(Book book, Book book2)
  {
    int index = findBook(book);
    books[index] = book2;
  }

  int findBook(Book book){
    for (int i=0;i<books.length;i++){
      if(books[i].name==book.name){
        return i;
      }
    }
    return -1;

  }
}

