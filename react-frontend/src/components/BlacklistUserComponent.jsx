import React, { Component } from 'react'
import BlacklistUserService from '../services/BlacklistUserService';
import { Table, Button, FormControl, Form} from 'react-bootstrap';

class BlacklistUserComponent extends Component {
    constructor(props) {
        super(props)

        this.changeBlacklistUsername = this.changeBlacklistUsername.bind(this);
        this.changeRegex = this.changeRegex.bind(this);

        this.state = {
            blacklistUsers: [],
            blacklistUsername: '',
            regex: false
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

    changeRegex = (event) => {
        this.setState({ regex: event.target.checked });
    }

    createBlacklistUser = () => {
        let blacklistUser = { username: this.state.blacklistUsername, regex: this.state.regex}
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
                                <th>Regex</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <td>
                                <FormControl placeholder="username to blacklist" value={this.state.blacklistUsername} onChange={this.changeBlacklistUsername} ></FormControl>
                            </td>
                            <td>
                                <Form.Check type="checkbox" onChange={this.changeRegex} />
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
                                                <Form.Check disabled={true} type="checkbox" defaultChecked={blacklistUser.regex} />
                                            </td>
                                            <td>
                                                <Button style={{ marginLeft: "10px" }} onClick={() => this.deleteBlacklistUser(blacklistUser.id)} className="btn btn-danger">Delete</Button>
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