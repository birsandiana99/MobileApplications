import 'package:flutter/material.dart';
import 'package:library_app/book-card.dart';

import 'book-repo.dart';
import 'book.dart';


class DeleteBook extends StatefulWidget {
  BookRepo repo;
  Book book;
  DeleteBook({this.repo, this.book});
  @override
  _DeleteBookState createState() => _DeleteBookState(repo,book);
}

class _DeleteBookState extends State<DeleteBook> {
  BookRepo repo;
  Book book;
  _DeleteBookState(this.repo, this.book);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Delete book"),
        centerTitle: true,
        backgroundColor: Color(0xFFF8BBD0),
      ),
      body: Column(
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
          SizedBox(height: 6.0),
          Text(
              book.description,
              style: TextStyle(
                fontSize: 16.0,
                color: Colors.purple[600],
              )
          ),
          SizedBox(height: 6.0),
          Text(
              book.landed,
              style: TextStyle(
                fontSize: 16.0,
                color: Colors.purple[600],
              )
          ),
          SizedBox(height: 6.0),
          Text(
              book.state,
              style: TextStyle(
                fontSize: 16.0,
                color: Colors.purple[600],
              )
          ),
          ButtonTheme(
              height: 5.0,
              padding: EdgeInsets.symmetric(horizontal: 5.0),
              child: RaisedButton(
                shape: new RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(0),
                ),
                color: Colors.deepPurple,
                onPressed: () {
                  repo.deleteBook(book);
                  Navigator.pop(context, repo);
                },
                child: Text("Delete", style: TextStyle(fontSize: 12, color:Colors.white)),
              )),
          SizedBox(height: 6.0),
        ]));
  }
}