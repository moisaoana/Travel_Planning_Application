import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";

class UserProfile extends Component {

    constructor(props) {
        super(props);
        this.state = {error:'',name:''}
        this.handleChangeName=this.handleChangeName.bind(this);
        this.create=this.create.bind(this);
        this.logout=this.logout.bind(this);
    }
    async componentDidMount() {
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'GET',
            headers:{   'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('http://localhost:8080/userprofile',requestOptions)
            .then(async response=>{
                if(response.status===403 || response.status===401){
                    this.setState({error: true});
                }else {
                    this.setState({error: false});
                }
            })
    }
    handleChangeName(event) {
        this.setState({name: event.target.value});
    }
    create(){
        const data = {
            name: this.state.name,
            username: localStorage.getItem("user")
        }
        console.log(data.type)
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/userprofile',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Planner added!");
                }else {
                    alert("Planner already exists!");
                }
                return response.json();
            });

    }
    logout(){
        localStorage.setItem('jwt','')
        localStorage.setItem('user','')
        localStorage.setItem('type','');
        localStorage.setItem('location',null);
        localStorage.setItem('planner',null);
    }

    render() {
        if (this.state.error) {
            return <Navigate to={"/accessdenied"}/>
        }
        return (
            <div className="Start">
                <header className="header">
                    <h1 className="title">Welcome,{localStorage.getItem("user")}!</h1>
                    <button className="button">
                        <Link className="link" to="/explore">Explore</Link>
                    </button>
                    <button className="button">
                        <Link className="link" to="/wishlist">My wishlist</Link>
                    </button>
                    <button className="button">
                        <Link className="link" to="/viewplanners">My planners</Link>
                    </button>
                    <button className="button" onClick={this.logout}>
                        <Link className="link" to="/">Logout</Link>
                    </button>
                    <div className="callout">
                        <div className="callout-header">Create a new planner:</div>
                        <div className="callout-container">
                            <textarea  name="name" value={this.state.name} onChange={this.handleChangeName} required />
                            <button onClick={this.create} className="rev-button">Create</button>
                        </div>
                    </div>
                </header>
            </div>
        )
    };
}

export default UserProfile;