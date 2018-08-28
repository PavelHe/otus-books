import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";

export default class GenreList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            genres: []
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({
            isLoading: true
        });

        fetch("/rest/genre")
            .then(response => response.json())
            .then(data => this.setState({
                genres: data,
                isLoading: false
            }));
    }

    async remove(id) {
        await fetch(`/rest/genre/${id}`, {
            method: "DELETE",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateGenres = [...this.state.genres].filter(genre => genre.id !== id);
            this.setState({genres: updateGenres})
        })
    };

    render() {
        const {genres, isLoading} = this.state;

        if (isLoading)
            return <p>Loading...</p>;

        const genreList = genres.map(genre => {
            return <tr key={genre.id}>
                <td style={{whiteSpace: 'nowrap'}}>{genre.name}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/genre/" + genre.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(genre.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/genre/new">Add Genre</Button>
                    </div>
                    <h3>Genres</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {genreList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        )
    }
}