import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";
class Explore extends Component {
    constructor(props) {
        super(props);
        this.state = {locations: [],countries:[],types:[], error: false,allCountries:false,allTypes:false};
        this.handleAddCountry = this.handleAddCountry.bind(this);
        this.handleAddType = this.handleAddType.bind(this);
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
        fetch('http://localhost:8080/explore/', requestOptions)
            .then(async response => {
                if (response.status === 403 || response.status === 401) {
                    this.setState({error: true});
                } else {
                    this.setState({error: false});
                    const d = await response.json();
                    this.setState({locations: d.locations});
                    this.setState({countries: d.countries});
                    this.setState({types: d.types});
                    console.log(d);
                }
            })
    }
    clickLink(loc){
        localStorage.setItem("location",JSON.stringify(loc));
        console.log(localStorage.getItem("location"))
    }
    handleAddCountry(event) {
        let e = document.getElementById("countries");
        let value = e.options[e.selectedIndex].value;
        console.log(value)
        if (value !== "ALL") {
            this.setState({country: value});
            this.setState({allCountries: false});
        }else{
            this.setState({allCountries: true});
        }
    }
    handleAddType(event) {
        let e = document.getElementById("types");
        let value = e.options[e.selectedIndex].value;
        console.log(value)
        if (value !== "ALL") {
            this.setState({type: value}, () => {
                console.log(this.state.type);
            });
            this.setState({allTypes: false});
        }else{
            this.setState({allTypes: true});
        }
    }

    render() {
        if (this.state.error) {
            return <Navigate to={"/accessdenied"}/>
        }
        return (
            <div className="App">
                <header className="header">
                    <h1>Explore</h1>
                    <div className="input">
                        <label>Country: </label>
                        <select id="countries" onChange={this.handleAddCountry}>
                            {this.state.countries?.map(c => {
                                return <option value={c.name} >{c.name}</option>;
                            })}
                        </select>
                        <label>Types: </label>
                        <select id="types" onChange={this.handleAddType}>
                        {this.state.types?.map(t => {
                            return <option value={t} >{t}</option>;
                        })}
                        </select>
                    </div>


                    <ul className="ul-no-bullets">
                        {this.state.locations.filter(l=>((this.state.allCountries || l.country===this.state.country) && (this.state.allTypes || l.locationType===this.state.type))).map(l => {
                            return  <li className="li">
                                <div className="flip-card">
                                    <div className="flip-card-inner">
                                        <div className="flip-card-front">
                                            <img className="photo-square" src={window.location.origin + '/img/'+l.image} alt="Avatar" />
                                        </div>
                                        <div className="flip-card-back">
                                            <div className="text-card">Click to view</div>
                                            <Link className="link" onClick={this.clickLink.bind(null,l)} to="/viewlocation">{l.name}</Link>
                                        </div>
                                    </div>
                                </div>

                                    </li>
                        })}
                    </ul>

                </header>
            </div>
        )
    }

}
export default Explore;
