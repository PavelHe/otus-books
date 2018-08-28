import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../AppNavbar';

class CommentEdit extends Component {

    constructor(props) {
        super(props);
        this.state = {
            item: {},
            bookId: null
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const genre = await (await fetch(`/rest/comment/${this.props.match.params.id}`)).json();
            this.setState({item: genre});
        }
        this.setState({
            bookId: this.props.location.state.bookId
        })
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item, bookId} = this.state;
        const methodName = (item.id) ? 'PUT' : 'POST';
        const uri = (item.id) ? `/rest/comment/${item.id}/${bookId}` : `/rest/comment/${bookId}`;

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

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Comment' : 'Add Comment'}</h2>;

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
                        <Label for="name">Text</Label>
                        <Input type="text" name="text" id="text" value={item.text || ''}
                               onChange={this.handleChange} autoComplete="text"/>
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

export default withRouter(CommentEdit);