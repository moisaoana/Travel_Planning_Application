import './App.css';
import './PopUp.css';
import './Grid.css';
import React, {Component} from "react";
import {Link, Navigate} from 'react-router-dom'


class ViewPlanner extends Component {
    constructor(props) {
        super(props);
        this.state = {planner: null, error: false,inviteUser:'',day:'',activity:'',start:0,end:0};
        this.closeForm = this.closeForm.bind(this);
        this.openForm = this.openForm.bind(this);
        this.invite = this.invite.bind(this);
        this.handleChangeInviteUser = this.handleChangeInviteUser.bind(this);
        this.handleChangeDay = this.handleChangeDay.bind(this);
        this.closeFormAddDay = this.closeFormAddDay.bind(this);
        this.openFormAddDay= this.openFormAddDay.bind(this);
        this.addDay = this.addDay.bind(this);
        this.closeFormAddActivity = this.closeFormAddActivity.bind(this);
        this.openFormAddActivity= this.openFormAddActivity.bind(this);
        this.handleChangeActivity = this.handleChangeActivity.bind(this);
        this.handleChangeStart = this.handleChangeStart.bind(this);
        this.handleChangeEnd = this.handleChangeEnd.bind(this);
        this.addActivity = this.addActivity.bind(this);
        this.check = this.check.bind(this);
    }

    async componentDidMount() {
        console.log(JSON.parse(localStorage.getItem("planner")))
        this.setState({planner: JSON.parse(localStorage.getItem("planner"))},() => {
        });
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'GET',
            headers:{   'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('http://localhost:8080/viewplanner',requestOptions)
            .then(async response=>{
                if(response.status===403 || response.status===401){
                    this.setState({error: true});
                }else {
                    this.setState({error: false});
                }
            })
        let elem = document.getElementsByClassName("column");
        for (let i = 0; i < elem.length; i++) {
                elem[i].style.width = "50%";
        }

    }
    openForm() {
        document.getElementById("myForm").style.display = "block";
    }

    closeForm() {
        document.getElementById("myForm").style.display = "none";
    }
    openFormAddDay() {
        document.getElementById("dayForm").style.display = "block";
    }

    closeFormAddDay() {
        document.getElementById("dayForm").style.display = "none";
    }
    openFormAddActivity() {
        document.getElementById("activityForm").style.display = "block";
    }

    closeFormAddActivity() {
        document.getElementById("activityForm").style.display = "none";
    }
    invite(){
        const data = {
            username: this.state.inviteUser,
            planner: this.state.planner
        }
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/invite',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("User added!");
                }else {
                    alert("User is already invited!");
                }
                return response.json();
            });
    }
    handleChangeInviteUser(event) {
        this.setState({inviteUser: event.target.value});
    }

    handleChangeDay(event) {
        this.setState({day: event.target.value});
    }
    handleChangeActivity(event) {
        this.setState({activity: event.target.value});
    }
    handleChangeStart(event) {
        this.setState({start: event.target.value});
    }
    handleChangeEnd(event) {
        this.setState({end: event.target.value});
    }

    addDay(){
        const data = {
            day: this.state.day,
            planner: this.state.planner
        }
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/addDay',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Day added!");
                }else {
                    alert("Day is already added!");
                }
                return response.json();
            });

    }

    openPage(pageName) {
        localStorage.setItem("day",pageName)
        let i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");

        for (i = 0; i < tabcontent.length; i++) {
            console.log(tabcontent[i])
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablink");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].style.backgroundColor = "";
        }
        document.getElementById(pageName).style.display = "block";
    }
    addActivity(){
        const data = {
            activity: this.state.activity,
            startH: this.state.start,
            endH: this.state.end,
            day: localStorage.getItem("day"),
            planner: this.state.planner
        }
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/addActivity',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Activity added!");
                }else {
                    alert("Activity is already added!");
                }
                return response.json();
            });

    }
    check(name){
        let checkBox = document.getElementById("myCheck");
        let text = document.getElementById(name);
        console.log(name)
        if (checkBox.checked === true){
            text.style.textDecoration =  "line-through";
        } else {
            text.style.textDecoration = "none";
        }
    }

    render() {
        if (this.state.error) {
            return <Navigate to={"/accessdenied"}/>
        }
        return (
            <div >
                <header className="header2">
                    <h1 className="title">{this.state.planner?.name}</h1>
                    <button className="open-button button-pos-1" onClick={this.openForm}>Invite</button>
                    <div className="chat-popup chat-pos-1" id="myForm">
                        <div className="form-container">
                            <h1 className="txt">Invite user</h1>
                            <textarea placeholder="Enter username.." name="user" value={this.state.inviteUser} onChange={this.handleChangeInviteUser} required/>
                            <button type="button" className="btn" onClick={this.invite}>Invite</button>
                            <button type="button" className="btn cancel" onClick={this.closeForm}>Close</button>
                        </div>
                    </div>

                    <button className="open-button button-pos-2" onClick={this.openFormAddDay}>Add day</button>
                    <div className="chat-popup chat-pos-2" id="dayForm">
                        <div className="form-container">
                            <h1 className="txt">Add day</h1>
                            <textarea placeholder="Enter a date.." name="day" value={this.state.day} onChange={this.handleChangeDay} required/>
                            <button type="button" className="btn" onClick={this.addDay}>Add</button>
                            <button type="button" className="btn cancel" onClick={this.closeFormAddDay}>Close</button>
                        </div>
                    </div>

                    <button className="open-button button-pos-3" onClick={this.openFormAddActivity}>Add activity</button>
                    <div className="chat-popup chat-pos-3" id="activityForm">
                        <div className="form-container">
                            <h1 className="txt">Add activity</h1>
                            <textarea placeholder="Enter an activity.." name="activity" value={this.state.activity} onChange={this.handleChangeActivity} required/>
                            <textarea placeholder="Enter start hour.." name="start" value={this.state.start} onChange={this.handleChangeStart} required/>
                            <textarea placeholder="Enter end hour.." name="end" value={this.state.end} onChange={this.handleChangeEnd} required/>
                            <button type="button" className="btn" onClick={this.addActivity}>Add</button>
                            <button type="button" className="btn cancel" onClick={this.closeFormAddActivity}>Close</button>
                        </div>
                    </div>
                    <div>
                        {this.state.planner?.days.map(d => {
                            return <div>
                                <div id={d.date} className="tabcontent">
                                    <label><strong>{d.date}</strong></label>
                                    {d.activities?.map(a => {
                                        return <div>
                                        <label className="parent" id={a.name}>{a.startHour}-{a.endHour}: <strong>{a.name}</strong> </label>
                                            <input onClick={this.check.bind(null,a.name)} className="child" type="checkbox" id="myCheck" />
                                        </div>


                                    })}

                                </div>
                                    <button  onClick= {this.openPage.bind(null,d.date)} className="tablink" >{d.date}</button>
                                </div>
                        })}

                    </div>


                </header>
            </div>
        )
    }
}
export default ViewPlanner;
