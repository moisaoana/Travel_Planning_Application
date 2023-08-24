import './App.css';
import React, {Component} from "react";
import {Link} from 'react-router-dom'
class ViewPlanners extends Component {
    constructor(props) {
        super(props);
        this.state = {planners: [], error: false};
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
        fetch('http://localhost:8080/viewplanners/' + localStorage.getItem("user"), requestOptions)
            .then(async response => {
                if (response.status === 403 || response.status === 401) {
                    this.setState({error: true});
                } else {
                    this.setState({error: false});
                    const d = await response.json();
                    this.setState({planners: d});
                    console.log(d);
                }
            })
    }
    clickLink(p){
        localStorage.setItem("planner",JSON.stringify(p));
        console.log(localStorage.getItem("planner"))
    }

    render() {
        return (
            <div className="Start">
                <header className="header">
                    <h1 className="title">My planners</h1>
                    <ul>
                        {this.state.planners?.map(p => {
                            return  <div className="danger">
                                    <p><strong><Link className="link" onClick={this.clickLink.bind(null,p)} to="/viewplanner">{p.name}</Link></strong></p>
                                    <ul className="ul">
                                        {p.users.map(u => {
                                            return <li className="li">{u}</li>
                                        })}
                                    </ul>
                                    </div>
                        })}
                    </ul>
                </header>
            </div>
        )
    }
}
export default ViewPlanners;
