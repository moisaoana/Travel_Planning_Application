import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";
class Wishlist extends Component {
    constructor(props) {
        super(props);
        this.state = {locations: [], error: false};
        // this.clickLink = this.clickLink.bind(this);
    }

    async componentDidMount() {
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('http://localhost:8080/wishlist/' + localStorage.getItem("user"), requestOptions)
            .then(async response => {
                if (response.status === 403 || response.status === 401) {
                    this.setState({error: true});
                } else {
                    this.setState({error: false});
                    const d = await response.json();
                    this.setState({locations: d});
                    console.log(d);
                }
            })
    }
    clickLink(loc){
        localStorage.setItem("location",JSON.stringify(loc));
        console.log(localStorage.getItem("location"))
    }

    render() {
        if (this.state.error) {
            return <Navigate to={"/accessdenied"}/>
        }
        return (
            <div className="App">
                <header className="header">
                    <h1>My wishlist</h1>
                    <ul>
                        {this.state.locations.map(l => {
                            return <li className="li"><Link className="link" onClick={this.clickLink.bind(null,l)} to="/viewlocation">{l.name}</Link></li>
                        })}
                    </ul>

                </header>
            </div>
        )
    }

}
export default Wishlist;
