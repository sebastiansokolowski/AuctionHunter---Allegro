import React, { Component } from 'react'
import TargetService from '../services/TargetService';
import AllegroService from '../services/AllegroService';
import { Modal, Button, Form, FormGroup, FormLabel, FormControl, Row, Col } from 'react-bootstrap';

class CreateTargetComponent extends Component {
    constructor(props) {
        super(props)

        this.selectCategory = this.selectCategory.bind(this);
        this.changeName = this.changeName.bind(this);
        this.changePhrase = this.changePhrase.bind(this);
        this.changeCategoryId = this.changeCategoryId.bind(this);
        this.handleShowSelectCategoryModal = this.handleShowSelectCategoryModal.bind(this);
        this.handleCloseSelectCategoryModal = this.handleCloseSelectCategoryModal.bind(this);
        this.handleShowCategoryParametersModal = this.handleShowCategoryParametersModal.bind(this);
        this.handleCloseCategoryParametersModal = this.handleCloseCategoryParametersModal.bind(this);

        this.state = {
            id: this.props.match.params.id,
            name: '',
            phrase: '',
            categoryId: null,
            categoryName: '',
            parameters: [],
            //view
            categoryIdsHistory: [],
            categories: [],
            showSelectCategoryModal: false,
            categoryParameters: null,
            showCategoryParametersModal: false,
        }
    }

    componentDidMount() {
        this.refreshCategories(null);

        if (!this.state.id) {
            return
        } else {
            TargetService.getTarget(this.state.id).then((res) => {
                let target = res.data;
                this.setState({
                    name: target.name,
                    categoryId: target.categoryId,
                    phrase: target.phrase,
                    parameters: target.parameters
                });
                if (target.categoryId) {
                    this.refreshCategorParameters(target.categoryId)
                }
            });
        }
    }

    refreshCategories(categoryId) {
        if (!categoryId) {
            AllegroService.getCategory().then((res) => {
                this.setState({ categories: res.data.categories });
            }).catch((error) => {
                console.log(error);
            });
        } else {
            AllegroService.getCategoryByParentId(categoryId).then((res) => {
                this.setState({ categories: res.data.categories });
            }).catch((error) => {
                console.log(error);
            });
        }
    }

    refreshCategorParameters(categoryId) {
        if (categoryId) {
            AllegroService.getCategoryParameters(categoryId).then((res) => {
                this.setState({ categoryParameters: res.data.parameters });
            }).catch((error) => {
                console.log(error);
            });
        }
    }

    selectPrevCategory = (e) => {
        let categoryId = this.state.categoryIdsHistory.pop();
        this.setState({ categoryId: categoryId });
        this.setState({ parameters: [] });
        this.refreshCategories(categoryId);
        this.refreshCategorParameters(categoryId);
    }

    selectCategory = (event) => {
        let categoryId = event.target.value;
        let category = this.state.categories.find(function (category) {
            return category.id === categoryId;
        });
        this.setState({ categoryName: category.name });
        this.setState({ categoryId: categoryId });
        this.setState({ parameters: [] });
        this.handleCloseSelectCategoryModal();
        this.refreshCategorParameters(categoryId);
    }

    showCategoryChildren = (event) => {
        this.state.categoryIdsHistory.push(this.state.categoryId)
        let categoryId = event.target.value;
        this.setState({ categoryId: categoryId });
        this.setState({ parameters: [] });
        this.refreshCategories(categoryId);
        this.refreshCategorParameters(categoryId);
    }

    saveOrUpdateTarget = (e) => {
        console.log(e);
        let target = { name: this.state.name, categoryId: this.state.categoryId, phrase: this.state.phrase, parameters: this.state.parameters };

        if (!this.state.id) {
            TargetService.createTarget(target).then(res => {
                this.props.history.push('/');
            }).catch((error) => {
                console.log(error);
            });
        } else {
            TargetService.updateTarget(this.state.id, target).then(res => {
                this.props.history.push('/');
            }).catch((error) => {
                console.log(error);
            });
        }
    }

    changeName = (event) => {
        this.setState({ name: event.target.value });
    }

    changePhrase = (event) => {
        this.setState({ phrase: event.target.value });
    }

    changeCategoryId = (event) => {
        this.setState({ categoryId: event.target.value });
        this.setState({ categoryName: '' });
        if (event.target.value) {
            this.refreshCategorParameters(event.target.value);
        } else {
            this.setState({ categoryParameters: null });
        }
    }

    getParameterValue(parameterId) {
        return this.state.parameters.find(a => a.name === parameterId)?.value;
    }

    isParameterChecked(parameterId, dictionaryId) {
        return this.state.parameters.find(a => a.name === parameterId && a.value === dictionaryId);
    }

    changeParameters = (event) => {
        let parameter = {
            name: event.target.id,
            value: event.target.value
        }
        let newParameters = this.state.parameters;

        //remove old value
        if (event.target.type === "radio"
            || event.target.type === "text"
            || (event.target.type === "checkbox" && !event.target.checked)) {
            newParameters = newParameters.filter(a => a.name !== parameter.name);
        }
        //push new value
        if (parameter.value && parameter.value !== '') {
            newParameters.push(parameter);
        }

        this.setState({ parameters: newParameters });
    }

    cancel() {
        this.setState({ categoryIdsHistory: [] })
        this.props.history.push('/target');
    }

    //SelectCategoryModal

    handleCloseSelectCategoryModal() {
        this.setState({ showSelectCategoryModal: false });
    }

    handleShowSelectCategoryModal() {
        this.setState({ showSelectCategoryModal: true });
    }

    //CategoryParametersModal

    handleShowCategoryParametersModal() {
        this.setState({ showCategoryParametersModal: true });
    }

    handleCloseCategoryParametersModal() {
        this.setState({ showCategoryParametersModal: false });
    }

    getTitle() {
        if (!this.state.id) {
            return <h3 className="text-center">Add Target</h3>
        } else {
            return <h3 className="text-center">Update Target</h3>
        }
    }

    getParameters(parameter) {
        switch (parameter.type) {
            case 'INTEGER':
            case 'FLOAT':
            case 'STRING':
                return (
                    <FormGroup>
                        <FormLabel>{parameter.name}:</FormLabel>
                        {parameter.restrictions.range ?
                            <Row>
                                <Col>
                                    <FormControl id={parameter.id + ".from"} placeholder="From"
                                        defaultValue={this.getParameterValue(parameter.id + ".from")} onChange={this.changeParameters} />
                                </Col>
                                <Col>
                                    <FormControl id={parameter.id + ".to"} placeholder="To"
                                        defaultValue={this.getParameterValue(parameter.id + ".to")} onChange={this.changeParameters} />
                                </Col>
                            </Row>
                            :
                            <FormControl id={parameter.id}
                                defaultValue={this.getParameterValue(parameter.id)} onChange={this.changeParameters} />
                        }
                    </FormGroup>
                )
            case 'DICTIONARY':
                return (
                    <Form.Group>
                        <FormLabel>{parameter.name}:</FormLabel>
                        {parameter.dictionary.map(
                            dictionaryValue =>
                                parameter.restrictions.multipleChoices ?
                                    <Form.Check type="checkbox" id={parameter.id} label={dictionaryValue.value}
                                        defaultChecked={this.isParameterChecked(parameter.id, dictionaryValue.id)} value={dictionaryValue.id}
                                        onChange={this.changeParameters} />
                                    :
                                    <Form.Check type="radio" name={parameter.id} id={parameter.id} label={dictionaryValue.value}
                                        defaultChecked={this.isParameterChecked(parameter.id, dictionaryValue.id)} value={dictionaryValue.id}
                                        onChange={this.changeParameters} />
                        )
                        }
                        {!parameter.restrictions.multipleChoices &&
                            <Form.Check type="radio" name={parameter.id} id={parameter.id} label="None"
                                defaultChecked={!this.getParameterValue(parameter.id)} value={null} onChange={this.changeParameters} />
                        }
                    </Form.Group>
                )
            default:
                return 'null';
        }
    }

    render() {
        var lowPrice = {
            id: 'sellingMode.format',
            name: 'Rodzaj oferty',
            type: 'DICTIONARY',
            restrictions: {
                multipleChoices: true
            },
            dictionary: [
                {
                    id: 'BUY_NOW',
                    value: 'Kup teraz'
                },
                {
                    id: 'AUCTION',
                    value: 'Aukcja'
                },
                {
                    id: 'ADVERTISEMENT',
                    value: 'Ogłoszenie'
                }
            ]

        };

        var price = {
            id: 'price',
            name: 'Cena',
            type: 'FLOAT',
            restrictions: {
                range: true
            }
        };

        return (
            <div>
                <br></br>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {
                                this.getTitle()
                            }
                            <>
                                {/* showSelectCategoryModal */}
                                <Modal show={this.state.showSelectCategoryModal} onHide={this.handleCloseSelectCategoryModal}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Select category</Modal.Title>
                                    </Modal.Header>
                                    {this.state.categoryIdsHistory.length > 0 &&
                                        <Button variant="secondary" onClick={this.selectPrevCategory}>
                                            Back
                                        </Button>
                                    }
                                    <Modal.Body>
                                        {
                                            this.state.categories.map(
                                                category =>
                                                    <div className="m-1">
                                                        <Button className="w-75" variant="outline-primary" value={category.id} onClick={this.selectCategory}>
                                                            {category.name}
                                                        </Button>
                                                        {!category.leaf &&
                                                            <Button className="w-25" variant="outline-primary" value={category.id} onClick={this.showCategoryChildren}>
                                                                &gt;
                                                            </Button>
                                                        }
                                                    </div>
                                            )
                                        }
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={this.handleCloseSelectCategoryModal}>
                                            Close
                                        </Button>
                                    </Modal.Footer>
                                </Modal>
                                {/* showCategoryParametersModal */}
                                <Modal show={this.state.showCategoryParametersModal} onHide={this.handleCloseCategoryParametersModal}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Category parameters</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        {this.state.categoryParameters &&
                                            this.state.categoryParameters.map(
                                                categoryParameter =>
                                                    <>
                                                        {
                                                            this.getParameters(categoryParameter)
                                                        }
                                                    </>
                                            )
                                        }
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={this.handleCloseCategoryParametersModal}>
                                            Close
                                        </Button>
                                    </Modal.Footer>
                                </Modal>
                            </>
                            <div className="card-body">
                                <Form>
                                    <FormGroup>
                                        <FormLabel>Name:</FormLabel>
                                        <FormControl value={this.state.name} onChange={this.changeName}></FormControl>
                                    </FormGroup>
                                    <FormGroup>
                                        <FormLabel>Phrase:</FormLabel>
                                        <FormControl value={this.state.phrase} onChange={this.changePhrase}></FormControl>
                                    </FormGroup>
                                    <FormGroup>
                                        <FormLabel>Category: {this.state.categoryName}</FormLabel>
                                        <FormControl value={this.state.categoryId} onChange={this.changeCategoryId}></FormControl>
                                        <Button className="m-1" onClick={this.handleShowSelectCategoryModal} size="lg" block>
                                            Select category
                                        </Button>
                                        {this.state.categoryParameters &&
                                            <FormGroup>
                                                <Button className="m-1" onClick={this.handleShowCategoryParametersModal} size="lg" block>
                                                    Show parameters
                                                </Button>
                                            </FormGroup>
                                        }
                                    </FormGroup>
                                    {
                                        this.getParameters(price)
                                    }
                                    {
                                        this.getParameters(lowPrice)
                                    }
                                    <Button variant="success" onClick={this.saveOrUpdateTarget}>
                                        Save
                                    </Button>
                                    <Button variant="danger" onClick={this.cancel.bind(this)} style={{ marginLeft: "10px" }}>
                                        Cancel
                                    </Button>
                                </Form>
                            </div>
                        </div>
                    </div>
                </div>
            </div >
        )
    }
}

export default CreateTargetComponent