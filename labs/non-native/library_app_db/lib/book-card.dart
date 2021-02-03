import 'package:flutter/material.dart';
import 'package:library_app/deleteBook.dart';
import 'book-repo.dart';
import 'book.dart';

class BookCard extends StatelessWidget {
  Book book;
  BookRepo repo;
  BookCard({this.book, this.repo});
  @override
  Widget build(BuildContext context) {

    return Card(
      margin: EdgeInsets.fromLTRB(16.0, 16.0, 16.0, 16.0),
      child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            Text(
                book.name,
                style: TextStyle(
                  fontSize: 16.0,
                  color: Colors.purple[600],
                )
            ),
            SizedBox(height: 6.0),
            Text(
                book.author,
                style: TextStyle(
                  fontSize: 16.0,
                  color: Colors.purple[600],
                )
            ),
            ButtonTheme(
              // minWidth: 15.0,
              height: 5.0,
              padding: EdgeInsets.symmetric(horizontal: 5.0),
              child: RaisedButton(
                shape: new RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(0),
                ),
                color: Colors.deepPurple,
                onPressed: () {
                  Navigator.of(context).push(
                      MaterialPageRoute(
                          builder: (context) => DeleteBook(repo: repo,book:book)
                      ));
                },
                child: Text("Delete", style: TextStyle(fontSize: 12, color:Colors.white)),
              )),
            SizedBox(height: 6.0),
                ]
      ),
    );
  }
}