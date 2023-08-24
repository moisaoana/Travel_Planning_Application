import './App.css';
import React, {Component} from "react";

class AccessDenied extends Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {

    }

    render() {
        return (
            <div>
                <header className="error">
                    <h1 className="title">Access Denied!</h1>
                    <h3>You don't have access to this page.</h3>
                </header>
            </div>
        )
    }

}
export default AccessDenied;
