import 'package:flutter/material.dart';
import 'package:library_app/book.dart';

import 'book-repo.dart';

class EditBook extends StatefulWidget {
  BookRepo repo;
  Book book;
  EditBook({this.repo, this.book});
  @override
  _EditBookState createState() => _EditBookState(repo,book);
}

class _EditBookState extends State<EditBook> {
  BookRepo repo;
  Book book;
  _EditBookState(this.repo, this.book);
  final name = TextEditingController();
  final author = TextEditingController();
  final descr = TextEditingController();
  final landed = TextEditingController();
  final state = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Edit book"),
        centerTitle: true,
        backgroundColor: Color(0xFFF8BBD0),
      ),
      body:Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Text(
            book.toString()
          ),
          TextFormField(
            controller: name,
            decoration: const InputDecoration(
                hintText: 'Enter the name of the book',
                labelText: "Name"
            ),
          ),
          TextFormField(
            controller: author,
            decoration: const InputDecoration(
                hintText: 'Enter the author of the book',
                labelText: "Author"
            ),
          ),
          TextFormField(
            controller: descr,
            decoration: const InputDecoration(
                hintText: 'Enter the description of the book',
                labelText: "Description"
            ),
          ),
          TextFormField(
            controller: state,
            decoration: const InputDecoration(
                hintText: 'Enter the state of the book',
                labelText: "State"
            ),
          ),
          TextFormField(
            controller: landed,
            decoration: const InputDecoration(
                hintText: 'Is the book landed?',
                labelText: "Landed"
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 14.0),
            child: ElevatedButton(
              onPressed: () {
                print(book);
                Book book2 = Book(author:author.text,name:name.text,description: descr.text,landed: landed.text,state: state.text);
                repo.editBook(book, book2);
                Navigator.pop(context, repo);
              },
              child: Text('Edit'),
            ),
          ),
        ],
      ),
    );
  }
}
