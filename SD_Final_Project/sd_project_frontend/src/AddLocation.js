import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";
class AddLocation extends Component {
    constructor(props) {
        super(props);
        this.state = {name:'',address:'',description:'',hours:'',error:false,countries:[],country:'',types:[],type:'',image:null};
        this.handleAddLocation = this.handleAddLocation.bind(this);
        this.handleChangeName=this.handleChangeName.bind(this);
        this.handleChangeDescription=this.handleChangeDescription.bind(this);
        this.handleChangeAddress=this.handleChangeAddress.bind(this);
        this.handleChangeHours=this.handleChangeHours.bind(this);
        this.handleAddCountry = this.handleAddCountry.bind(this);
        this.handleAddType = this.handleAddType.bind(this);
        this.onImageChange=this.onImageChange.bind(this);
    }

    async componentDidMount() {
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'GET',
            headers:{   'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
            }
        };
        fetch('http://localhost:8080/addlocation',requestOptions)
            .then(async response=>{
                if(response.status===403 || response.status===401){
                    this.setState({error: true});
                }else {
                    this.setState({error: false});
                    const d = await response.json();
                    this.setState({countries: d.countries});
                    this.setState({types: d.types});
                    console.log(this.state.countries)
                    console.log(this.state.types)
                }
            })
    }

    handleChangeName(event) {
        this.setState({name: event.target.value});
    }
    handleChangeDescription(event) {
        this.setState({description: event.target.value});
    }
    handleChangeAddress(event) {
        this.setState({address: event.target.value});
    }
    handleChangeHours(event) {
        this.setState({hours: event.target.value});
    }
    handleAddCountry(event) {
        let e = document.getElementById("countries");
        let value = e.options[e.selectedIndex].value;
        console.log(value)
        this.setState({country: value});
    }
    handleAddType(event) {
        let e = document.getElementById("types");
        let value = e.options[e.selectedIndex].value;
        console.log(value)
        this.setState({type: value},() => {
            console.log(this.state.type);
        });

    }
    onImageChange(event){
        if (event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            this.setState({
                //image: URL.createObjectURL(img).blob()
                image: img.name
            });
            console.log(img.name)

        }
    }

    handleAddLocation(event) {
        event.preventDefault();
        const data = {
            name: this.state.name,
            address: this.state.address,
            hours: this.state.hours,
            description: this.state.description,
            country: this.state.country,
            locationType: this.state.type,
            image: this.state.image,
            creator_username: localStorage.getItem("user")
        }
        console.log(data.type)
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/addlocation',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Location added!");
                }else {
                    alert("Location already exists!");
                }
                return response.json();
            });


    }

    render() {

        if (this.state.error) {
            return <Navigate to={"/accessdenied"}/>
        }
        return (
            <div className="App">
                <header className="header">
                    <h1 className="title">Add a new location!</h1>
                    <form onSubmit={this.handleAddLocation}>
                        <div className="input">
                            <label>Name </label>
                            <textarea  name="name" value={this.state.name} onChange={this.handleChangeName} required />
                        </div>
                        <div className="input">
                            <label>Address </label>
                            <textarea name="address" value={this.state.address} onChange={this.handleChangeAddress} required />
                        </div>
                        <div className="input">
                            <label>Description </label>
                            <textarea name="description" value={this.state.description} onChange={this.handleChangeDescription} required />
                        </div>
                        <div className="input">
                            <label>Hours </label>
                            <textarea name="hours" value={this.state.hours} onChange={this.handleChangeHours} required />
                        </div >

                        <div className="input">
                            <label>Country: </label>
                        <select id="countries" onChange={this.handleAddCountry}>
                            {this.state.countries?.map(c => {
                                return <option value={c.name} >{c.name}</option>;
                            })}
                        </select>
                        </div>
                        <div className="input">
                            <label>Type: </label>
                            <select id="types" onChange={this.handleAddType}>
                                {this.state.types?.map(t => {
                                    return <option value={t} >{t}</option>;
                                })}
                            </select>
                        </div>
                        <div className="input">
                            <label>Upload a photo: </label>
                            <input type="file" name="myImage" onChange={this.onImageChange} />
                        </div>
                        <input className="Reg-button" type="submit" value="Add" />
                        <button className="Reg-button">
                            <Link className="link" to="/creatorprofile">Back</Link>
                        </button>
                    </form>
                </header>
            </div>
        )
    }

}
export default AddLocation;
