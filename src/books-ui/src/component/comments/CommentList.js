import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";

export default class CommentList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            comments: {},
            bookId: null
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({
            comments: this.props.location.state.comments,
            bookId: this.props.location.state.bookId,
            isLoading: false
        })
    }

    async remove(id) {
        await fetch(`/rest/comment/${id}`, {
            method: "DELETE",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateComments = [...this.state.comments].filter(comment => comment.id !== id);
            this.setState({comments: updateComments})
        })
    };


    render() {
        const {comments, isLoading, bookId} = this.state;
        if (isLoading)
            return <p>Loading...</p>;

        const commentList = comments.map(comment => {
            return <tr key={comment.id}>
                <td style={{whiteSpace: 'nowrap'}}>{comment.name}</td>
                <td style={{whiteSpace: 'nowrap'}}>{comment.text}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={{pathname:"/comment/" + comment.id, state: {bookId: bookId}}}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(comment.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={{pathname: "/comment/new", state: {bookId: bookId}}}>Add
                            Comment</Button>
                    </div>
                    <h3>Comments</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Text</th>
                        </tr>
                        </thead>
                        <tbody>
                        {commentList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        )
    }
}