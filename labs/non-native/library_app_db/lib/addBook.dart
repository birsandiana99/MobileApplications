import 'package:flutter/material.dart';
import 'package:library_app/book-repo.dart';

import 'book.dart';
class AddBook extends StatefulWidget {
  BookRepo repo;
  AddBook({this.repo});
  @override
  _AddBookState createState() => _AddBookState(repo);
}

class _AddBookState extends State<AddBook> {
  BookRepo repo;
  _AddBookState(this.repo);
  final name = TextEditingController();
  final author = TextEditingController();
  final descr = TextEditingController();
  final landed = TextEditingController();
  final state = TextEditingController();
  @override
  Widget build(BuildContext context) {
    // print(repo.getBooks());
    return Scaffold(
      appBar: AppBar(
        title: Text("Add book"),
        centerTitle: true,
        backgroundColor: Color(0xFFF8BBD0),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          TextField(
            controller: name,
            decoration: const InputDecoration(
              hintText: 'Enter the name of the book',
              labelText: "Name"
            ),
          ),
          TextField(
            controller: author,
            decoration: const InputDecoration(
                hintText: 'Enter the author of the book',
                labelText: "Author"
            ),
          ),
          TextField(
            controller: descr,
            decoration: const InputDecoration(
                hintText: 'Enter the description of the book',
                labelText: "Description"
            ),
          ),
          TextField(
            controller: state,
            decoration: const InputDecoration(
                hintText: 'Enter the state of the book',
                labelText: "State"
            ),
          ),
          TextField(
            controller: landed,
            decoration: const InputDecoration(
                hintText: 'Is the book landed?',
                labelText: "Landed"
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 14.0),
            child: ElevatedButton(
              onPressed: () async{
                bool ok = repo.addBook(Book(author:author.text,name:name.text,description: descr.text,landed: landed.text,state: state.text));
                print(ok);
                print(repo.books.length);
                Navigator.pop(context);
              },
              child: Text('Add'),
            ),
          ),
        ],
      ),
      );
  }
}
