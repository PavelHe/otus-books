import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";

export default class BookList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            books: []
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({
            isLoading: true
        });

        fetch("/v2/book")
            .then(response => response.json())
            .then(data => this.setState({
                books: data,
                isLoading: false
            }));
    }

    async remove(id) {
        await fetch(`/v2/book/${id}`, {
            method: "DELETE",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateBooks = [...this.state.books].filter(book => book.id !== id);
            this.setState({
                books: updateBooks
            })
        })
    }

    render() {
        const {books, isLoading} = this.state;

        if (isLoading)
            return <p>Loading...</p>;

        const bookList = books.map(book => {
            return <tr key={book.id}>
                <td style={{whiteSpace: 'nowrap'}}>{book.name}</td>
                <td style={{whiteSpace: 'nowrap'}}>{book.description}</td>
                <td style={{whiteSpace: 'nowrap'}}>{book.author.name} {book.author.surname}</td>
                <td style={{whiteSpace: 'nowrap'}}>{book.genre.name}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="warning" tag={Link}
                                to={{pathname: "/comments", state: {comments: book.comments, bookId: book.id}}}>Comments</Button>
                        <Button size="sm" color="primary" tag={Link} to={"/book/" + book.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(book.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/book/new">Add Book</Button>
                    </div>
                    <h3>Books</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Description</th>
                            <th width="20%">Author</th>
                            <th width="20%">Genre</th>
                        </tr>
                        </thead>
                        <tbody>
                        {bookList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        )
    }

}