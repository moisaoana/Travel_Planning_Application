import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from 'react-router-dom'

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {username: '',password: '',type:'', isLoggedIn:false};
        this.handleChangeUsername = this.handleChangeUsername.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    async componentDidMount() {
        //const response = await fetch('/login');
        //const body = await response.json();
    }

    handleChangeUsername(event) {
        this.setState({username: event.target.value});
    }
    handleChangePassword(event) {
        this.setState({password: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = {
            username: this.state.username,
            password: this.state.password,
        }
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/login',requestOptions)
            .then(async r=>{
                if (r.ok) {
                    const d=await r.json();
                    localStorage.setItem("jwt", d.accessToken);
                    console.log(localStorage.getItem("jwt"))
                    this.setState({type: d.role});
                    console.log(this.state.type)
                    this.setState({isLoggedIn: true});
                    alert("Successful login!");
                }else{
                    alert("Incorrect username or password!");
                }
        })
    }

    render() {
        if (this.state.isLoggedIn) {
            localStorage.setItem('user',this.state.username );
            localStorage.setItem('type',this.state.type);
            if(this.state.type==="ROLE_USER"){
                return <Navigate to={"/userprofile"}/>
            }else if(this.state.type==="ROLE_ADMIN"){
                return <Navigate to={"/adminprofile"}/>
            }else{
                return <Navigate to={"/creatorprofile"}/>
            }
        }
        return (
            <div className="Start">
                <header className="header">
                    <h1 className="title">Login</h1>
                    <form onSubmit={this.handleSubmit}>
                        <div className="input">
                            <label>Username</label>
                            <input type="text" name="username" value={this.state.username} onChange={this.handleChangeUsername} required />
                        </div>
                        <div className="input">
                            <label>Password</label>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleChangePassword} required />
                        </div>
                        <div align="right">
                            <input className="Reg-button" type="submit" value="Login" />
                            <button className="Reg-button">
                                <Link className="link" to="/">Back</Link>
                            </button>
                        </div>
                    </form>
                </header>
            </div>
        )
    };
}

export default Login;
