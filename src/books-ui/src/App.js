import React, {Component} from 'react';
import './App.css';
import GenreList from "./component/genre/GenreList";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {CookiesProvider} from "react-cookie";
import AuthorList from "./component/author/AuthorList";
import Home from "./component/Home";
import BookList from "./component/book/BookList";
import CommentList from "./component/comments/CommentList";
import GenreEdit from "./component/genre/GenreEdit";
import AuthorEdit from "./component/author/AuthorEdit";
import CommentEdit from "./component/comments/CommentEdit";
import BookEdit from "./component/book/BookEdit";

class App extends Component {
    render() {
        return (
            <CookiesProvider>
                <Router>
                    <Switch>
                        <Route exact path="/books" component={BookList}/>
                        <Route exact path='/genres' component={GenreList}/>
                        <Route exact path='/authors' component={AuthorList}/>
                        <Route exact path="/comments" component={CommentList}/>
                        <Route exact path="/" component={Home}/>
                        <Route exact path="/book/:id" component={BookEdit}/>
                        <Route exact path="/genre/:id" component={GenreEdit}/>
                        <Route exact path="/author/:id" component={AuthorEdit}/>
                        <Route exact path="/comment/:id" component={CommentEdit}/>
                    </Switch>
                </Router>
            </CookiesProvider>
        )
    }
}

export default App;
