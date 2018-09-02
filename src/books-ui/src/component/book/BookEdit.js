import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../AppNavbar';

class BookEdit extends Component {

    emptyItem = {
        name: "",
        description: "",
        comments: [],
        author: {},
        genre: {},
        photo: null
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            genres: [],
            authors: []
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeAuthor = this.handleChangeAuthor.bind(this);
        this.handleChangeGenre = this.handleChangeGenre.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const book = await (await fetch(`/rest/book/${this.props.match.params.id}`)).json();
            this.setState({item: book});
        }
        fetch("/v2/genre")
            .then(response => response.json())
            .then(data => this.setState({
                genres: data
            }));
        fetch("/v2/author")
            .then(response => response.json())
            .then(data => this.setState({
                authors: data
            }));
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleChangeGenre(event) {
        const target = event.target;
        const {value} = target;
        let item = this.state.item;

        fetch(`/v2/genre/name/${value}`)
            .then(response => response.json())
            .then(data => {
                item.genre = data;
                this.setState({item});
            });
    }

    handleChangeAuthor(event) {
        const target = event.target;
        const {value} = target;
        const fullName = value.split(" ");
        const name = fullName[0];
        const surname = fullName[1];
        let item = this.state.item;

        fetch(`/v2/author/fullname/${name}/${surname}`)
            .then(response => response.json())
            .then(data => {
                item.author = data;
                this.setState({item});
            });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        const methodName = (item.id) ? 'PUT' : 'POST';
        const uri = (item.id) ? `/v2/book/${item.id}` : `/v2/book`;

        await fetch(uri, {
            method: methodName,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/books');
    }

    static setDefaultOption(item) {
        if (!item.name) {
            return <option>---</option>
        }
    }

    render() {
        const {item, genres, authors} = this.state;
        const {genre} = item;
        const {author} = item;
        const title = <h2>{item.id ? 'Edit Book' : 'Add Book'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={this.handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="genre">Genre</Label>
                        <Input type="select" name="genre" id="genre" value={genre.name}
                               onChange={this.handleChangeGenre}>
                            {BookEdit.setDefaultOption(genre)}
                            {genres.map(g => {
                                return <option key={g.id}>
                                    {g.name}
                                </option>
                            })}
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="author">Author</Label>
                        <Input type="select" name="author" id="author"
                               value={(author.id) ? `${author.name} ${author.surname}` : "Select"}
                               onChange={this.handleChangeAuthor}>
                            {BookEdit.setDefaultOption(author)}
                            {authors.map(a => {
                                return <option key={a.id}>
                                    {`${a.name} ${a.surname}`}
                                </option>
                            })}
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(BookEdit);