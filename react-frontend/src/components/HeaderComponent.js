import React, { Component } from 'react'
import { Link } from "react-router-dom";

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {

        }
    }

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                        <span className="navbar-brand">Auction Hunter</span>
                        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                            <div className="navbar-nav">
                                <Link className="nav-item nav-link" to="/">Targets</Link>
                                <Link className="nav-item nav-link" to="/add">Add target</Link>
                            </div>
                        </div>
                    </nav>
                </header>
            </div>
        )
    }
}

export default HeaderComponent