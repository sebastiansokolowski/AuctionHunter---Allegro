import React, { Component } from 'react'
import BlacklistUserService from '../services/BlacklistUserService';
import { Table, Button, FormControl } from 'react-bootstrap';

class BlacklistUserComponent extends Component {
    constructor(props) {
        super(props)

        this.changeBlacklistUsername = this.changeBlacklistUsername.bind(this);

        this.state = {
            blacklistUsers: [],
            blacklistUsername: ''
        }
    }

    componentDidMount() {
        this.refreshBlacklistUsers();
    }

    refreshBlacklistUsers() {
        BlacklistUserService.getBlacklistUsers().then((res) => {
            res.data.sort((a, b) => a.username.localeCompare(b.username));
            this.setState({ blacklistUsers: res.data });
        }).catch((error) => {
            console.log(error);
        });
    }

    deleteBlacklistUser(id) {
        BlacklistUserService.deleteBlacklistUser(id).then(res => {
            this.refreshBlacklistUsers();
        });
    }

    changeBlacklistUsername = (event) => {
        this.setState({ blacklistUsername: event.target.value });
    }

    createBlacklistUser = () => {
        let blacklistUser = { username: this.state.blacklistUsername }

        BlacklistUserService.createBlacklistUser(blacklistUser).then(res => {
            this.refreshBlacklistUsers();
            this.setState({ blacklistUsername: "" });
        }).catch((error) => {
            console.log(error);
        });
    }

    render() {
        return (
            <div>
                <div className="container">
                    <Table className="table">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <td>
                                <FormControl placeholder="username to blacklist" value={this.state.blacklistUsername} onChange={this.changeBlacklistUsername} ></FormControl>
                            </td>
                            <td>
                                <Button style={{ marginLeft: "10px" }} onClick={this.createBlacklistUser}>
                                    Add
                            </Button>
                            </td>
                            {
                                this.state.blacklistUsers.map(
                                    blacklistUser =>
                                        <tr key={blacklistUser.id}>
                                            <td>{blacklistUser.username}</td>
                                            <td>
                                                <button style={{ marginLeft: "10px" }} onClick={() => this.deleteBlacklistUser(blacklistUser.id)} className="btn btn-danger">Delete</button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </Table>
                </div>
            </div>
        )
    }
}

export default BlacklistUserComponent