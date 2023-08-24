import './App.css';
import React, {Component} from "react";
import {Link, Navigate} from "react-router-dom";
import ReactStars from "react-rating-stars-component";
import JsPDF from 'jspdf';
import html2canvas from "html2canvas";


class ViewLocation extends Component {
    constructor(props) {
        super(props);
        this.state = {loc:null,error:false,img:'',score:0,review:''};
        this.handleChangeReview=this.handleChangeReview.bind(this);
        this.giveReview=this.giveReview.bind(this);
        this.addToWishlist=this.addToWishlist.bind(this);
        this.delete=this.delete.bind(this);
    }
    async componentDidMount() {
        console.log(JSON.parse(localStorage.getItem("location")))
        this.setState({loc: JSON.parse(localStorage.getItem("location"))},() => {
            console.log(this.state.loc.image);
            this.setState({img:this.state.loc.image});
        });
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'GET',
            headers:{   'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('http://localhost:8080/viewlocation',requestOptions)
            .then(async response=>{
                if(response.status===403 || response.status===401){
                    this.setState({error: true});
                }else {
                    this.setState({error: false});
                }
            })


    }
    ratingChanged = (newRating) => {
        console.log(newRating);
        this.setState({score:newRating},() => {
            console.log(this.state.score);
            const token = localStorage.getItem("jwt")
            const data = {
                location: this.state.loc,
                score: this.state.score,
            }
            const requestOptions = {
                method: 'PUT',
                headers:{ 'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`},
                body: JSON.stringify(data)
            };
            fetch('http://localhost:8080/viewlocation',requestOptions)
        });
    };
    handleChangeReview(event) {
        this.setState({review: event.target.value});
    }
    giveReview() {
        const data = {
            location: this.state.loc,
            user:localStorage.getItem("user"),
            review: this.state.review
        }
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/viewlocation',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Review added!");
                }else {
                    alert("Error adding review!");
                }
                return response.json();
            });


    }
    addToWishlist() {
        const data = {
            location: this.state.loc,
            user:localStorage.getItem("user"),
        }
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'POST',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/wishlist',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Added to wishlist!");
                }else {
                    alert("Already in wishlist!");
                }
                return response.json();
            });
    }
    delete(){
        const token = localStorage.getItem("jwt")
        const requestOptions = {
            method: 'DELETE',
            headers:{ 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`},
            body: this.state.loc.name
        };
        fetch('http://localhost:8080/deletelocation',requestOptions)
            .then(response =>  {
                if (response.ok) {
                    alert("Location deleted!");
                }else {
                    alert("Error!");
                }
                return response.json();
            });
    }

    render() {
        if (this.state.error) {
            return <Navigate to={"/accessdenied"}/>
        }
        return (
            <body>
            <div className="splitl lefts">
                <header className="header2">
                    <h1 className="title-loc">{this.state.loc?.name}</h1>
                    <img className="photo" src={window.location.origin + '/img/'+this.state.img} />
                    <b className="descr">{this.state.loc?.description}</b>
                    <div className="text"><strong>Address:</strong> {this.state.loc?.address}</div>
                    <div className="text"><strong>Hours:</strong> {this.state.loc?.hours}</div>
                    <div className="text"><strong>Country:</strong> {this.state.loc?.country}</div>
                    <div className="text"><strong>Category:</strong> {this.state.loc?.locationType}</div>
                    <div className="text"><strong>Score:</strong> {this.state.loc?.score}</div>

                    <label>Reviews:</label>
                    <ul className="ul-no-bullets">
                        {this.state.loc?.reviews.map(r => {
                            return  <li>
                                    <div className="callout">
                                        <div className="callout-header">{r.user}</div>
                                        <div className="callout-container">
                                            <p>{r.review}</p>
                                        </div>
                                    </div>
                                    </li>
                        })}
                    </ul>

                </header>
            </div>
            <div className="splitr rights">
                <header className="header2">
                    {(localStorage.getItem("type") === "ROLE_USER") > 0 &&
                    <label>Give a score</label>
                    }
                    {(localStorage.getItem("type")==="ROLE_USER") > 0 &&
                    <ReactStars
                        classNames="stars"
                        count={5}
                        onChange={this.ratingChanged.bind(null)}
                        size={24}
                        activeColor="#ffd700"
                    />
                    }
                    {(localStorage.getItem("type") === "ROLE_USER") > 0 &&
                    <label>Give a review</label>
                    }
                    {(localStorage.getItem("type") === "ROLE_USER") > 0 &&
                    <textarea name="review" value={this.state.review} onChange={this.handleChangeReview} required />
                    }
                    {(localStorage.getItem("type") === "ROLE_USER") > 0 &&
                    <button onClick={this.giveReview} className="rev-button">Review</button>
                    }
                    {(localStorage.getItem("type") === "ROLE_USER") > 0 &&
                    <button onClick={this.addToWishlist} className="rev-button">Add to wishlist</button>
                    }
                    {(localStorage.getItem("type") === "ROLE_CREATOR") > 0 &&
                    <button  onClick={this.delete} className="btn-trash">Delete</button>
                    }

                </header>
            </div>
            </body>

        )
    }

}
export default ViewLocation;
