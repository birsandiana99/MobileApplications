import 'package:flutter/material.dart';
import 'package:library_app/editBook.dart';
import 'addBook.dart';
import 'book-repo.dart';
import 'book.dart';
import 'home.dart';
void main() {
  runApp(Library()
    // home: Home()
  );
}

class Library extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: "/home",
      routes:{
        '/home': (context) => Home(),
        '/add': (context) => AddBook(),
        '/edit': (context) => EditBook()
      }
    );
  }
}







