class Book{
    String name;
    String author;
    String landed;
    String description;
    String state;

    Book({this.name, this.author, this.description, this.landed, this.state});

    @override
    String toString(){
        return this.name + " " + this.author;
    }
}