import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";

export default class AuthorList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            authors: []
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({
            isLoading: true
        });
        fetch("/rest/author")
            .then(response => response.json())
            .then(data => this.setState({
                authors: data,
                isLoading: false
            }))
    }

    async remove(id) {
        await fetch(`/rest/author/${id}`, {
            method: "DELETE",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateAuthors = [...this.state.authors].filter(author => author.id !== id);
            this.setState({
                authors: updateAuthors
            })
        })
    }

    render() {
        const {authors, isLoading} = this.state;

        if (isLoading)
            return <p>Loading...</p>;

        const authorList = authors.map(author => {
            return <tr key={author.id}>
                <td style={{whiteSpace: 'nowrap'}}>{author.name}</td>
                <td style={{whiteSpace: 'nowrap'}}>{author.surname}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/author/" + author.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(author.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/author/new">Add Author</Button>
                    </div>
                    <h3>Authors</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Surname</th>
                        </tr>
                        </thead>
                        <tbody>
                        {authorList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        )
    }

}