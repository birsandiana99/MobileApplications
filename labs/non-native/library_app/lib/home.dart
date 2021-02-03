import 'package:flutter/material.dart';
import 'package:library_app/addBook.dart';
import 'package:library_app/editBook.dart';
import 'book-card.dart';
import 'book.dart';
import 'book-repo.dart';
class Home extends StatefulWidget {
  @override
  // BookRepo repo;
  // Book b1 = Book(author:'auth1',name:'name1',description: "desc1",landed: "yes",state: "old");
  // repo.addBook(b1);
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  List<Book> bookss =[
    Book(author:'auth1',name:'name1',description: "desc1",landed: "yes",state: "old"),
    Book(author:'auth2',name:'name2',description: "desc1",landed: "yes",state: "old"),
    Book(author:'auth3',name:'name3',description: "desc1",landed: "yes",state: "old"),
    Book(author:'auth4',name:'name4',description: "desc1",landed: "yes",state: "old"),
    Book(author:'auth55',name:'name5',description: "desc1",landed: "yes",state: "old"),
  ];
  BookRepo repo = BookRepo(books:new List<Book>());

  @override
  Widget build(BuildContext context) {
    repo.books = bookss;
    return Scaffold(
      appBar: AppBar(
        title: Text("Library"),
        centerTitle: true,
        backgroundColor: Color(0xFFF8BBD0),
      ),
      body: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child: Column(
          children: repo.books.map((book) => GestureDetector(
              onTap: (){
                print(book.name);
                Navigator.of(context).push(
                    MaterialPageRoute(
                        builder: (context) => EditBook(repo: repo, book: book)
                    )
                );
              },
              child: BookCard(book:book, repo:repo)),
          ).toList(),
        ),
      ),
      floatingActionButton: FloatingActionButton(
          onPressed: () {
              Navigator.of(context).push(
                  MaterialPageRoute(
                      builder: (context) => AddBook(repo: repo)
                  )
              );
            print('you clicked the add button');
          },
          // child: Text("add"),
          child: Icon(
            Icons.add,
            color: Colors.pink[900],
          ),
          backgroundColor: Colors.pink[300]
      ),
    );
  }
}
