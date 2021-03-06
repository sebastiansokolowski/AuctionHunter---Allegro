import React, { Component } from 'react'
import TargetService from '../services/TargetService'

class ListTargetComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            targets: []
        }
    }

    componentDidMount() {
        this.refreshTargets();
    }

    refreshTargets() {
        TargetService.getTargets().then((res) => {
            res.data.sort((a, b) => a.name.localeCompare(b.name));
            this.setState({ targets: res.data });
        }).catch((error) => {
            console.log(error);
        });
    }

    viewTarget(target) {
        var link = "https://allegro.pl/";
        var sellerLogin = target.parameters.find (parameter => parameter.name === "seller.login")
        if (sellerLogin) {
            link += `uzytkownik/${sellerLogin.value}/${target.categoryId}?`
        } else {
            link += `kategoria/${target.categoryId}?`;
        }
        link +=`&order=d`;
        if(target.phrase){
            link +=`&string=${target.phrase}`;
        }
        for (let parameter of target.parameters) {
            if (parameter.name === "price.from") {
                link += `&price.from=${parameter.value}`;
            }
            if (parameter.name === "price.to") {
                link += `&price.to=${parameter.value}`;
            }
            if (parameter.name === "sellingMode.format" && parameter.value === "BUY_NOW") {
                link += "&offerTypeBuyNow=1";
            }
            if (parameter.name === "searchMode" && parameter.value === "DESCRIPTIONS") {
                link += "&description=1";
            }
        }
        window.open(link);
    }

    editTarget(id) {
        this.props.history.push(`/edit/${id}`);
    }

    deleteTarget(id) {
        TargetService.deleteTarget(id).then(res => {
            this.refreshTargets();
        });
    }

    render() {
        return (
            <div>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Phrase</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.targets.map(
                                    target =>
                                        <tr key={target.id}>
                                            <td>{target.name}</td>
                                            <td>{target.phrase}</td>
                                            <td>
                                                <button style={{ marginLeft: "10px" }} onClick={() => this.viewTarget(target)} className="btn btn-info">View</button>
                                                <button style={{ marginLeft: "10px" }} onClick={() => this.editTarget(target.id)} className="btn btn-info">Update</button>
                                                <button style={{ marginLeft: "10px" }} onClick={() => this.deleteTarget(target.id)} className="btn btn-danger">Delete</button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default ListTargetComponent