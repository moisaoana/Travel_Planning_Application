import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";

class CreatorProfile extends Component {

    constructor(props) {
        super(props);
        this.state = {error:''}
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
        fetch('http://localhost:8080/creatorprofile',requestOptions)
            .then(async response=>{
                if(response.status===403 || response.status===401){
                    this.setState({error: true});
                }else {
                    this.setState({error: false});
                }
            })
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
                            <Link className="link" to="/addlocation">Add new location</Link>
                        </button>
                        <button className="button">
                            <Link className="link" to="/viewcreator">View my locations</Link>
                        </button>
                        <button className="button" onClick={this.logout}>
                            <Link className="link" to="/">Logout</Link>
                        </button>
                    </header>
                </div>
        )
    };
}

export default CreatorProfile;