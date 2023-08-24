import './App.css';
import React, {Component} from "react";
import {Link} from 'react-router-dom'
class StartPage extends Component {
    constructor(props) {
        super(props);
        this.clickRegister= this.clickRegister.bind(this);
        this.clickLogin= this.clickLogin.bind(this);
    }

    async componentDidMount() {
    }
    clickRegister(){
    }
    clickLogin(){

    }
    render() {
        return (
            <div className="Start">
                <header className="header">
                    <h1 className="title">Welcome to Map Out!</h1>
                    <button className="button">
                        <Link className="link" to="/register">Register</Link>
                    </button>
                    <button className="button">
                        <Link className="link" to="/login">Login</Link>
                    </button>
                </header>
            </div>
        )
    }
}
export default StartPage;
