import './App.css';
import './Admin.css';
import './PopUp.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";

class AdminProfile extends Component {

    constructor(props) {
        super(props);
        this.state = {error:'',country:'',countries:[],countryToDelete:'',users:[]}
        this.handleChangeCountry = this.handleChangeCountry.bind(this);
        this.addCountry = this.addCountry.bind(this);
        this.delCountry = this.delCountry.bind(this);
        this.handleChangeDeleteCountry = this.handleChangeDeleteCountry.bind(this);
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
        fetch('http://localhost:8080/adminprofile',requestOptions)
            .then(async response=>{
                if(response.status===403 || response.status===401){
                    this.setState({error: true});
                }else {
                    this.setState({error: false});
                    const d = await response.json();
                    console.log(d)
                    this.setState({countries: d.countries});
                    this.setState({users: d.users});
                }
            })
    }
     open(name) {
        // Declare all variables
        let i, tabcontent, tablinks;

        // Get all elements with class="tabcontent" and hide them
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        // Get all elements with class="tablinks" and remove the class "active"
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        // Show the current tab, and add an "active" class to the link that opened the tab
        document.getElementById(name).style.display = "block";
        //evt.currentTarget.className += " active";
    }

    handleChangeCountry(event) {
        this.setState({country: event.target.value});
    }
    addCountry(){
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: this.state.country
        };
        fetch('http://localhost:8080/addcountry',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Country added!");
                }else {
                    alert("Country already exists!");
                }
                return response.json();
            });

    }
    handleChangeDeleteCountry(event) {
        let e = document.getElementById("countries");
        let value = e.options[e.selectedIndex].value;
        console.log(value)
        this.setState({countryToDelete: value});
    }
    delCountry(){
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'DELETE',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: this.state.countryToDelete
        };
        fetch('http://localhost:8080/deletecountry',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Country deleted!");
                }else {
                    alert("Error!");
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
            <div>
                <header className="header2">
                    <h1 className="title">Welcome,{localStorage.getItem("user")}!</h1>
                    <div className="tab">
                        <button className="tablinks" onClick={this.open.bind(null, 'Add country')}>Add country</button>
                        <button className="tablinks" onClick={this.open.bind(null, 'Delete country')}>Delete country</button>
                        <button className="tablinks" onClick={this.open.bind(null, 'View users')}>View users</button>
                        <button className="tablinks" onClick={this.logout}> <Link className="link" to="/">Logout</Link></button>
                    </div>

                    <div id="Add country" className="tabcontent">
                        <h3>Add Country</h3>
                        <textarea placeholder="Enter a new country.." name="country" value={this.state.country} onChange={this.handleChangeCountry} required/>
                        <div>
                        <button type="button" className=" form-container btn" onClick={this.addCountry}>Add</button>
                        </div>
                    </div>

                    <div id="Delete country" className="tabcontent">
                        <h3>Delete country</h3>
                        <select id="countries" onChange={this.handleChangeDeleteCountry}>
                            {this.state.countries?.map(c => {
                                return <option value={c.name} >{c.name}</option>;
                            })}
                        </select>
                        <div>
                            <button type="button" className=" form-container btn" onClick={this.delCountry}>Delete</button>
                        </div>
                    </div>

                    <div id="View users" className="tabcontent">
                        <h3>View users</h3>
                        <ul>
                            {this.state.users?.map(u => {
                                return <li >{u}</li>;
                            })}
                        </ul>
                    </div>

                </header>
            </div>
        )
    };
}

export default AdminProfile;