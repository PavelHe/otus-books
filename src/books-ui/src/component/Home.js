import React, {Component} from 'react';
import '../App.css';
import AppNavbar from "./AppNavbar";

export default class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <div style={{
                    top: '50%',
                    left: '50%',
                    position: 'absolute',
                    margin: '-125px 0 0 -125px'
                }}>
                    <h1>Books Library Application</h1>
                </div>
            </div>
        );
    }

}