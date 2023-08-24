import './App.css';
import React, {Component} from "react";
import {Link} from 'react-router-dom'

class Register extends Component {

    constructor(props) {
        super(props);
        this.state = {firstName: '',lastName: '',username: '',password: '',confpassword: '',type:0,response:'', email:''};
        this.createAcc= this.createAcc.bind(this);
        this.handleChangeFirst = this.handleChangeFirst.bind(this);
        this.handleChangeLast = this.handleChangeLast.bind(this);
        this.handleChangeUsername = this.handleChangeUsername.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeConfPassword = this.handleChangeConfPassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeType1 = this.handleChangeType1.bind(this);
        this.handleChangeEmail = this.handleChangeEmail.bind(this);

    }
    async componentDidMount() {
    }
    createAcc(){

    }
    handleChangeFirst(event) {
        this.setState({firstName: event.target.value});
    }
    handleChangeLast(event) {
        this.setState({lastName: event.target.value});
    }
    handleChangeUsername(event) {
        this.setState({username: event.target.value});
    }
    handleChangePassword(event) {
        this.setState({password: event.target.value});
    }
    handleChangeConfPassword(event) {
        this.setState({confpassword: event.target.value});
    }
    handleChangeType1(event) {
        //this.setState({type: 1});
        let e = document.getElementById("typeSelect");
        let value = e.options[e.selectedIndex].value;
        this.setState({type: value});
        console.log(value)
    }
    handleChangeEmail(event) {
        this.setState({email: event.target.value});
    }


    handleSubmit(event) {
        if (this.state.password!==this.state.confpassword) {
            alert("Passwords don't match!");
            event.preventDefault();
        }
        else {
            event.preventDefault();
            console.log(this.state.type);
            const data = {
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                username: this.state.username,
                password: this.state.password,
                type: this.state.type,
                email: this.state.email
            }
            const requestOptions = {
                method: 'POST',
                headers:{ 'Content-Type': 'application/json'},
                body: JSON.stringify(data)
            };
            fetch('http://localhost:8080/register',requestOptions)
                .then(response =>{
                    if(response.status===406){
                        alert("Username exists!");
                    }else if(response.status===400){
                        alert("Invalid email!");
                    }else{
                        alert("Account successfully created!");
                    }}
                )
        }

    }

    render() {
        return (
            <div className="Start">
                <header className="header">
                    <h1 className="title">Create an account</h1>
                    <form onSubmit={this.handleSubmit}>
                        <div className="input">
                            <label>First Name </label>
                            <input type="text" name="first" value={this.state.firstName} onChange={this.handleChangeFirst} required />
                        </div>
                        <div className="input">
                            <label>Last Name </label>
                            <input type="text" name="last" value={this.state.lastName} onChange={this.handleChangeLast} required />
                        </div>
                        <div className="input">
                            <label>Username</label>
                            <input type="text" name="username" value={this.state.username} onChange={this.handleChangeUsername} required />
                        </div>
                        <div className="input">
                            <label>Email</label>
                            <input type="text" name="email" value={this.state.email} onChange={this.handleChangeEmail} required />
                        </div>
                        <div className="input">
                            <label>Password</label>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleChangePassword} required />
                        </div>
                        <div className="input">
                            <label>Confirm Password</label>
                            <input type="password" name="confpassword" value={this.state.confpassword} onChange={this.handleChangeConfPassword} required />
                        </div>
                        <div className="input">
                            <label>Account Type </label>
                            <select id="typeSelect" onChange={this.handleChangeType1}>
                                <option value="0" >Regular User</option>
                                <option value="1" >Admin</option>
                                <option value="2" >Creator</option>
                            </select>
                        </div>
                        <div align="right">
                            <input className="Reg-button" type="submit" value="Register" />
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

export default Register;
