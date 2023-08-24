import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter, Route, Routes} from "react-router-dom";
import './index.css';
import reportWebVitals from './reportWebVitals'
import StartPage from "./StartPage";
import Register from "./Register";
import Login from "./Login";
import AccessDenied from "./AccessDenied";
import CreatorProfile from "./CreatorProfile";
import AddLocation from "./AddLocation";
import ViewCreator from "./ViewCreator";
import ViewLocation from "./ViewLocation";
import UserProfile from "./UserProfile";
import Explore from "./Explore";
import Wishlist from "./Wishlist";
import ViewPlanners from "./ViewPlanners";
import ViewPlanner from "./ViewPlanner";
import AdminProfile from "./AdminProfile";

ReactDOM.render(
    <HashRouter>
        <Routes>
            <Route exact path="/" element={<StartPage />}/>
            <Route exact path="/register" element={<Register />}/>
            <Route exact path="/login" element={<Login />}/>
            <Route exact path="/accessdenied" element={<AccessDenied/>}/>
            <Route exact path="/creatorprofile" element={<CreatorProfile/>}/>
            <Route exact path="/addlocation" element={<AddLocation/>}/>
            <Route exact path="/viewcreator" element={<ViewCreator/>}/>
            <Route exact path="/viewlocation" element={<ViewLocation/>}/>
            <Route exact path="/userprofile" element={<UserProfile/>}/>
            <Route exact path="/explore" element={<Explore/>}/>
            <Route exact path="/wishlist" element={<Wishlist/>}/>
            <Route exact path="/viewplanners" element={<ViewPlanners/>}/>
            <Route exact path="/viewplanner" element={<ViewPlanner/>}/>
            <Route exact path="/adminprofile" element={<AdminProfile/>}/>
        </Routes>
    </HashRouter>
    ,
    document.getElementById('root')
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();